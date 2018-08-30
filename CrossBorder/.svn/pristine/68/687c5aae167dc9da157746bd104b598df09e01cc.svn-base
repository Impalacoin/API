package com.impalapay.airtel.servlet.report.chart.pie;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;

import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;



public class Charts extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) 
throws ServletException, IOException {

doTestPieChart(request,response);
   }

   protected void doTestPieChart(HttpServletRequest request, 
                                 HttpServletResponse response)
      throws IOException, ServletException
   {
      OutputStream out = response.getOutputStream();

      try
      {
    	  DefaultCategoryDataset bardataset = new DefaultCategoryDataset();  
    	     bardataset.setValue(6,"Marks" ,"Aditi" );  
    	     bardataset.setValue(3,"Marks" ,"Pooja" );  
    	     bardataset.setValue(10,"Marks" ,"Ria" );  
    	     bardataset.setValue(5,"Marks" ,"Twinkle" );  
    	     bardataset.setValue(20,"Marks" ,"Rutvi" );  
    	     JFreeChart barchart = ChartFactory.createBarChart(  
    	         "Students-Marks",      //Title  
    	         "Students",             // X-axis Label  
    	         "Marks",               // Y-axis Label  
    	         bardataset,             // Dataset  
    	         PlotOrientation.VERTICAL,      //Plot orientation  
    	         false,                // Show legend  
    	         true,                // Use tooltips  
    	         false                // Generate URLs  
    	      );  
    	     barchart.getTitle().setPaint(Color.BLUE);    // Set the colour of the title  
    	     barchart.setBackgroundPaint(Color.BLACK);    // Set the background colour of the chart  
    	     CategoryPlot cp = barchart.getCategoryPlot();  // Get the Plot object for a bar graph  
    	     cp.setBackgroundPaint(Color.BLACK);       // Set the plot background colour  
    	     cp.setRangeGridlinePaint(Color.RED);      // Set the colour of the plot gridlines  

      }
      catch (Exception e)
      {
         System.out.println(e.toString());
      }
      finally
      {
         out.close();
      }

   }


}
