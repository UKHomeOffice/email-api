package uk.gov.homeoffice.emailapi.resources;

import org.slf4j.LoggerFactory;
import uk.gov.homeoffice.emailapi.entities.EmailApiStatus;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorParsingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TemplatePopulatorParsingExceptionMapper
    implements ExceptionMapper<TemplatePopulatorParsingException> {


    private static final org.slf4j.Logger LOGGER =
        LoggerFactory.getLogger(TemplatePopulatorParsingExceptionMapper.class);

    @Override
    public Response toResponse(TemplatePopulatorParsingException exception) {
        LOGGER.error("Template is broken", exception);

        return Response.status(EmailApiStatus.TemplateInvalid).encoding(MediaType.APPLICATION_JSON)
            .entity(EmailApiStatus.TemplateInvalid).build();
    }
}
