package com.franckyi.modcenter.client.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.enums.EnumCategory;
import com.franckyi.modcenter.api.beans.enums.EnumSortFilter;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.core.tasks.ModBrowserListTask;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;
import com.franckyi.modcenter.client.view.region.LoadingPane;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ModBrowserController implements Initializable {

	public String currentMcVersion;
	public EnumSortFilter currentSortFilter;
	public boolean currentSortOrder = false;
	public String currentSearchText;
	public EnumCategory currentCategory;

	public int page = 1;
	public int maxPage;

	public boolean searching;

	@FXML
	private Label pageNumber;

	@FXML
	private JFXComboBox<EnumSortFilter> sortFilter;

	@FXML
	private ImageView sortOrder;

	@FXML
	private JFXComboBox<String> mcVersion;

	@FXML
	public JFXTextField search;

	@FXML
	public JFXComboBox<EnumCategory> categories;

	@FXML
	public ScrollPane modBrowserScrollPane;

	@FXML
	private JFXButton first, previous, next, last;

	@FXML
	public TabPane tabPane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mcVersion.getItems().add("");
		try {
			mcVersion.getItems().addAll(ModCenterAPI.getVersions());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mcVersion.setValue("");
		sortFilter.getItems().addAll(EnumSortFilter.values());
		sortFilter.setValue(EnumSortFilter.TOTAL_DL);
		sortOrder.setImage(new Image(getClass().getResourceAsStream("../view/img/arrow-down.png")));
		categories.getItems().addAll(EnumCategory.values());
		categories.setValue(EnumCategory.ANY);
		currentMcVersion = mcVersion.getValue();
		currentSortFilter = sortFilter.getValue();
		currentCategory = categories.getValue();
		currentSearchText = "";
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
			page = 1;
			updateModBrowser();
		}
	}

	@FXML
	void sortOrderChanged(MouseEvent event) {
		if (!searching) {
			if (currentSortOrder) {
				currentSortOrder = false;
				sortOrder.setImage(new Image(getClass().getResourceAsStream("../view/img/arrow-down.png")));
			} else {
				currentSortOrder = true;
				sortOrder.setImage(new Image(getClass().getResourceAsStream("../view/img/arrow-up.png")));
			}
			page = 1;
			updateModBrowser();
		}
	}

	@FXML
	void searchFilterChanged(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			currentSearchText = search.getText();
			page = 1;
			updateModBrowser();
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

	@FXML
	void categoryChanged(ActionEvent event) {
		if (currentCategory != categories.getValue()) {
			currentCategory = categories.getValue();
			page = 1;
			updateModBrowser();
		}
	}

	public void updateModBrowser() {
		modBrowserScrollPane.setVvalue(0);
		lock(true);
		modBrowserScrollPane.setContent(new LoadingPane());
		pageNumber.setText("Page n°" + page);
		Thread nextTask = new Thread(new ModBrowserListTask(this));
		nextTask.setName("ModBrowserListTask");
		nextTask.start();
	}

	public void lock(boolean b) {
		searching = b;
		mcVersion.setDisable(b);
		sortFilter.setDisable(b);
		search.setDisable(b);
		categories.setDisable(b);
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

	public void addTab(Parent root, Project project) {
		for (Tab tab : tabPane.getTabs())
			if (tab.getUserData() != null && tab.getUserData().equals(project.getProjectId())) {
				tab.setContent(root);
				tabPane.getSelectionModel().select(tab);
				return;
			}
		Tab tab = new Tab();
		tab.setContent(root);
		tab.setText(project.getName());
		tab.setUserData(project.getProjectId());
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab);
	}

	public static ModBrowserController get() {
		return (ModBrowserController) ModCenterClient.INSTANCE.parents.get(FXMLFile.MOD_BROWSER).getUserData();
	}

}
