package com.franckyi.mpb;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.franckyi.mpb.core.data.DataFiles;
import com.franckyi.mpb.core.json.JsonConfig;
import com.google.gson.Gson;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;

public class MPBConfig {

	private static Gson gson = new Gson();
	
	public static Property<Boolean> displayModsThumbnail =  new SimpleBooleanProperty(true);

	public static void init() {
		try {
			if(DataFiles.CONFIG_FILE.createNewFile()) {
				save();
			} else
				load();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void load() {
		try {
			JsonConfig cfg = gson.fromJson(new FileReader(DataFiles.CONFIG_FILE), JsonConfig.class);
			displayModsThumbnail.setValue(cfg.displayModsThumbnail);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		try {
			gson.toJson(new JsonConfig().setDisplayModsThumbnail(displayModsThumbnail.getValue()),
					new FileWriter(DataFiles.CONFIG_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reset() {
		displayModsThumbnail.setValue(true);
	}

}
