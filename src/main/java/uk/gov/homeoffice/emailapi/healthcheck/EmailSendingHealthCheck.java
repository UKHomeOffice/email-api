package uk.gov.homeoffice.emailapi.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import org.apache.commons.mail.HtmlEmail;
import uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig.HtmlEmailFactory;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;

public class EmailSendingHealthCheck extends HealthCheck {
    private final HtmlEmailFactory htmlEmailFactory;

    public EmailSendingHealthCheck(HtmlEmailFactory htmlEmailFactory) {
        this.htmlEmailFactory = htmlEmailFactory;
    }

    @Override
    protected Result check() throws Exception {
        try {
            HtmlEmail testEmail = htmlEmailFactory.getHtmlEmail();
            testEmail.setMsg("Testing");
            testEmail.setSubject("Testing");
            testEmail.setFrom("health-check@example.com");

            InternetAddress testToAddress = new InternetAddress("health-check@example.net");
            ArrayList<InternetAddress> toAddresses = new ArrayList<>();
            toAddresses.add(testToAddress);

            testEmail.setTo(toAddresses);
            testEmail.send();

            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy(e);
        }
    }
}