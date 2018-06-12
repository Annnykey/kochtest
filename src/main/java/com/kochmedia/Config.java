package com.kochmedia;

import java.util.Properties;
import java.io.*;

import org.apache.log4j.Logger;

public class Config {
	final static Logger logger = Logger.getLogger(Mail.class);
	final static Properties properties = new Properties();
	final String configPath = "src/main/resources/config.properties";

	public Config(){
		try {
			InputStream input = new FileInputStream(configPath);
			properties.load(input);
		} catch (FileNotFoundException e){
			logger.error("Config file not found " + configPath);
			System.exit(1);
		} catch (IOException e) {
			logger.error("Failed loading config file " + configPath);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String get(String key){
		return properties.getProperty(key);
	}
}