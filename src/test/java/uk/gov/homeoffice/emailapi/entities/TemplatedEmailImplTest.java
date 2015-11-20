package uk.gov.homeoffice.emailapi.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplatedEmailImplTest {

    @Test
    public void testItHasRecipients() {
        Collection<String> input = new ArrayList<>();
        TemplatedEmailImpl subject = new TemplatedEmailImpl(input, null, null, null, null, null);

        assertThat(subject.getRecipients(), instanceOf(List.class));
    }

    @Test
    public void itHasASender() {
        final TemplatedEmailImpl subject = new TemplatedEmailImpl(null, "sender", null, null, null, null);

        assertThat(subject.getSender(), equalTo("sender"));
    }

    @Test
    public void itHasASubject() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, "subject", null, null, null);

        assertThat(subject.getSubject(), equalTo("subject"));
    }

    @Test
    public void itHasAHtmlTemplate() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, "html template", null, null);

        assertThat(subject.getHtmlTemplate(), equalTo("html template"));
    }

    @Test
    public void itHasATxtTemplate() {
        TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, null, null, "txt template");

        assertThat(subject.getTextTemplate(), equalTo("txt template"));
    }

    @Test
    public void itHasVariables() {
        final TemplatedEmailImpl subject =
            new TemplatedEmailImpl(null, null, null, null, new HashMap<>(), null);

        assertThat(subject.getVariables(), instanceOf(HashMap.class));
    }
}
