package com.impalapay.airtel.servlet.util;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;

import org.apache.log4j.Logger;

/**
 * Loads parameters from the config.properties file. These are then accessible
 * throughout the application.
 * <p>
 * Copyright (c) ImpalaPay LTD., June 14, 2014
 *
 * @author <a href="mailto:eugene@impalapay.com">Eugene Chimita</a>
 * @version %I%, %G%
 */
public class PropertiesConfig extends HttpServlet {

    // Values in config file to be retained in a HashMap
    private static HashMap<String, String> configHash;
    private Logger logger;

    
    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        logger = Logger.getLogger(this.getClass());
        initConfigHash();
        logger.info("Having initialized PropertiesConfig Servlet...");
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

        initConfigHash();

        PrintWriter out = response.getWriter();
        out.println("Have reloaded configuration file. Values are as follows:");

        Iterator<String> iter = configHash.keySet().iterator();
        String key;

        while (iter.hasNext()) {
            key = iter.next();
            out.println("Key is '" + key + "', value is '" + configHash.get(key) + "'");
        }

        out.close();

        logger.info("Have reloaded configuration file.");
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
     * @param configAttribute
     * @return String the value of the attribute we seek
     */
    public static String getConfigValue(String configAttribute) {
        return configHash.get(configAttribute);
    }

    
    /**
     * Populate the internal HashMap which will hold configuration keys and
     * values
     *
     */
    private void initConfigHash() {
        PropertiesConfiguration config;
        String key;

        String configFile = getServletContext().getRealPath("/") + getInitParameter("config-file");

        configHash = new HashMap<>();

        try {
            config = new PropertiesConfiguration();
            config.setListDelimiter('|');	// our array delimiter
            config.setFileName(configFile);
            config.load();

            Iterator<String> keys = config.getKeys();

            while (keys.hasNext()) {
                key = keys.next();
                configHash.put(key, StringUtils.strip((String) config.getProperty(key)));
            }

            logger.info("Have loaded configuration file into memory.");

        } catch (ConfigurationException e) {
            logger.error("ConfigurationException when trying to initialize configuration HashMap");
            logger.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
