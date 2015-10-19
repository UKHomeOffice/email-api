package uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HtmlEmailFactoryImplTest {

    @Test
    public void test_it_sets_server_config() throws Exception {
        HtmlEmailFactoryImpl subject = new HtmlEmailFactoryImpl(
                "localhost",
                25,
                "",
                "",
                false,
                true,
                false
        );

        assertThat(subject.getHtmlEmail().getHostName(), equalTo("localhost"));
        assertThat(subject.getHtmlEmail().getSmtpPort(), equalTo("25"));
        assertThat(subject.getHtmlEmail().isSSLOnConnect(), equalTo(false));
        assertThat(subject.getHtmlEmail().isStartTLSEnabled(), equalTo(true));
        assertThat(subject.getHtmlEmail().isStartTLSRequired(), equalTo(false));
    }
}