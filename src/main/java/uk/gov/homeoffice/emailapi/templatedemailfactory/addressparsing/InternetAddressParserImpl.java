package uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

public class InternetAddressParserImpl implements InternetAddressParser {

    public Collection<InternetAddress> getInternetAddresses(Collection<String> unparsedRecipients)
        throws InternetAddressParsingException {

        final Collection<InternetAddress> recipients = new ArrayList<>();

        for (final String emailAddress : unparsedRecipients) {
            recipients.add(getInternetAddress(emailAddress));
        }

        return recipients;
    }

    private InternetAddress getInternetAddress(final String sender)
        throws InternetAddressParsingException {

        try {
            return new InternetAddress(sender);
        } catch (AddressException e) {
            throw new InternetAddressParsingException(e);
        }
    }
}
