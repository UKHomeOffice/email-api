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

    public String populateTemplate(String templateName, Map<String, Object> variables) throws TemplatePopulatorException {
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            StringWriter out = new StringWriter();
            template.process(variables, out);

            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new TemplatePopulatorException(e);
        }
    }
}
