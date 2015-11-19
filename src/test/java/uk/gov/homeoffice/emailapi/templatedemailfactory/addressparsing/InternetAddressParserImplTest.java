package uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing;

import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class InternetAddressParserImplTest {

    @Test
    public void testItCanCreateInternetAddresses() throws Exception {
        final InternetAddressParserImpl internetAddressParser = new InternetAddressParserImpl();
        final ArrayList<String> addresses = new ArrayList<>();
        addresses.add("test@example.net");
        addresses.add("test user <test@example.org>");

        final Collection<InternetAddress> expected = new ArrayList<>();
        expected.add(new InternetAddress("test@example.net"));
        expected.add(new InternetAddress("test@example.org", "test user"));

        final Collection<InternetAddress> actual = internetAddressParser.getInternetAddresses(addresses);

        assertThat(actual, equalTo(expected));
    }


    @Test(expected = InternetAddressParsingException.class)
    public void testItThrowsExceptionsOnInvalidAddresses() throws Exception {
        final InternetAddressParserImpl internetAddressParser = new InternetAddressParserImpl();
        final ArrayList<String> addresses = new ArrayList<>();
        addresses.add("not a real address");
        internetAddressParser.getInternetAddresses(addresses);
    }
}
