package liquido.aws.rest_api.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.http.HttpServletResponse;

import spark.Request;
import spark.Response;

public class LoggerUtils {
	public static void logReqResInfoToString(Request request, Response response) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append(request.requestMethod());
		sb.append(" " + request.url());
		sb.append(" " + request.body());
		HttpServletResponse raw = response.raw();
		sb.append(" Reponse: " + raw.getStatus());
		sb.append(" " + raw.getHeader("content-type"));
		sb.append(" " + response.body());
		String log = sb.toString();
		
		appendToFile(PathUtils.getPathToLogFile(), log);
	}

	private static void appendToFile(String filePath, String text) throws IOException {
		Files.write(Paths.get(filePath), text.getBytes(), StandardOpenOption.APPEND);
	}
}
