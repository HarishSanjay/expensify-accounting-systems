package ai.cloudeagle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Contains bean definitions
 */
@Configuration
public class AppConfig {
	
	@Bean
	RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper mapper = JsonMapper.builder()
			    .addModule(new JavaTimeModule())
			    .build();
		return mapper;
	}
}
