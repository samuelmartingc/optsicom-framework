package es.optsicom.lib.experiment;

import es.optsicom.lib.Method;
import es.optsicom.lib.instancefile.InstanceFile;

public class OptsicomException extends Exception {

	private InstanceFile instanceFile;
	private Method method;

	public OptsicomException(InstanceFile iFile, Method method, Exception cause) {
		super(cause);
		this.instanceFile = iFile;
		this.method = method;
	}
	
	public InstanceFile getInstanceFile() {
		return instanceFile;
	}
	
	public Method getTimeLimit() {
		return method;
	}
}
