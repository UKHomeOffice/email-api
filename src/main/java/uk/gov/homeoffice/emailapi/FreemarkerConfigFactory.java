package uk.gov.homeoffice.emailapi;

import freemarker.template.Configuration;

public interface FreemarkerConfigFactory {
    Configuration buildFreemarkerConfig(String templateDirectory);
}
