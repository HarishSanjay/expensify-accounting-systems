package ai.cloudeagle.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.cloudeagle.dto.Credentials;
import ai.cloudeagle.dto.ExpenseList;
import ai.cloudeagle.dto.Filters;
import ai.cloudeagle.dto.InputSettings;
import ai.cloudeagle.dto.OnReceive;
import ai.cloudeagle.dto.OutputSettings;
import ai.cloudeagle.dto.RequestJobDescription;
import ai.cloudeagle.services.VendorService;
import ai.cloudeagle.utils.ExpensifyType;

/**
 * Used to fetch vendor information periodically
 */
@Component
public class VendorJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendorJob.class);

	@Value("${expensify.url}")
	private String EXPENSIFY_URL;

	@Value("${expensify.templateFilePath}")
	private String filePath;

	@Value("${expensify.partnerUserId}")
	private String partnerUserId;

	@Value("${expensify.partnerUserSecret}")
	private String partnerUserSecret;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private VendorService vendorService;

	@Scheduled(initialDelay = 1 * 60 * 1000, fixedDelay = 6 * 60 * 60 * 1000) // Runs every 6 hours
	public void extractVendorData() {
		LOGGER.info("Fetching Vendor Report at: " + LocalDateTime.now());
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			String requestJson = createRequestForExport();
			LOGGER.debug("Request job description: " + requestJson);

			File file = new File(filePath);
			byte[] byteArray = Files.readAllBytes(file.toPath());

			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			map.add("requestJobDescription", requestJson);
			map.add("template", new String(byteArray, StandardCharsets.UTF_8));

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
			ResponseEntity<String> response = restTemplate.postForEntity(EXPENSIFY_URL, entity, String.class);

			LOGGER.info("Response: " + response.getBody());
			if (response.getStatusCode().value() == 200) {
				LOGGER.info("Initiating download process...");
				String fileName = response.getBody();
				requestJson = createRequestForDownload(fileName);
				LOGGER.debug("Request job description for download: " + requestJson);
				map = new LinkedMultiValueMap<>();
				map.add("requestJobDescription", requestJson);

				entity = new HttpEntity<>(map, headers);

				response = restTemplate.postForEntity(EXPENSIFY_URL, entity, String.class);

				LOGGER.debug("Response for download: " + response.getBody());
				ExpenseList list = mapper.readValue(response.getBody(), ExpenseList.class);
				list.getExpenses().stream().forEach(expense -> {
					LOGGER.info("Saving transaction id: " + expense.getTransactionId());
					vendorService.saveVendorExpense(expense);
				});
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

	}

	private String createRequestForExport() throws JsonProcessingException {
		LOGGER.info("Creating request JSON for export...");
		RequestJobDescription request = new RequestJobDescription();
		request.setType(ExpensifyType.FILE.toString());

		OnReceive onReceive = new OnReceive();
		onReceive.addResponse("returnRandomFileName");
		request.setOnReceive(onReceive);

		LOGGER.info("Creating inputSettings data...");
		InputSettings inputSettings = new InputSettings();
		inputSettings.setType(ExpensifyType.COMBINED_REPORT_DATA.toString());

		Filters filters = new Filters();
		filters.setStartDate(LocalDate.of(2024, 7, 25));
		filters.setEndDate(LocalDate.now());
		inputSettings.setFilters(filters);
		request.setInputSettings(inputSettings);

		LOGGER.info("Creating outputSettings data...");
		OutputSettings outputSettings = new OutputSettings();
		outputSettings.setFileExtension("json");
		request.setOutputSettings(outputSettings);

		Credentials credentials = new Credentials();
		credentials.setPartnerUserID(partnerUserId);
		credentials.setPartnerUserSecret(partnerUserSecret);
		request.setCredentials(credentials);

		String requestJson = mapper.writeValueAsString(request);
		return requestJson;
	}

	private String createRequestForDownload(String fileName) throws JsonProcessingException {
		RequestJobDescription request = new RequestJobDescription();
		request.setType(ExpensifyType.DOWNLOAD.toString());

		Credentials credentials = new Credentials();
		credentials.setPartnerUserID(partnerUserId);
		credentials.setPartnerUserSecret(partnerUserSecret);
		request.setCredentials(credentials);

		request.setFileName(fileName);
		request.setFileSystem("integrationServer");
		String requestJson = mapper.writeValueAsString(request);
		return requestJson;
	}
}
