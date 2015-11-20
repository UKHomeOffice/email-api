package uk.gov.homeoffice.emailapi;

import freemarker.template.Configuration;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import uk.gov.homeoffice.emailapi.healthcheck.EmailSendingHealthCheck;
import uk.gov.homeoffice.emailapi.resources.EmailExceptionMapper;
import uk.gov.homeoffice.emailapi.resources.EmailSendingResource;
import uk.gov.homeoffice.emailapi.resources.InternetAddressParsingExceptionMapper;
import uk.gov.homeoffice.emailapi.resources.TemplatePopulatorIOExceptionMapper;
import uk.gov.homeoffice.emailapi.resources.TemplatePopulatorParsingExceptionMapper;
import uk.gov.homeoffice.emailapi.service.TemplatedEmailSender;
import uk.gov.homeoffice.emailapi.service.TemplatedEmailSenderImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.TemplatedEmailFactoryImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing.InternetAddressParser;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing.InternetAddressParserImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig.HtmlEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig.HtmlEmailFactoryImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.FreemarkerTemplatePopulatorImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulator;

public class EmailApiApplication extends Application<EmailApiConfiguration> {
    private final FreemarkerConfigFactoryImpl freemarkerConfigFactory =
        new FreemarkerConfigFactoryImpl();

    public static void main(String[] args) throws Exception {
        new EmailApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "email-api";
    }

    @Override
    public void initialize(Bootstrap<EmailApiConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor()));

        bootstrap.addBundle(new SwaggerBundle<EmailApiConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                EmailApiConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });

    }

    @Override
    public void run(EmailApiConfiguration configuration, Environment environment)
        throws FreemarkerConfigException {
        environment.jersey().register(InternetAddressParsingExceptionMapper.class);
        environment.jersey().register(TemplatePopulatorIOExceptionMapper.class);
        environment.jersey().register(TemplatePopulatorParsingExceptionMapper.class);
        environment.jersey().register(EmailExceptionMapper.class);

        Configuration freemarkerConfig =
            freemarkerConfigFactory.buildFreemarkerConfig(configuration.getEmailTemplatePath());

        TemplatePopulator freemarkerTemplateParser =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfig);
        InternetAddressParser internetAddressParser = new InternetAddressParserImpl();
        HtmlEmailFactory htmlEmailFactory =
            new HtmlEmailFactoryImpl(configuration.getHostname(), configuration.getPort(),
                configuration.getUsername(), configuration.getPassword(),
                configuration.getOnSslConnect(), configuration.getStartTslEnabled(),
                configuration.getRequireTsl());

        TemplatedEmailFactory templatedEmailFactory =
            new TemplatedEmailFactoryImpl(freemarkerTemplateParser, internetAddressParser,
                htmlEmailFactory);
        TemplatedEmailSender templatedEmailSender =
            new TemplatedEmailSenderImpl(templatedEmailFactory);

        final EmailSendingResource resource = new EmailSendingResource(templatedEmailSender);
        environment.jersey().register(resource);
        environment.healthChecks()
            .register("smtp-server", new EmailSendingHealthCheck(htmlEmailFactory));
    }

}
