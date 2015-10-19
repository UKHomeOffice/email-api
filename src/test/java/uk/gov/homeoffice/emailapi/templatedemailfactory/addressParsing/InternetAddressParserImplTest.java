package uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing;

import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class InternetAddressParserImplTest {

    @Test
    public void test_it_can_create_internet_addresses() throws Exception {
        InternetAddressParserImpl internetAddressParser = new InternetAddressParserImpl();
        ArrayList<String> addresses = new ArrayList<>();
        addresses.add("test@example.net");
        addresses.add("test user <test@example.org>");

        Collection<InternetAddress> expected = new ArrayList<>();
        expected.add(new InternetAddress("test@example.net"));
        expected.add(new InternetAddress("test@example.org", "test user"));

        Collection<InternetAddress> actual = internetAddressParser.getInternetAddresses(addresses);

        assertThat(actual, equalTo(expected));
    }


    @Test(expected = InternetAddressParsingException.class)
    public void test_it_throws_exceptions_on_invalid_addresses() throws Exception {
        InternetAddressParserImpl internetAddressParser = new InternetAddressParserImpl();
        ArrayList<String> addresses = new ArrayList<>();
        addresses.add("not a real address");
        internetAddressParser.getInternetAddresses(addresses);
    }
}