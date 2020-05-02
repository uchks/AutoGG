package pw._2pi.autogg.util;

import pw._2pi.autogg.gg.AutoGG;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ConfigUtil {
    private static File CONFIG_FILE;

    static {
        ConfigUtil.CONFIG_FILE = new File(AutoGG.getInstance().getMinecraft() + "/config/autogg_delay.cfg");
    }

    public static void setConfigDelay() {
        try {
            if (!ConfigUtil.CONFIG_FILE.exists()) {
                ConfigUtil.CONFIG_FILE.createNewFile();
            }
            final BufferedWriter bw = new BufferedWriter(new FileWriter(ConfigUtil.CONFIG_FILE));
            bw.write(Integer.toString(AutoGG.getInstance().getDelay()));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getConfigDelay() {
        if (!ConfigUtil.CONFIG_FILE.exists()) {
            setConfigDelay();
        }
        try {
            final String delayString = new String(Files.readAllBytes(ConfigUtil.CONFIG_FILE.toPath()), StandardCharsets.UTF_8);
            System.out.println(delayString);
            final int delay = Integer.parseInt(delayString);
            if (delay < 0 || delay > 5) {
                throw new NumberFormatException("Invalid integer");
            }
            return delay;
        } catch (NumberFormatException nfe) {
            ConfigUtil.CONFIG_FILE.delete();
            setConfigDelay();
            nfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return AutoGG.getInstance().getDelay();
    }
}
