package ebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class EbookResources {

	@Value("classpath:ebook.properties")
	public Resource ebookResource;
	
	public Resource getEbookResource(){
		return ebookResource;
	}
}
