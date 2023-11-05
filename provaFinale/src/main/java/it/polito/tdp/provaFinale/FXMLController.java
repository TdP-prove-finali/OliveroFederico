package it.polito.tdp.provaFinale;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.provaFinale.model.Istance;
import it.polito.tdp.provaFinale.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class FXMLController{
    
	Model model;
	
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
	    void doSchedule(ActionEvent event) {
	    	
	    	List<List<Istance>> schedulated = model.scheduleIstances();
//	    	for(int i=0; i<50; i++) {
//	    		
//	    		String toPrint = "";
//	    		
//	    		boolean endFlag = false;
//	    		
//	    		for(int k=0; k<schedulated.size(); k++) {
//	    			
//	    			if(schedulated.get(k).size() > i) {
//	    				toPrint+=schedulated.get(k).get(i).toString()+"                          ";
//	    				endFlag = true;
//	    			}
//	    			else
//	    				toPrint+="-------------------"+"                          ";
//
//	    		}
//	    		
//	    		toPrint.strip();
//	    		toPrint+="\n";
//	    		
//	    		this.outputTextArea.appendText(toPrint);
//	    		
//	    		if(!endFlag)
//	    			break;
//	    	}
	    	
	    	TableView table = new TableView();
	    	
	    }
	    
	    @FXML
	    void doReset(ActionEvent event) {
	    	
	    	this.outputTextArea.clear();
	    	this.emissioniCheckBox.setSelected(false);
	    	this.riscattiCheckBox.setSelected(false);
	    	this.switchCheckBox.setSelected(false);
	    	this.schedulingCheckBox.setSelected(false);
	    	this.stabilitySlider.setValue(0);
	    }

	    @FXML
	    void doSimulation(ActionEvent event) {

	    }

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

		public void setModel(Model model) {
			// TODO Auto-generated method stub
			this.model = model;
			
		}
    
       
}
