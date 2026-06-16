package com.park.parking.util;

import java.util.List;

import lombok.Data;

public class Customer {
	
	private  Long customerId;
	String customerName = "Ranjith";
	private  String customerEmail;
	private  List<Long> mobileNos = List.of(9962921187l,9787343091l);
	
    public static final String APP_NAME  = "MyApplication";

	public Long getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public List<Long> getMobileNos() {
		return mobileNos;
	}

	
}
