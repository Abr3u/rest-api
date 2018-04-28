package metacase.handlers;

import java.util.Collections;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.NodeList;

public class CDataHandler implements SOAPHandler<SOAPMessageContext> {

	private static final boolean isTest = true;
	
	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleMessage(SOAPMessageContext soapMessage) {
		try {
			SOAPMessage message = soapMessage.getMessage();
			boolean isOutboundMessage = (Boolean) soapMessage.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			
			if(isTest) soapMessage.put("javax.xml.ws.service.endpoint.address", "https://webapp.metacase.eu:3443/clientservices/BpoClientsWebService.asmx");
			
			// is a request?
			if (isOutboundMessage) {
				//get Message node
				NodeList messageList = message.getSOAPBody().getElementsByTagName("Message");
				org.w3c.dom.Node messageNode = messageList.item(0);
				
				// build a CDATA NODE with the text in the message node
				Node cddata = (Node) message.getSOAPPart().createCDATASection(messageNode.getTextContent());
				
				//reset message node content
				messageNode.setTextContent("");
				
				// add the CDATA's node as the message node content
				messageNode.appendChild(cddata);

				message.saveChanges();
			}

		} catch (Exception ex) {
			// fail
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext soapMessage) {
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}
}