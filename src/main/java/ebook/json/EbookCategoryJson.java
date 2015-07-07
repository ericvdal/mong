package ebook.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import ebook.persistance.EbookCategory;

public class EbookCategoryJson {

	@JsonProperty("category")
	private String category;
	
	public EbookCategoryJson(EbookCategory ebookCategory){
		
		category = ebookCategory.getCategory();
		
	}
	
	public EbookCategoryJson(String ebookCategory){
		
		category = ebookCategory;
		
	}
	
}
