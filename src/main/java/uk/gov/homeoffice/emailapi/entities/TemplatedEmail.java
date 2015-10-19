package uk.gov.homeoffice.emailapi.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.Map;

@ApiModel(description = "Enough information to populate and send and email based on a template")
@JsonDeserialize(as = TemplatedEmailImpl.class)
@JsonSerialize(as = TemplatedEmailImpl.class)
public interface TemplatedEmail {
    @ApiModelProperty(value = "Sender of the email in format \"example@example.org\" or \"Annie Example <annie@example.com>\"", required = true)
    String getSender();

    @ApiModelProperty(value = "Subject of the email", required = true)
    String getSubject();

    @ApiModelProperty(value = "If a recipient uses HTML to view their they will see this template", required = true)
    String getHtmlTemplate();

    @ApiModelProperty(value = "If a recipient uses TXT to view their they will see this template", required = true)
    String getTextTemplate();

    @ApiModelProperty(value = "Variables to pass to both templates with string keys, and any JSON Number, String, Array, Object as the value    ", required = true)
    Map<String, Object> getVariables();

    @ApiModelProperty(value = "Array of recipients in an array of strings in format \"example@example.org\" or \"Annie Example <annie@example.com>\"", required = true)
    Collection<String> getRecipients();
}
