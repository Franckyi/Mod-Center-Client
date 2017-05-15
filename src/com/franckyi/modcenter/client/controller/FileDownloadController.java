package com.franckyi.modcenter.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FileDownloadController implements Initializable {
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

    @FXML
    private VBox root;

    @FXML
    private Label projectName;

    @FXML
    private VBox requiredLibraries;

    @FXML
    private VBox optionalLibraries;

    @FXML
    void close(ActionEvent event) {
    	
    }

}
