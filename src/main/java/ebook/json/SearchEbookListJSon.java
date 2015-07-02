package ebook.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchEbookListJSon {

	@JsonProperty("Error")
	private String error;
	

	@JsonProperty("Time")
	private Double time;
	

	@JsonProperty("Total")
	private String total;
	

	@JsonProperty("Page")
	private Integer page;
	

	@JsonProperty("Books")
	private List<EbookJson> books;


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
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}


	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}


	/**
	 * @return the books
	 */
	public List<EbookJson> getBooks() {
		return books;
	}
	
	@Override
	public String toString(){
		String s = "Error: " + error + "\n Total: "+ total + "\n page: " + page;
		
		return s;
	}
	
}
