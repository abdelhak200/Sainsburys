package com.grocery.sainsburys;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SainsburysProductsTest {

	private WebClient webClient;
	 
	@Before
	public void init() throws Exception {
	    webClient = new WebClient();
	}
	 
	@After
	public void close() throws Exception {
	    webClient.close();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetJsonString() throws Exception {
		
		    webClient.getOptions().setCssEnabled(false);
		    webClient.getOptions().setJavaScriptEnabled(false);
		 
		    String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		    HtmlPage page = webClient.getPage(url);
		    String xpath = "//ul[@class='productLister gridView']/li";
		    
		    List<HtmlElement> htmlProduct = (List<HtmlElement>) page.getByXPath(xpath);
		    
		    Assert.assertTrue(htmlProduct.size() > 0);
		    
		    HtmlAnchor  link = (HtmlAnchor) htmlProduct.get(0).getFirstByXPath(".//div//h3/a");
		    HtmlPage postPage = link.click();
		 
		    HtmlElement descTd = (HtmlElement) postPage.getFirstByXPath("//table[@class='nutritionTable']/tbody/tr[2]/td[1]");
		  
		    Assert.assertTrue(descTd.asText().length() > 0);
	}

}
