package com.mboatek.mKsms;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;


public class Client {

	private String API_KEY;
	private String API_HASH;
	private String url ;
	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private static Client mInstance;
	private String BASE_URL ="http://api.mksms.cm";
	
	private Client(String aPI_KEY, String aPI_HASH) {
		super();
		this.API_KEY = aPI_KEY;
		this.API_HASH = aPI_HASH;
	}
	
	public static Client getInstance(String api_key, String api_hash) {
		if(mInstance == null) {
			mInstance = new Client(api_key,api_hash);
		}
		return mInstance;
	}
	
	/**
	 * get messages on the server
	 * @param date
	 * @return a list of messages as a JsonArray
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException 
	 */
	public Response get_messages(Date date) throws ClientProtocolException, IOException {
		
		url=BASE_URL+"/sms/available/?api_key="+this.API_KEY+"&api_hash="+this.API_HASH;
		Response response = null;
		boolean read = false;
		int direction = Message.IN;
		
		@SuppressWarnings("deprecation")
		Timestamp sq = new Timestamp(date.getDate());
		
		JsonObject params = new JsonObject();
		params.addProperty("read", read);
		params.addProperty("direction", direction);
		params.addProperty("min_date", sq.toString());
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String paramaters =  gson.toJson(params);
		
		HttpGet httpget = new HttpGet(url);
		httpget.setHeader("Accept", "application/json");
		httpget.setHeader("Content-type", "application/json");
		
	    CloseableHttpResponse result = httpclient.execute(httpget);
        
	    try 
	    {
			HttpEntity responseEntity = result.getEntity();
			
			if(responseEntity != null) {
				
				JsonObject result1 =(JsonObject) new JsonParser().parse(EntityUtils.toString(responseEntity)).getAsJsonObject();
				response = new Response(result1);
			    EntityUtils.consume(responseEntity);
			}
			
		}finally {
			result.close();
		}
	    
		return response;
	}
	
	/**
	 * send a message on the server
	 * @param message
	 * @return boolean as result
	 * @throws ClientProtocolException
	 * @throws IOException*/
	
	public Response send_message(Message message) throws ClientProtocolException, IOException{
		
		Response response = null;
		url=BASE_URL+"/sms/send/";
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		JsonObject params = message.create_json_from_message();
		params.addProperty("api_key", this.API_KEY);
		params.addProperty("api_hash", this.API_HASH);
		String paramaters =  gson.toJson(params);
		StringEntity entity = new StringEntity(paramaters, ContentType.APPLICATION_JSON);
	    
	    HttpPost httpPost = new HttpPost(url);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	    httpPost.setEntity(entity);	  
	    
	    CloseableHttpResponse result = httpclient.execute(httpPost);
	
		try {
			HttpEntity responseEntity = result.getEntity();
			
			if(responseEntity != null) {
				JsonObject result1 =(JsonObject) new JsonParser().parse(EntityUtils.toString(responseEntity));
				response = new Response(result1);
			    EntityUtils.consume(responseEntity);
			}
			
		}finally {
			result.close();
		}
		
	    return response;
	}
	
	/**
	 * send a code to a number to verify 
	 * @param number
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response start_verification(String number, String nameofservice) throws ClientProtocolException, IOException {
		
		Response response = null;
		url =BASE_URL+"/phone/verify/start/";
		
		JsonObject params = new JsonObject();
		params.addProperty("number", number);
		params.addProperty("name", nameofservice);
		params.addProperty("api_key", this.API_KEY);
		params.addProperty("api_hash", this.API_HASH);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String paramaters =  gson.toJson(params);
		
		StringEntity entity = new StringEntity(paramaters, ContentType.APPLICATION_JSON);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(entity);
		
	    CloseableHttpResponse result = httpclient.execute(httpPost);
	
		try {
			HttpEntity responseEntity = result.getEntity();
			
			if(responseEntity != null) {
				
				JsonObject result1 =(JsonObject) new JsonParser().parse(EntityUtils.toString(responseEntity));
				response = new Response(result1);
			    EntityUtils.consume(responseEntity);
			}
			
		}finally {
			result.close();
		}
		
		return response;
	}
	
	/**
	 * verify if a number and code are correct 
	 * @param number
	 * @param code
	 * @return a boolean 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Response confirm_verification(String number, String code) throws ClientProtocolException, IOException {
		
		Response response = null;
		url =BASE_URL+"/phone/verify/confirm/";

		JsonObject params = new JsonObject();
		params.addProperty("api_key", this.API_KEY);
		params.addProperty("api_hash", this.API_HASH);
		params.addProperty("number", number);
		params.addProperty("code", code);
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String paramaters =  gson.toJson(params);
		StringEntity entity = new StringEntity(paramaters, ContentType.APPLICATION_JSON);
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setEntity(entity);	 
		
		CloseableHttpResponse result = httpclient.execute(httpPost);
		try {
			HttpEntity responseEntity = result.getEntity();
			
			if(responseEntity != null) {
				
				JsonObject result1 =(JsonObject) new JsonParser().parse(EntityUtils.toString(responseEntity));
				response = new Response(result1);
			    EntityUtils.consume(responseEntity);
			}
			
		}finally {
			result.close();
		}
		
		return response;
	
	}
}
