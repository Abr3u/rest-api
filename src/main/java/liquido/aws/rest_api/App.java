package liquido.aws.rest_api;

import static spark.Spark.after;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.servlet.MultipartConfigElement;

import liquido.aws.rest_api.handlers.SendDocMetacaseHandler;
import liquido.aws.rest_api.handlers.ValidSignatureHandler;
import liquido.aws.rest_api.handlers.ValidSignatureXmlHandler;
import liquido.aws.rest_api.utils.LoggerUtils;
import liquido.aws.rest_api.utils.PathUtils;
import liquido.aws.rest_api.utils.crypto.CryptoUtils;
import liquido.aws.rest_api.utils.crypto.PdfSigner;
import spark.Spark;

public class App {
	private static File uploadDir;

	public static void main(String[] args) {
		/*System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");*/
		
		App app = new App();
		app.setPort();
		app.setupStaticFilesStorage();
		// app.setupTrustAndKeyStore();
		app.setupRoutes();
		app.setupLogger();
	}

	private void setPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		//running on heroku
        if (processBuilder.environment().get("PORT") != null) {
            port(Integer.parseInt(processBuilder.environment().get("PORT")));
        }
        else {
        	port(4567);
        }
	}

	private void setupTrustAndKeyStore() {
		Spark.secure(PathUtils.getKeyStorePath(), CryptoUtils.getKeyStorePassword(), PathUtils.getTrustStorePath(),
				CryptoUtils.getTrustStorePassword());
	}

	private void setupLogger() {
		after((request, response) -> {
			LoggerUtils.logReqResInfoToString(request, response);
			System.out.println("---> "+request.requestMethod()+" "+request.url()+" ::: "+response.status()+" "+response.body());
		});
	}

	private void setupStaticFilesStorage() {
		String pdfsDir = PathUtils.getPdfsDirectory();
		uploadDir = new File(pdfsDir);
		uploadDir.mkdir(); // create the upload directory if it doesn't exist

		staticFiles.externalLocation(pdfsDir);
	}

	public void setupRoutes() {
		post("/sendDocMetacase",new SendDocMetacaseHandler());

		post("/validSignature", new ValidSignatureHandler());
		post("/validSignatureXML", new ValidSignatureXmlHandler());

		post("/signPdf", (req, res) -> {

			String pdfsDir = PathUtils.getPdfsDirectory();
			Path tempFile = Files.createTempFile(uploadDir.toPath(), "", ".pdf");

			req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

			try (InputStream input = req.raw().getPart("file").getInputStream()) {
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}
			;
			String uploadedName = tempFile.getFileName().toString();

			// files/2678359395719028.pdf
			String inputFileName = pdfsDir + uploadedName;
			// 2678359395719028
			String inputId = inputFileName.replaceAll("\\D+", "");
			// files/2678359395719028Signed.pdf
			String outputFileName = pdfsDir + inputId + "Signed.pdf";

			PdfSigner.signPdf(inputFileName, outputFileName);
			// tempFile.toFile().delete();//delete unsigned doc
			return "Pdf Signed successfully " + uploadedName;

		});
	}
}
