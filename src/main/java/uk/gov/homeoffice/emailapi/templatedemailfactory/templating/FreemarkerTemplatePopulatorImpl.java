package uk.gov.homeoffice.emailapi.templatedemailfactory.templating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerTemplatePopulatorImpl implements TemplatePopulator {
    private final Configuration freemarkerConfig;

    public FreemarkerTemplatePopulatorImpl(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String populateTemplate(String templateName, Map<String, Object> variables)
        throws TemplatePopulatorIOException, TemplatePopulatorParsingException {
        try {
            final Template template = freemarkerConfig.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(variables, out);

            String parsedTemplate = out.toString();

            if (parsedTemplate.trim().isEmpty()) {
                // Emails can never be empty
                throw new TemplatePopulatorParsingException(
                    "Template is Empty, emails can never be empty");
            }

            return parsedTemplate;
        } catch (IOException e) {
            throw new TemplatePopulatorIOException(e);
        } catch (TemplateException e) {
            throw new TemplatePopulatorParsingException(e);
        }
    }
}
