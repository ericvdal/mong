package ebook.persistance;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import ebook.persistance.mongo.order.OrderBy;

@Document(collection = "category")
public class EbookCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;

	@Indexed
	private String category;
	
	@OrderBy(value = "updateTime", order = Sort.Direction.ASC)
	@DateTimeFormat
	private java.util.Date lastTimeUpdate;
	
	private Integer nbTotalPage;
	
	private Integer nbActualPage;
	
	@SuppressWarnings("unused")
	private EbookCategory(){
		
	}
	
	public EbookCategory(String category, Integer nbTotalPage){
		this.category = category;
		this.nbTotalPage = nbTotalPage;
		this.lastTimeUpdate = new Timestamp((new java.util.Date().getTime()));
	}
	
	public String getCategory(){
		return category;
	}
	
	public Integer getNbActualPage(){
		return nbActualPage;
	}

	public Integer getNbTotalPage(){
		return nbTotalPage;
	}
	
	public EbookCategory updateLastTimeUpdate(){
		this.lastTimeUpdate = new Timestamp((new java.util.Date().getTime()));
		return this;
	}
	
	public void setUpdateActualPage(Integer nbActualPage){
		this.lastTimeUpdate = new Timestamp((new java.util.Date().getTime()));
		this.nbActualPage = nbActualPage;
	}
	
}
