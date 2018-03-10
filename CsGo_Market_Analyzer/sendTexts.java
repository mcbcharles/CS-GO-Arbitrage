package CsGo_Market_Analyzer;

/**
 * Created by mb3752 on 2016-04-12.
 * This class sends text notifications to the user and tests the phone number to avoid errors while the program is running.
 */

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

class sendTexts {
    private static final String ACCOUNT_SID = "AC3398ee51de2e4e4263786b9b1023ebcd";
    private static final String AUTH_TOKEN = "7bb8f52b1bd153809c6ad6eb580fd564";
    private final logFileMakerWriter log = new logFileMakerWriter();

    void sendText(String[] makeGuiStringArray, String link) throws TwilioRestException, IOException {
        String methodName = "sendText";
        if (makeGuiStringArray[6].equals("skip")) {
            log.setText(Level.INFO, getClass().toString(), methodName, "Skipping text notification...\n");
        } else {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "Profitable item on the steam market:" + link));
            params.add(new BasicNameValuePair("To", "+1" + makeGuiStringArray[6]));
            params.add(new BasicNameValuePair("From", "+14013798549"));
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
        }
    }

    boolean testTexting(String[] make_gui_string_array) throws IOException {
        String methodName = "testTexting";
        try {
            TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("Body", "Your phone number works, and you may proceed with the program"));
            params.add(new BasicNameValuePair("To", "+1" + make_gui_string_array[6]));
            params.add(new BasicNameValuePair("From", "+14013798549"));
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
            return (false);
        }
        return (true);
    }
}