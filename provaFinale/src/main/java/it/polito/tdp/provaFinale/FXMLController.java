package it.polito.tdp.provaFinale;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.provaFinale.model.Istance;
import it.polito.tdp.provaFinale.model.Model;
import it.polito.tdp.provaFinale.model.RowIstances;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
	    private TableView<RowIstances> table = new TableView<RowIstances>();
	    
	    @FXML
	    private TableColumn<RowIstances, String> col1;

	    @FXML
	    private TableColumn<RowIstances, String> col2;

	    @FXML
	    private TableColumn<RowIstances, String> col3;

	    @FXML
	    private TableColumn<RowIstances, String> col4;

	    @FXML
	    private TableColumn<RowIstances, String> col5;
	    
	    @FXML
	    void doSchedule(ActionEvent event) {
	    	
	    	List<RowIstances> toPrint = model.scheduleIstances();
	    	
	    	 ObservableList<RowIstances> data = FXCollections.observableArrayList(toPrint);
	    	
	    	//lavoro sulla tabella
	    	this.table.setEditable(true);
	    	
	    	table.getColumns().clear();
	    	
	    	col1 = new TableColumn<RowIstances, String>("UFT-ONE-1");
	        col1.setMinWidth(146);
	    	col1.setCellValueFactory(
	                new PropertyValueFactory<RowIstances, String>("i1"));
	    	
	    	col2 = new TableColumn<RowIstances, String>("UFT-ONE-2");
	        col2.setMinWidth(146);
	    	col2.setCellValueFactory(
	                new PropertyValueFactory<RowIstances, String>("i2"));
	    	
	    	col3 = new TableColumn<RowIstances, String>("UFT-ONE-3");
	        col3.setMinWidth(146);
	    	col3.setCellValueFactory(
	                new PropertyValueFactory<RowIstances, String>("i3"));
	    	
	    	col4 = new TableColumn<RowIstances, String>("UFT-ONE-4");
	        col4.setMinWidth(146);
	    	col4.setCellValueFactory(
	                new PropertyValueFactory<RowIstances, String>("i4"));
	    	
	    	col5 = new TableColumn<RowIstances, String>("UFT-ONE-5");
	        col5.setMinWidth(146);
	    	col5.setCellValueFactory(
	                new PropertyValueFactory<RowIstances, String>("i5"));
	    	
	    	table.setItems(data);
	        table.getColumns().addAll(col1, col2, col3, col4, col5);
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
	        assert col1 != null : "fx:id=\"col1\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	        assert col2 != null : "fx:id=\"col2\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	        assert col3 != null : "fx:id=\"col3\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	        assert col4 != null : "fx:id=\"col4\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	        assert col5 != null : "fx:id=\"col5\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'InterfacciaGrafica.fxml'.";
	    }

		public void setModel(Model model) {
			// TODO Auto-generated method stub
			this.model = model;
			
		}
    
       
}
