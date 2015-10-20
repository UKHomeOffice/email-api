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
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactoryException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        @ApiResponse(code = 400, message = "Invalid Email Template Request Sent, or Template in request does not exist"),
        @ApiResponse(code = 500, message = "Email failed to send")})
    @POST
    @Timed
    @ApiOperation("Send an email based on a template")
    public void sendEmail(@ApiParam("Email template and params") TemplatedEmail templatedEmail)
        throws WebApplicationException, EmailException {

        try {
            templatedEmailSender.sendEmail(templatedEmail);
        } catch (TemplatedEmailFactoryException e) {
            throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
        }
    }
}
