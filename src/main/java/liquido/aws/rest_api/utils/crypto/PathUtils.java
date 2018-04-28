package liquido.aws.rest_api.utils.crypto;

public class PathUtils {
	
	private static final String resourcesDir = "src/main/resources/";
	
	public static String getPathToResourceByName(String fileName) {
		return resourcesDir+fileName;
	}
}
