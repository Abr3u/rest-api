package liquido.aws.rest_api.models;

import liquido.aws.rest_api.handlers.base.Validable;

public class XmlRequest implements Validable{

	public String body;

	@Override
	public boolean isValid() {
		return body != null && !body.isEmpty();
	}
	
}
