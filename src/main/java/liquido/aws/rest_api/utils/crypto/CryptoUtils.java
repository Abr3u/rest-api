package liquido.aws.rest_api.utils.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import liquido.aws.rest_api.utils.PathUtils;

public class CryptoUtils {
	
	public static PublicKey getPubKeyFromCert(String certificateName) throws CertificateException, FileNotFoundException {
		String pathToCert = PathUtils.getPathToResourceByName("certs/"+certificateName);
		FileInputStream fin = new FileInputStream(pathToCert);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		Certificate certificate = f.generateCertificate(fin);
		PublicKey pk = certificate.getPublicKey();
		return pk;
	}

	public static boolean checkSignature(byte[] data, byte[] signatureValue, java.security.PublicKey public_key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initVerify(public_key);
			signature.update(data);
			return signature.verify(signatureValue);
	}
	
	public static byte[] getHash(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data);
		return md.digest();
	}
	
	public static byte[] decrypt(byte[] buffer, java.security.PublicKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher rsa;
		rsa = Cipher.getInstance("RSA");
		rsa.init(Cipher.DECRYPT_MODE, key);
		return rsa.doFinal(buffer);
	}
	
	public static byte[] toDecodedBase64ByteArray(byte[] base64EncodedByteArray) {
		return DatatypeConverter.parseBase64Binary(new String(base64EncodedByteArray, Charset.forName("UTF-8")));
	}
}

