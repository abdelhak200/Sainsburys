package com.grocery.sainsburys.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//in this mode we can ignore to represent the attributes with the null value
@JsonInclude(Include.NON_NULL)
public class Total {

	private Double gross;
	private Double vat;
	
	public Double getGross() {
		return gross;
	}
	public void setGross(Double gross) {
		this.gross = gross;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}

}
