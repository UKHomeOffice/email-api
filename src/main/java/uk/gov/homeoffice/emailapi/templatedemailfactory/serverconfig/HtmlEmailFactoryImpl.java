package uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

public class HtmlEmailFactoryImpl implements HtmlEmailFactory {

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final boolean sslOnConnect;
    private final boolean startTslEnabled;
    private final boolean requireTsl;

    public HtmlEmailFactoryImpl(String hostname, int port, String username, String password, boolean sslOnConnect, boolean startTslEnabled, boolean requireTsl) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
        this.sslOnConnect = sslOnConnect;
        this.startTslEnabled = startTslEnabled;
        this.requireTsl = requireTsl;
    }

    public HtmlEmail getHtmlEmail() {
        HtmlEmail email = new HtmlEmail();

        email.setHostName(hostname);
        email.setSmtpPort(port);

        if (username != null && password != null && !username.isEmpty()) {
            email.setAuthenticator(new DefaultAuthenticator(username, password));
        }

        email.setSSLOnConnect(sslOnConnect);
        email.setStartTLSEnabled(startTslEnabled);
        email.setStartTLSRequired(requireTsl);

        return email;
    }
}
