package com.impalapay.airtel.servlet.report.chart.bar;

import com.impalapay.airtel.cache.CacheVariables;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.impalapay.airtel.accountmgmt.session.SessionStatistics;
import com.impalapay.airtel.beans.geolocation.Country;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 * Generates a bar chart detailing the count of successful transaction
 * countries.
 * <p>
 * It is assumed that this class is only called when a user is logged in.
 * <p>
 * Copyright (c) Impalapay Ltd., Feb 12, 2015
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 *
 */
public class TransactionBarDay extends HttpServlet {

    final String CHART_TITLE = "Successful";
    final int CHART_WIDTH = 800;
    final int CHART_HEIGHT = 600;
    
    //public static final int DAY_COUNT = 7; // Number of days over which to display the graph
   
    private DefaultCategoryDataset dataset;
    private String username = "";
        
    private Cache statisticsCache;

    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        CacheManager mgr = CacheManager.getInstance();
        statisticsCache = mgr.getCache(CacheVariables.CACHE_STATISTICS_BY_USERNAME);
    }

    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream out = response.getOutputStream();

        username = request.getParameter("username");

        response.setContentType("image/png");
        ChartUtilities.writeChartAsPNG(out, getChart(), CHART_WIDTH, CHART_HEIGHT);
    }
    

    /**
     *
     * @param request
     * @param response
     * @throws ServletException, IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    

    /**
     *
     * @return {@link JFreeChart}
     */
    private JFreeChart getChart() {
    	 SessionStatistics statistics = new SessionStatistics();
         Element element;
         String dateStr;
         //Map<Country, Double> countryTransactionAmountDays = new HashMap<>();
         
         Map<Country, Integer> countryTransactionCount = new HashMap<>();
         dataset = new DefaultCategoryDataset();



         if ((element = statisticsCache.get(username)) != null) {
             statistics = (SessionStatistics) element.getObjectValue();
         }


         //countryTransactionAmountDays = statistics.getCountryTransactionAmountDays();
         countryTransactionCount = statistics.getCountryTransactionCountSuccess();


         //Map<Country, Double> countryTransactionAmount;
         Map<Country, Integer> countryTransactionSuccess;
         Iterator<Country> countryIter;
         Country country;
         //do {
             
             countryTransactionSuccess = countryTransactionCount;
              

             if (countryTransactionSuccess != null) {	// It is possible that on particular days the account has no top ups
                 countryIter = countryTransactionSuccess.keySet().iterator();

                 while (countryIter.hasNext()) {
                     country = countryIter.next();
                     dataset.addValue(countryTransactionSuccess.get(country),country.getName(), country.getCountrycode());
                   
                 }
             }

             //dateMidnightStart = dateMidnightStart.plus(Hours.hours(24));
             //numDays++;
         //} while (numDays < DAY_COUNT);

         JFreeChart chart = ChartFactory.createStackedBarChart(
                 CHART_TITLE + " Transaction Count", // chart title
                 "Countries", // domain axis label
                 "Count ", // range axis label
                 dataset, // data
                 PlotOrientation.VERTICAL, // orientation
                 true, // include legend
                 true, // tooltips?
                 false // URLs?
                 );
        
         // get a reference to the plot for further customisation...
         CategoryPlot plot = (CategoryPlot) chart.getPlot();
        /** 
         final CategoryItemRenderer renderer = new CustomBarRenderer(
        		new Paint[]{
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51),
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), 
                 		new Color(255,51,51), // Red colour for Airtel ?
                 		new Color(51,255,51), // Blue colour for Orange ?
                 		new Color(0,0,204) }); // Green colour for Safaricom ?
         
         plot.setRenderer(renderer);**/
         
         StackedBarRenderer barRenderer = ((StackedBarRenderer) plot.getRenderer());

         //add top up amount to the bar sections
         barRenderer.setBaseItemLabelGenerator(
                 new StandardCategoryItemLabelGenerator("{2}", new DecimalFormat("#,###")));
         barRenderer.setBaseItemLabelsVisible(true);
         
         return chart;
     }
}

/*
** Local Variables:
**   mode: java
**   c-basic-offset: 2
**   tab-width: 2
**   indent-tabs-mode: nil
** End:
**
** ex: set softtabstop=2 tabstop=2 expandtab:
**
*/