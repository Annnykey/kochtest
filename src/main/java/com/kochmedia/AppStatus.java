package com.kochmedia;

import java.util.Date;

public class AppStatus 
{
	public AppStatus(String appname){
		name = appname;
		status = Status.SUCCESS;
		date = new Date();
	}

	public enum Status {
    	SUCCESS, FAILED, CRASHED
	}

	public String name;
	public Date date;
	public Status status;
	public String comment;
}