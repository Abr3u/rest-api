package liquido.aws.rest_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PathUtils {

	private static final String resourcesDir = "src/main/resources/";
	private static final String pathToCertificates = resourcesDir + "certs/";
	private static final String pathToTrustStore = pathToCertificates + "/cacerts";
	private static final String pathToKeyStore = pathToCertificates + "/mkeystore";
	private static final String logDir = "logs/";
	private static final String pdfsDir = "pdfs/";

	public static String getResourcePathByName(String fileName) {
		return resourcesDir + fileName;
	}

	public static String getTrustStorePath() {
		return pathToTrustStore;
	}
	
	public static String getKeyStorePath() {
		return pathToKeyStore;
	}

	public static String getPathToLogFile() throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String fileName = dtf.format(now)+"/log.txt";

		Path path = Paths.get(logDir + fileName);
		
		if (Files.notExists(path)) {
			Files.createDirectories(path.getParent());
			Files.createFile(path);
		}

		return path.toString();
	}

	public static String getPathToCertificate(String certificateName) {
		return pathToCertificates+certificateName;
	}

	public static String getPdfsDirectory() {
		return pdfsDir;
	}
}
