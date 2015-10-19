package uk.gov.homeoffice.emailapi.templatedemailfactory.addressParsing;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.Collection;

public class InternetAddressParserImpl implements InternetAddressParser {

    public Collection<InternetAddress> getInternetAddresses(Collection<String> unparsedRecipients) throws InternetAddressParsingException {
        Collection<InternetAddress> recipients = new ArrayList<>();

        for (String emailAddress : unparsedRecipients) {
            try {
                recipients.add(new InternetAddress(emailAddress));
            } catch (AddressException e) {
                throw new InternetAddressParsingException(e);
            }
        }

        return recipients;
    }
}
