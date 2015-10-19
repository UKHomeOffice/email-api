package uk.gov.homeoffice.emailapi.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

public class TemplatedEmailImpl implements TemplatedEmail {

    @JsonProperty
    private final Collection<String> recipients;

    @JsonProperty
    private final String sender;

    @JsonProperty
    private final String subject;

    @JsonProperty
    private final String htmlTemplate;

    @JsonProperty
    private final Map<String, Object> variables;

    @JsonProperty
    private final String textTemplate;

    @JsonCreator
    public TemplatedEmailImpl(@JsonProperty("recipients") Collection<String> recipients,
                              @JsonProperty("sender") String sender,
                              @JsonProperty("subject") String subject,
                              @JsonProperty("htmlTemplate") String htmlTemplate,
                              @JsonProperty("variables") Map<String, Object> variables,
                              @JsonProperty("textTemplate") String textTemplate) {

        this.recipients = recipients;
        this.sender = sender;
        this.subject = subject;
        this.htmlTemplate = htmlTemplate;
        this.variables = variables;
        this.textTemplate = textTemplate;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public String getHtmlTemplate() {
        return htmlTemplate;
    }

    @Override
    public String getTextTemplate() {
        return textTemplate;
    }

    @Override
    public Map<String, Object> getVariables() {
        return variables;
    }

    @Override
    public Collection<String> getRecipients() {
        return recipients;
    }
}
