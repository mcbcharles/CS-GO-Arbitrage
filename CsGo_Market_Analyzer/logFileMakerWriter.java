package CsGo_Market_Analyzer;


import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by mb3752 on 2016-05-11.
 */
public class logFileMakerWriter {
    private final Logger logger = Logger.getLogger("Log");
    private final iniDataGetterAndSetter ini = new iniDataGetterAndSetter();
    public void makeLogFile(){
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd-YYYY hh꞉mm꞉ss a");
        try {
            String path = ini.dataGetter("paths", "logFilePath");
            FileHandler fileHandler = new FileHandler(path + "\\CsGoArbitrageLog-" + format.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            for (int x = 0; x < 100; x++){
                System.out.println("SPECIFY A LOG FILE LOCATION IN THE Setting.ini FILE");
            }
            System.exit(2);
        }
    }

    void setText(Level level, String sourceClass, String sourceMethod, String msg) {
        if (level == Level.SEVERE){
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame,  msg, "An error has occurred", JOptionPane.ERROR_MESSAGE);
            System.exit(420);
        }
        logger.logp(level, sourceClass, sourceMethod, msg);
    }
}