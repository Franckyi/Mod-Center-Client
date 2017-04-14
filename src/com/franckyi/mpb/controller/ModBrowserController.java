package com.franckyi.mpb.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.SortFilter;
import com.franckyi.mpb.core.tasks.ModBrowserListTask;
import com.franckyi.mpb.core.tasks.SearchModsTask;
import com.franckyi.mpb.view.region.LoadingPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ModBrowserController implements Initializable {

	public MCVersion currentMcVersion;
	public SortFilter currentSortFilter;
	public String currentSearchText;
	
	public int page = 1;
	public int maxPage;

	@FXML
	private Label pageNumber;

	@FXML
	private JFXComboBox<SortFilter> sortFilter;

	@FXML
	private JFXComboBox<MCVersion> mcVersion;
	
	@FXML
	public JFXTextField search;

	@FXML
	public ScrollPane modBrowserScrollPane;

	@FXML
	private JFXButton first, previous, next, last;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mcVersion.getItems().addAll(MCVersion.values());
		mcVersion.setValue(MCVersion.ALL);
		sortFilter.getItems().addAll(SortFilter.values());
		sortFilter.setValue(SortFilter.MONTHLY_DL);
		currentMcVersion = mcVersion.getValue();
		currentSortFilter = sortFilter.getValue();
		modBrowserScrollPane.setContent(new LoadingPane());
		modBrowserScrollPane.setStyle(
				"-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: 0, 1, 2; -fx-background-radius: 5, 4, 3;");
		first.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		previous.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		next.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		last.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
		updateModBrowser();
	}

	@FXML
	void mcVersionChanged(ActionEvent event) {
		if (!mcVersion.getValue().equals(currentMcVersion)) {
			currentMcVersion = mcVersion.getValue();
			page = 1;
			updateModBrowser();
		}
	}

	@FXML
	void sortFilterChanged(ActionEvent event) {
		if (!sortFilter.getValue().equals(currentSortFilter)) {
			currentSortFilter = sortFilter.getValue();
			updateModBrowser();
		}
	}
	
	 @FXML
	 void searchFilterChanged(KeyEvent event) {
		 if(event.getCode().equals(KeyCode.ENTER)){
			 currentSearchText = search.getText();
			 Thread t = new Thread(new SearchModsTask(currentSearchText));
			 t.setName("SearchModsTask");
			 t.start();
		 }
	 }

	@FXML
	void firstPage(ActionEvent event) {
		if (page > 1) {
			page = 1;
			updateModBrowser();
		}
	}

	@FXML
	void previousPage(ActionEvent event) {
		if (page > 1) {
			page--;
			updateModBrowser();
		}
	}

	@FXML
	void nextPage(ActionEvent event) {
		if (page < maxPage) {
			page++;
			updateModBrowser();
		}
	}

	@FXML
	void lastPage(ActionEvent event) {
		if (page < maxPage) {
			page = maxPage;
			updateModBrowser();
		}
	}

	public void updateModBrowser() {
		lock(true);
		modBrowserScrollPane.setContent(new LoadingPane());
		pageNumber.setText("Page n°" + page);
		Thread task = new Thread(new ModBrowserListTask(currentMcVersion, currentSortFilter, page, this));
		task.setName("ModBrowserListTask");
		task.start();
	}

	public void lock(boolean b) {
		mcVersion.setDisable(b);
		sortFilter.setDisable(b);
		search.setDisable(b);
		first.setDisable(b);
		previous.setDisable(b);
		next.setDisable(b);
		last.setDisable(b);
	}

	public void updateButtons() {
		modBrowserScrollPane.requestFocus();
		if (page == maxPage) {
			next.setDisable(true);
			last.setDisable(true);
		}
		if (page == 1) {
			first.setDisable(true);
			previous.setDisable(true);
		}
	}

}
