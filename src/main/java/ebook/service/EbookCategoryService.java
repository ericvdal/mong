package ebook.service;

import java.util.List;

import ebook.persistance.EbookCategory;

public interface EbookCategoryService {

	public EbookCategory getNextEbookCategory(List<String> ebookCategoryList) throws InterruptedException;
	
	public boolean updateEbookCategory(String category, Integer nbActualPage) throws InterruptedException;
	
	public boolean insertEbookCategory(String category, Integer nbTotalPage) throws InterruptedException;
	
	public List<EbookCategory> getAll() throws InterruptedException;
	

	public EbookCategory getEbookCategory(String category) throws InterruptedException;
}
