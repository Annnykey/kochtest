package com.kochmedia;

import com.kochmedia.AppStatus;
import org.apache.log4j.Logger;
import java.util.*;

public class Report 
{
	final static Logger logger = Logger.getLogger(Report.class);

	public static String generate(List<AppStatus> appStatusList){
		String report = "";
		for (int i = 0; i < appStatusList.size(); i++) {
    		AppStatus appStatus = appStatusList.get(i);
    		report += "\n " + appStatus.name + " " + appStatus.status;
    	}

    	logger.debug("Report: " + report);

		return report;
	}
}