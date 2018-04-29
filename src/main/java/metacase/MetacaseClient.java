package metacase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import liquido.aws.rest_api.utils.PathUtils;
import metacase.handlers.CDataHandler;
import metacase.https.bpoclientswebservice_metacase.BpoClientsWebService;
import metacase.https.bpoclientswebservice_metacase.BpoClientsWebServiceSoap;
import metacase.https.bpoclientswebservice_metacase.SenderDataInformation;
import metacase.https.bpoclientswebservice_metacase.SenderDataResponse;

public class MetacaseClient {
	private final String trustStorePassword = "changeit";
	
	private final String username = "CandorUser";
	private final String password = "C@ndor1ser";

    public SenderDataResponse sendDocuments(SenderDataInformation info){
    	System.setProperty("javax.net.ssl.trustStore",PathUtils.getTrustStorePath());
		System.setProperty("javax.net.ssl.trustStorePassword",trustStorePassword);
		
        BpoClientsWebService webService = new BpoClientsWebService();
        setHandlers(webService);
        BpoClientsWebServiceSoap port = webService.getBpoClientsWebServiceSoap();
        setAuthorization(port);
        
        return port.receiveDataFromClient(info);
    }

	private void setAuthorization(BpoClientsWebServiceSoap port) {
		Map<String, Object> requestContext = ((BindingProvider)port).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
	}

	private void setHandlers(BpoClientsWebService service) {
		service.setHandlerResolver(new HandlerResolver() {

			@Override
			public List<Handler> getHandlerChain(PortInfo portInfo) {
				List<Handler> handlerChain = new ArrayList<Handler>();
				handlerChain.add(new CDataHandler());
				return handlerChain;
			}
		});
	}

}
