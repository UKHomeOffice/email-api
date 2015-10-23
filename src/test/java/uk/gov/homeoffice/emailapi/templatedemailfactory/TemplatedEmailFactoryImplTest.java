package uk.gov.homeoffice.emailapi.templatedemailfactory;

import org.apache.commons.mail.HtmlEmail;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmail;
import uk.gov.homeoffice.emailapi.entities.TemplatedEmailImpl;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParser;
import uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing.InternetAddressParsingException;
import uk.gov.homeoffice.emailapi.templatedemailfactory.serverconfig.HtmlEmailFactory;
import uk.gov.homeoffice.emailapi.templatedemailfactory.templating.TemplatePopulator;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class TemplatedEmailFactoryImplTest {
    private TemplatePopulator templatePopulator;
    private ArrayList<InternetAddress> internetAddresses;
    private TemplatedEmailFactoryImpl templatedEmailFactory;


    @Before
    public void setupSubject() throws InternetAddressParsingException {
        templatePopulator = mock(TemplatePopulator.class);

        internetAddresses = new ArrayList<>();

        InternetAddressParser internetAddressParser = mock(InternetAddressParser.class);
        when(internetAddressParser.getInternetAddresses(Matchers.<Collection<String>>any()))
            .thenReturn(internetAddresses);


        HtmlEmailFactory htmlEmailFactory = mock(HtmlEmailFactory.class);
        when(htmlEmailFactory.getHtmlEmail()).thenReturn(mock(HtmlEmail.class, CALLS_REAL_METHODS));

        templatedEmailFactory =
            new TemplatedEmailFactoryImpl(templatePopulator, internetAddressParser,
                htmlEmailFactory);
    }

    @Test
    public void test_it_sets_the_recipients() throws Exception {
        when(templatePopulator
            .populateTemplate(Matchers.<String>any(), Matchers.<Map<String, Object>>any()))
            .thenReturn("Template Contents");

        internetAddresses.add(new InternetAddress("test@example.com"));

        TemplatedEmail input =
            new TemplatedEmailImpl(new ArrayList<>(), "sender@example.com", "Subject",
                "html template", new HashMap<>(), "text template");
        HtmlEmail output = templatedEmailFactory.build(input);

        assertThat(output.getToAddresses(), equalTo(internetAddresses));
    }

    @Test
    public void test_it_sets_from() throws Exception {
        when(templatePopulator
            .populateTemplate(Matchers.<String>any(), Matchers.<Map<String, Object>>any()))
            .thenReturn("Template Contents");

        internetAddresses.add(new InternetAddress("test@example.com"));

        TemplatedEmail input =
            new TemplatedEmailImpl(new ArrayList<>(), "sender@example.com", "Subject",
                "html template", new HashMap<>(), "text template");
        HtmlEmail output = templatedEmailFactory.build(input);
        InternetAddress expected = new InternetAddress("sender@example.com");

        assertThat(output.getFromAddress(), equalTo(expected));
    }

    @Test
    public void test_it_sets_subject() throws Exception {
        when(templatePopulator
            .populateTemplate(Matchers.<String>any(), Matchers.<Map<String, Object>>any()))
            .thenReturn("Template Contents");

        internetAddresses.add(new InternetAddress("test@example.com"));

        TemplatedEmail input =
            new TemplatedEmailImpl(new ArrayList<>(), "sender@example.com", "Subject",
                "html template", new HashMap<>(), "text template");
        HtmlEmail output = templatedEmailFactory.build(input);

        assertThat(output.getSubject(), equalTo("Subject"));
    }

    @Test
    public void test_it_sets_html_template() throws Exception {
        when(templatePopulator.populateTemplate(anyString(), Matchers.<Map<String, Object>>any()))
            .thenReturn("Template Contents");

        internetAddresses.add(new InternetAddress("test@example.com"));

        TemplatedEmail input =
            new TemplatedEmailImpl(new ArrayList<>(), "sender@example.com", "Subject",
                "html template", new HashMap<>(), "text template");
        HtmlEmail output = templatedEmailFactory.build(input);

        verify(output).setHtmlMsg("Template Contents");
    }

    @Test
    public void test_it_sets_text_template() throws Exception {
        when(templatePopulator
            .populateTemplate(any(String.class), Matchers.<Map<String, Object>>any()))
            .thenReturn("Template Contents");

        internetAddresses.add(new InternetAddress("test@example.com"));

        TemplatedEmail input =
            new TemplatedEmailImpl(new ArrayList<>(), "sender@example.com", "Subject",
                "html template", new HashMap<>(), "text template");
        HtmlEmail output = templatedEmailFactory.build(input);

        verify(output).setTextMsg("Template Contents");
    }
}
