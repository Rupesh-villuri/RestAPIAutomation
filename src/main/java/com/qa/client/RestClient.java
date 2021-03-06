package com.qa.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {
	
	//1. GET Method without Headers:
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();//it will create one simple http client
		
		HttpGet httpget= new HttpGet(url); 
		//we have created one http get connection with this url, this is the http get request
		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httpget); 
		//this is like clicking on send button in postman for get request
		//.execute means it is hitting that Get Url
		//this is throwing ClientProtocolException, IOException exception
		
		return closeableHttpResponse;	
		
	}
	
	//2. GET Method with Headers:
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{
			
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
			
		for(Map.Entry<String,String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse closebaleHttpResponse =  httpClient.execute(httpget); //hit the GET URL
		return closebaleHttpResponse;
				
	}
	
	//3.POST Method with Headers
	public CloseableHttpResponse post(String url ,String entityString, HashMap<String,String> headerMap) throws ClientProtocolException, IOException{
	
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url); //http post request
		httppost.setEntity(new StringEntity(entityString)); //for payload
		
		//for headers
		for (Map.Entry<String, String> entry:headerMap.entrySet()) {
			httppost.addHeader(entry.getKey(),entry.getKey());
		}
		
		CloseableHttpResponse closeableHttpResponse = httpClient.execute(httppost); //for executing the request 
		
		return closeableHttpResponse;
		
	}
	
}

