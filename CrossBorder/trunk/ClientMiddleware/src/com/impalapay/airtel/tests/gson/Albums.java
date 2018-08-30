package com.impalapay.airtel.tests.gson;

import java.util.ArrayList;
import java.util.List;

public class Albums {
	public String title;
	public String message;
	public List<String> errors = new ArrayList<String>();
	public String total;
	public int total_pages;
	public int page;
	public String limit;
	List<Dataset> dataset = new ArrayList<Dataset>();
}
