package com.kochmedia;

import org.apache.log4j.Logger;
import com.kochmedia.AppStatus;
import com.kochmedia.AppStatus.Status;
import javax.mail.Message;
import java.util.*;

public class Filter 
{
	final static Logger logger = Logger.getLogger(Filter.class);

	public static List<AppStatus> filter(Message[] messages){

		List<AppStatus> appStatusList = new ArrayList<>();

		for (int i = 0, n = messages.length; i < n; i++) {
			Message message = messages[i];
			AppStatus appStatus = getAppStatus(message);
			appStatusList.add(appStatus);
		}

		return appStatusList;
	}

	private static AppStatus getAppStatus(Message message){
		AppStatus appStatus = new AppStatus();
		try{
			appStatus.name = message.getFrom()[0].toString();
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