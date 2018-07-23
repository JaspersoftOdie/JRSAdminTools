package com.jaspersoft.solutionsengineer.tools;


import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class RetrieveResources {
	
	  @SuppressWarnings("deprecation")
	public static void main(String[] args) {
		  
			String version;
			String permissionMask;
			String creationDate;
			String updateDate;
			String label;
			String uri;
			String resourceType;
			Date uDate;
			
			List<Resources> rs = new ArrayList<Resources>();
			Resources r;
			
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			
			
		    HttpClient httpClient = HttpClientBuilder.create().build();
			HttpEntity entity = null;
			HttpHost target = null;
			HttpGet getRequest = null;
			HttpDelete deleteRequest = null;
			
		    try {
		    target = new HttpHost("localhost", 8640, "http");
			getRequest = new HttpGet("/jasperserver-pro/rest_v2/resources?type=xlsx&type=pdf&j_username=superuser&j_password=superuser");
			getRequest.setHeader("Accept", "application/json");
		    getRequest.setHeader("Content-type", "application/json");
			HttpResponse getResponse = httpClient.execute(target, getRequest);
		    entity = getResponse.getEntity();

		    System.out.println("----------------------------------------");
		    System.out.println(getResponse.getStatusLine());
		    Header[] headers = getResponse.getAllHeaders();
		    for (int i = 0; i < headers.length; i++) {
		      System.out.println(headers[i]);
		    }
		    System.out.println("----------------------------------------");
		    } catch (Exception e){
		    	e.printStackTrace();
		    }
		    if (entity != null) {
		    	try {
		    		String json1 = EntityUtils.toString(entity);
		    		JSONParser parser = new JSONParser();
		    		JSONObject jsonObject;
		    	
		    	    jsonObject = (JSONObject) parser.parse(json1);

		    	    //out.println("<br>"+jsonObject);

		    	    JSONArray from_JRS = (JSONArray)jsonObject.get("resourceLookup");
		    	    
		    	/*    Iterator iterator = from_JRS.iterator();
		    	    while (iterator.hasNext()) {
		    	        System.out.println(iterator.next());
		    	    }*/
		    	    
		    	    for (int i = 0; i < from_JRS.size(); i++) {

		    	        JSONObject jsonObjectRow = (JSONObject) from_JRS.get(i);
		    	        uri = (String) jsonObjectRow.get("uri");
		    	        creationDate = (String) jsonObjectRow.get("creationDate");
		    	        label = (String) jsonObjectRow.get("label");
		    	        updateDate = (String) jsonObjectRow.get("updateDate");
		    	        uDate = format.parse( (String) jsonObjectRow.get("updateDate"));
		    	        Date testDate = format.parse("2017-11-20T11:15:01");
		    	        if (uDate.after(testDate) ) {
		    	        	r = new Resources();
		    	        	r.setUri(uri);
		    	        	r.setUpdateDate(updateDate);
		    	        	rs.add(r);	
		    	        } else {
		    	        	System.out.println("uri="+uri+"; date="+creationDate+"; label="+label+"; updateDate="+updateDate+"; udate=" + uDate);
		    	        }
		    	        
		    	    }
		    	} catch (Exception e) {
                    e.printStackTrace();
		    	}			    	
		    
		    	System.out.println("Array Size:" + rs.size());
		    	
		    	if (rs.size() > 0) {
		    		 try {
		    			 	for(int q=0;q < rs.size();q++) {
		    			 		deleteRequest = new HttpDelete("/jasperserver-pro/rest_v2/resources" + rs.get(q).uri + "&j_username=superuser&j_password=superuser");
			    				deleteRequest.setHeader("Accept", "application/json");
			    			    deleteRequest.setHeader("Content-type", "application/json");
			    				HttpResponse getResponse = httpClient.execute(target, deleteRequest);
			    			    entity = getResponse.getEntity();	
			    			    System.out.println(getResponse.getStatusLine());
		    			 	} 
		    		 } catch (Exception e) {
		    			 		e.printStackTrace();
		    			 	}
		    	}
		    }
		  
	  }
}
