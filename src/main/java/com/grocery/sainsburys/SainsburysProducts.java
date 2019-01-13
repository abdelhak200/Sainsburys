package com.grocery.sainsburys;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.grocery.sainsburys.module.Product;
import com.grocery.sainsburys.module.Total;

public class SainsburysProducts implements ISainsburysPrd{
	
	public final static Logger logger = Logger.getLogger(SainsburysProducts.class.getName());
	
	public static void main(String[] args) {

		//use this line in case you want run it using some IDE replace args[0] with url
		//String url = "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";
		
		SainsburysProducts sp = new SainsburysProducts();

		//run it using command shell arguments
		ArrayList<Product> arrayList = sp.getJsonArrayList(args[0]);
		String jsonString =  sp.getJsonString(arrayList);
		printToFile(jsonString);
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Product> getJsonArrayList(String url) {

		WebClient client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		ArrayList<Product> arrayList = new ArrayList<Product>();
		
		try {

			HtmlPage page = client.getPage(url);

			List<HtmlElement> products = (List<HtmlElement>) page.getByXPath("//ul[@class='productLister gridView']/li");

			if (products.isEmpty()) {

				logger.info("No products found!");

			} else {

				for (HtmlElement htmlProduct : products) {

					//get the title and price
					HtmlAnchor  title = ((HtmlAnchor) htmlProduct.getFirstByXPath(".//div//h3/a"));
					HtmlElement unit_price = ((HtmlElement) htmlProduct.getFirstByXPath(".//div//div//div/p[@class='pricePerUnit']"));

					// When product doesn't have any price, we set the price null
					String productPrice = unit_price == null ? null : unit_price.getFirstChild().asText();

					// Here get the HTML page by clicking in link
					HtmlPage linkPage = title.click();

					//get kcal_per_100g and description
					HtmlElement kcal_per_100g = ((HtmlElement) linkPage.getFirstByXPath("//table[@class='nutritionTable']/tbody/tr[2]/td[1]"));
					HtmlElement desc = ((HtmlElement) linkPage.getFirstByXPath("//div[contains(@class, 'memo') or contains(@class, 'productText')]/p"));

					// kcal_per_100g
					Integer kcal = kcal_per_100g == null ? null : new Integer(kcal_per_100g.asText().replace("kcal", ""));
					String description = desc == null ? null : desc.asText();

					Product product = new Product();
					product.setTitle(title == null ? null : title.asText());
					product.setKcal_per_100g(kcal);
					product.setUnit_price(productPrice == null ? null : new Double(productPrice.replace("£","")));
					product.setDescription(description);

					arrayList.add(product);

				}
			}

		} catch (FailingHttpStatusCodeException | IOException e) {
			logger.debug(e.getMessage(), e);
		} finally {

			if (client != null)
				client.close();
			logger.info("Client closed!");
		}

		return arrayList;
	}
	
	public String getJsonString(ArrayList<Product> arrayList){
		
		String jsonString = "";

		try {

			HashMap<String, Object> obj = new HashMap<String, Object>();
			obj.put("results", arrayList);
			obj.put("total", getTotal(arrayList));

			ObjectMapper mapper = new ObjectMapper();
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);

		} catch (JsonProcessingException e) {
			logger.debug(e.getMessage(), e);
		}

		return jsonString;
	}
	
	//write the content in the file
	private static void printToFile(String jsonString){
		try {
			
		    FileWriter fileWriter = new FileWriter("jsonOut.txt");
		    fileWriter.write(jsonString);
		    fileWriter.close();
		    
			 logger.info(jsonString);
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private Total getTotal(ArrayList<Product> arrayList){
		double gross = 0.0;

		for (Product prod : arrayList) {
			gross += prod.getUnit_price();
		}

		// calculate vat value (20%)
		double vat = (gross / 120) * 20;

		// gross and vat up to 2 decimal places
		vat = (double) Math.round(vat * 100) / 100;
		gross = (double) Math.round(gross * 100) / 100;

		Total tot = new Total();
		tot.setVat(vat == 0.0 ? null : vat);
		tot.setGross(gross == 0.0 ? null : gross);

		return tot;
	}
}
