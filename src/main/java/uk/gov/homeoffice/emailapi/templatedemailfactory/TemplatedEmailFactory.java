package uk.gov.homeoffice.emailapi.templatedemailfactory;

import org.apache.commons.mail.Email;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;

public interface TemplatedEmailFactory {
    Email build(TemplatedEmail templatedEmail) throws TemplatedEmailFactoryException;
}
