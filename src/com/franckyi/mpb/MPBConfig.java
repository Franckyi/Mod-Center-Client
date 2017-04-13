package com.franckyi.mpb;

import java.io.IOException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import com.franckyi.mpb.core.data.DataFiles;

public class MPBConfig {

	private static Configurations configs;
	private static FileBasedConfigurationBuilder<PropertiesConfiguration> builder;
	private static Configuration cfg;

	public static void initConfig() {
		configs = new Configurations();
		loadConfig();
	}

	public static void loadConfig() {
		try {
			builder = configs.propertiesBuilder(DataFiles.CONFIG_FILE);
			DataFiles.CONFIG_FILE.createNewFile();
			cfg = builder.getConfiguration();
			for (EnumConfig config : EnumConfig.values())
				if (!cfg.containsKey(config.key)) {
					MPBApplication.print("Generating default configuration file");
					defaults();
					builder.save();
				}
		} catch (ConfigurationException | IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean getBoolean(EnumConfig config) {
		if (cfg.containsKey(config.key))
			return Boolean.parseBoolean(cfg.getProperty(config.key).toString());
		return false;
	}

	public static Integer getInteger(EnumConfig config) {
		return Integer.parseInt(cfg.getProperty(config.key).toString());
	}

	public static String getString(EnumConfig config) {
		return cfg.getProperty(config.key).toString();
	}

	public static void setProperty(EnumConfig config, Object value) {
		cfg.setProperty(config.key, value);
	}

	public static void defaults() {
		for (EnumConfig config : EnumConfig.values())
			setProperty(config, config.value);
	}

	public static void saveConfig() {
		try {
			builder.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	public enum EnumConfig {

		displayModsThumbnail(true);

		public String key;
		public Object value;

		EnumConfig(Object value) {
			this.key = this.toString();
			this.value = value;
		}
	}

}