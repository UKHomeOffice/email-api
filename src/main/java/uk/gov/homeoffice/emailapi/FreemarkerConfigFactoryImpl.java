package uk.gov.homeoffice.emailapi;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class FreemarkerConfigFactoryImpl implements FreemarkerConfigFactory {
    public Configuration buildFreemarkerConfig(String templateDirectory) throws FreemarkerConfigException {
        Configuration freemarker = new Configuration(Configuration.VERSION_2_3_22);

        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDirectory));
        } catch (IOException e) {
            throw new FreemarkerConfigException("Could not load template directory \"" + templateDirectory + "\".", e);
        }

        freemarker.setDefaultEncoding("UTF-8");
        freemarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return freemarker;
    }
}
