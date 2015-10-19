package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.EmailException;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactoryException;

public interface TemplatedEmailSender {
    void sendEmail(TemplatedEmail templatedEmail) throws TemplatedEmailFactoryException, EmailException;
}
