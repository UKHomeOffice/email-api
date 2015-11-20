package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.junit.Test;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorIOException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorParsingException;

import static org.mockito.Mockito.*;

public class TemplatedEmailSenderImplTest {

    @Test
    public void itGetsATemplatedEmailAndSendsIt()
        throws TemplatePopulatorParsingException, InternetAddressParsingException,
        TemplatePopulatorIOException, EmailException {
        final TemplatedEmail mockTemplatedEmail = mock(TemplatedEmail.class);
        final Email mockEmail = mock(Email.class);
        final TemplatedEmailFactory mockFactory = mock(TemplatedEmailFactory.class);
        when(mockFactory.build(mockTemplatedEmail)).thenReturn(mockEmail);

        new TemplatedEmailSenderImpl(mockFactory).sendEmail(mockTemplatedEmail);

        verify(mockEmail).send();
    }


}
