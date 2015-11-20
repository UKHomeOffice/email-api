package uk.gov.homeoffice.emailapi.templatedemailfactory.templating;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FreemarkerTemplatePopulatorImplTest {

    public static final String TEMPLATE_NAME = "template-name";

    @Test
    public void testItGetsContentOfTheTemplate()
        throws IOException, TemplateException, TemplatePopulatorParsingException,
        TemplatePopulatorIOException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate(TEMPLATE_NAME)).thenReturn(freemarkerTemplate);

        final Map<String, Object> expectedDataModel = new HashMap<>();

        doAnswer(invocation -> {
            Writer writer = (Writer) invocation.getArguments()[1];
            try {
                writer.write("Hello, world!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }).when(freemarkerTemplate).process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        final String actual = subject.populateTemplate(TEMPLATE_NAME, expectedDataModel);

        assertThat(actual, equalTo("Hello, world!"));
    }

    @Test(expected = TemplatePopulatorIOException.class)
    public void testItWrapsExceptionsInIoError()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        final Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate(TEMPLATE_NAME)).thenReturn(freemarkerTemplate);
        Map<String, Object> expectedDataModel = new HashMap<>();
        doThrow(new IOException()).when(freemarkerTemplate)
            .process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate(TEMPLATE_NAME, expectedDataModel);
    }


    @Test(expected = TemplatePopulatorParsingException.class)
    public void testItWrapsExceptionsInParseError()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate(TEMPLATE_NAME)).thenReturn(freemarkerTemplate);
        Map<String, Object> expectedDataModel = new HashMap<>();
        doThrow(new TemplateException(null)).when(freemarkerTemplate)
            .process(eq(expectedDataModel), any(Writer.class));

        final FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate(TEMPLATE_NAME, expectedDataModel);
    }


    @Test(expected = TemplatePopulatorParsingException.class)
    public void testItThrowsAParseErrorOnEmptyEmail()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        final Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate(TEMPLATE_NAME)).thenReturn(freemarkerTemplate);

        Map<String, Object> expectedDataModel = new HashMap<>();

        doAnswer(invocation -> {
            final Writer writer = (Writer) invocation.getArguments()[1];
            try {
                writer.write("   ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }).when(freemarkerTemplate).process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate(TEMPLATE_NAME, expectedDataModel);
    }
}
