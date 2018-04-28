package liquido.aws.rest_api.utils;

public class PathUtils {
	
	private static final String resourcesDir = "src/main/resources/";
	private static final String pathToTrustStore = resourcesDir+"certs/cacerts";
	
	public static String getPathToResourceByName(String fileName) {
		return resourcesDir+fileName;
	}

	public static String getPathToTrustStore() {
		return pathToTrustStore;
	}
}
