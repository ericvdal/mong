package ebook.ws.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ebook.json.DetailEbookJson;
import ebook.json.SearchEbookListJSon;

@Service
public class WsEbookClientImpl implements WsEbookClient {
	
	private static final String searchEbook = "http://it-ebooks-api.info/v1/search/";
	
	private static final String getEbook = "http://it-ebooks-api.info/v1/book/";

	RestTemplate restTemplate;
	private HttpEntity<String> request = null;
	
	private static HttpHeaders headers = null;
	private ObjectMapper objectMapper;
	
	@Autowired
	public WsEbookClientImpl(RestTemplate restTemplate, ObjectMapper objectMapper){
		this.headers = getHeader();
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
		
	}
	
	@Override
	public SearchEbookListJSon getEbookList(String parameter, Integer numPage) {
		String url = searchEbook+parameter+"/page/"+numPage;
		System.out.println("url = " + url);
		SearchEbookListJSon obj = (SearchEbookListJSon) restTemplate.getForObject(url, SearchEbookListJSon.class,"");
		System.out.println ("result: " + obj);
		return obj;
	}
	
	@Override
	public DetailEbookJson getEbook(String id) {
		String url = getEbook+id;
		System.out.println("url = " + url);
		DetailEbookJson obj = (DetailEbookJson) restTemplate.getForObject(getEbook+id, DetailEbookJson.class);
		return obj;
	}
		
	/**
	 * 
	 * @return specific httpHeader for Transcontinental
	 */
	private static HttpHeaders getHeader(){
		if (headers == null){
			headers = new HttpHeaders();
			headers.add("accept", MediaType.APPLICATION_JSON_VALUE);
		}
		return headers;
	}

	@Override
	public byte[] loadPdfFile(String url) {
		
		HttpHeaders newHeader = new HttpHeaders();
		newHeader.add(HttpHeaders.REFERER, "http://it-ebooks.info");
		HttpEntity requestEntity =new HttpEntity(newHeader);
		
		ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();

		List<MediaType> supportedApplicationTypes = new ArrayList<MediaType>();
		MediaType pdfApplication = new MediaType("application","octet-stream");
		supportedApplicationTypes.add(pdfApplication);

		byteArrayHttpMessageConverter.setSupportedMediaTypes(supportedApplicationTypes);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(byteArrayHttpMessageConverter);
		restTemplate.setMessageConverters(messageConverters);
		
		
		ResponseEntity<byte[]> streamFile = restTemplate.exchange(url, HttpMethod.GET, requestEntity, byte[].class);

		return streamFile.getBody();
	}


}
