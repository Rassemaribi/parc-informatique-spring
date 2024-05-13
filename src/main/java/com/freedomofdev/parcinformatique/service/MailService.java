package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeAcquisition;
import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.User;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.Recipient;
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

    // confirmation email
    public void sendConfirmationEmail(User user) {

        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Bienvenue au parc informatique";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("ynrw7gyv13kg2k8e");
        email.AddVariable("prenom", user.getPrenom());
        email.AddVariable("username", user.getUsername());
        email.AddVariable("password", user.getPassword());


        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {

            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {

            e.printStackTrace();
        }
    }

    // acceptance email
    public void sendAcceptanceEmail(User user, DemandeReparation demandeReparation) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Votre demande de réparation a été acceptée";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);
        email.setTemplateId("zr6ke4n5mwv4on12");
        String etat = "Votre demande de réparation " + String.valueOf(demandeReparation.getReference()) + " pour l'actif "+  String.valueOf(demandeReparation.getActif().getNom())+ " a été acceptée. Nous allons nous occuper de votre demande dans les plus brefs délais.";
        email.AddVariable( "etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    // rejection email
    public void sendRejectionEmail(User user, DemandeReparation demandeReparation) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Votre demande de réparation a été rejetée";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("zr6ke4n5mwv4on12");
        String etat = "Votre demande de réparation " + String.valueOf(demandeReparation.getReference()) + " pour l'actif "+  String.valueOf(demandeReparation.getActif().getNom())+ " a été refusée pour le motif suivant "+demandeReparation.getMotifRejet();
        email.AddVariable( "etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    public void sendRepairSuccessEmail(User user, DemandeReparation demandeReparation) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Votre réparation a été effectuée avec succès";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("zr6ke4n5mwv4on12");
        String etat = "Votre actif "+ String.valueOf(demandeReparation.getActif().getNom())+ " concerné par la demande de réparation " + String.valueOf(demandeReparation.getReference()) + " a été réparé avec succès. Vous pouvez maintenant le récupérer.";
        email.AddVariable( "etat", etat);
        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    // MailService.java

    public void sendRepairFailureEmail(User user, DemandeReparation demandeReparation) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Votre réparation n'a pas pu être effectuée";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("zr6ke4n5mwv4on12");
        String etat = "Votre actif "+ String.valueOf(demandeReparation.getActif().getNom())+ " concerné par la demande de réparation " + String.valueOf(demandeReparation.getReference()) + " n'a pas pu etre réparé. Vous pouvez nous contacter pour demander l'acquisition d'un nouvel actif.";
        email.AddVariable( "etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    // MailService.java

    public void sendReminderEmail(User user, DemandeReparation demandeReparation) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Rappel: Demande de réparation en retard";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("zr6ke4n5mwv4on12");
        String etat = "La demande de réparation " + String.valueOf(demandeReparation.getReference()) + " pour l'actif "+  String.valueOf(demandeReparation.getActif().getNom())+ " est en retard. Merci de prendre les mesures nécessaires.";
        email.AddVariable( "etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    // MailService.java

    public void sendAcquisitionAcceptanceEmail(User user, DemandeAcquisition demandeAcquisition) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Your acquisition request has been accepted";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("template_id_for_acceptance");
        String etat = "Your acquisition request " + demandeAcquisition.getReference() + " has been accepted.";
        email.AddVariable("etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    public void sendAcquisitionRejectionEmail(User user, DemandeAcquisition demandeAcquisition) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Your acquisition request has been rejected";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("template_id_for_rejection");
        String etat = "Your acquisition request " + demandeAcquisition.getReference() + " has been rejected for the following reason: " + demandeAcquisition.getRejectionReason();
        email.AddVariable("etat", etat);

        MailerSend ms = new MailerSend();
        ms.setToken(mailerSendApiKey);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

    public void sendAcquisitionNotificationEmail(User user, DemandeAcquisition demandeAcquisition) {
        Email email = new Email();

        email.setFrom("Freedom Of Dev Services", mailDomain);

        email.subject = "Your acquisition request is pending";

        Recipient recipient = new Recipient(user.getUsername(), user.getEmail());

        email.AddRecipient(recipient);

        email.setTemplateId("template_id_for_notification");
        String etat = "Your acquisition request " + demandeAcquisition.getReference() + " is pending.";
        email.AddVariable("etat", etat);

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
