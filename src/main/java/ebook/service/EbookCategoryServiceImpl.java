package ebook.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebook.persistance.EbookCategory;
import ebook.persistance.EbookCategoryRepository;

@Service
public class EbookCategoryServiceImpl implements EbookCategoryService {

	@Autowired
	private EbookCategoryRepository categoryRepository;
	
	@Override
	public boolean updateEbookCategory(String category, Integer nbActualPage) throws InterruptedException {
		
		EbookCategory ebookCategory = categoryRepository.findByCategory(category);
		ebookCategory.setUpdateActualPage(nbActualPage);
		categoryRepository.save(ebookCategory);
		return false;
	}
	
	
	@Override
	public boolean insertEbookCategory(String category, Integer nbTotalPage) throws InterruptedException {
		
		EbookCategory newEbookCategory = new EbookCategory(category, nbTotalPage);
		newEbookCategory.setUpdateActualPage(1);
		categoryRepository.insert(newEbookCategory);
		return false;
	}

	@Override
	public List<EbookCategory> getAll() throws InterruptedException {
		return categoryRepository.findAll();
	}

	@Override
	public EbookCategory getNextEbookCategory(List<String> ebookCategoryList) throws InterruptedException {
		
		if (ebookCategoryList != null){
		
			List<EbookCategory> savedEbookCategories = getAll();
			List<String> saveEbookCategoriesString = new ArrayList<String>();
			for (EbookCategory savedEbookCategory: savedEbookCategories){
				saveEbookCategoriesString.add(savedEbookCategory.getCategory());
			}
			
			for(String ebookCategoryParam: ebookCategoryList){
				if (!saveEbookCategoriesString.contains(ebookCategoryParam)){
					EbookCategory ebookCategory = new EbookCategory(ebookCategoryParam, null);
					ebookCategory.setUpdateActualPage(1);
					return ebookCategory;
				}
			}	
		
			return savedEbookCategories.get(0);
		}	
		// default return if ebookCAtegoryList is empty
		return null;
	}


	@Override
	public EbookCategory getEbookCategory(String category)
			throws InterruptedException {
		return categoryRepository.findByCategory(category);
	}

	

}
