package tiptonspiderj1.com;

public class UserPreferences {
	// set up all variables for user settings
	private String baudRate;
	private String dataBits;	
	private String stopBits;
	private String parity;
	private String flowControl;
	// initialize the instance of this class to be used through the application
	private static UserPreferences instance = new UserPreferences(); 
	
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(String baudRate) {
		this.baudRate = baudRate;
	}
	public String getDataBits() {
		return dataBits;
	}
	public void setDataBits(String dataBits) {
		this.dataBits = dataBits;
	}
	public String getStopBits() {
		return stopBits;
	}
	public void setStopBits(String stopBits) {
		this.stopBits = stopBits;
	}
	public String getParity() {
		return parity;
	}
	public void setParity(String parity) {
		this.parity = parity;
	}
	public String getFlowControl() {
		return flowControl;
	}
	public void setFlowControl(String flowControl) {
		this.flowControl = flowControl;
	}
	public static UserPreferences getInstance() {
		return instance;
	}
	
	/**
	*
	* @param instance is used to get access to the rest of the private variables
	* in the class
	*/
	public static void setInstance(UserPreferences instance) {
		UserPreferences.instance = instance;
	}
	

}
