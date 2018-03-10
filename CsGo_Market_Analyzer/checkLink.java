package CsGo_Market_Analyzer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by halas on 5/19/2016.
 */
class checkLink {
    private final logFileMakerWriter log = new logFileMakerWriter();
    private final ArrayList<String> linksArray = new ArrayList<>();
    
    boolean check(String link) throws IOException {
        String methodName = "check";
        Boolean isThere = false;
        if (linksArray.contains(link)){
            isThere = true;
            log.setText(Level.INFO, getClass().toString(), methodName, "Link has already been checked. Skipping... Link: " + link);
        }else {
            linksArray.add(link);
            log.setText(Level.INFO, getClass().toString(), methodName, "Current link to this weapon: " + link);
        }
        return isThere;
    }
}