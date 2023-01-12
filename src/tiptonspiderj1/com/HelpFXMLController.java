package tiptonspiderj1.com;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpFXMLController implements Initializable {
	
	 @FXML
	 private WebView webView;
	 // set up an engine for loading web pages
	 private WebEngine engine;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the engine variable
		engine = webView.getEngine();
		Platform.runLater(() -> {
			// load the wiki page to explain serial communication
			engine.load("https://en.wikipedia.org/wiki/Serial_port#Settings");
		});
		
	}

}
