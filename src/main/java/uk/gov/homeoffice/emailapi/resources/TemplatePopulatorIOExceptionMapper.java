package uk.gov.homeoffice.emailapi.resources;

import org.slf4j.LoggerFactory;
import uk.gov.homeoffice.emailapi.entities.EmailApiStatus;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorIOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TemplatePopulatorIOExceptionMapper
    implements ExceptionMapper<TemplatePopulatorIOException> {

    private static final org.slf4j.Logger LOGGER =
        LoggerFactory.getLogger(TemplatePopulatorIOException.class);

    @Override
    public Response toResponse(TemplatePopulatorIOException exception) {
        LOGGER.warn("Attempting to load non-existent template", exception);

        return Response.status(EmailApiStatus.TemplateUnreadable)
            .entity(EmailApiStatus.TemplateUnreadable).encoding(MediaType.APPLICATION_JSON).build();
    }
}
