package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.EmailException;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactoryException;

public class TemplatedEmailSenderImpl implements TemplatedEmailSender {
    private final TemplatedEmailFactory templatedEmailFactory;

    public TemplatedEmailSenderImpl(TemplatedEmailFactory templatedEmailFactory) {
        this.templatedEmailFactory = templatedEmailFactory;
    }

    @Override
    public void sendEmail(TemplatedEmail templatedEmail) throws TemplatedEmailFactoryException, EmailException {
        templatedEmailFactory.build(templatedEmail).send();
    }
}
