package com.kochmedia;

import java.util.Properties;
import javax.mail.*;
import javax.mail.search.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class Mail {

	final static Logger logger = Logger.getLogger(Mail.class);

   	public static Message[] getNewMessages() 
   	{
		//TODO: move to separate class
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("config.properties");
			// load a properties file
			prop.load(input);

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String host = prop.getProperty("receive.host");
			String storeType = prop.getProperty("receive.storeType");
			String user = prop.getProperty("receive.email");
			String password = prop.getProperty("receive.password");
			String folder = prop.getProperty("receive.folder");

			//create properties field
			//TODO unify with my properties
			Properties properties = new Properties();

			properties.put("mail.imap.host", host);
			properties.put("mail.imap.port", "995");
			properties.put("mail.imap.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			//create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("imaps");

			store.connect(host, user, password);

			//create the folder object and open it
			Folder emailFolder = store.getFolder(folder);

			logger.debug("No of Unread Messages : " + emailFolder.getUnreadMessageCount());
			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			logger.debug("messages.length : " + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];
				logger.debug("---------------------------------");
				logger.debug("Email Number " + (i + 1));
				logger.debug("Subject: " + message.getSubject());
				logger.debug("From: " + message.getFrom()[0]);
			}

			//close the store and folder objects
			emailFolder.close(false);
			store.close();

			return messages;

		} catch (AuthenticationFailedException e) {
			logger.error("Invalid email credentials!");
		} catch (Exception e) {
			logger.error("Error reading messages!");
			e.printStackTrace();
		}

		//return empty array if exception
		return new Message[0];
   }

   public static Boolean send(String report){
   		return true;
   }

   public static Boolean setRead(Message[] messages){
   		return true;
   }

}