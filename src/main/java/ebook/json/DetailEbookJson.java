package ebook.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailEbookJson {

	@JsonProperty("ID")
	private Long id;
	
	@JsonProperty("Error")
	private String error;
	
	@JsonProperty("Time")
	private Double time;
	
	@JsonProperty("Title")
	private String title;
	

	@JsonProperty("SubTitle")
	private String subTitle;
	
	@JsonProperty("Description")
	private String description;

	@JsonProperty("Author")
	private String author;
	
	@JsonProperty("ISBN")
	private String isbn;
	
	@JsonProperty("Year")
	private String year;
	
	@JsonProperty("Page")
	private String page;
	
	@JsonProperty("Publisher")
	private String publisher;
	
	@JsonProperty("Image")
	private String image;
	
	@JsonProperty("Download")
	private String download;
	
	@JsonProperty("Downloaded")
	private Boolean downloaded;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @return the time
	 */
	public Double getTime() {
		return time;
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @return the download
	 */
	public String getDownload() {
		return download;
	}
	
	public Boolean getDownloaded() {
		return downloaded;
	}
}
