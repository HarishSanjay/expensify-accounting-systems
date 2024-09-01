package ai.cloudeagle.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import ai.cloudeagle.dto.Expense;
import ai.cloudeagle.model.Transaction;
import ai.cloudeagle.model.Vendor;
import ai.cloudeagle.repo.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VendorServiceImpl.class);

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private VendorRepository vendorRepo;

	@Override
	public void saveVendorExpense(Expense expense) {
		if (Objects.isNull(vendorRepo.findByTransactionId(expense.getTransactionId()))) {
			Vendor vendor = vendorRepo.findByVendorName(expense.getVendorName()).orElse(new Vendor());

			if (ObjectUtils.isEmpty(vendor.get_id())) {
				vendor.setStartDate(expense.getCreatedDate());
				vendor.setVendorName(expense.getVendorName());
				vendor.setModifiedAt(LocalDateTime.now());
			}
			Transaction transaction = new Transaction();
			transaction.setTransactionId(expense.getTransactionId());
			transaction.setAmount(expense.getAmount());
			transaction.setCategory(expense.getCategory());
			transaction.setInsertedAt(LocalDateTime.parse(expense.getInsertedAt(), formatter));
			vendor.addTransaction(transaction);
			vendor = vendorRepo.save(vendor);
			LOGGER.info("Vendor saved with ID: " + vendor.get_id());
		} else {
			LOGGER.info("Transaction already exists...");
		}

	}

}
