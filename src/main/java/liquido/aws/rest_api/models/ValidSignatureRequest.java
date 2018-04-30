package liquido.aws.rest_api.models;

import liquido.aws.rest_api.handlers.base.Validable;

public class ValidSignatureRequest implements Validable{
	public String data;
	public String signature;
	public String certificateName;
	
	public boolean isValid() {
		return data != null && !data.isEmpty() && signature != null && !signature.isEmpty() && certificateName != null && !certificateName.isEmpty();
	}
}
