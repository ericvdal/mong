package ebook.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EbookCategoryRepository extends MongoRepository<EbookCategory, String> {

	public EbookCategory findByCategory(String category);

	
	
}
