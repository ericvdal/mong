package ebook.ws.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ebook.json.SearchEbookListJSon;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:job-read-files.xml" })
public class WsEbookClientTest implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Autowired
	private WsEbookClient ebookClient;
	
	@Test
	public void test(){
		SearchEbookListJSon ebookList = ebookClient.getEbookList("java", 1);
		Assert.assertTrue(ebookList == null);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
