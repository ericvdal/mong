package ebook.service;

import java.util.List;

import ebook.persistance.Ebook;

public interface EbookService {

	public boolean populateEbook(List<String> searchParameterList) throws InterruptedException, APILimitException;
	
	public List<Ebook> getAll() throws InterruptedException;
	
	public List<Ebook> getAllOrder() throws InterruptedException;
	
	public Ebook get(String id) throws InterruptedException;
	
}
