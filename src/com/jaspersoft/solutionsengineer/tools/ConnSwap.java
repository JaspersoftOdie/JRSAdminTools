package com.jaspersoft.solutionsengineer.executers;

import java.util.ResourceBundle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConnSwap {
	private static ResourceBundle swapDBProperties = null;
	private static final Log LOG= LogFactory.getLog(ConnSwap.class);


		    public static synchronized void loadProperties(String propertiesFilename) {
		        if (swapDBProperties == null) {
		            swapDBProperties = ResourceBundle.getBundle(propertiesFilename);
		            if (swapDBProperties == null) {
		                LOG.error("Unable to open the database properties file '" + propertiesFilename + "'");
		                System.out.println("Unable to open the database properties file '" + propertiesFilename + "'");
		            } else {
		                LOG.debug("DB properties file loaded");
		                System.out.println("DB properties file loaded");
		          }
		        }
		    }
		    public static String getProperty(String key) {
		        if (swapDBProperties == null) {
		            return null;
		        } 
		        return swapDBProperties.getString(key);
		    }	
}
