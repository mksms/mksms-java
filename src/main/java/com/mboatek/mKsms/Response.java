package com.mboatek.mKsms;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 
 * @author aurelle
 *
 */
public class Response {

	 private boolean success;
	 private String message;
	 private int coast;
	 private JsonArray data;

	 public Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	 /**
	  * create a new response object from a json object
	  * @param response
	  */
		public Response(JsonObject response){
	    	
	    	this.success = response.get("success").getAsBoolean();
	    	
	        if(response.has("message")){
	             this.message = response.get("message").getAsString();
	         }else {
	        	 this.message = "no message for this request";
	         }
	         if (response.has("cost")){
	        	 this.coast = response.get("cost").getAsInt();
	         }
	         if(response.has("smses")){
	             this.data = response.getAsJsonArray("smses");
	         }
	    }
	    public boolean getSuccess() {
	        return success;
	    }

	    public void setSuccess(boolean success) {
	        this.success = success;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public int getCoast() {
	        return coast;
	    }

	    public void setCoast(int coast) {
	        this.coast = coast;
	    }

	    public JsonArray getData() {
	        return data;
	    }

	    public void setData(JsonArray data) {
	        this.data = data;
	    }
	    
}
