package uk.gov.homeoffice.emailapi.templatedemailfactory.templating;

public class TemplatePopulatorParsingException extends Exception {
    public TemplatePopulatorParsingException(Throwable cause) {
        super(cause);
    }

    public TemplatePopulatorParsingException(String message) {
        super(message);
    }
}
