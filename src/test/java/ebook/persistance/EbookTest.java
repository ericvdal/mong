package ebook.persistance;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ebook.service.APILimitException;
import ebook.service.EbookCategoryService;
import ebook.service.EbookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:job-read-files.xml" })
public class EbookTest implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;

	
	@Autowired
	private EbookRepository repository;
	
	@Autowired
	private EbookService ebookService;
	
	@Autowired
	private EbookCategoryService categoryService;
	
	@Test
	public void testSearch(){
		List<Ebook> ebookList = repository.findAll();
		Assert.assertTrue(ebookList == null || ebookList.isEmpty());
	}
	
	
	
	@Test
	public void testService(){
		boolean isServiceOk = true;
		int nbBookCountStart = 0;
		int nbBookCountEnd = 0;
		try {
			
			nbBookCountStart = ebookService.getAllOrder().size();
			
			List<String> searchList = ebookService.getListCategoryParam();
			/*
			String str="";
			List<String> searchList = new ArrayList<String>();
			Resource resource = ebookResources.getEbookResource();
			InputStream inputStream = resource.getInputStream();
			 try {
			        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			        if (inputStream!=null) {                         
			            while ((str = reader.readLine()) != null) { 
			            	if (!str.isEmpty()){
			            		searchList.add(str);
			            	}
			            }               
			        }
			    } finally {
			        try { inputStream.close(); } catch (Throwable ignore) {}
			    }
			*/
			while(true){ 
				isServiceOk = ebookService.populateEbook(searchList);
			}
		} catch (InterruptedException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
		catch (APILimitException ale){
			System.out.println("API Limit exception");
		}
		catch (Exception e){
			e.printStackTrace();
		}
		

		try {
			nbBookCountEnd = ebookService.getAllOrder().size();
			System.out.println ("loaded " +(nbBookCountEnd-nbBookCountStart) + " books");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertTrue(isServiceOk);
	}

	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Test
	public void getListDownload(){
		
		int nbBookFound = 0;
		
		try {
			java.util.Date date1= new java.util.Date();
			System.out.println("Tests begin" + new Timestamp(date1.getTime()));
			List<Ebook> ebookList = ebookService.getAllOrder();
			
			if (ebookList != null){
				for (Ebook ebook:ebookList){
					System.out.println(ebook.getTitle());
					nbBookFound++;
				}
			}

			java.util.Date date2= new java.util.Date();
			System.out.println("Tests end " + new Timestamp(date2.getTime()));
			
			System.out.println(nbBookFound + " ebooks Found in " + new Long(date2.getTime() - date1.getTime()) + " ms");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_getByCategory() {
		try {
			List<String> categoryParamList = ebookService.getListCategoryParam();
			
			if (categoryParamList != null){
				
				EbookCategory ebookCategory = null;
				
				while(ebookCategory == null){
					Double ran =  (Math.random() * categoryParamList.size());
					
					String randomCategory = categoryParamList.get(ran.intValue());
						
					ebookCategory = categoryService.getEbookCategory(randomCategory);
					
				}
				
		
				List<Ebook> ebookList = ebookService.getByCategory(ebookCategory);
				
				Assert.assertTrue(ebookList != null && ebookList.size() > 0);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		
	}
	
}
