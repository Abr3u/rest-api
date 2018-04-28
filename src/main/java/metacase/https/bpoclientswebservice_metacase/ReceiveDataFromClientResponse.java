package metacase.https.bpoclientswebservice_metacase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReceiveDataFromClientResult" type="{https://BpoClientsWebService.metacase.eu/}SenderDataResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "receiveDataFromClientResult"
})
@XmlRootElement(name = "ReceiveDataFromClientResponse")
public class ReceiveDataFromClientResponse {

    @XmlElement(name = "ReceiveDataFromClientResult")
    protected SenderDataResponse receiveDataFromClientResult;

    /**
     * Gets the value of the receiveDataFromClientResult property.
     * 
     * @return
     *     possible object is
     *     {@link SenderDataResponse }
     *     
     */
    public SenderDataResponse getReceiveDataFromClientResult() {
        return receiveDataFromClientResult;
    }

    /**
     * Sets the value of the receiveDataFromClientResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderDataResponse }
     *     
     */
    public void setReceiveDataFromClientResult(SenderDataResponse value) {
        this.receiveDataFromClientResult = value;
    }

}
