package ebook.persistance;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service(value="ebookRepository")
public interface EbookRepository extends MongoRepository<Ebook, String>{

    public Ebook findByTitle(String title);
    
    public List<Ebook> findByCategories(String category);
    
	public List<Ebook> findByOrderByTitleAsc();
    
}
