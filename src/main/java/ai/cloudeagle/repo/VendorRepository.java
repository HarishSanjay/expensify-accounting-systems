package ai.cloudeagle.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import ai.cloudeagle.model.Vendor;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String> {

	@Query("{ 'transactions.transactionId': ?0 }")
	public Optional<Vendor> findByTransactionId(String transactionId);
	
	public Optional<Vendor> findByVendorName(String vendorName);
}
