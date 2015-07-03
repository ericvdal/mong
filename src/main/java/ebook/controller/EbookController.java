package ebook.controller;

import java.io.IOException;
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

import ebook.json.EbookCategoryJson;
import ebook.json.EbookJson;
import ebook.persistance.Ebook;
import ebook.persistance.EbookCategory;
import ebook.service.EbookCategoryService;
import ebook.service.EbookService;


@Controller
public class EbookController {

	private static final Log LOG = LogFactory.getLog(EbookController.class);
	
	private static final String GET_ALL_EBOOK = "/getall";
	
	private static final String GET_BY_CATEGORY = "/get_by_category/{category}";
	
	private static final String EBOOK = "/ebook/{id}";
	
	private static final String LIST_CATEGORY = "/category_list";
	
	@Autowired
	private EbookService ebookService;
	
	@Autowired
	private EbookCategoryService ebookCategoryService;
	
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
	
	@RequestMapping(value=GET_BY_CATEGORY, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<EbookJson> getEbookListByCategory(@PathVariable String category){
		LOG.debug("getEbookListByCategory");
		
		List<EbookJson> ebookJsonList = new ArrayList<EbookJson>();
		
		try {
			EbookCategory ebookcategory = ebookCategoryService.getEbookCategory(category);
			
			if (ebookcategory != null){
				List<Ebook> ebookList = ebookService.getByCategory(ebookcategory);
				
				if (ebookList != null){
					for (Ebook ebook:ebookList){
						EbookJson ebookJon = new EbookJson(ebook);
						ebookJsonList.add(ebookJon);
					}
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

	@RequestMapping(value=LIST_CATEGORY, method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK) 	
	public @ResponseBody List<EbookCategoryJson> getCategoryList() {

		LOG.debug("getCategoryList");
		List<String> categoryList = null;
		List<EbookCategoryJson> ebookCategoryJsonList = new ArrayList<EbookCategoryJson>();
		try {
			categoryList = ebookService.getListCategoryParam();
			if (categoryList != null){
				for (String cat : categoryList){
					ebookCategoryJsonList.add(new EbookCategoryJson(cat));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ebookCategoryJsonList;
		
	}
		

}
