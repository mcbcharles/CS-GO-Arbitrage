package CsGo_Market_Analyzer;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by mb3752 on 2016-04-11.
 * This class sends email notifications and tests the users email to prevent errors while the code is running.
 */
class sendEmails {
    private final logFileMakerWriter lof = new logFileMakerWriter();

    void emailUser(String[] makeGuiStringArray, String[] emailData) throws IOException {
        String methodName = "emailUser";
        if (makeGuiStringArray[0].equals("skip")) {
            lof.setText(Level.INFO, getClass().toString(), methodName, "Skipping email notification...");
        } else {
            final String username = makeGuiStringArray[0];
            final String password = makeGuiStringArray[1];

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(makeGuiStringArray[2]));
                message.setSubject("Profitable Item on the Steam Market!");
                message.setText(emailData[1]);
                Transport.send(message);
                lof.setText(Level.INFO, getClass().toString(), methodName, "Email sent to: " + makeGuiStringArray[2]);
            } catch (Exception e) {
                lof.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
            }
        }
    }

    boolean testEmail(String[] makeGuiStringArray) throws IOException {
        String methodName = "testEmail";
        if (makeGuiStringArray[0].contains("none")) {
            return (true);
        } else {
            final String username = makeGuiStringArray[0];
            final String password = makeGuiStringArray[1];

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(makeGuiStringArray[0]));
                message.setSubject("TEST EMAIL");
                message.setText("Your email worked!");
                Transport.send(message);
                lof.setText(Level.INFO, getClass().toString(), methodName, "Email sent!");
            } catch (Exception e) {
                lof.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
                return (false);
            }
            return (true);
        }
    }
}