package pw._2pi.autogg.util;

import org.apache.commons.io.IOUtils;
import pw._2pi.autogg.gg.AutoGG;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class GetTriggers implements Runnable {
    @Override
    public void run() {
        try {
            final String rawTriggers = IOUtils.toString(new URL("https://gist.githubusercontent.com/sukarodo/5b0ebfdaa0680b26c588f164246569df/raw/c6eb0e290797b080c136c69adad0cf333b8796dd/triggers.txt"));
            AutoGG.getInstance().setTriggers(new ArrayList(Arrays.asList(rawTriggers.split("\n"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
