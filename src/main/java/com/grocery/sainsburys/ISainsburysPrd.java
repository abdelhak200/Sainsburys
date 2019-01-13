package com.grocery.sainsburys;

import java.util.ArrayList;

import com.grocery.sainsburys.module.Product;

public interface ISainsburysPrd {

	public ArrayList<Product> getJsonArrayList(String url);
	public String getJsonString(ArrayList<Product> arrayList);
}
