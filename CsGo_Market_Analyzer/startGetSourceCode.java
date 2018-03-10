package CsGo_Market_Analyzer;

/**
 * This class calls the methods in getSourceCode and gets the amount of pages that program will have to go through.
 */

import com.twilio.sdk.TwilioRestException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Created by halas on 4/15/2016.
 */

class startGetSourceCode {
    private final getSourceCode GSC = new getSourceCode();
    private final logFileMakerWriter log = new logFileMakerWriter();
    private final JFrame frame = new JFrame();
    private final iniDataGetterAndSetter ini = new iniDataGetterAndSetter();

    void starter(String[] make_gui_string_array) throws IOException, InterruptedException, TwilioRestException {
        String methodName = "starter";
        String line;
        String tempMaxNumber;
        int maxPageNumber = 1;
        GSC.makeFile();
        GSC.addRecords(1);
        GSC.closeFile();
        File file = new File("Sourcecode.html");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                while (line.isEmpty()){
                    line = scanner.nextLine();
                }
                if (line.contains("searchResults_total")) {
                    tempMaxNumber = line;
                    String[] part = tempMaxNumber.split(">");
                    String part1 = part[5];
                    tempMaxNumber = part1.substring(0, 5);
                    maxPageNumber = Integer.parseInt(tempMaxNumber.replace(",", ""));
                    maxPageNumber = maxPageNumber / 10;
                }
            }
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
        }
        log.setText(Level.INFO, getClass().toString(), methodName, "Page number: " + maxPageNumber);
        int pageNumber = Integer.parseInt(ini.dataGetter("price", "startNum"));
        while (pageNumber <= maxPageNumber) {
            log.setText(Level.INFO, getClass().toString(), methodName, "Page number: " + pageNumber);
            GSC.makeFile();
            GSC.addRecords(pageNumber);
            GSC.closeFile();
            GSC.check(make_gui_string_array);
            GSC.getPrices(make_gui_string_array);
            pageNumber++;
        }
        JOptionPane.showMessageDialog(frame, "The program has finished!");
        log.setText(Level.INFO, getClass().toString(), methodName, "Finished!");
        System.exit(1);
    }
}