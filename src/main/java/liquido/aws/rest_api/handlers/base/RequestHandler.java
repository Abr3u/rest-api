package liquido.aws.rest_api.handlers.base;

import java.util.Map;

public interface RequestHandler<V extends Validable> {
	Answer process(V value, Map<String, String> urlParams);
}
