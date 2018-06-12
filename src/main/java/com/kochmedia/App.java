package com.kochmedia;

import org.apache.log4j.Logger;
import java.util.*;
import javax.mail.Message;

import com.kochmedia.Mail;
import com.kochmedia.Report;
import com.kochmedia.Config;

/**
 * Hello world!
 *
 */
public class App 
{
	final static Logger logger = Logger.getLogger(App.class);
    final static Config config = new Config();

    public static void main( String[] args )
    {
    	logger.debug("Checking " + config.get("receive.email") + " for new mails...");

    	//get new mails
    	List<Email> emails = Mail.getNewMessages();

        if(emails.size() == 0){
            logger.debug("No new messages... Terminating...");
            return;
        }

        for (Email email : emails){
            logger.debug("Subject: " + email.subject);
            logger.debug("From: " + email.from);
        }

    	logger.debug("Received " + emails.size() + " new messages... Processing...");

    	//filter mails
    	List<AppStatus> apps = Filter.filter(emails);

        logger.debug("Found " + apps.size() + " application reports...");

        logger.debug("Building report...");
    	//make report
    	String report = Report.generate(apps);

        logger.debug("Sending report...");
    	//send report
    	Boolean sendStatus = Mail.send(report);

        logger.debug("Setting messages read...");
    	//set messages read
    	if (sendStatus == true){
    		Boolean setReadStatus = Mail.setRead(emails);
    	}

        logger.debug( emails.size() + " emails processed... Report send... Have a nice day..." );
    }
}
