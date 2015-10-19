package uk.gov.homeoffice.emailapi.service;

import org.apache.commons.mail.Email;
import org.junit.Test;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactory;

import static org.mockito.Mockito.*;

public class TemplatedEmailSenderImplTest {

    @Test
    public void it_gets_a_templated_email_and_sends_it() throws Exception {
        TemplatedEmail mockTemplatedEmail = mock(TemplatedEmail.class);
        Email mockEmail = mock(Email.class);
        TemplatedEmailFactory mockFactory = mock(TemplatedEmailFactory.class);
        when(mockFactory.build(mockTemplatedEmail)).thenReturn(mockEmail);

        new TemplatedEmailSenderImpl(mockFactory).sendEmail(mockTemplatedEmail);

        verify(mockEmail).send();
    }


}