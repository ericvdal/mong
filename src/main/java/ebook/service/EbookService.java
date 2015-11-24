package ebook.service;

import java.io.IOException;
import java.util.List;

import ebook.persistance.Ebook;
import ebook.persistance.EbookCategory;

public interface EbookService {

	public boolean populateEbook(List<String> searchParameterList) throws InterruptedException, APILimitException;
	
	public List<Ebook> getAll() throws InterruptedException;
	
	public List<Ebook> getAllOrder() throws InterruptedException;

	public List<Ebook> getByCategory(EbookCategory category) throws InterruptedException;
	
	public Ebook get(String id) throws InterruptedException;
	
	public List<String> getListCategoryParam() throws IOException;
	

	public List<Ebook> getByCategoryOrderedByTile(EbookCategory category) throws InterruptedException;
	
	public void saveFileEbook(Ebook ebook)  throws InterruptedException, IOException;
	
}
