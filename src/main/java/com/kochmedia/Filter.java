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
		List<AppStatus> appStatusList = new ArrayList<>();
		JSONParser parser = new JSONParser();

		for (Email email : emails) {
			try{
				JSONArray filter = (JSONArray)parser.parse(config.get("filter"));
				for (int i = 0; i < filter.size(); i++) {
					JSONObject app = (JSONObject)filter.get(i);
					String appName = (String) app.get("name");
					JSONObject appFilter = (JSONObject)app.get("filter");
					String subject = (String) appFilter.get("subject");
					logger.debug(subject);
					//String from = appFilter.get("from");
					//String body = appFilter.get("body");
					if(email.subject.equals(subject)){
						AppStatus appStatus = new AppStatus();
						appStatus.name = appName;
						//TODO add status to filter
						appStatus.status = Status.SUCCESS;
						//TODO add date
						appStatusList.add(appStatus);
					}
				}
			}catch(Exception e){
				logger.error("Error parsing filter settings from config!");
				logger.debug(e);
			}
		}

		return appStatusList;
	}
}