package tiptonspiderj1.com;

public class UserPreferences {
	// set up all variables for user settings
	private String baudRate;
	private String dataBits;	
	private String stopBits;
	private String parity;
	private String flowControl;	
	
	public String getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(final String baudRate) {
		this.baudRate = baudRate;
	}
	public String getDataBits() {
		return dataBits;
	}
	public void setDataBits(final String dataBits) {
		this.dataBits = dataBits;
	}
	public String getStopBits() {
		return stopBits;
	}
	public void setStopBits(final String stopBits) {
		this.stopBits = stopBits;
	}
	public String getParity() {
		return parity;
	}
	public void setParity( final String parity) {
		this.parity = parity;
	}
	public String getFlowControl() {
		return flowControl;
	}
	public void setFlowControl( final String flowControl) {
		this.flowControl = flowControl;
	}
	
	// default constructor for the class
	UserPreferences(){};
	
	// constructor for setting all parameters of this class
	public UserPreferences(String baudRate, String dataBits, String stopBits, String parity, String flowControl) {		
		this.baudRate = baudRate;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
		this.parity = parity;
		this.flowControl = flowControl;		
	}	

}
