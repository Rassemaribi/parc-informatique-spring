package com.freedomofdev.parcinformatique.service;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail_token}")
    private String mailerSendApiKey;

    @Value("${mail_domain}")
    private String mailDomain;

    public void sendConfirmationEmail(String to, String subject, String text) {
        try {
            Email email = new Email();
            email.subject = subject;
            email.text = text;
            email.addRecipient("User", to);
            email.setFrom("Freedom Of Dev Services", mailDomain);

            MailerSend ms = new MailerSend();
            ms.setToken(mailerSendApiKey);

            MailerSendResponse response = ms.emails().send(email);

            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }
}
