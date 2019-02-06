package commonlibs;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ClientToServerMessage implements Serializable{
	private String clientName;
	private double temperature;
	private Date timeOfReading;
	private String typeOfMessage;
	
	public ClientToServerMessage(String clientName) {
		this.clientName = clientName;
	}
	
	
	public void SetReading(double temperature, Date timeOfReading) {
		this.temperature = temperature;
		this.timeOfReading = timeOfReading;
		this.typeOfMessage = "reading";
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public double getTemperature() {
		return temperature;
	}


	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}


	public Date getTimeOfReading() {
		return timeOfReading;
	}


	public void setTimeOfReading(Date timeOfReading) {
		this.timeOfReading = timeOfReading;
	}


	public String getTypeOfMessage() {
		return typeOfMessage;
	}


	public void setTypeOfMessage(String typeOfMessage) {
		this.typeOfMessage = typeOfMessage;
	}
	
	
	
}
