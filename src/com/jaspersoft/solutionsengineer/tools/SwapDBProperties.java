package com.jaspersoft.solutionsengineer.executers;

import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SwapDBProperties {
	private static final Log log = LogFactory.getLog(SwapDBProperties.class);

    protected ResourceBundle swapDBProperties = null;

    public void loadProperties(String propertiesFileName) {
        swapDBProperties = ResourceBundle.getBundle(propertiesFileName);
        if (swapDBProperties == null) {
            log.error("Unable to open the database properties file \"" + propertiesFileName + "\"");
        } else {
            log.error("DB properties file loaded");
        }
    }

    public String getProperty(String key) {
        if (swapDBProperties == null) {
            log.error("DB properties file not loaded");
            return null;
        }

        return swapDBProperties.getString(key);
    }
}
