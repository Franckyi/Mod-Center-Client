package com.franckyi.modcenter.client;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModCenterClient extends Application {

	public static ModCenterClient INSTANCE;
	public MCCConfig config;
	public Stage mainStage;
	public Map<FXMLFile, Parent> parents = new HashMap<>();

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

	@Override
	public void start(Stage primaryStage) throws Exception {
		INSTANCE = this;
		loadFXML(FXMLFile.MOD_BROWSER);
		loadFXML(FXMLFile.SETTINGS);
		loadFXML(FXMLFile.MAIN);
		mainStage = primaryStage;
		mainStage.setScene(new Scene(parents.get(FXMLFile.MAIN)));
		mainStage.setResizable(false);
		mainStage.setTitle(MCCReference.TITLE + " v" + MCCReference.VERSION);
		mainStage.show();
		print("Showing main window");
	}

	public static void main(String[] args) {
		print(MCCReference.TITLE + " v" + MCCReference.VERSION + " by " + MCCReference.AUTHOR);
		DataFiles.MOD_CACHE_FOLDER.mkdirs();
		MCCConfig.initConfig();
		try {
			ModCenterAPI.init();
			print("Mod Center API initialized");
		} catch (ClassNotFoundException | SQLException e) {
			printErr("Error while initializing Mod Center API");
			e.printStackTrace();
		}
		launch(args);
	}

	public static void print(String str) {
		System.out
				.println("[" + dtf.format(LocalDateTime.now()) + "] {" + Thread.currentThread().getName() + "} " + str);
	}

	public static void printErr(String str) {
		System.err
				.println("[" + dtf.format(LocalDateTime.now()) + "] {" + Thread.currentThread().getName() + "} " + str);
	}

	public void loadFXML(FXMLFile file) throws IOException {
		print("Loading FXML : " + file.url);
		FXMLLoader loader = new FXMLLoader(getClass().getResource(file.url));
		Parent root = loader.load();
		root.setUserData(loader.getController());
		parents.put(file, root);
	}

}
