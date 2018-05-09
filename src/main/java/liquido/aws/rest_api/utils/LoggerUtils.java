package liquido.aws.rest_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import spark.Request;
import spark.Response;

public class LoggerUtils {
	public static void logReqResInfoToString(Request request, Response response) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		HttpServletResponse raw = response.raw();
		String a = "\n\n[" + dtf.format(now)+"]";
		a += "Request: ";
		a += request.requestMethod();
		a += " " + request.url();
		a += "\n Body: " + request.body();
		a += "\n Reponse: " + raw.getStatus();
		a += " Body: " + response.body();

		appendToFile(PathUtils.getPathToLogFile(), a);
	}

	private static void appendToFile(String filePath, String text) throws IOException {
		Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
	}
}
