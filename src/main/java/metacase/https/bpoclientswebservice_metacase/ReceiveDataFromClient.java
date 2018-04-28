package metacase.https.bpoclientswebservice_metacase;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="dataInput" type="{https://BpoClientsWebService.metacase.eu/}SenderDataInformation" minOccurs="0"/>
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
    "dataInput"
})
@XmlRootElement(name = "ReceiveDataFromClient")
public class ReceiveDataFromClient {

    protected SenderDataInformation dataInput;

    /**
     * Gets the value of the dataInput property.
     * 
     * @return
     *     possible object is
     *     {@link SenderDataInformation }
     *     
     */
    public SenderDataInformation getDataInput() {
        return dataInput;
    }

    /**
     * Sets the value of the dataInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link SenderDataInformation }
     *     
     */
    public void setDataInput(SenderDataInformation value) {
        this.dataInput = value;
    }

}
