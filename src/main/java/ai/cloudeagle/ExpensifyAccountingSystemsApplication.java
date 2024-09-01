package ai.cloudeagle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Harish Sanjay
 */

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class ExpensifyAccountingSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensifyAccountingSystemsApplication.class, args);
	}

}
