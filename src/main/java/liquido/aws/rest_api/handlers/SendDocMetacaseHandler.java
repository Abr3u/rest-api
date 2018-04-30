package liquido.aws.rest_api.handlers;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import liquido.aws.rest_api.handlers.base.AbstractRequestHandler;
import liquido.aws.rest_api.handlers.base.Answer;
import liquido.aws.rest_api.models.XmlRequest;
import liquido.aws.rest_api.utils.ObjectMapper;
import liquido.aws.rest_api.utils.crypto.CryptoUtils;
import metacase.MetacaseClient;
import metacase.https.bpoclientswebservice_metacase.SenderDataInformation;
import metacase.https.bpoclientswebservice_metacase.SenderDataResponse;

public class SendDocMetacaseHandler extends AbstractRequestHandler<XmlRequest> {

	public SendDocMetacaseHandler() {
		super(XmlRequest.class, true);
	}

	@Override
	protected Answer processImpl(XmlRequest value, Map<String, String> urlParams) {
		String signatureStr = ObjectMapper.getSignatureFromRequestXml(value.body);
		SenderDataInformation info = ObjectMapper.getInfoFromRequestXml(value.body);

		try {
			byte[] data = info.getMessage().getBytes("UTF-8");
			byte[] signature = CryptoUtils.toDecodedBase64ByteArray(signatureStr.getBytes());

			boolean valid = CryptoUtils.checkSignature(data, signature, CryptoUtils.getSigningPubKey());
			if (!valid) {
				return Answer.nok("Invalid Request - Bad Signature");
			}
		} catch (FileNotFoundException e) {
			return Answer.nok("Certificate Not Found");
		} catch (CertificateException e) {
			return Answer.nok("Expired Certificate Date");
		} catch (SignatureException | ArrayIndexOutOfBoundsException e) {
			return Answer.nok("Badly Formatted Signature");
		} catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return Answer.nok("Unexpected Error: "+e.getMessage());
		}
		
		// call webservice
		MetacaseClient client = new MetacaseClient();
		SenderDataResponse response = client.sendDocuments(info);

		// tranform xml response to json
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String body = gson.toJson(response);
		return Answer.ok(body);
	}

}
