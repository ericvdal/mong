package ebook.ws.client;

import ebook.json.DetailEbookJson;
import ebook.json.SearchEbookListJSon;

public interface WsEbookClient {
 
	public SearchEbookListJSon getEbookList(String parameter, Integer numPage);
	
	public DetailEbookJson getEbook(String id);
}
