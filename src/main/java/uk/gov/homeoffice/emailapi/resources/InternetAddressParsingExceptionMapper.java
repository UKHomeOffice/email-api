package uk.gov.homeoffice.emailapi.resources;

import org.slf4j.LoggerFactory;
import uk.gov.homeoffice.emailapi.entities.EmailApiStatus;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing.InternetAddressParsingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternetAddressParsingExceptionMapper
    implements ExceptionMapper<InternetAddressParsingException> {

    private static final org.slf4j.Logger LOGGER =
        LoggerFactory.getLogger(InternetAddressParsingExceptionMapper.class);

    @Override
    public Response toResponse(InternetAddressParsingException exception) {
        LOGGER.warn("Invalid recipient email passed to API", exception);

        return Response.status(EmailApiStatus.InvalidEmail).encoding(MediaType.APPLICATION_JSON)
            .entity(EmailApiStatus.InvalidEmail).build();
    }
}
