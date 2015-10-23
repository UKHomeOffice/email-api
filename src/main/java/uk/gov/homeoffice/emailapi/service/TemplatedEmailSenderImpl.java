package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.EmailException;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorIOException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorParsingException;

public class TemplatedEmailSenderImpl implements TemplatedEmailSender {
    private final TemplatedEmailFactory templatedEmailFactory;

    public TemplatedEmailSenderImpl(TemplatedEmailFactory templatedEmailFactory) {
        this.templatedEmailFactory = templatedEmailFactory;
    }

    @Override
    public void sendEmail(TemplatedEmail templatedEmail)
        throws EmailException, TemplatePopulatorParsingException, TemplatePopulatorIOException,
        InternetAddressParsingException {
        templatedEmailFactory.build(templatedEmail).send();
    }
}
