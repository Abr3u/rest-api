package liquido.aws.rest_api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

import liquido.aws.rest_api.handlers.ValidSignatureHandler;
import liquido.aws.rest_api.handlers.base.Answer;
import liquido.aws.rest_api.models.ValidSignatureRequest;


public class ValidSignatureTest{
	private final static String data = "ola";
	
	private final static String goodCertName = "myalias.cer";
	private final static String badCertName = "blablabla.cer";
	private final static String expiredCertName = "expired.cer";
	
	private final static String goodSignature = "FikO5QO0MEEP3XJi4Ywe9N5ymQr61E/I/SLvRqeDXwjsf36f+8DttVqMyZb0a83JZ4bcfW9pig//g98sdCyVMPtRgXiiam+kkVq89CaGeUK/8EJKj432vL99q0pMzs25ow3B/9i2BI9lNsgIr9nFUT/BKIb6LCoSzXu/ecallbNxhYspzpsiwFjb67Te9NhjI6XR0HSSCi0KnC80fY5ats8xYjoejctXo0gNMU2UkMtJixFg2D4lS8l+bHoYKj9Mg8AgjyJJYnmXhdeYXA71cm5UsF/0IctUJZbTah4QWJwzN0LEkkUdN7KXCKAyK/3KhbNn7RLW0dcdJDjTZV6Hng==";
	private final static String badSignature = "WikO5QO0MEEP3XJi4Ywe9N5ymQr61E/I/SLvRqeDXwjsf36f+8DttVqMyZb0a83JZ4bcfW9pig//g98sdCyVMPtRgXiiam+kkVq89CaGeUK/8EJKj432vL99q0pMzs25ow3B/9i2BI9lNsgIr9nFUT/BKIb6LCoSzXu/ecallbNxhYspzpsiwFjb67Te9NhjI6XR0HSSCi0KnC80fY5ats8xYjoejctXo0gNMU2UkMtJixFg2D4lS8l+bHoYKj9Mg8AgjyJJYnmXhdeYXA71cm5UsF/0IctUJZbTah4QWJwzN0LEkkUdN7KXCKAyK/3KhbNn7RLW0dcdJDjTZV6Hng==";
	private final static String badFormatSignature = "KhbNn7RLW0dcdJDjTZV6Hng==";
	
	private final static int goodCode = 200;
	private final static int badCode = 500;
	
	@Test
	public void shouldValidateSignature() {
		ValidSignatureRequest req = new ValidSignatureRequest();
		req.certificateName = goodCertName;
		req.data = data;
		req.signature = goodSignature;
		
		assertTrue(req.isValid());
		
		ValidSignatureHandler handler = new ValidSignatureHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), goodCode);
		assertEquals(answer.getBody(), "Valid");
	}
	
	@Test
	public void shouldNOTValidateSignature() {
		ValidSignatureRequest req = new ValidSignatureRequest();
		req.certificateName = goodCertName;
		req.data = data;
		req.signature = badSignature;
		
		assertTrue(req.isValid());
		
		ValidSignatureHandler handler = new ValidSignatureHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), goodCode);
		assertEquals(answer.getBody(), "Invalid");
	}
	
	@Test
	public void shouldFailBadSignatureFormat() {
		ValidSignatureRequest req = new ValidSignatureRequest();
		req.certificateName = goodCertName;
		req.data = data;
		req.signature = badFormatSignature;
		
		assertTrue(req.isValid());
		
		ValidSignatureHandler handler = new ValidSignatureHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), badCode);
		assertEquals(answer.getBody(), "Badly Formatted Signature");
	}
	
	@Test
	public void shouldFailNoCertificateFound() {
		ValidSignatureRequest req = new ValidSignatureRequest();
		req.certificateName = badCertName;
		req.data = data;
		req.signature = goodSignature;
		
		assertTrue(req.isValid());
		
		ValidSignatureHandler handler = new ValidSignatureHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), badCode);
		assertEquals(answer.getBody(), "Certificate Not Found");
	}
	
	@Test
	public void shouldFailCertificateExpired() {
		ValidSignatureRequest req = new ValidSignatureRequest();
		req.certificateName = expiredCertName;
		req.data = data;
		req.signature = goodSignature;
		
		assertTrue(req.isValid());
		
		ValidSignatureHandler handler = new ValidSignatureHandler();
		Answer answer = handler.process(req, Collections.emptyMap());
		
		assertEquals(answer.getCode(), badCode);
		assertEquals(answer.getBody(), "Expired Certificate Date");
	}
}
