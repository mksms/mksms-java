package com.mboatek.mKsms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * contact class
 * @author aurelle
 *
 */
public class Contact {
	
  public String name;
  public String number;
	
  /**
   * create a new object
   * @param name
   * @param number
   */
	public Contact(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	/**
	 * get the name of the contact
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * set the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get the number
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * set the number
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * create Jsonobject from contact
	 * @return
	 */
	public JsonObject create_json_from_contact() {
		  
		    GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    String contact =  gson.toJson(this);
		    JsonParser jsonParser = new JsonParser();
		    JsonObject contactJson = (JsonObject)jsonParser.parse(contact);
		    
		    return contactJson;
		    
	}
	 
	/**
	 * create a contact object from Json
	 * @param jsonObject
	 * @return
	 */
	public Contact create_contact_from_Json(JsonObject jsonObject) {
		    
		    GsonBuilder builder = new GsonBuilder();
		    Gson gson = builder.create();
		    Contact contact = gson.fromJson(jsonObject, this.getClass());
		    
		    return contact;
	}
	  
	  
	  
}
