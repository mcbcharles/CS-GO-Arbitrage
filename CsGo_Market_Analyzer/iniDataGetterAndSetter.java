package CsGo_Market_Analyzer;

import org.ini4j.Ini;
import java.io.File;
import java.io.IOException;

/**
 * Created by Matthew on 2016-05-31.
 */
class iniDataGetterAndSetter {
    String dataGetter(String section, String key) throws IOException {
        Ini settings = new Ini(new File("Settings.ini"));
        return settings.get(section, key);
    }

    void dataSetter(String section, String key, String value) throws IOException {
        Ini settings = new Ini(new File("Settings.ini"));
        settings.put(section, key, value);
        settings.store();
    }
}
