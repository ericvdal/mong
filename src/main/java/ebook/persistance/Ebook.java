package ebook.persistance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ebook")
public class Ebook implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3035194403990830516L;


	@Id
    private String id;
	
	
    private String title;
    private String subtitle;
    private String description;
    private String author;
    private String ISBN;
    private String year;
    private String page;
    private String publisher;
    private String image;
    private String download;
    private List<String> categories;
    
    @SuppressWarnings("unused")
	private Ebook(){
    	
    }

    public boolean updateCategorie(String category){
    	boolean update = false;
    	if (categories == null){
    		categories = new ArrayList<String>();
    		categories.add(category);
    		update = true;
    	}
    	if (category != null && !categories.contains(category)){
    		categories.add(category);
    		update = true;
    	}
    	
    	return update;
    }
    
    public Ebook(String id, String title, String subtitle, String description,
			String author, String iSBN, String year, String page,
			String publisher, String image, String download, List<String> categories) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.description = description;
		this.author = author;
		this.ISBN = iSBN;
		this.year = year;
		this.page = page;
		this.publisher = publisher;
		this.image = image;
		this.download = download;
		this.categories = categories;
	}
    
    @Override
    public String toString() {
        return String.format(
                "Ebook[id=%s, title='%s', subtitle='%s', description='%s']",
                id, title, subtitle, description);
    }

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getDescription() {
		return description;
	}

	public String getAuthor() {
		return author;
	}

	public String getISBN() {
		return ISBN;
	}

	public String getYear() {
		return year;
	}

	public String getPage() {
		return page;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getImage() {
		return image;
	}

	public String getDownload() {
		return download;
	}

	public List<String> getCategories() {
		return categories;
	}
}
