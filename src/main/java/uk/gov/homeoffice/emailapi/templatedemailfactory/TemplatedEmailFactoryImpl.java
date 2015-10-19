package uk.gov.homeoffice.emailapi.templatedemailfactory;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParser;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig.HtmlEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulator;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorException;

import javax.mail.internet.InternetAddress;
import java.util.Collection;

public class TemplatedEmailFactoryImpl implements TemplatedEmailFactory {

    private final TemplatePopulator templateEngine;
    private final InternetAddressParser addressParser;
    private final HtmlEmailFactory htmlEmailFactory;

    public TemplatedEmailFactoryImpl(TemplatePopulator templateEngine, InternetAddressParser addressParser, HtmlEmailFactory htmlEmailFactory) {
        this.templateEngine = templateEngine;
        this.addressParser = addressParser;
        this.htmlEmailFactory = htmlEmailFactory;
    }

    public HtmlEmail build(TemplatedEmail templatedEmail) throws TemplatedEmailFactoryException {
        try {
            HtmlEmail email = htmlEmailFactory.getHtmlEmail();

            Collection<InternetAddress> recipients = addressParser.getInternetAddresses(templatedEmail.getRecipients());
            email.setTo(recipients);

            email.setFrom(templatedEmail.getSender());
            email.setSubject(templatedEmail.getSubject());

            String htmlProcessedTemplate = templateEngine.populateTemplate(templatedEmail.getHtmlTemplate(), templatedEmail.getVariables());
            email.setHtmlMsg(htmlProcessedTemplate);

            String txtProcessedTemplate = templateEngine.populateTemplate(templatedEmail.getTextTemplate(), templatedEmail.getVariables());
            email.setTextMsg(txtProcessedTemplate);

            return email;
        } catch (InternetAddressParsingException | TemplatePopulatorException | EmailException e) {
            throw new TemplatedEmailFactoryException(e);
        }
    }
}
