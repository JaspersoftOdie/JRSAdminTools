package com.jaspersoft.solutionsengineer.tools;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.Header;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;

import org.json.simple.*;

import org.json.simple.parser.JSONParser;
import org.json.simple.Jsoner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Helpers {

	String version;
	String permissionMask;
	String creationDate;
	String updateDate;
	String label;
	String uri;
	String resourceType;
	Date uDate;
	
//	List<Resources> rs = new ArrayList<Resources>();
//	Resources r;
	
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	
    HttpClient httpClient = HttpClientBuilder.create().build();
	HttpEntity entity = null;
	HttpHost target = null;
	HttpGet getRequest = null;
	HttpDelete deleteRequest = null;
	
	// connect to a host
	public HttpHost getHost(String hostname, int port) {
		return new HttpHost(hostname, port, "http");
	}
	
	// Used for GETs	
	public HttpResponse getResponse(String request, HttpHost target) {
		HttpGet getReq;
		HttpResponse getResponse = null; 
		getReq = new HttpGet(request);
		getReq.setHeader("Accept", "application/json");
	    getReq.setHeader("Content-type", "application/json");
	    try {
	    	getResponse = httpClient.execute(target, getReq);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return getResponse;
	}
	
	public HttpResponse deleteResponse(String request, HttpHost target) {
		HttpDelete getReq;
		HttpResponse getResponse = null; 
		getReq = new HttpDelete(request);
		getReq.setHeader("Accept", "application/json");
	    getReq.setHeader("Content-type", "application/json");
	    try {
	    	getResponse = httpClient.execute(target, getReq);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return getResponse;
	}
	
	public HttpResponse deleteResponse(String request, HttpHost target, Header[] header) {
		HttpDelete getReq;
		HttpResponse getResponse = null; 
		getReq = new HttpDelete(request);
		getReq.setHeader("Accept", "application/json");
	    getReq.setHeader("Content-type", "application/json");
	    getReq.setHeaders(header);
	    try {
	    	getResponse = httpClient.execute(target, getReq);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return getResponse;
	}
	
	@SuppressWarnings("deprecation")
	public JSONArray queryJSON(HttpEntity json, String arrName) {
		JSONArray jsArray = null;
		try {
			String json1 = EntityUtils.toString(json);
			JSONParser parser = new JSONParser();
			JSONObject jsonObject;
		
		    jsonObject = (JSONObject) parser.parse(json1);

		    jsArray = (JSONArray)jsonObject.get(arrName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsArray;
	}
	
	public JSONArray queryJSON(String json, String arrName) {
		JSONArray jsArray = null;
		try {
			String json1 = json;
			JSONParser parser = new JSONParser();
			JSONObject jsonObject;
		
		    jsonObject = (JSONObject) parser.parse(json1);

		    jsArray = (JSONArray)jsonObject.get(arrName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsArray;
	}
	
/*	// This is using the non-deprecated classes. Will test later. -Odie
	
	public JsonArray queryJson(String json, String arrName) {
		JsonArray jsArray = null;
		try {
			String json1 = json;
			JSONParser parser = new JSONParser();
			JsonObject jsonObject;
			Jsoner jsoner; //figure out how the hell to run this. Not clear from docs. It replaces parser.
			
		    jsonObject = (JsonObject) parser.parse(json1);

		    jsArray = (JsonArray)jsonObject.get(arrName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsArray;
	}*/

	
	public Date formatDate(String format, String toDate) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date returnDate = new Date();
		try {
			returnDate = dateFormat.parse( toDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnDate;
	}
	
	public String returnFileContents(String filename) 
			throws IOException
			{
			  BufferedReader reader = new BufferedReader(new FileReader(filename));
			  String line;
			  StringBuilder sb = new StringBuilder();
			  while ((line = reader.readLine()) != null)
			  {
			    sb.append(line + "\n");
			  }
			  reader.close();
			  return sb.toString();
			}
	
	// Methods that execute my tools start here!
	
	public void JRSCompare(String oldF, String newF, String outputFileName) throws IOException, Exception {
		
		  System.out.println("Hi, you're in the Helper class, baby!");
		
		  List<String> strNoChange = new ArrayList<String>();
		  List<String> strHasChange = new ArrayList<String>();
		  List<String> strNotFound = new ArrayList<String>();
		 
		  int noChanges = 0;
		  int hasChanges = 0;
		  int notFound = 0;
		 
		  File ourFolder = new File(oldF);
		  File theirFolder = new File(newF);
		  
		  BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
	      
	      File[] ourFiles = ourFolder.listFiles();
	      File[] theirFiles = theirFolder.listFiles(); 
	     
	      try
	      {
	    	writer.newLine();
	    	writer.append(" ============ WEB-INF Comparison between ===========");
	    	writer.newLine();
	    	writer.append("Standard JRS Directory: " + oldF);
	    	writer.newLine();
	    	writer.append("Customer JRS Directory: " + newF);
	    	writer.newLine();writer.newLine();writer.newLine();
	    	
	        for (File file1 : theirFiles) {
	      	  if (file1.isFile())
	            {   
	      		  	ByteArrayOutputStream out = new ByteArrayOutputStream();
	      		  	File ourFile = new File(oldF + "/" + file1.getName());
	      		  	if(!ourFile.createNewFile()) {
	      		  		RawText rt1 = new RawText(ourFile);
	      		  		RawText rt2 = new RawText(new File(file1.getAbsolutePath()));
	      		  		EditList diffList = new EditList();
	      		  		diffList.addAll(new HistogramDiff().diff(RawTextComparator.WS_IGNORE_ALL, rt1, rt2));
	      		  		DiffFormatter diff = new DiffFormatter(out);
	      		  		diff.format(diffList, rt1, rt2);
	      		  		if (out.toString().trim().length() > 0) {  
	      		  			strHasChange.add(file1.getName());
	      		  			writer.append(" ======================= " + file1.getName() + " ======================================= \n");
	      		  			writer.newLine();
	      		  			writer.newLine();
	      		  			writer.append(out.toString());
	      		  			writer.newLine();writer.newLine();
	      		  			writer.newLine();
	      		  			hasChanges++;
	      		  		} else {
	      		  			strNoChange.add(file1.getName());
	      		  			noChanges++;
	      		  		}
	      		  	} else {
	      		  		ourFile.delete();
	      		  		strNotFound.add(file1.getName());
	      		  		notFound++;
	      		  	}
	            }
	        }
	      } catch (IOException e)
	      {
	        e.printStackTrace();
	      }	      		
	      
	      System.out.println("No Changes: " + noChanges);
	      System.out.println("Has Changes: " + hasChanges);
	      System.out.println("Not Found: " + notFound);
	      
	      writer.append("No Changes: " + noChanges);
	      writer.newLine();
	      writer.append("Has Changes: " + hasChanges);
	      writer.newLine();
	      writer.append("Customer Files Only:" + notFound);
	      writer.newLine();writer.newLine();
	      
	      writer.append("########## Files with No Changes: ##########");
	      writer.newLine();
	      
	      for(int i=0;i < strNoChange.size();i++) {
	    	  writer.append(strNoChange.get(i));
	    	  writer.newLine();
	      }
	      
	      writer.newLine();writer.newLine();
	      writer.append("########## Files with Changes: ##########");
	      writer.newLine();

	      for(int i=0;i < strHasChange.size();i++) {
	    	  writer.append(strHasChange.get(i));
	    	  writer.newLine();
	      }
	      
	      writer.newLine();writer.newLine();
	      writer.append("########## Files in Customer Directory ONLY: ##########");
	      writer.newLine();

	      for(int i=0;i < strNotFound.size();i++) {
	    	  writer.append(strNotFound.get(i));
	    	  writer.newLine();
	      }
	      
	      writer.close();
	      
	}
	
	public void fileCleanup(List<String> params) throws IOException {
		String version;
		String permissionMask;
		String creationDate;
		String updateDate;
		String label;
		String uri;
		String resourceType;
		Date bDate;
		Date aDate;
		Date cDate;
		    
//		List<Resources> rs = new ArrayList<Resources>();
		List<Resources> notDeleted = new ArrayList<Resources>();
		List<Resources> beenDeleted = new ArrayList<Resources>();
		
		Resources r;
		Resources r2;
		
		Helpers helper = new Helpers();
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Boolean isOutput = (params.get(8).length() > 0 ) ? true : false;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(params.get(8)));
		
		// String urlStringFC = "/jasperserver-pro/rest_v2/resources?type=xlsx&j_username=" + params.get(3) + "&j_password=" + params.get(4);
		String urlStringFC = "/jasperserver-pro/rest_v2/resources?" + params.get(7) + "&j_username=" + params.get(3) + "&j_password=" + params.get(4);
		String urlStringDelete = "";
		
	    HttpClient httpClient = HttpClientBuilder.create().build();
	
		HttpHost target = null;
		HttpGet getRequest = null;
		HttpDelete deleteRequest = null;
		
		target = helper.getHost(params.get(1), Integer.parseInt(params.get(2)));
		
		HttpResponse getResponse = helper.getResponse(urlStringFC, target);
	    HttpEntity entity = getResponse.getEntity();
	    
	    writer.append("----------------------------------------");
	    writer.newLine();
	    
	    writer.append(getResponse.getStatusLine().toString());
	    Header[] headers = getResponse.getAllHeaders();
	    for (int i = 0; i < headers.length; i++) {
	      writer.append(headers[i].toString());
	      writer.newLine();
	    }
	    
	    writer.append("----------------------------------------");
	    writer.newLine();
	    
	    if (entity != null) {
    	    JSONArray from_JRS = helper.queryJSON(entity, "resourceLookup");
    	    for (int i = 0; i < from_JRS.size(); i++) {
    	        JSONObject jsonObjectRow = (JSONObject) from_JRS.get(i);
    	        uri = (String) jsonObjectRow.get("uri");
    	        creationDate = (String) jsonObjectRow.get("creationDate");
    	        label = (String) jsonObjectRow.get("label");
    	        updateDate = (String) jsonObjectRow.get("updateDate");
    	        //format dates
    	        aDate = helper.formatDate("yyyy-MM-dd'T'HH:mm:ss", params.get(5));
    	        bDate = helper.formatDate("yyyy-MM-dd'T'HH:mm:ss",params.get(6));
    	        cDate = helper.formatDate("yyyy-MM-dd'T'HH:mm:ss",(String) jsonObjectRow.get("creationDate"));
    	        
    	        //load resource java objects
    	        if (cDate.after(aDate) && cDate.before(bDate)  ) {
    	        	r = new Resources();
    	        	r.setUri(uri);
    	        	r.setUpdateDate(updateDate);
    	        	r.setCreationDate(creationDate);
    	        	r.setLabel(label);
    	        	beenDeleted.add(r);	
    	        } else {
    	        	r2 = new Resources();
    	        	r2.setUri(uri);
    	        	r2.setUpdateDate(updateDate);
    	        	r2.setCreationDate(creationDate);
    	        	r2.setLabel(label);
    	        	notDeleted.add(r2);	
//    	        	System.out.println("NOT DELETED! uri="+uri+"; date="+creationDate+"; label="+label+"; updateDate="+updateDate);
    	        }
    	    }
    	
    	    for(int q=0;q < beenDeleted.size();q++) {
    	    	urlStringDelete = "/jasperserver-pro/rest_v2/resources" + beenDeleted.get(q).uri;
    	    	getResponse = helper.deleteResponse(urlStringDelete, target,headers); 
			    entity = getResponse.getEntity();
			    // System.out.println(getResponse.getStatusLine() + " for " + beenDeleted.get(q).uri);
    	    	writer.append(getResponse.getStatusLine() + " for " + beenDeleted.get(q).uri+ " Created Date: " + beenDeleted.get(q).creationDate);
    	    	writer.newLine();
    	    	}
    	    
    	    for(int q=0;q < notDeleted.size();q++) {		 	 
    	    	writer.append("NOT DELETED: " + notDeleted.get(q).uri + " Created Date: " + notDeleted.get(q).creationDate);
    	    	writer.newLine();
    	    }
    	    
    	    writer.close();
	    }		
	}
}
