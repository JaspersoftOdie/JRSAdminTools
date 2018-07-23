package com.jaspersoft.solutionsengineer.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
 

/**
 * A simple Java REST GET example using the Apache HTTP library.
 * This executes a call against the Yahoo Weather API service, which is
 * actually an RSS service (http://developer.yahoo.com/weather/).
 * 
 * Try this Twitter API URL for another example (it returns JSON results):
 * http://search.twitter.com/search.json?q=%40apple
 * (see this url for more twitter info: https://dev.twitter.com/docs/using-search)
 * 
 * Apache HttpClient: http://hc.apache.org/httpclient-3.x/
 *
 */
public class ApacheHttpRestClient1 {

  public static void main(String[] args) {
    
    HttpClient httpClient = HttpClientBuilder.create().build();
    
    try {
    HttpHost target = new HttpHost("localhost", 8640, "http");
	HttpGet getRequest = new HttpGet("/jasperserver-pro/rest_v2/resources?type=pdf&j_username=superuser&j_password=superuser");
	getRequest.setHeader("Accept", "application/json");
    getRequest.setHeader("Content-type", "application/json");
	HttpResponse getResponse = httpClient.execute(target, getRequest);
    HttpEntity entity = getResponse.getEntity();

    System.out.println("----------------------------------------");
    System.out.println(getResponse.getStatusLine());
    Header[] headers = getResponse.getAllHeaders();
    for (int i = 0; i < headers.length; i++) {
      System.out.println(headers[i]);
    }
    System.out.println("----------------------------------------");

    if (entity != null) {
    	Gson gson = new GsonBuilder().create();
    	String json1 = EntityUtils.toString(entity);
    	System.out.println(json1);
        ResourceLookup empObj = gson.fromJson(json1, ResourceLookup.class);
//        for(int i = 1;i < empObj.length;i++) {
    	  System.out.println("uri:" + empObj.getLookup().get(1).uri);
//      } 
      
    }
	
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
}