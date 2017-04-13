package com.franckyi.mpb;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.franckyi.mpb.core.data.DataFiles;
import com.franckyi.mpb.core.data.cache.ModBrowserCache;
import com.franckyi.mpb.view.fxml.FXMLFile;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MPBApplication extends Application {

	public static final String TITLE = "Modpack Builder";
	public static final String VERSION = "0.1.0";
	public static final String AUTHOR = "Franckyi";

	public static MPBApplication INSTANCE;
	public MPBConfig config;
	public Stage mainStage, secondaryStage = new Stage();
	public ModBrowserCache cache = new ModBrowserCache();
	public Map<FXMLFile, Parent> parents = new HashMap<>();

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

	@Override
	public void start(Stage primaryStage) throws Exception {
		INSTANCE = this;
		for (FXMLFile file : FXMLFile.values()) {
			print("Loading FXML : " + file.url);
			if (file.equals(FXMLFile.MOD_BROWSER)) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(file.url));
				Parent root = loader.load();
				root.setUserData(loader.getController());
				parents.put(file, root);
			} else
				parents.put(file, FXMLLoader.load(getClass().getResource(file.url)));
		}
		secondaryStage.setResizable(false);
		secondaryStage.setAlwaysOnTop(true);
		mainStage = primaryStage;
		mainStage.setScene(new Scene(parents.get(FXMLFile.MAIN)));
		mainStage.setResizable(false);
		mainStage.setTitle("Modpack Builder 0.1.0");
		mainStage.show();
		print("Showing main window");
	}

	public static void main(String[] args) {
		print(TITLE + " v" + VERSION + " by " + AUTHOR);
		DataFiles.MOD_CACHE_FOLDER.mkdirs();
		MPBConfig.initConfig();
		launch(args);
	}

	public static void print(String str) {
		System.out
				.println("[" + dtf.format(LocalDateTime.now()) + "] {" + Thread.currentThread().getName() + "} " + str);
	}

}
