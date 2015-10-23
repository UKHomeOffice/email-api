package uk.gov.homeoffice.emailapi.resources;

import com.codahale.metrics.annotation.Timed;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.commons.mail.EmailException;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.service.TemplatedEmailSender;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorIOException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulatorParsingException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/outbound")
@Api("/outbound")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmailSendingResource {

    private final TemplatedEmailSender templatedEmailSender;

    public EmailSendingResource(TemplatedEmailSender templatedEmailSender) {
        this.templatedEmailSender = templatedEmailSender;
    }

    @ApiResponses(value = {@ApiResponse(code = 204, message = "Successfully sent"),
        @ApiResponse(code = 442, message = "Posted entity is not valid in some way (See message for details)"),
        @ApiResponse(code = 500, message = "Email failed to send")})
    @POST
    @Timed
    @ApiOperation("Send an email based on a template")
    public void sendEmail(
        @ApiParam("Email template and params") @Valid TemplatedEmail templatedEmail)
        throws EmailException, TemplatePopulatorParsingException, TemplatePopulatorIOException,
        InternetAddressParsingException {

        templatedEmailSender.sendEmail(templatedEmail);
    }
}
