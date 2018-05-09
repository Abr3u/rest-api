package liquido.aws.rest_api.handlers.base;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import liquido.aws.rest_api.models.XmlRequest;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class AbstractRequestHandler<V extends Validable> implements RequestHandler<V>, Route {

    private Class<V> valueClass;
    private boolean isXmlRequest;

    private static final int HTTP_BAD_REQUEST = 400;

    public AbstractRequestHandler(Class<V> valueClass){
        this.valueClass = valueClass;
        this.isXmlRequest = false;
    }
    
    public AbstractRequestHandler(Class<V> valueClass, boolean isXmlRequest){
        this.valueClass = valueClass;
        this.isXmlRequest = isXmlRequest;
    }

    public final Answer process(V value, Map<String, String> urlParams) {
        if (!value.isValid()) {
            return new Answer(HTTP_BAD_REQUEST);
        } else {
            return processImpl(value, urlParams);
        }
    }

    protected abstract Answer processImpl(V value, Map<String, String> urlParams);


    @Override
    public Object handle(Request request, Response response) throws Exception {
    	V value;
    	if(isXmlRequest) {
    		XmlRequest xml = new XmlRequest();
    		xml.body = request.body();
    		value = (V) xml;
    	}
    	else {
    		GsonBuilder builder = new GsonBuilder();
    		Gson gson = builder.create();
    		value = gson.fromJson(request.body(), valueClass);
    	}
        
		Map<String, String> urlParams = new HashMap<>();
        Answer answer = process(value, urlParams);
        response.status(answer.getCode());
        response.body(answer.getBody());
        return answer.getBody();
    }

}