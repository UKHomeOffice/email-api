package uk.gov.homeoffice.emailapi.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplatedEmailImplTest {

    @Test
    public void test_it_has_recipients() {
        Collection<String> input = new ArrayList<>();
        TemplatedEmailImpl subject = new TemplatedEmailImpl(input, null, null, null, null, null);

        assertThat(subject.getRecipients(), instanceOf(ArrayList.class));
    }

    @Test
    public void it_has_a_sender() {
        TemplatedEmailImpl subject = new TemplatedEmailImpl(null, "sender", null, null, null, null);

        assertThat(subject.getSender(), equalTo("sender"));
    }

    @Test
    public void it_has_a_subject() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, "subject", null, null, null);

        assertThat(subject.getSubject(), equalTo("subject"));
    }

    @Test
    public void it_has_a_html_template() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, "html template", null, null);

        assertThat(subject.getHtmlTemplate(), equalTo("html template"));
    }

    @Test
    public void it_has_a_txt_template() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, null, null, "txt template");

        assertThat(subject.getTextTemplate(), equalTo("txt template"));
    }

    @Test
    public void it_has_variables() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, null, new HashMap<>(), null);

        assertThat(subject.getVariables(), instanceOf(HashMap.class));
    }
}
