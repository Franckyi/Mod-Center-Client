package com.franckyi.modcenter.client.controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import com.franckyi.modcenter.api.EnumCategory;
import com.franckyi.modcenter.api.EnumSortFilter;
import com.franckyi.modcenter.client.MCCConfig;
import com.franckyi.modcenter.client.MCCConfig.EnumConfig;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.core.json.JsonModcache;
import com.franckyi.modcenter.client.core.json.JsonProject;
import com.franckyi.modcenter.client.core.json.MCCJson;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;
import com.franckyi.modcenter.client.view.region.ProjectVisual;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MyModsController implements Initializable {

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
	public ScrollPane myModsScrollPane;

	@FXML
	private JFXButton first, previous, next, last;

	public static JsonModcache projects;
	public List<JsonProject> fullList, curList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		updateProjects();
		sortProjects();
	}

	@FXML
	void mcVersionChanged(ActionEvent event) {
		if (!mcVersion.getValue().equals(currentMcVersion)) {
			currentMcVersion = mcVersion.getValue();
			page = 1;
			sortProjects();
		}
	}

	@FXML
	void sortFilterChanged(ActionEvent event) {
		if (!sortFilter.getValue().equals(currentSortFilter)) {
			currentSortFilter = sortFilter.getValue();
			page = 1;
			sortProjects();
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
			sortProjects();
		}
	}

	@FXML
	void searchFilterChanged(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			currentSearchText = search.getText();
			page = 1;
			sortProjects();
		}
	}

	@FXML
	void firstPage(ActionEvent event) {
		if (page > 1) {
			page = 1;
			sortProjects();
		}
	}

	@FXML
	void previousPage(ActionEvent event) {
		if (page > 1) {
			page--;
			sortProjects();
		}
	}

	@FXML
	void nextPage(ActionEvent event) {
		if (page < maxPage) {
			page++;
			sortProjects();
		}
	}

	@FXML
	void lastPage(ActionEvent event) {
		if (page < maxPage) {
			page = maxPage;
			sortProjects();
		}
	}

	@FXML
	void categoryChanged(ActionEvent event) {
		if (currentCategory != categories.getValue()) {
			currentCategory = categories.getValue();
			page = 1;
			sortProjects();
		}
	}

	public void updateProjects() {
		try {
			projects = MCCJson.getGson().fromJson(new FileReader(DataFiles.MOD_CACHE_FILE), JsonModcache.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fullList = new ArrayList<>(projects.values());
	}

	public void sortProjects() {
		curList = new ArrayList<>(fullList);
		curList.sort(new ProjectComparator(currentSortFilter, currentSortOrder));
		curList = curList.subList(MCCConfig.getInteger(EnumConfig.projectsPerPage) * page - 1,
				MCCConfig.getInteger(EnumConfig.projectsPerPage) * page);
		showProjects();
	}

	public void showProjects() {
		VBox vbox = new VBox();
		for (JsonProject project : curList) {
			vbox.getChildren().add(new Separator());
			vbox.getChildren().add(new ProjectVisual(project));
		}
		if (vbox.getChildren().isEmpty())
			vbox.getChildren().add(new Label("No projects found"));
		else
			vbox.getChildren().remove(0);
		myModsScrollPane.setContent(vbox);
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
		myModsScrollPane.requestFocus();
		if (page == maxPage) {
			next.setDisable(true);
			last.setDisable(true);
		}
		if (page == 1) {
			first.setDisable(true);
			previous.setDisable(true);
		}
	}

	public static MyModsController get() {
		return (MyModsController) ModCenterClient.INSTANCE.parents.get(FXMLFile.MY_MODS).getUserData();
	}

	public class ProjectComparator implements Comparator<JsonProject> {

		private EnumSortFilter filter;
		private boolean order;

		public ProjectComparator(EnumSortFilter currentSortFilter, boolean currentSortOrder) {
			filter = currentSortFilter;
			order = currentSortOrder;
		}

		@Override
		public int compare(JsonProject o1, JsonProject o2) {
			JsonProject p1 = (order) ? o1 : o2;
			JsonProject p2 = (order) ? o2 : o1;
			if (filter.equals(EnumSortFilter.NAME))
				return (p1.getName().compareTo(p2.getName()));
			if (filter.equals(EnumSortFilter.TOTAL_DL))
				return (p1.getDownloads() > p2.getDownloads()) ? -1 : 1;
			if (filter.equals(EnumSortFilter.CREATED))
				return (p1.getId() > p2.getId()) ? -1 : 1;
			if (filter.equals(EnumSortFilter.UPDATED))
				return (p1.getUpdated().compareTo(p2.getUpdated()));
			return 0;
		}

	}

}
