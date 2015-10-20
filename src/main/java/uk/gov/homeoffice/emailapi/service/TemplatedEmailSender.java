package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.EmailException;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorIOException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorParsingException;

public interface TemplatedEmailSender {
    void sendEmail(TemplatedEmail templatedEmail)
        throws EmailException, TemplatePopulatorParsingException, TemplatePopulatorIOException,
        InternetAddressParsingException;
}
