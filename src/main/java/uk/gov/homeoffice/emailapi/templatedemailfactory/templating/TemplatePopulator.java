package uk.gov.homeoffice.emailapi.templatedemailfactory.templating;

import java.util.Map;

public interface TemplatePopulator {
    String populateTemplate(String template, Map<String, Object> variables) throws TemplatePopulatorException;
}
