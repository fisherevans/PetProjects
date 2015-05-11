package com.fisherevans.miblio_theca;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by immortal on 5/10/2015.
 */
public class Config {
    public static final String SETTINGS_FILE = "miblio.properties";

    private static Config instance = null;

    private Map<String, String> _properties;

    public final File INPUT_DIR;
    public final Integer INPUT_RECURSION;

    public final File OUTPUT_AUDIO_DIR;
    public final String OUTPUT_AUDIO_FORMAT;

    private Config() {
        load(SETTINGS_FILE);

        INPUT_DIR = new File(getProperty("input.dir"));
        INPUT_RECURSION = new Integer(getProperty("input.recursion"));

        OUTPUT_AUDIO_DIR = new File(getProperty("output.audio.dir"));
        OUTPUT_AUDIO_FORMAT = getProperty("output.audio.format");
    }

    private void load(String path) {
        try {
            _properties = new HashMap<>();
            Scanner input = new Scanner(new File(path));
            while (input.hasNextLine()) {
                String line = input.nextLine().replaceAll("#.*", "").trim();
                if (line.length() != 0 && line.indexOf("=") > 0) {
                    String[] split = line.split("=", 2);
                    _properties.put(split[0], split[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to load settings: " + path);
            System.exit(1);
        }
    }

    public String getProperty(String key) {
        return _properties.get(key);
    }

    public static Config getInstance() {
        if(instance == null)
            instance = new Config();
        return instance;
    }
}
