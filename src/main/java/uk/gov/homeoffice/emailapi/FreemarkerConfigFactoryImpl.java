package uk.gov.homeoffice.emailapi;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class FreemarkerConfigFactoryImpl implements FreemarkerConfigFactory {
    public Configuration buildFreemarkerConfig(String templateDirectory) {
        Configuration freemarker = new Configuration(Configuration.VERSION_2_3_22);

        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDirectory));
        } catch (IOException e) {
            System.err.println("Could not load template directory \"" + templateDirectory + "\".");
            System.err.println();
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
            System.exit(2);
        }

        freemarker.setDefaultEncoding("UTF-8");
        freemarker.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return freemarker;
    }
}