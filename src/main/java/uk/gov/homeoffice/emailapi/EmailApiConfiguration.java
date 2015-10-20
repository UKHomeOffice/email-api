package uk.gov.homeoffice.emailapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailApiConfiguration extends Configuration {

    @JsonProperty("swagger") public SwaggerBundleConfiguration swaggerBundleConfiguration;

    private String emailTemplatePath;
    private String hostname;
    private int port;
    private String username;
    private String password;
    private boolean onSslConnect;
    private boolean startTslEnabled;
    private boolean requireTsl;

    @NotEmpty
    @JsonProperty
    public String getEmailTemplatePath() {
        return emailTemplatePath;
    }

    @NotEmpty
    @JsonProperty
    public String getHostname() {
        return hostname;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public boolean getOnSslConnect() {
        return onSslConnect;
    }

    @JsonProperty
    public boolean getStartTslEnabled() {
        return startTslEnabled;
    }

    @JsonProperty
    public boolean getRequireTsl() {
        return requireTsl;
    }
}
