package metacase.https.bpoclientswebservice_metacase;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.bpoclientswebservice_metacase package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.bpoclientswebservice_metacase
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReceiveDataFromClient }
     * 
     */
    public ReceiveDataFromClient createReceiveDataFromClient() {
        return new ReceiveDataFromClient();
    }

    /**
     * Create an instance of {@link SenderDataInformation }
     * 
     */
    public SenderDataInformation createSenderDataInformation() {
        return new SenderDataInformation();
    }

    /**
     * Create an instance of {@link ReceiveDataFromClientResponse }
     * 
     */
    public ReceiveDataFromClientResponse createReceiveDataFromClientResponse() {
        return new ReceiveDataFromClientResponse();
    }

    /**
     * Create an instance of {@link SenderDataResponse }
     * 
     */
    public SenderDataResponse createSenderDataResponse() {
        return new SenderDataResponse();
    }

}
