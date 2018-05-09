package liquido.aws.rest_api.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import metacase.https.bpoclientswebservice_metacase.SenderDataInformation;

public class ObjectMapper {

	public static SenderDataInformation getInfoFromRequestXml(String reqBody) {
		SenderDataInformation info = new SenderDataInformation();
		Document doc = getDomElement(reqBody);
		
		NodeList nodes = doc.getElementsByTagName("Company");
		Element node = (Element) nodes.item(0);
		String value = node.getTextContent();
		info.setCompany(value);
		
		nodes = doc.getElementsByTagName("System");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setSYSTEM(value);
		
		nodes = doc.getElementsByTagName("Bank");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setBank(value);
		
		nodes = doc.getElementsByTagName("MessageId");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setMessageID(value);
		
		nodes = doc.getElementsByTagName("Proposal");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setProposal(value);
		
		nodes = doc.getElementsByTagName("Type");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setType(value);
		
		nodes = doc.getElementsByTagName("User");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setUser(value);
		
		nodes = doc.getElementsByTagName("CreationDate");
		node = (Element) nodes.item(0);
		value = node.getTextContent();
		info.setCreationDate(value);
		
		nodes = doc.getElementsByTagName("Message");
		node = (Element) nodes.item(0);
		value = getCharacterDataFromElement(node);
		info.setMessage(value);
		
		return info;
	}
	
	public static Document getDomElement(String xml) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(true);
        dbf.setNamespaceAware(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }
	
	public static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }

	public static String getSignatureFromRequestXml(String reqBody) {
		Document doc = getDomElement(reqBody);

		NodeList nodes = doc.getElementsByTagName("Signature");
		Element node = (Element) nodes.item(0);
		return node.getTextContent();
	}
	
	public static String getMessageFromRequestXml(String reqBody) {
		Document doc = getDomElement(reqBody);

		NodeList nodes = doc.getElementsByTagName("Message");
		Element node = (Element) nodes.item(0);
		String value = getCharacterDataFromElement(node);
		return (!value.isEmpty()) ? value : node.getTextContent();
	}

	public static String getCertNameFromRequestXml(String body) {
		Document doc = getDomElement(body);

		NodeList nodes = doc.getElementsByTagName("CertificateName");
		Element node = (Element) nodes.item(0);
		return node.getTextContent();
	}
	
}
