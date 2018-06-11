package com.kochmedia;

import org.apache.log4j.Logger;
import com.kochmedia.Mail;
import com.kochmedia.Report;
import java.util.*;

import javax.mail.Message;

/**
 * Hello world!
 *
 */
public class App 
{
	final static Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
    	logger.debug("This is debug!");

    	//get new mails
    	Message[] messages = Mail.get();

    	logger.debug("Received " + messages.length + " new messages");

    	//filter mails
    	List<AppStatus> apps = Filter.filter(messages);

    	//make report
    	String report = Report.generate(apps);

    	//send report
    	Boolean sendStatus = Mail.send(report);

    	//set messages read
    	if (sendStatus == true){
    		Boolean setReadStatus = Mail.setRead(messages);
    	}

        System.out.println( "Report send!" );
    }
}
