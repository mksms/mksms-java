package librarymksms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonArray;
import com.mboatek.mKsms.Client;
import com.mboatek.mKsms.Contact;
import com.mboatek.mKsms.Message;
import com.mboatek.mKsms.Response;

public class Test {

	
	Contact contact = new Contact("aurelle", "691349146");
    Message message = new Message("bonjour miss", contact);
    Client client = Client.getInstance("830EA3BB2A", "73249341d85f566b6f2b8cef4563d6c149efe4df2b43f21776a6c9faf7f61af5");
    
    
	@org.junit.Test
	public void testSendMessage() throws ClientProtocolException, IOException {
		
		Response res = client.send_message(message);
	    assertTrue(res.getSuccess());
	}
	
	@org.junit.Test
	public void testGetMessage() throws ClientProtocolException, IOException {

		Date date = new Date();
	    date.setDate(Calendar.DAY_OF_MONTH);
	    Response res = client.get_messages(date);
	    JsonArray unexpected = new JsonArray();
	    System.out.println(unexpected);
	    assertNotEquals(unexpected, res.getData());
	    
	}
	
	@org.junit.Test
	public void testStartVerification() throws ClientProtocolException, IOException {
		
		Response res = client.start_verification("691349146", "mboatek");
		assertTrue(res.getSuccess());
	}
	
	@org.junit.Test
	public void testConfirmVerification() throws ClientProtocolException, IOException {
		
		Response res = client.confirm_verification("691349146", "12345");
		assertFalse(res.getSuccess());
	}
	
	
	@org.junit.Test
	public void testConfirmVerification_fail() throws ClientProtocolException, IOException {
		
		Response res = client.confirm_verification("691349146", "5678");
		assertFalse(res.getSuccess());
		
	}
	
}
