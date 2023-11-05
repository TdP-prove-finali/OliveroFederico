package it.polito.tdp.provaFinale;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class FXMLController{
    
	 @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private AnchorPane Ancor;

	    @FXML
	    private CheckBox emissioniCheckBox;

	    @FXML
	    private TextArea outputTextArea;

	    @FXML
	    private Button resetButton;

	    @FXML
	    private CheckBox riscattiCheckBox;

	    @FXML
	    private Button scheduleButton;

	    @FXML
	    private CheckBox schedulingCheckBox;

	    @FXML
	    private Button simulateButton;

	    @FXML
	    private Slider stabilitySlider;

	    @FXML
	    private CheckBox switchCheckBox;

	    @FXML
	    void initialize() {
	        assert Ancor != null : "fx:id=\"Ancor\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert emissioniCheckBox != null : "fx:id=\"emissioniCheckBox\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert outputTextArea != null : "fx:id=\"outputTextArea\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert riscattiCheckBox != null : "fx:id=\"riscattiCheckBox\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert scheduleButton != null : "fx:id=\"scheduleButton\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert schedulingCheckBox != null : "fx:id=\"schedulingCheckBox\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert simulateButton != null : "fx:id=\"simulateButton\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert stabilitySlider != null : "fx:id=\"stabilitySlider\" was not injected: check your FXML file 'Scene.fxml'.";
	        assert switchCheckBox != null : "fx:id=\"switchCheckBox\" was not injected: check your FXML file 'Scene.fxml'.";

	    }
    
       
}
