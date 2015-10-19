package uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig;

import org.apache.commons.mail.HtmlEmail;

public interface HtmlEmailFactory {
    HtmlEmail getHtmlEmail();
}
