package com.kochmedia;

import org.apache.log4j.Logger;
import javax.mail.Message;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import com.kochmedia.AppStatus;
import com.kochmedia.Email;
import com.kochmedia.Config;
import com.kochmedia.AppStatus.Status;

public class Filter 
{
	final static Logger logger = Logger.getLogger(Filter.class);
	final static Config config = new Config();

	public static List<AppStatus> filter(List<Email> emails){
		JSONParser parser = new JSONParser();

		try{
			Object filterConfig = parser.parse(config.get("filter"));
			JSONArray filterArray = (JSONArray)filterConfig;
			for (int i = 0; i < filterArray.size(); i++) {
				logger.debug(filterArray.get(i));
			}
		}catch(ParseException e){
			logger.error("Error parsing filter settings from config!");
			logger.debug(e);
		}

		List<AppStatus> appStatusList = new ArrayList<>();

		for (Email email : emails) {
			AppStatus appStatus = getAppStatus(email);
			appStatusList.add(appStatus);
		}

		return appStatusList;
	}

	private static AppStatus getAppStatus(Email email){
		AppStatus appStatus = new AppStatus();
		try{
			appStatus.name = email.from;
			appStatus.status = Status.SUCCESS;
		} catch (Exception e) {
			logger.error("Error parsing message!");
			e.printStackTrace();
		}

		//TODO
		//if (message.subject == "SUCCESS"){
		//	AppStatus appStatus = new AppStatus();
		//	appStatus.status = Status.SUCCESS;
		//}
		return appStatus;
	}
}