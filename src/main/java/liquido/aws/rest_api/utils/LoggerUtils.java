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
		StringBuilder sb = new StringBuilder();
		sb.append("\n [" + dtf.format(now)+"]");
		sb.append("Request: \n");
		sb.append(request.requestMethod());
		sb.append(" " + request.url());
		sb.append("\n Body: " + request.body());
		HttpServletResponse raw = response.raw();
		sb.append("\n Reponse: " + raw.getStatus());
		sb.append("\n Body: " + response.body());
		sb.append("\n");
		String log = sb.toString();

		appendToFile(PathUtils.getPathToLogFile(), log);
	}

	private static void appendToFile(String filePath, String text) throws IOException {
		Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
	}
}
