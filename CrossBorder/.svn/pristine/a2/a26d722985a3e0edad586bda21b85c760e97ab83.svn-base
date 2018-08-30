package com.impalapay.airtel.servlet.report.chart.pie;

import com.impalapay.airtel.accountmgmt.session.SessionStatistics;
import com.impalapay.airtel.cache.CacheVariables;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.impalapay.airtel.beans.geolocation.Country;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class Charts2 extends HttpServlet {

    final String CHART_TITLE = "Transaction Amount by Country";
    final int CHART_WIDTH = 400;
    final int CHART_HEIGHT = 400;
    
    private Cache statisticsCache;
    
    private String username="";
    
 

    
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
        
       username ="demo";
        //username = request.getParameter("username");

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
     * @return JFreeChart
     */
    private JFreeChart getChart() {
    	/**
    	
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Graphic Novels", 192);
        dataset.setValue("History", 125);
        dataset.setValue("Military Fiction", 236); 
        dataset.setValue("Mystery", 547); 
        dataset.setValue("Performing Arts", 210);
        dataset.setValue("Science, Non-Fiction", 70);
        dataset.setValue("Science Fiction", 989); 

   
     // create a chart...
        JFreeChart chart = ChartFactory.createPieChart(
                CHART_TITLE,
                dataset,
                true, // legend?
                true, // tooltips?
                false // URLs?
                );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setIgnoreZeroValues(true);
        plot.setIgnoreNullValues(true);

        return chart;
        **/
    	//second part
    	/**
    	 DefaultPieDataset dataset = new DefaultPieDataset();

         dataset.setValue("Graphic Novels", 192);
         dataset.setValue("History", 125);
         dataset.setValue("Military Fiction", 236); 
         dataset.setValue("Mystery", 547); 
         dataset.setValue("Performing Arts", 210);
         dataset.setValue("Science, Non-Fiction", 70);
         dataset.setValue("Science Fiction", 989); 
         **/
    
        HashMap<String, Double> countHash = new HashMap<>();
        Iterator<String> keyIter;
        String key;
        Element element;
        SessionStatistics statistics = null;

        if ((element = statisticsCache.get(username)) != null) {
            statistics = (SessionStatistics) element.getObjectValue();
        }


        Map<Country, Double> countryTransactionAmount = statistics.getCountryTransactionAmountSuccess();
        Iterator<Country> transactionIter = countryTransactionAmount.keySet().iterator();
        Country country;

        while (transactionIter.hasNext()) {
            country = transactionIter.next();
            countHash.put(country.getName(), countryTransactionAmount.get(country));
        }

        keyIter = countHash.keySet().iterator();
       
        // create a dataset...
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        while (keyIter.hasNext()) {
            key = keyIter.next();
            dataset.setValue(key, countHash.get(key).intValue());
        }
         
         // create a chart...
         JFreeChart chart = ChartFactory.createPieChart(
                 CHART_TITLE,
                 dataset,
                 true, // legend?
                 true, // tooltips?
                 false// URLs?
                 );
                 
         
         PiePlot plot = (PiePlot) chart.getPlot();
         
         //set colors for pie chart sections
        /**
         plot.setSectionPaint("Burkina Faso", new Color(255,51,51));	//red
         plot.setSectionPaint("Chad", new Color(51,255,51));	//green
         plot.setSectionPaint("Democratic Republic of Congo(DRC)", new Color(0,0,204));	//blue
         plot.setSectionPaint("Gabon", new Color(255,51,51));	//red
         plot.setSectionPaint("Ghana", new Color(51,255,51));	//green
         plot.setSectionPaint("Kenya", new Color(0,0,204));	//blue
         plot.setSectionPaint("Madagascar", new Color(255,51,51));	//red
        **/
        
        
         PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
             "{0} ({2})", new DecimalFormat("0"), new DecimalFormat("0.0%"));
                 
         plot.setLabelGenerator(gen);
         plot.setIgnoreZeroValues(true);
         plot.setIgnoreNullValues(true);
         
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