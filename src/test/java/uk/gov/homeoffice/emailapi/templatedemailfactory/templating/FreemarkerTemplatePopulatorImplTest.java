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

    @Test
    public void test_it_gets_content_of_the_template()
        throws IOException, TemplateException, TemplatePopulatorParsingException,
        TemplatePopulatorIOException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate("template-name")).thenReturn(freemarkerTemplate);

        Map<String, Object> expectedDataModel = new HashMap<>();

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
        String actual = subject.populateTemplate("template-name", expectedDataModel);

        assertThat(actual, equalTo("Hello, world!"));
    }

    @Test(expected = TemplatePopulatorIOException.class)
    public void test_it_wraps_exceptions_in_io_error()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate("template-name")).thenReturn(freemarkerTemplate);
        Map<String, Object> expectedDataModel = new HashMap<>();
        doThrow(new IOException()).when(freemarkerTemplate)
            .process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate("template-name", expectedDataModel);
    }


    @Test(expected = TemplatePopulatorParsingException.class)
    public void test_it_wraps_exceptions_in_parse_error()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate("template-name")).thenReturn(freemarkerTemplate);
        Map<String, Object> expectedDataModel = new HashMap<>();
        doThrow(new TemplateException(null)).when(freemarkerTemplate)
            .process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate("template-name", expectedDataModel);
    }


    @Test(expected = TemplatePopulatorParsingException.class)
    public void test_it_throws_a_parse_error_on_empty_email()
        throws IOException, TemplateException, TemplatePopulatorIOException,
        TemplatePopulatorParsingException {
        Configuration freemarkerConfiguration = mock(Configuration.class);
        Template freemarkerTemplate = mock(Template.class);

        when(freemarkerConfiguration.getTemplate("template-name")).thenReturn(freemarkerTemplate);

        Map<String, Object> expectedDataModel = new HashMap<>();

        doAnswer(invocation -> {
            Writer writer = (Writer) invocation.getArguments()[1];
            try {
                writer.write("   ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }).when(freemarkerTemplate).process(eq(expectedDataModel), any(Writer.class));

        FreemarkerTemplatePopulatorImpl subject =
            new FreemarkerTemplatePopulatorImpl(freemarkerConfiguration);
        subject.populateTemplate("template-name", expectedDataModel);
    }
}
