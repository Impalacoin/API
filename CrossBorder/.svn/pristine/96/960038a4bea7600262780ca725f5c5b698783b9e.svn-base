package com.impalapay.airtel.servlet.report.chart.bar;

import com.impalapay.airtel.cache.CacheVariables;

import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.impalapay.airtel.util.CustomBarRenderer;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.joda.time.DateMidnight;
import org.joda.time.Hours;

/**
 * Generates a bar chart detailing the daily traffic of
 * {@link ke.co.shujaa.airtimegw.server.beans.topup.Topup}. This is for all
 * networks. The most recent day on the bar is today.
 * <p>
 * It is assumed that this class is only called when a user is logged in.
 * <p>
 * Copyright (c) Shujaa Solutions Ltd., Sep 24, 2013
 *
 * @author <a href="mailto:anthonym@shujaa.co.ke">Anthony Wafula</a>
 * @version %I%, %G%
 *
 */
public class TransactionBarDay1 extends HttpServlet {

    final String CHART_TITLE = "Top up amount by Day";
    final int CHART_WIDTH = 800;
    final int CHART_HEIGHT = 600;
    
    public static final int DAY_COUNT = 7; // Number of days over which to display the graph
   
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
        Map<String, Map<Country, Double>> countryTransactionAmountDay = new HashMap<>();
        dataset = new DefaultCategoryDataset();

        int numDays = 0;
        DateMidnight dateMidnightStart = DateMidnight.now().minus(Hours.hours(24 * (DAY_COUNT - 1)));


        if ((element = statisticsCache.get(username)) != null) {
            statistics = (SessionStatistics) element.getObjectValue();
        }


        countryTransactionAmountDay = statistics.getCountryTransactionAmountDay();


        Map<Country, Double> countryTransactionAmount;
        Iterator<Country> countryIter;
        Country country;
        do {
            dateStr = new SimpleDateFormat("MMM d").format(new Date(dateMidnightStart.getMillis()));
            countryTransactionAmount = countryTransactionAmountDay.get(dateStr);

            if (countryTransactionAmount != null) {	// It is possible that on particular days the account has no top ups
                countryIter = countryTransactionAmount.keySet().iterator();

                while (countryIter.hasNext()) {
                    country = countryIter.next();
                    dataset.addValue(countryTransactionAmount.get(country), country.getName(), dateStr);
                    
                }
            }

            dateMidnightStart = dateMidnightStart.plus(Hours.hours(24));
            numDays++;
        } while (numDays < DAY_COUNT);

        JFreeChart chart = ChartFactory.createStackedBarChart(
                CHART_TITLE + " for the last 7 days.", // chart title
                "Day", // domain axis label
                "Amount (USD)", // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
                );
       
        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        
        final CategoryItemRenderer renderer = new CustomBarRenderer(
        		new Paint[]{
                		new Color(255,51,51), // Red colour for Airtel ?
                		new Color(51,255,51), // Blue colour for Orange ?
                		new Color(0,0,204) }); // Green colour for Safaricom ?
        
        plot.setRenderer(renderer);
        
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