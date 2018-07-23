package com.jaspersoft.solutionsengineer.tools;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JRSAdminTools {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

	/*
	 * args in question
	 * args[0] = JSON containing parameters
	 */
    
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	Helpers helper = new Helpers();
	List<String> params = new ArrayList<String>();
	
	String paramFile = args[0];
	//String paramFile = "C:/Users/whenders/Documents/odie/solutions/admin_tools/bulkargs.json";
    String program= "-fc";
    String hostname= "";
    String port= "";
    String username= "";
    String password= "";
    String startDate= "";
    String endDate = "";
    String folder = "";
    String outputFile = "";
    String type = "";
    String[] types;
    String typeString = "";
    
	String myArgs = helper.returnFileContents(paramFile);
	
    JSONArray argArray = helper.queryJSON(myArgs, "bulkargs");
	  
//	  for (int i = 0; i < argArray.size(); i++) {
    
        JSONObject jsonObjectRow = (JSONObject) argArray.get(0);
        program = (String) jsonObjectRow.get("program");
        hostname = (String) jsonObjectRow.get("hostname");
        port = (String) jsonObjectRow.get("port");
        username = (String) jsonObjectRow.get("username");
        password = (String) jsonObjectRow.get("password");
        startDate = (String) jsonObjectRow.get("startDate");
        endDate = (String) jsonObjectRow.get("endDate");
        folder = (String) jsonObjectRow.get("folder");
        outputFile = (String) jsonObjectRow.get("outputFile");
        type = (String) jsonObjectRow.get("type");
        types = type.split(" ");
        
        for(int i=0;i<types.length;i++) {
        	if (i==0) {
        		typeString = typeString + "type="+types[i];
        	} else {
        		typeString = typeString + "&type="+types[i];
        	}
        }
        
       // System.out.println(typeString);
        
        params.add(program);
        params.add(hostname);
        params.add(port);
        params.add(username);
        params.add(password);
        params.add(startDate);
        params.add(endDate);
        params.add(typeString);
        params.add(outputFile);
        params.add(folder);
        
        //for(int i=0;i<params.size();i++) {
        //	System.out.println(params.get(i));
        //}
	    
//	  }
	
	
	Boolean isStartDate = (startDate.length()>0) ? true : false;	
	Boolean isEndDate = (endDate.length()>0) ? true : false;
	
	if(!isStartDate) {
		startDate="1970-01-01T11:30:01";
	}
	
	if(!isEndDate) {
		endDate="2070-01-01T11:30:01";
	}
	
	if (params.get(0).equals("-fc")) {
		helper.fileCleanup(params);
	} else {
		System.out.println("use -fc for FileCleanup, please!");
	}

}
	
}
