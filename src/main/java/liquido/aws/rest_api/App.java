package liquido.aws.rest_api;

import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import liquido.aws.rest_api.models.VerifySignatureRequest;
import liquido.aws.rest_api.utils.ObjectMapper;
import liquido.aws.rest_api.utils.crypto.CryptoUtils;
import liquido.aws.rest_api.utils.crypto.PdfSigner;
import metacase.MetacaseClient;
import metacase.https.bpoclientswebservice_metacase.SenderDataInformation;
import metacase.https.bpoclientswebservice_metacase.SenderDataResponse;

public class App {
	private static final int HTTP_BAD_REQUEST = 500;
	
	private static final String pdfsDir = "pdfs/";
	private static File uploadDir;

	public static void main(String[] args) {
		App app = new App();
		app.setupStaticFilesStorage();
		app.setupRoutes();
	}
	
	private void setupStaticFilesStorage() {
		uploadDir = new File(pdfsDir);
		uploadDir.mkdir(); // create the upload directory if it doesn't exist

		staticFiles.externalLocation(pdfsDir);
	}

	public void setupRoutes() {
		post("/sendDocMetacase", (req, res) -> {
			SenderDataInformation info = ObjectMapper.getInfoFromRequestXml(req);
			
			//call webservice
			MetacaseClient client = new MetacaseClient();
			SenderDataResponse response = client.sendDocuments(info);
			
			//tranform xml response to json
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			
			return gson.toJson(response);
		});
		
		post("/validSignature", (req, res) -> {
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			VerifySignatureRequest signatureRequest = gson.fromJson(req.body(), VerifySignatureRequest.class);
			if (!signatureRequest.isValid()) {
				res.status(HTTP_BAD_REQUEST);
				return "Invalid Request";
			}
			byte[] data = (signatureRequest.data).getBytes("UTF-8");
			byte[] signature = CryptoUtils.toDecodedBase64ByteArray((signatureRequest.signature).getBytes());

			return CryptoUtils.checkSignature(data, signature,
					CryptoUtils.getPubKeyFromCert(signatureRequest.certificateName));
		});
		
		post("/signPdf", (req, res) -> {

			Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".pdf");

			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

			try (InputStream input = req.raw().getPart("file").getInputStream()) {
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}
			;
			String uploadedName = tempFile.getFileName().toString();
			
			//files/2678359395719028.pdf
			String inputFileName = pdfsDir + uploadedName;
			//2678359395719028
			String inputId = inputFileName.replaceAll("\\D+","");
			//files/2678359395719028Signed.pdf
			String outputFileName = pdfsDir + inputId + "Signed.pdf";
			
			PdfSigner.signPdf(inputFileName, outputFileName);
			//tempFile.toFile().delete();//delete unsigned doc
			return "<h1>Pdf Signed successfully</h1>"+uploadedName;

		});
	}
}
