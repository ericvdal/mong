package ebook.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import ebook.persistance.Ebook;

public class EbookJson {

	@JsonProperty("Error")
	private String error;
	

	@JsonProperty("Time")
	private Long time;
	
	
	@JsonProperty("ID")
	private Long id;
	

	@JsonProperty("Title")
	private String title;
	

	@JsonProperty("SubTitle")
	private String subTitle;
	

	@JsonProperty("Description")
	private String description;
	
	
	@JsonProperty("Image")
	private String image;
	
	
	@JsonProperty("isbn")
	private Long isbn;

	public EbookJson(){
		
	}

	public EbookJson(Ebook ebook){
		if (ebook != null){
			this.title = ebook.getTitle();
			this.description = ebook.getDescription();
			this.image = ebook.getImage();
			if (ebook.getISBN() != null){
				this.isbn =  new Long(ebook.getISBN());
			}
			if (ebook.getId() != null){
				this.id =  new Long(ebook.getId());
			}
			
			this.subTitle = ebook.getSubtitle();
		}
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * @return the subTitle
	 */
	public String getSubTitle() {
		return subTitle;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}


	/**
	 * @return the isbn
	 */
	public Long getIsbn() {
		return isbn;
	}


	public String getError() {
		return error;
	}


	public Long getTime() {
		return time;
	}
}
