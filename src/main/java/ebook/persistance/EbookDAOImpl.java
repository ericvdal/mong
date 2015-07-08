package ebook.persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class EbookDAOImpl implements EbookDAO {

	private static final String COLLECTION = "ebook";
	
	@Autowired
	 MongoTemplate mongoTemplate;

	@Override
	public List<Ebook> sortByQuery(Query query) {
		List<Ebook> ebooks = mongoTemplate.find(query, Ebook.class);
		System.out.println();
		System.out.println("Executed Query :- " + query);
		System.out.println();
		return ebooks;
	}
	
	
}
