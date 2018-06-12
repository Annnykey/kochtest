package com.kochmedia;

import java.util.*;
import javax.mail.*;
import javax.mail.search.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;

import com.kochmedia.Config;
import com.kochmedia.Email;

public class Mail {

	final static Logger logger = Logger.getLogger(Mail.class);
	final static Config config = new Config();

   	public static List<Email> getNewMessages() 
   	{
   		List<Email> emails = new ArrayList<>();

		try {
			String host = config.get("receive.host");
			String storeType = config.get("receive.storeType");
			String user = config.get("receive.email");
			String password = config.get("receive.password");
			String folder = config.get("receive.folder");

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

			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

			for (int i = 0, n = messages.length; i < n; i++) {
	            Message message = messages[i];
	            Email email = new Email();
	            try{
	            	email.from = message.getFrom()[0].toString();
	            	email.subject = message.getSubject().toString();
	            	email.body = message.getContent().toString();
	            }catch(Exception e){
	                logger.error("Failed to parse email...");
	            }
	            emails.add(email);
	        }

			//close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (AuthenticationFailedException e) {
			logger.error("Invalid email credentials!");
		} catch (Exception e) {
			logger.error("Error reading messages!");
			e.printStackTrace();
		}

		return emails;
	}

	//TODO redo to use local mail
	public static Boolean send(String report){
		String host = config.get("send.host");
		String from = config.get("send.from");
		String pass = config.get("send.password");
		String recipient = config.get("send.recipient");
		String subject = config.get("send.subject");

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);

		//gmail 
		properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", from);
        properties.put("mail.smtp.password", pass);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");


		Session session = Session.getDefaultInstance(properties);
		MimeMessage message = new MimeMessage(session);

		try {	
			InternetAddress toAddress = new InternetAddress(recipient);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setText(report);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
		} catch (Exception e) {
			logger.error("Sending report failed!");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean setRead(List<Email> emails){
		return true;
	}

}