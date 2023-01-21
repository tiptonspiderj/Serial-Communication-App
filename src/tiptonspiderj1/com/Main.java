package tiptonspiderj1.com;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	/***************************************************************************
	*                                 Variables                                *
	***************************************************************************/
	// create the only instance of the FXMLController
	FXMLController fxController = new FXMLController();
	// set up a variable for setting icons in alert messages
	public static Stage stage;
	// set up a variable for the scene to get nodes if needed
	private static Scene scene;

	/****************************************************************************
	*                             Methods/Functions                             *
	****************************************************************************/

	@Override // this method is automatically called when the arguments are launched
	public void start(Stage primaryStage) {
		// set our variable stage to the stage of the main window
		stage = primaryStage;
		try {
			// set up the source controller for the new window
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SerialCommunication.fxml"));
			// load the controls for the window
			Parent root = loader.load();
			// set the controller for the window
			fxController = loader.getController();
			// set the scene for the window
			scene = new Scene(root);
			// set the css file for the window
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// set the title for the window
			primaryStage.setTitle("Serial Communicator");
			// set the icon for the window
			primaryStage.getIcons().add(new Image("/images/icon.png"));
			// set the scene onto the stage for the window
			primaryStage.setScene(scene);
			// finally open the window for the user
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// add an action to close all windows when the application closes
		primaryStage.setOnCloseRequest(e -> {
			try {
				// make sure we disconnect the serial port
				FXMLController.getInstance().disconnect();
			} catch (NullPointerException ex) {
			}
			// force the application to close
			Platform.exit();
		});
	} // end of the start method

	// this default method starts the entire program
	public static void main(String[] args) {
		launch(args);
	}

} // end of the Main class
