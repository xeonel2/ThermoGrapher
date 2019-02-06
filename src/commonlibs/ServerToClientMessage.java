package commonlibs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ServerToClientMessage implements Serializable {
	private String typeOfMessage;
	private String errorMessage;
	public String getTypeOfMessage() {
		return typeOfMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public int getSampleFrequency() {
		return sampleFrequency;
	}
	private int sampleFrequency;
	
	public ServerToClientMessage(String typeOfMessage, int sampleFrequency) {
		this.typeOfMessage = typeOfMessage;
		this.sampleFrequency = sampleFrequency;
	}
	
	public ServerToClientMessage(String typeOfMessage, String errorMessage) {
		this.typeOfMessage = typeOfMessage;
		this.errorMessage = errorMessage;
	}
	

}
