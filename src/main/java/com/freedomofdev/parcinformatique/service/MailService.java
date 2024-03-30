package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.User;
import com.mailersend.sdk.Recipient;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${mail_token}")
    private String mailerSendApiKey;

    @Value("${mail_domain}")
    private String mailDomain;

    public void sendConfirmationEmail(User user) {

        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Confirmation de votre inscription sur le parc informatique";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("zr6ke4nz3je4on12");

        email.AddVariable(recipient,"username", user.getUsername());

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {

            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {

            e.printStackTrace();
        }
    }
}
