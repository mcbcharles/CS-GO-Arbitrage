package CsGo_Market_Analyzer;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.FormatterClosedException;
import java.util.Scanner;
import java.util.Formatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by mb3752 on 2016-04-20.
 * This class scrapes the data from the selling graphs from each gun. It checks how many times the weapon was sold in the
 * past week and compares it to the users requested amount.
 */

class graphChecker {
    private final String[] pastWeekDates = new String[7];
    private Formatter formatter;
    private int totalSold;
    private final logFileMakerWriter log = new logFileMakerWriter();

    int makeFile(String currentGunLink, boolean onlyMakeFile) throws IOException {
        String methodName = "makeFile";
        /**Creates a new HTML formatter used in the addRecords & check class*/
        try {
            formatter = new Formatter("Current_Gun.html");
            if (!onlyMakeFile) return addRecords(currentGunLink);
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
        }
        return 0;
    }


    private int addRecords(String currentGunLink) throws IOException, InterruptedException {
        String methodName = "addRecords";
        /**Opens a link and scrapes the html from the page into Sourcecode.html*/
        String html = null;
        totalSold = 0;
        try {
            html = Jsoup.connect(currentGunLink).maxBodySize(0).timeout(600000).get().html();
        } catch (HttpStatusException e) {
            log.setText(Level.WARNING, getClass().toString(), methodName, "Too many requests recently. Program will resume in four minutes (" + timeInFourMinutes() + ")");
            TimeUnit.SECONDS.sleep(240);
            addRecords(currentGunLink);
        }
        try {
            formatter.format("%s \n ", (html));
        }catch (FormatterClosedException e ){
            makeFile(currentGunLink, true);
        }
        formatter.close();
        return dateGetter();
    }

    private int dateGetter() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd YYYY");
        for (int i = 0; i <= 6; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            pastWeekDates[i] = (dateFormat.format(cal.getTime()));
        }
        return pastWeekSold(pastWeekDates);
    }

    private int pastWeekSold(String[] date) throws IOException {
        String methodName = "pastWeekSold";
        File file = new File("Current_Gun.html");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()){
                    line = scanner.nextLine();
                }
                if (line.substring(1, line.length()).contains("var line1=")) {
                    String[] sellDates = line.split("\\[");
                    for (int i = 2; i < sellDates.length; i++) {
                        for (int x = 0; x <= 6; x++){
                            if (sellDates[i].substring(1, 12).equals(date[x])){
                                totalSold++;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.setText(Level.SEVERE, getClass().toString(), methodName, Arrays.toString(e.getStackTrace()));
        }
        return totalSold;
    }

    private String timeInFourMinutes(){
        DateFormat dateFormat = new SimpleDateFormat("hh꞉mm꞉ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, +4);
        return dateFormat.format(calendar.getTime());
    }
}