package com.mboatek.mKsms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mboatek.mKsms.Contact;

/**
 * message class
 * @author aurelle
 *
 */
public class Message {

	public String body;
	public Contact contact;
	public static final int BOTH = 0, OUT = 1, IN = -1;
	
	/**
	 * create a new message object
	 * @param body
	 * @param contact
	 */
	public Message(String body, Contact contact) {
		super();
		this.body = body;
		this.contact = contact;
	}

	/**
	 * get the body 
	 * @return
	 */
	public String getBody() {
		return body;
	}

	/**
	 * set the body
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * get the contact of message
	 * @return
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * set contact
	 * @param contact
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	/**
	 * create json Object from message object
	 * @return a json object
	 */
	public JsonObject create_json_from_message() {
	
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String message =  gson.toJson(this);
		JsonParser jsonParser = new JsonParser();
		JsonObject messageJson = (JsonObject)jsonParser.parse(message);
		return messageJson;
		
	}
	
	/**
	 * create an object message from a json object
	 * @param jsonObject
	 * @return object message
	 */
	public Message create_message_from_json(JsonObject jsonObject) {
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		Message message = gson.fromJson(jsonObject, this.getClass());
		return message;
	}
	
}
