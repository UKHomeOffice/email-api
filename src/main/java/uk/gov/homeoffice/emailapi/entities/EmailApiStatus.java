package uk.gov.homeoffice.emailapi.entities;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EmailApiStatus implements StatusType {
    InvalidEmail(422, "Invalid recipient email"),
    TemplateUnreadable(422, "Template does not exist"),
    TemplateInvalid(422, "Template Invalid"),
    SendingEmailFailed(Status.INTERNAL_SERVER_ERROR.getStatusCode(),
        "Failed sending email to SMTP server.");

    private final Status.Family family;

    private final String reason;

    private final int code;

    EmailApiStatus(final int statusCode, final String reasonPhrase) {
        this.code = statusCode;
        this.reason = reasonPhrase;
        this.family = Status.Family.familyOf(statusCode);
    }

    @JsonValue
    public Map<String, List<String>> toEntity() {

        Map<String, List<String>> map = new HashMap<>();
        List<String> errors = new ArrayList<>();
        errors.add(reason);
        map.put("errors", errors);

        return map;
    }

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public Status.Family getFamily() {
        return family;
    }

    @Override
    public String getReasonPhrase() {
        return reason;
    }
}
