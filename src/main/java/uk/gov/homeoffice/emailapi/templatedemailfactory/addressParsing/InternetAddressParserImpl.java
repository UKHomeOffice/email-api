package uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

public class InternetAddressParserImpl implements InternetAddressParser {

    public Collection<InternetAddress> getInternetAddresses(Collection<String> unparsedRecipients)
        throws InternetAddressParsingException {

        Collection<InternetAddress> recipients = new ArrayList<>();

        for (String emailAddress : unparsedRecipients) {
            recipients.add(getInternetAddress(emailAddress));
        }

        return recipients;
    }

    private InternetAddress getInternetAddress(String sender)
        throws InternetAddressParsingException {

        try {
            return new InternetAddress(sender);
        } catch (AddressException e) {
            throw new InternetAddressParsingException(e);
        }
    }
}
