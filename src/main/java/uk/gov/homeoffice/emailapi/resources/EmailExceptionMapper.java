package uk.gov.homeoffice.emailapi.resources;

import org.apache.commons.mail.EmailException;
import org.slf4j.LoggerFactory;
import uk.gov.homeoffice.emailapi.entities.EmailApiStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EmailExceptionMapper implements ExceptionMapper<EmailException> {

    private static final org.slf4j.Logger LOGGER =
        LoggerFactory.getLogger(EmailExceptionMapper.class);


    @Override
    public Response toResponse(EmailException exception) {
        LOGGER.error("Failed to connect to SMTP server", exception);

        return Response.status(EmailApiStatus.SendingEmailFailed)
            .encoding(MediaType.APPLICATION_JSON).entity(EmailApiStatus.SendingEmailFailed).build();
    }
}
