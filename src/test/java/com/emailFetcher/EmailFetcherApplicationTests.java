package com.emailFetcher;
package com.example;

import javax.mail.*;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailFetcherApplicationTests {

	public void fetchEmails() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");

        // Replace 'your-email@example.com' and 'your-password' with your email credentials
        store.connect("imap.your-email-provider.com", 993, "your-email@example.com", "your-password");

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        int messageCount = inbox.getMessageCount();
        int startIndex = Math.max(1, messageCount - 200 + 1); // Fetch the last 200 emails

        Message[] messages = inbox.getMessages(startIndex, messageCount);

        for (Message message : messages) {
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("Sent Date: " + message.getSentDate());
            System.out.println("---------------------------");
        }

        inbox.close(false);
        store.close();
    }

    public static void main(String[] args) {
        EmailFetcherService emailFetcherService = new EmailFetcherService();
        try {
            emailFetcherService.fetchEmails();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
