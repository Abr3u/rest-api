package liquido.aws.rest_api.handlers;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Map;

import liquido.aws.rest_api.handlers.base.AbstractRequestHandler;
import liquido.aws.rest_api.handlers.base.Answer;
import liquido.aws.rest_api.models.XmlRequest;
import liquido.aws.rest_api.utils.ObjectMapper;
import liquido.aws.rest_api.utils.crypto.CryptoUtils;

public class ValidSignatureXmlHandler extends AbstractRequestHandler<XmlRequest> {

	public ValidSignatureXmlHandler() {
		super(XmlRequest.class, true);
	}

	@Override
	protected Answer processImpl(XmlRequest value, Map<String, String> urlParams) {
		String signatureStr = ObjectMapper.getSignatureFromRequestXml(value.body);
		String message = ObjectMapper.getMessageFromRequestXml(value.body);
		String certName = ObjectMapper.getCertNameFromRequestXml(value.body);

		System.out.println("data "+message);
		System.out.println("certName "+certName);
		System.out.println("signatureStr "+signatureStr);
		
		try {
			byte[] data = message.getBytes("UTF-8");
			byte[] signature = CryptoUtils.toDecodedBase64ByteArray(signatureStr.getBytes());

			boolean valid = CryptoUtils.checkSignature(data, signature, CryptoUtils.getPubKeyFromCert(certName));
			return (valid) ? Answer.ok("Valid"): Answer.ok("Invalid");
		} catch (FileNotFoundException e) {
			return Answer.nok("Certificate Not Found");
		} catch (CertificateException e) {
			return Answer.nok("Expired Certificate Date");
		} catch (SignatureException | ArrayIndexOutOfBoundsException e) {
			return Answer.nok("Badly Formatted Signature");
		} catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return Answer.nok("Unexpected Error: "+e.getMessage());
		}
	}

}
