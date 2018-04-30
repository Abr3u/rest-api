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
import liquido.aws.rest_api.models.ValidSignatureRequest;
import liquido.aws.rest_api.utils.crypto.CryptoUtils;

public class ValidSignatureHandler extends AbstractRequestHandler<ValidSignatureRequest> {
	public ValidSignatureHandler() {
		super(ValidSignatureRequest.class);
	}

	@Override
	protected Answer processImpl(ValidSignatureRequest value, Map<String, String> urlParams) {
		try {
			byte[] data = (value.data).getBytes("UTF-8");
			byte[] signature = CryptoUtils.toDecodedBase64ByteArray((value.signature).getBytes());
			
			boolean valid = CryptoUtils.checkSignature(data, signature,
					CryptoUtils.getPubKeyFromCert(value.certificateName));
			
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
