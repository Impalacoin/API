package com.impalapay.airtel.servlet.highchart.test;


/**
 * 
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.impalapay.airtel.tests.highchart.BrowserShare;

/**
 * Test our {@link com.impalapay.airtel.persistence.accountmgmt.AccountStatusDAO}
 * <p>
 * Copyright (c) ImpalaPay LTD., November 05, 2014
 *
 * @author <a href="mailto:kmuli@impalapay.com">Kelvin Muli</a>
 * 
 */


 @WebServlet(name="dataServlet",value="/servlet/highchart/test/dataServlet")
public class DataServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        
        List<BrowserShare> resultList = getData();
        Gson gson = new Gson();
        String result = gson.toJson(resultList);//Turn into json data  
        
        PrintWriter out = response.getWriter();
        out.write(result);
        out.flush();
        out.close();
    }
    
    /**
     * Get Data
     */
    private List<BrowserShare> getData() {
        
        List<BrowserShare> resultList = new ArrayList<BrowserShare>();
        resultList.add(new BrowserShare("Kenya",23.55F));
        resultList.add(new BrowserShare("Nigeria",24.99F));
        resultList.add(new BrowserShare("Rwanda",44.13F));
        resultList.add(new BrowserShare("Burundi",0.49F));
        resultList.add(new BrowserShare("Ghana",1.63F));
        resultList.add(new BrowserShare("Tanzania",5.21F));
        return resultList;
    }


}