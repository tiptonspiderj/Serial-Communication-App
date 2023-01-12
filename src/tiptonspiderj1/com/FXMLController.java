package tiptonspiderj1.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLController implements Initializable, SerialPortDataListener {

	/***************************************************************************
	 *                                Variables                                *
	 ***************************************************************************/
	@FXML
	private MenuItem menuLoad;

	@FXML
	private MenuItem menuExit;

	@FXML
	private MenuItem menuHelp;

	@FXML
	private MenuItem menuSaveParam;

	@FXML
	private MenuItem menuLoadParam;

	@FXML
	private ComboBox<String> baudRate;

	@FXML
	private ComboBox<String> dataBits;

	@FXML
	private ComboBox<String> stopBits;

	@FXML
	private ComboBox<String> parity;

	@FXML
	private ComboBox<String> flowControl;

	@FXML
	private JFXButton btnConnect;

	@FXML
	private JFXButton btnSendFile;

	@FXML
	private JFXButton btnSaveFile;

	@FXML
	private JFXButton btnClearText;

	@FXML
	private TextArea fileText;

	@FXML
	private ComboBox<String> availablePorts;

	// set up the variable used for the instance of the main window
	private static FXMLController instance;
	// map the port names to the CommPortIdentifiers
	private HashMap<String, SerialPort> portMap = new HashMap<String, SerialPort>();
	// set up an array for holding the port names
	private ObservableList<String> portList = FXCollections.observableArrayList();
	// set up an array for possible baud rates
	private ObservableList<String> baudList = FXCollections.observableArrayList("2400", "4800", "9600", "19200",
			"38400", "57600", "115200");
	// set up an array for possible data bits
	private ObservableList<String> dataBitList = FXCollections.observableArrayList("5", "6", "7", "8");
	// set up an array for possible stop bits
	private ObservableList<String> stopBitList = FXCollections.observableArrayList("1", "1.5", "2");
	// set up an array for possible parity settings
	private ObservableList<String> parityList = FXCollections.observableArrayList("Even", "Odd", "None", "Space",
			"Mark");
	// set up an array for possible flow control
	private ObservableList<String> flowControlList = FXCollections.observableArrayList("RTS/CTS", "XON/XOFF", "None");
	// set up a variable for the selected port
	private String selectedPort = null;
	// this object contains the opened port
	private static SerialPort[] serialPorts;
	private SerialPort serialPort = null;
	// input and output streams for sending and receiving data
	private static BufferedReader input = null;
	private static OutputStream output = null;	
	// set up integer variables to hold serial port parameters
	private int baud, databit, stopbit, paritee, flowcontrol;

	/****************************************************************************
	 *                           Setters and Getters                            *
	 ****************************************************************************/

	public static FXMLController getInstance() {
		return instance;
	}

	public ComboBox<String> getAvailablePorts() {
		return this.availablePorts;
	}

	public TextArea getFileText() {
		return this.fileText;
	}

	public void setFileText(String fileText) {
		this.fileText.setText(fileText);
	}

	/****************************************************************************
	 *                             Methods/Functions                            *
	 ****************************************************************************/

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// initialize the instance of this class so it can be shared across controllers
		mainController();
		// scan the computer ports
		searchPorts();
		// add data to our combo boxes
		availablePorts.setItems(portList);
		baudRate.setItems(baudList);
		dataBits.setItems(dataBitList);
		stopBits.setItems(stopBitList);
		parity.setItems(parityList);
		flowControl.setItems(flowControlList);
		// set up default values for each serial port parameter
		baudRate.setValue(baudList.get(1));
		dataBits.setValue(dataBitList.get(2));
		stopBits.setValue(stopBitList.get(2));
		parity.setValue(parityList.get(0));
		flowControl.setValue(flowControlList.get(1));
		// set the values associated for the default port parameters
		baud = 4800;
		databit = 7;
		stopbit = 2;
		paritee = 2;
		flowcontrol = 4;
		menuHelp.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
		Platform.runLater(() -> {
			// request focus in the text area after the GUI is created
			availablePorts.requestFocus();
		}); // end of the run later method
	} // end of the initialize method

	// this method initializes the instance of this class object
	public void mainController() {
		instance = this;
	} // end of the mainController method

	// this method returns an array of available ports
	public void searchPorts() {
		try {
			// get port names from computer
			serialPorts = SerialPort.getCommPorts();
			// loop through the port names while more port names exist
			for (SerialPort port: serialPorts) {
				portMap.put(port.getSystemPortName(), port);
				portList.add(port.getSystemPortName());
				}				
		} catch (NullPointerException e) {
		} // end of the try/catch statement
	} // end of searchPorts method

	@FXML
	void closeWindow(ActionEvent event) {
		// call this method to close the serial connection
		disconnect();
		// close all open windows for the application
		Platform.exit();
	} // end of the closeWindow method

	void disconnect() {
		try {
			// close the input stream
			input.close();
			// remove the listener on the serialPort
			serialPort.removeDataListener();
			// close the output stream
			output.close();
		} catch (IOException | NullPointerException e) {
		} // end of the try/catch statement
		try {
			// close the serialPort connection
			serialPort.closePort();
		} catch (NullPointerException ex) {
		} // end of the try/catch statement
	} // end of the disconnect method

	@FXML
	void listPorts(MouseEvent event) {
		// clear our list
		portList.clear();
		// scan the computer ports
		searchPorts();
		// add available ports to the combo box
		getAvailablePorts().setItems(portList);
	} // end of the listPorts method

	@FXML
	void setBaudRate(ActionEvent event) {
		// set our baud rate to the combo box value
		baud = Integer.parseInt(baudRate.getValue());
	} // end of the setBaudRate method

	@FXML
	void setDataBits(ActionEvent event) {
		// get the setting the user wants
		String temp = dataBits.getValue();
		// set up a switch statement to set the data bits port parameter
		switch (temp) {
		case ("5"):
			databit = 5;
			break;
		case ("6"):
			databit = 6;
			break;
		case ("7"):
			databit = 7;
			break;
		case ("8"):
			databit = 8;
			break;
		} // end of the switch case statement
	}

	@FXML
	void setFlowControl(ActionEvent event) {
		// get the setting the user wants
		String temp = flowControl.getValue();
		// set up a switch statement to set the data flow control port parameter
		switch (temp) {
		case ("RTS/CTS"):
			flowcontrol = SerialPort.FLOW_CONTROL_RTS_ENABLED;
			
			break;
		case ("XON/XOFF"):
			flowcontrol = SerialPort.FLOW_CONTROL_XONXOFF_IN_ENABLED;
			break;
		case ("None"):
			flowcontrol = SerialPort.FLOW_CONTROL_DISABLED;
			break;
		} // end of the switch case statement
	}

	@FXML
	void setParity(ActionEvent event) {
		// get the setting the user wants
		String temp = parity.getValue();
		// set up a switch statement to set the parity port parameter
		switch (temp) {
		case ("Even"):
			paritee = SerialPort.EVEN_PARITY;
			break;
		case ("Odd"):
			paritee = SerialPort.ODD_PARITY;
			break;
		case ("None"):
			paritee = SerialPort.NO_PARITY;
			break;
		case ("Space"):
			paritee = SerialPort.SPACE_PARITY;
			break;
		case ("Mark"):
			paritee = SerialPort.MARK_PARITY;
			break;
		} // end of the switch case statement
	}

	@FXML
	void setStopBits(ActionEvent event) {
		// get the setting the user wants
		String temp = stopBits.getValue();
		// set up a switch statement to set the data stop bits port parameter
		switch (temp) {
		case ("1"):
			stopbit = SerialPort.ONE_STOP_BIT;
			break;
		case ("1.5"):
			stopbit = SerialPort.ONE_POINT_FIVE_STOP_BITS;
			break;
		case ("2"):
			stopbit = SerialPort.TWO_STOP_BITS;
			break;
		} // end of the switch case statement
	} // end of the setStopBits method

	@FXML
	void handleSelectPort(ActionEvent event) {
		// get the string name of the serial port
		selectedPort = availablePorts.getValue();
		// set up the CommPortIdentifier(Communications port management) for the
		// selected port.
		serialPort = portMap.get(selectedPort);
		System.out.println(serialPort);
	} // end of the handleSelectPort method

	@FXML
	void connect(ActionEvent event) throws Exception {
		if (btnConnect.getText().contains("Connect")) {

			// set up the comm port and initialized it to null
			
					// try to open the selected port with the timeout restriction of 2 seconds
					serialPort.openPort();
				
				
				
					// Set the baud rate, number of data bits, the stop bits, and no parity checking
					serialPort.setComPortParameters(baud, databit, stopbit, paritee);
					// set up a switch statement to set the user's desired flow control parameter
					switch (flowcontrol) {
					case (1):
						//serialPort.setFlowControlMode(
								//SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
						break;
					case (4):
						//serialPort.setFlowControlMode(
								//SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
						break;
					case (0):
						//serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
						break;
					}
				
			try {
				// start the output stream
				output = serialPort.getOutputStream();
				// start the input stream now that we have sent our commands to Grbl
				input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			} catch (Exception eb) {
				eb.printStackTrace();
			}
			try {
				// add an event listener to the serial port so we don't have to poll and data is
				// always received
				// this method is better than polling and uses less processing power
				serialPort.addDataListener(this);
				// when data is available notify the serialEvent method
				//serialPort.notifyOnDataAvailable(true);
				// catch the error of trying to add too many event listeners to the serial
				// port(only one is allowed)
			} catch (Exception ec) {
				ec.printStackTrace();
			} // end of try/catch statement
				// set the text of the button
			btnConnect.setText("Disconnect");
		} else {
			// call the disconnect method of this class
			disconnect();
			// set the text of the button
			btnConnect.setText("Connect");
		} // end of the if statement
	}// end of the connect method

	@FXML
	void handleClearText(ActionEvent event) {
		// clear the text in the text area
		setFileText("");
	} // end of the handleClearText method

	@FXML
	void openHelp(ActionEvent event) {		
		// set up the stage for the settings window
		Stage helpStage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Help.fxml"));
		// load the controls for the window
		Parent help;
		try {
			help = (Parent) fxmlLoader.load();
			// make the window always on top of open windows
			helpStage.setAlwaysOnTop(true);
			// set the title for the window
			helpStage.setTitle("Serial Communications Help");
			// set the icon for the window
			helpStage.getIcons().add(new Image("/images/icon.png"));
			// set the scene for the stage
			helpStage.setScene(new Scene(help));
			// finally display the window to the user
			helpStage.show();
		} catch (IOException e) {
		}
	} // end of the openHelp method

	@FXML
	void loadParameters(ActionEvent event) {
		// create a new file chooser window
		FileChooser openFile = new FileChooser();
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		openFile.getExtensionFilters().add(extFilter);
		// show the save file window and set up a file variable
		File openedFile = openFile.showOpenDialog(null);
		// make sure the filed name saved is not null
		if (openedFile != null) {
			// create a new Gson object
			Gson gson = new Gson();
			//set up a new instance of the preferences class
			UserPreferences pref = new UserPreferences();
			try {
				// get the preferences from the settings file
				pref = gson.fromJson(new FileReader(openedFile), UserPreferences.class);	
				// set the instance of this class to the settings we just read from the file
				UserPreferences.setInstance(pref);
				// set all of the user's preferences
				baudRate.setValue(pref.getBaudRate());
				dataBits.setValue(pref.getDataBits());
				flowControl.setValue(pref.getFlowControl());
				parity.setValue(pref.getParity());
				stopBits.setValue(pref.getStopBits());
				
			} catch (JsonSyntaxException e) {					
			} catch (JsonIOException e) {				
			} catch (FileNotFoundException e) {					
			} // end of the try/catch statement	
		} // end of the if statement
	} // end of the loadParameters method

	@FXML
	void saveParameters(ActionEvent event) {
		// set up a variable for writing data to the file
		Writer writer = null;
		try {
			// set up a Gson builder
			GsonBuilder builder = new GsonBuilder();
			// initialize the gson builder
			Gson gson = builder.create();
			// set all of the user's preferences
			UserPreferences.getInstance().setBaudRate(baudRate.getValue());
			UserPreferences.getInstance().setDataBits(dataBits.getValue());
			UserPreferences.getInstance().setFlowControl(flowControl.getValue());
			UserPreferences.getInstance().setParity(parity.getValue());
			UserPreferences.getInstance().setStopBits(stopBits.getValue());
			// get the instance of the UserPreference class
			UserPreferences preference = UserPreferences.getInstance();
			// create a new file chooser window
			FileChooser saveFile = new FileChooser();
			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
			saveFile.getExtensionFilters().add(extFilter);
			// show the save file window and set up a file variable
			File savedFile = saveFile.showSaveDialog(null);
			// make sure the filed name saved is not null
			if (savedFile != null) {
				writer = new FileWriter(savedFile);
				// convert the class object to a json object
				String jsonString = gson.toJson(preference);
				// write the object to the file
				writer.write(jsonString);
				// close the file
				writer.close();
			}
		} catch (IOException e) {
		} // end of try/catch statement
	} // end of the saveParameters method

	@FXML
	void handleSaveFile(ActionEvent event) throws IOException {
		// create a new file chooser window
		FileChooser saveFile = new FileChooser();
		// show the save file window and set up a file variable
		File savedFile = saveFile.showSaveDialog(null);
		// make sure the filed name saved is not null
		if (savedFile != null) {
			// create the file if it doesn't exist
			savedFile.createNewFile();
			// set up the output stream
			OutputStream outToFile = new FileOutputStream(savedFile);
			// convert the string to a byte[] and then write it to the file
			outToFile.write(getFileText().getText().getBytes());
			// we are done with the file so close the output stream
			outToFile.close();
		} // end of the if statement
	} // end of the handSaveFile method

	@FXML
	void handleSendFile(ActionEvent event) {
		try {
			// convert our data into a byte array
			byte[] data = getFileText().getText().getBytes();
			// send the data to the machine
			output.write(data);
			// clear the output buffer and force the data to be sent
			output.flush();
			// give our output time to clear
			Thread.sleep(20);
		} catch (IOException | InterruptedException | NullPointerException e) {
		} // end of the try/catch statement
	} // end of the handleSendFile method

	@FXML
	void loadFile(ActionEvent event) throws Exception {
		// set up the instance of a file chooser to open files on the computer
		FileChooser myfile = new FileChooser();
		// add a filter so all files may be viewed to open
		myfile.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		// add the open button to the file chooser window
		File selectedFile = myfile.showOpenDialog(null);
		// check to make sure a file has been selected
		if (selectedFile != null) {
			// Set up a scanner to get the text from the file
			Scanner input = new Scanner(selectedFile.getAbsoluteFile());
			// clear the list if a previous file was loaded
			setFileText("");
			// loop through the file if we are not at the end of the file
			while (input.hasNextLine()) {
				// add the line of text to the text area in the GUI
				fileText.appendText(input.nextLine().toUpperCase() + "\r\n");
				// increment the counter
			} // end of while loop
				// close the file stream being read
			input.close();
		}
	} // end of the loadFile method

	@Override
	public int getListeningEvents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		// is our event a data available event?
				//if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
					//try {
						// check to see if the port is ready and available to get data from
						//if (input.ready() && (input.lines() != null)) {
							// get the line of data from the serial port
							//String data = input.readLine();
							//Platform.runLater(() -> {
								// append the line of text to the text area
								//FXMLController.instance.getFileText().appendText(data + "\r\n");
							//});
						//} // end of the if statement
					//} catch (Exception ex) {
					//} // end of the try/catch statement
				//} // end of the if statement
			} // end of the serial event method

}// end of the FXMLController class