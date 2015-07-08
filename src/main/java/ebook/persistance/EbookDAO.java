package ebook.persistance;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

public interface EbookDAO {
	
	public List<Ebook> sortByQuery(Query query);
}
