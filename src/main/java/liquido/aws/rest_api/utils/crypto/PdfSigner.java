package liquido.aws.rest_api.utils.crypto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Calendar;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

import liquido.aws.rest_api.utils.PathUtils;

public class PdfSigner {

	private static final String keystoreName = "cacerts";
	private static final String keystorePassword = "changeit";
	private static final String keyAlias = "selfsigned";
	private static final String keyPassword = "changeit";
	

	private static final String digestAlgorithm = DigestAlgorithms.SHA256;

	public static void setupBouncyCastle() {
		Security.addProvider(new BouncyCastleProvider());
	}

	public static void signPdf(String inputFilename, String outputFilename)
			throws FileNotFoundException, IOException, DocumentException, GeneralSecurityException {
		setupBouncyCastle();
		
		KeyStore ks = KeyStore.getInstance("JKS");
		String keystorePath = PathUtils.getPathToResourceByName("/certs/"+keystoreName);
		ks.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
		PrivateKey pk = (PrivateKey) ks.getKey(keyAlias, keyPassword.toCharArray());
		Certificate[] chain = ks.getCertificateChain(keyAlias);
		String provider = BouncyCastleProvider.PROVIDER_NAME;
		
		// Creating the reader and the stamper
		PdfReader reader = new PdfReader(inputFilename);
		FileOutputStream os = new FileOutputStream(outputFilename);
		PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
		
		// Creating the appearance
		PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
		appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
		appearance.setReason("It's personal");
		appearance.setLocation("Foobar");
		appearance.setSignDate(Calendar.getInstance());
		appearance.setVisibleSignature(new Rectangle(72, 732, 144, 780), 1, null);
		
		// Creating the signature
		ExternalDigest digest = new BouncyCastleDigest();
		ExternalSignature signature = new PrivateKeySignature(pk, digestAlgorithm, provider);
		MakeSignature.signDetached(appearance, digest, signature, chain, null, null, null, 0, CryptoStandard.CMS);
	}
	
}
