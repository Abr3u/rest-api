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
import java.security.cert.X509Certificate;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import liquido.aws.rest_api.utils.PathUtils;

public class CryptoUtils {
	private static final String CIPHER_ALGORITHM = "RSA";

	private static final String HASHING_ALGORITHM = "SHA-256";

	private static final String SIGNING_ALGORITHM = "SHA256withRSA";
	
	private static final String keyStorePassword = "store123";
	private static final String trustStorePassword = "changeit";

	private static final String signingPubKeyAlias = "myalias";
	private static final String signingCertificateAlias = signingPubKeyAlias + ".cer";

	private static final Map<String, String> aliasToKeyPass = Collections
			.unmodifiableMap(Stream.of(new AbstractMap.SimpleEntry<>(signingPubKeyAlias, keyStorePassword))
					.collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

	public static PublicKey getPubKeyFromCert(String certificateName) throws CertificateException, FileNotFoundException {
		String pathToCert = PathUtils.getPathToCertificate(certificateName);
		FileInputStream fin = new FileInputStream(pathToCert);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		Certificate certificate = f.generateCertificate(fin);
		if (certificate instanceof X509Certificate) {
			((X509Certificate) certificate).checkValidity();
			PublicKey pk = certificate.getPublicKey();
			return pk;
		}
		else {
			throw new FileNotFoundException();//if it is not X509 certificate
		}
		
	}

	public static boolean checkSignature(byte[] data, byte[] signatureValue, java.security.PublicKey public_key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
		signature.initVerify(public_key);
		signature.update(data);
		return signature.verify(signatureValue);
	}

	public static byte[] getHash(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
		md.update(data);
		return md.digest();
	}

	public static byte[] decrypt(byte[] buffer, java.security.PublicKey key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher rsa;
		rsa = Cipher.getInstance(CIPHER_ALGORITHM);
		rsa.init(Cipher.DECRYPT_MODE, key);
		return rsa.doFinal(buffer);
	}

	public static byte[] toDecodedBase64ByteArray(byte[] base64EncodedByteArray) {
		return DatatypeConverter.parseBase64Binary(new String(base64EncodedByteArray, Charset.forName("UTF-8")));
	}

	public static String getKeyStorePassword() {
		return keyStorePassword;
	}

	public static String getTrustStorePassword() {
		return trustStorePassword;
	}

	public static String getKeyPassByAlias(String alias) {
		return aliasToKeyPass.get(alias);
	}

	public static String getSigningPubKeyAlias() {
		return signingPubKeyAlias;
	}

	public static PublicKey getSigningPubKey() throws CertificateException, FileNotFoundException {
		return getPubKeyFromCert(signingCertificateAlias);
	}

}
