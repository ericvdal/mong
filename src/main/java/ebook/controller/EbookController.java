package ebook.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ebook.json.EbookJson;
import ebook.persistance.Ebook;
import ebook.service.EbookService;


@Controller
public class EbookController {

	private static final Log LOG = LogFactory.getLog(EbookController.class);
	
	private static final String GET_ALL_EBOOK = "/getall";
	
	private static final String EBOOK = "/ebook/{id}";
	
	@Autowired
	private EbookService ebookService;
	
	@RequestMapping(value=GET_ALL_EBOOK, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)  
	@ResponseStatus(HttpStatus.OK) 	
	public @ResponseBody List<EbookJson> getEbookList(){

		LOG.debug("getEbookList");
		List<EbookJson> ebookJsonList = new ArrayList<EbookJson>();
		try {
			List<Ebook> ebookList = ebookService.getAll();
			
			if (ebookList != null){
				for (Ebook ebook:ebookList){
					EbookJson ebookJon = new EbookJson(ebook);
					ebookJsonList.add(ebookJon);
				}
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ebookJsonList;
	}
	
	@RequestMapping(value=EBOOK, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)  
	@ResponseStatus(HttpStatus.OK) 	
	public @ResponseBody EbookJson getEbook(
			@PathVariable String id){
		EbookJson ebookJon = null;
		LOG.debug("getEbook");
		try {
			Ebook ebook = ebookService.get(id);
			if (ebook != null){
				ebookJon = new EbookJson(ebook);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ebookJon;
	}
}
