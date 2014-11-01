/*
 * Created by Chris Hoffman on 2014.10.31  * 
 * Copyright © 2014 Chris Hoffman. All rights reserved. * 
 */

package com.brigreen.sessionbeanpackage;

import com.brigreen.planneroftheapes.Reminders;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionManager;

/**
 *
 * @author Chris
 */
@Singleton
public class MailReminder {
    
    @PersistenceContext 
    private EntityManager em;
    
    @Resource 
    //private TransactionManager tx;

    @Schedule(dayOfWeek = "*", month = "*", hour = "13", dayOfMonth = "*", year = "*", minute = "20", second = "0", persistent = false) 
    public void myTimer() {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate1 = df1.format(new Date());
        List<Reminders> reminderlist = (List<Reminders>) em.
                createNamedQuery("Reminders.natfindByDate").setParameter(1, 
                formattedDate1).getResultList();
        for(Reminders reminder : reminderlist)
        {
            this.businessMethod(reminder.getFirstName(), reminder.getTitle(), 
                    reminder.getEmail(), reminder.getText());
            em.createNativeQuery("DELETE FROM reminders WHERE id = ?").setParameter(1, reminder.getId()).executeUpdate();
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void businessMethod(String name, String subject, String email, String body) {
        final String username = "planneroftheapes@gmail.com";
		final String password = "monkeymonkey123";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("planneroftheapes@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject("Reminder from Planner of the Apes: " + subject);
			message.setText("Hello " + name + ","
				+ "\n\n Planner of the Apes just wanted to remind you that:"
                                + "\n\t" + "\""+ body +"\"");
 
			Transport.send(message);
 
			System.out.println("Reminder Sent to " + email);
 
		} catch (MessagingException e) {
			System.out.println("Reminder Not Sent - Failure");
		}
    }
    
}
