package com.jaspersoft.solutionsengineer.executers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.query.JRAbstractQueryExecuter;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuter;

public class SwapSQLQueryExecuter extends JRJdbcQueryExecuter {
	   protected static int instanceNumberCounter = 0;
		protected static final Log log = LogFactory.getLog(SwapSQLQueryExecuter.class);

	    protected boolean createdConnection = false;
	    protected int thisInstanceNumber = instanceNumberCounter++;

	    public SwapSQLQueryExecuter(JasperReportsContext jasperReportsContext, JRDataset dataset, Map<String,? extends JRValueParameter> parameters) {
	        super(jasperReportsContext, dataset, parameters);

	        if (instanceNumberCounter == Integer.MAX_VALUE)
	            instanceNumberCounter = 0;

	        log.error("swapSQLQueryExecuter:" + thisInstanceNumber + ": dataset.getName() = " + dataset.getName());

	        if (connection != null) {
	            log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": connection != null");
	        } else {
	            log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": connection == null");

	            SwapDBProperties SwapDBProperties = new SwapDBProperties();
	            SwapDBProperties.loadProperties("environment");

	            String pDbConn = (String) this.getParameterValue("p_DbConn");
	            try {
	                connection = getSwapConnection(SwapDBProperties, pDbConn);
	            } catch (Exception exception) {
	                log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": Error creating connection", exception);
	                throw new RuntimeException("Error connecting to database.", exception);
	            }
	            createdConnection = true;

	            JRValueParameter reportConnectionParameter = getValueParameter(JRParameter.REPORT_CONNECTION, true);
	            if (reportConnectionParameter != null) {
	                reportConnectionParameter.setValue(connection);
	                log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": REPORT_CONNECTION set.");
	            } else {
	                log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": REPORT_CONNECTION not found.");
	            }
	        }
	    }

	    /* (non-Javadoc)
	     * @see net.sf.jasperreports.engine.util.JRQueryExecuter#close()
	     */
	    @Override
	    public synchronized void close() {
	        super.close();

	        if (connection != null && createdConnection) {
	            try {
	                connection.close();
	                log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": Connection closed");
	            } catch (SQLException e) {
	                log.error("SwapSQLQueryExecuter:" + thisInstanceNumber + ": Error while closing connection", e);
	            } finally {
	                connection = null;
	            }
	        }
	    }

	    protected static Connection getSwapConnection(SwapDBProperties swapDBProperties, String whichConn) throws JRException {
	        // Note, this design requires that the driver class name, user name, and password be the same for all environments.
	        String driverClass = swapDBProperties.getProperty("jdbc.driverClassName");
	        String userName = swapDBProperties.getProperty("jdbc.username");
	        String password = swapDBProperties.getProperty("jdbc.password");
	        String url = swapDBProperties.getProperty("jdbc." + whichConn + "URL");

	        Connection conn = null;
	        try {
	            Class.forName(driverClass);
	            conn = DriverManager.getConnection(url, userName, password);
	        } catch (Exception e) {
	            throw new JRException(e);
	        }

	        return conn;
	    }
	
	
}
