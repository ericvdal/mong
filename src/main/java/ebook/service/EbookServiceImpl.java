package ebook.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;

import ebook.json.DetailEbookJson;
import ebook.json.EbookJson;
import ebook.json.SearchEbookListJSon;
import ebook.persistance.Ebook;
import ebook.persistance.EbookCategory;
import ebook.persistance.EbookDAO;
import ebook.persistance.EbookRepository;
import ebook.ws.client.WsEbookClient;

@Service
public class EbookServiceImpl implements EbookService {
	
	private static final int DELAY_REQUEST_TIME_IN_MS = 400;
	
	private static final Log LOG = LogFactory.getLog(EbookServiceImpl.class);
	
	private static final Integer NB_REQUEST = 990;
	
	private static final Integer MAX_PAGE = 100;
	
	@Value("#{ebookConfigProp[fileLocation]}")
	public String fileLocation;
	
	@Autowired
	WsEbookClient wsEbookClient;
	
	@Autowired
	private EbookRepository repository;

	@Autowired
	private EbookCategoryService ebookCategoryService;
	
	@Autowired
	private EbookResources ebookResources;
	
	@Autowired
	private EbookDAO ebookDao;
	
	private int nbQuery = 0;
	
	@Override
	public Ebook get(String id) throws InterruptedException {
		return repository.findOne(id);
	}
	
	@Override
	public boolean populateEbook(List<String> searchParameterList) throws InterruptedException, APILimitException {
		boolean result = false;
		
		EbookCategory ebookCategory = ebookCategoryService.getNextEbookCategory(searchParameterList);
		String searchParameter = ebookCategory.getCategory();
		int nbActualPage = ebookCategory.getNbActualPage();
		
		boolean isNext = isCategoryNotProcess(ebookCategory);
		
		while (isNext){
			isNext = processEbookSearch(searchParameter, nbActualPage);
			TimeUnit.MILLISECONDS.sleep(DELAY_REQUEST_TIME_IN_MS);
			nbActualPage++;
			ebookCategory = ebookCategoryService.getEbookCategory(searchParameter);
			if (isNext){
				isNext = isCategoryNotProcess(ebookCategory);
			}
		}
		
		
		System.out.println("we run " + nbQuery + " queries against WS");
		
		return result;
	}

	private boolean isCategoryNotProcess(EbookCategory ebookCategory) {
		boolean isNotProcessed = true;
		if (ebookCategory != null && ebookCategory.getNbActualPage() != null
				&& ebookCategory.getNbTotalPage() != null){
			if (ebookCategory.getNbTotalPage() < ( ebookCategory.getNbActualPage() + 10)){
				isNotProcessed = false;
			}
		}
		
		return isNotProcessed;
	}

	@Override
	public List<Ebook> getAll() throws InterruptedException {
		return repository.findAll();
	}
	
	@Override
	public List<Ebook> getAllOrder() throws InterruptedException {
		
	//	Criteria criteria=Criteria.where("tagName").is("water temperature");
		Query query = new Query();
	//	query.addCriteria(criteria);
		query.with(new Sort(Sort.Direction.ASC,"title")) ;   

		return repository.findByOrderByTitleAsc();
	}
	
	
	@Override
	public List<Ebook> getByCategory(EbookCategory category) throws InterruptedException {
		return repository.findByCategories(category.getCategory());
	}

	
	@Override
	public List<Ebook> getByCategoryOrderedByTile(EbookCategory category) throws InterruptedException {

		System.out.println("Sort By Ebook Type [Ascending] -----------------");
		
		Query query = new Query();
		String cat = category.getCategory();
		query.addCriteria(new Criteria().where("categories").is(cat));
		query.with(new Sort(Sort.Direction.ASC,"title")) ;   
		List<Ebook> ebooks = ebookDao.sortByQuery(query);
		
		for (Ebook ebook : ebooks) {
		    System.out.println(ebook);    
		}
		
		return ebooks;
	}
	
	private boolean processEbookSearch(String searchParameter, int numPage) throws InterruptedException, APILimitException {
		System.out.println("Numpage = " + numPage);
		boolean isNext;
		if (numPage <= MAX_PAGE){
			isNext = true;
		} else {
			isNext = false;
		}
		
		SearchEbookListJSon searchEbookListJson = wsEbookClient.getEbookList(searchParameter, numPage);
		if ("0".equals(searchEbookListJson.getTotal())){
			isNext = false;
		}
		nbQuery ++;
		
		if (searchEbookListJson != null && isNext){
			
			Integer p = searchEbookListJson.getPage();
			String pTotalEbook = searchEbookListJson.getTotal();
			Integer totalEbook = null;
			try {
				totalEbook = Integer.valueOf(pTotalEbook);
			}
			catch (NumberFormatException nfe){
				System.out.println(pTotalEbook);
				throw new APILimitException();
			}
			if (p == null || totalEbook == null || p>totalEbook)
				isNext = false;
			
			saveEbookList(searchEbookListJson.getBooks(), searchParameter);
			
			processCategory(searchEbookListJson, searchParameter);
			
			
		}else {
			System.out.println("no ebooks ");
		}
		return isNext;
	}

	private void saveEbookList(List<EbookJson> listEbookJson,String  searchParameter) throws InterruptedException {
		

		if (listEbookJson != null && !listEbookJson.isEmpty() && nbQuery<NB_REQUEST){
			for (EbookJson ebookJson:listEbookJson){
				String id = ebookJson.getId().toString();
				System.out.println("process id: " + ebookJson.getId() + ",  title: " + ebookJson.getTitle());
				
				if (!repository.exists(id)){
				
					DetailEbookJson detailEbook = wsEbookClient.getEbook(id);
					System.out.println("processEbookSearch insert id=" + id);
					LOG.info("********************");
					LOG.info("id: " + id);
					LOG.info("title: " + detailEbook.getTitle());
					
					nbQuery ++;
					TimeUnit.MILLISECONDS.sleep(DELAY_REQUEST_TIME_IN_MS);
					String description = detailEbook.getDescription();
					String image = detailEbook.getImage();
					String isbn = detailEbook.getIsbn();
					String subtitle = detailEbook.getSubTitle();
					String title = detailEbook.getTitle();
					String author = detailEbook.getAuthor();
					
					String year = detailEbook.getYear();
					String page = detailEbook.getPage();
					String publisher = detailEbook.getPublisher();
					String download = detailEbook.getDownload();
					List<String> categorieList = new ArrayList<String>();
					categorieList.add(searchParameter);
					Ebook ebook = new Ebook(id, title, subtitle, description, author, isbn, year, page, publisher, image, download, categorieList, false);
					repository.insert(ebook);
				}
				
				else{
					Ebook ebookUpdate = repository.findOne(id);
					if (ebookUpdate != null && ebookUpdate.updateCategorie(searchParameter)){
						repository.save(ebookUpdate);
					}	
				}
			}
		}
		
	}

	private void processCategory(SearchEbookListJSon searchEbookListJson, String category) throws InterruptedException {
	
		if (searchEbookListJson != null && (searchEbookListJson.getError() == null || ("0").equals(searchEbookListJson.getError()))){
			Integer nbTotalPage = new Integer(searchEbookListJson.getTotal());
			Integer nbActualPage = searchEbookListJson.getPage(); 
			
			if (nbActualPage != null && nbTotalPage != null){
				if (nbActualPage == 1){
					ebookCategoryService.insertEbookCategory(category, nbTotalPage);
				} else{
					ebookCategoryService.updateEbookCategory(category, nbActualPage);
				}
			}
		}
	}

	@Override
	public List<String> getListCategoryParam() throws IOException {
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
		 return searchList;
	}

	@Override
	public void saveFileEbook(Ebook ebook) throws InterruptedException, IOException {
		if (ebook.getTitle() == null){
			repository.delete(ebook);
		}
		else {
			File ebookFile = new File(fileLocation+ebook.getTitle().replace("/", "-").replace("?", "").replace(":", "")+".pdf");
			if (!ebook.getDownloaded() || ebook.getDownloaded() == null || !ebookFile.exists()){
				
				System.out.println(ebook.getTitle() +" need to be SAVED");
				
				
				
				if (!ebookFile.exists() && ebook.getTitle() != null 
						&& ebook.getDownload() != null){
					
					LocalDateTime localDateTimeStart = LocalDateTime.now();
					
					byte[] streamFile = wsEbookClient.loadPdfFile(ebook.getDownload());
					Files.write(  streamFile,ebookFile);
					
					ebook.setDownloaded(true);
					repository.save(ebook);
					
					LocalDateTime localDateTimeEnd = LocalDateTime.now();
					

					Duration  duration  = Duration .between(localDateTimeStart, localDateTimeEnd);

					long siz = streamFile.length;
					long durSec = duration.getSeconds();
					
					System.out.println("we saved " + ebookFile.toString() + " size: " + siz + "bytes in " + duration.getSeconds() + "seconds @ " +  siz/durSec/1024 + "kb/s");
									
				}
				else {
					ebook.setDownloaded(true);
					repository.save(ebook);
					System.out.println("file " + ebook.getTitle() +" already saved");
				}
			}			
		}

	}

	

	
	

	

}
