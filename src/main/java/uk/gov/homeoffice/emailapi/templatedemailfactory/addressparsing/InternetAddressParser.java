package uk.gov.homeoffice.emailapi.templatedemailfactory.addressparsing;

import javax.mail.internet.InternetAddress;
import java.util.Collection;

public interface InternetAddressParser {

    Collection<InternetAddress> getInternetAddresses(Collection<String> unparsedRecipients)
        throws InternetAddressParsingException;
}
