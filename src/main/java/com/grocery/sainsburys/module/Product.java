package com.grocery.sainsburys.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//in this mode we can ignore to represent the attributes with the null value
@JsonInclude(Include.NON_NULL)
public class Product {

	private String title ; 
	private Integer kcal_per_100g ;
    private Double unit_price ;
    private String description ;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getKcal_per_100g() {
		return kcal_per_100g;
	}
	public void setKcal_per_100g(Integer kcal_per_100g) {
		this.kcal_per_100g = kcal_per_100g;
	}
	public Double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(Double unit_price) {
		this.unit_price = unit_price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
}
