package com.kochmedia;

import java.util.Date;

public class AppStatus 
{
	public enum Status {
    	SUCCESS, FAILED, CRASHED
	}

	public String name;
	public Date date;
	public Status status;
	public String comment;
}