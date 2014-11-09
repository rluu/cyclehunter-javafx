package com.ryanluu.cyclehunter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import com.ryanluu.cyclehunter.model.LookbackMultiple;


/**
 * Contains various settings that are used during the
 * running of the application.  This class provides functions
 * for loading settings from the system, and also saving it back.
 *
 */
public class CycleHunterSettings implements Serializable {

    /**
     * Log4j static logger instance.
     */
    private static final Logger logger =
            Logger.getLogger(CycleHunterSettings.class);

    /**
     * Contains the user home directory.  (e.g. /home/username).
     * Unfortunately, in JavaFX for jdk1.8.0_20,
     * calling System.getProperty("user.home") consistently returns null.
     * So we can't the home directory.  If we see a null, the
     * failover logic uses /tmp as the home directory instead.
      */
    private static String USER_HOME_DIR = System.getenv("user.home");
    {
        // Static code block to ensure USER_HOME_DIR is valid.
        logger.debug("user.home is: " + CycleHunterSettings.USER_HOME_DIR);

        if (CycleHunterSettings.USER_HOME_DIR == null) {
            logger.warn("Ahhh!!!! Why can't we get the " +
                    "user home directory?  Is it because " +
                    "JavaFX simulates being in a Java Applet?  " +
                    "Instead, I'll use /tmp as the home directory instead.  " +
                    "This would fail on Windows boxes.");

            // Use alternate filename:
            CycleHunterSettings.USER_HOME_DIR = "/tmp";
            logger.debug("Using alternate userHomeDir: " +
                    CycleHunterSettings.USER_HOME_DIR);
        }
    }

    /**
     * Directory name that will be where we store all application data.
     */
    private static final String SETTINGS_DIRECTORY = ".cycleHunter";

    /**
     * Name of the file which we will store CycleHunterSettings into.
     * This file has data in JSON format, formatted by the
     * Jackson JSON library.  (jackson-mapper-asl).
     */
    private static final String SETTINGS_FILENAME = "cycleHunterSettings.json";

    private String lastOpenedCsvFilename = "";

    private Map<String, Boolean> planetNamesEnabledMap =
            new LinkedHashMap<String, Boolean>();

    private Map<LookbackMultiple, Boolean> integerLookbackMultiplesEnabledMap =
            new LinkedHashMap<LookbackMultiple, Boolean>();

    private Map<LookbackMultiple, Boolean> geometricLookbackMultiplesEnabledMap =
            new LinkedHashMap<LookbackMultiple, Boolean>();


    /**
     * Flag that indicates that this settings object has
     * been properly initialized.
     */
    private transient boolean initialized = false;

    /**
     * Default constructor.
     */
    public CycleHunterSettings() {
    }

    /**
     * Initialization function.  This will initialize
     * the settings object with hard-coded default values
     * throughout.
     */
    private void initializeWithDefaultSettings() {

        // No previous opened file by default.
        lastOpenedCsvFilename = "";

        // No planets are enabled by default.
        planetNamesEnabledMap.put("G.Ascendant", false);

        planetNamesEnabledMap.put("G.CalendarDay", false);

        planetNamesEnabledMap.put("G.Sun", false);
        planetNamesEnabledMap.put("G.Moon", false);
        planetNamesEnabledMap.put("G.Mercury", false);
        planetNamesEnabledMap.put("G.Venus", false);
        planetNamesEnabledMap.put("G.Mars", false);
        planetNamesEnabledMap.put("G.Jupiter", false);
        planetNamesEnabledMap.put("G.Saturn", false);
        planetNamesEnabledMap.put("G.Uranus", false);
        planetNamesEnabledMap.put("G.Neptune", false);
        planetNamesEnabledMap.put("G.Pluto", false);
        planetNamesEnabledMap.put("G.TrueNorthNode", false);

        planetNamesEnabledMap.put("H.Mercury", false);
        planetNamesEnabledMap.put("H.Venus", false);
        planetNamesEnabledMap.put("H.Earth", false);
        planetNamesEnabledMap.put("H.Mars", false);
        planetNamesEnabledMap.put("H.Jupiter", false);
        planetNamesEnabledMap.put("H.Saturn", false);
        planetNamesEnabledMap.put("H.Uranus", false);
        planetNamesEnabledMap.put("H.Neptune", false);
        planetNamesEnabledMap.put("H.Pluto", false);



        // Integer LookbackMulitples.
        // Integers to include:
        // Note: TreeSets stay sorted and do not keep duplicates.
        Set<Integer> integers = new TreeSet<Integer>();

        for (int i = 0; i < 60; i++) {
            integers.add(i);
        }

        // Multiples of 7 up to 392.
        for (int i = 0; i <= 168; i += 7) {
            integers.add(i);
        }

        // Multiples of 19.
        for (int i = 0; i <= 19 * 24; i += 19) {
            integers.add(i);
        }

        // Multiples of 11 up to high number.
        for (int i = 0; i <= 11 * 24; i += 11) {
            integers.add(i);
        }

        // Multiples of 12 up to high number.
        for (int i = 0; i <= 12 * 24; i += 12) {
            integers.add(i);
        }

        // Multiples of 37.
        for (int i = 0; i <= 37 * 24; i += 37) {
            integers.add(i);
        }

        // Multiples of 49.
        for (int i = 0; i <= 49 * 12; i += 49) {
            integers.add(i);
        }

        // Multiples of 72.
        for (int i = 0; i <= 72 * 12; i += 72) {
            integers.add(i);
        }

        // Squares.
        for (int i = 0; i < 19; i++) {
            integers.add(i*i);
        }

        logger.debug("Length of integers is: " + integers.size());

        // These ints will come out in increasing order.
        for (int multiple : integers) {

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(String.valueOf(multiple));
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);

            // Integer LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            integerLookbackMultiplesEnabledMap.put(lm, enabled);
        }

        // Geometric LookbackMulitples.
        double phi = (1.0 + Math.sqrt(5.0)/2.0);

        // These values:
        //
        // Math.pow(Math.sqrt(phi), 1.0),        // Approximately: 1.272
        // Math.pow(Math.sqrt(phi), 2.0),        // Approximately: 1.618
        // Math.pow(Math.sqrt(phi), 3.0),        // Approximately: 2.058
        // Math.pow(Math.sqrt(phi), 4.0),        // Approximately: 2.618
        // Math.pow(Math.sqrt(phi), 5.0),        // Approximately: 3.330
        // Math.pow(Math.sqrt(phi), 6.0),        // Approximately: 4.236
        // Math.pow(Math.sqrt(phi), 7.0),        // Approximately: 5.388
        // Math.pow(Math.sqrt(phi), 8.0),        // Approximately: 6.854
        //
        // Math.sqrt(Math.pow(1.0, 2.0) + Math.pow(phi, 2)),   // Approximately: 1.902
        //
        // Math.sqrt(2.0),        // Approximately: 1.414
        // Math.sqrt(3.0),        // Approximately: 1.732
        // Math.sqrt(5.0),        // Approximately: 2.236
        //

        for (int i = 0; i <= 8; i++) {

            double multiple = Math.pow(Math.sqrt(phi), 1.0 * i);
            String name = String.format("sqrt(phi) ^ %d", i);

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(String.valueOf(multiple));
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
        }

        {
            double multiple = Math.sqrt(Math.pow(1.0, 2.0) + Math.pow(phi, 2));
            String name = "sqrt( (1^2) + (phi^2) )";

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(String.valueOf(multiple));
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
        }

        for (int i : new int[] { 2, 3, 5 }) {
            double multiple = Math.sqrt(1.0 * i);
            String name = String.format("sqrt( %d )", i);

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(String.valueOf(multiple));
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
        }


        initialized = true;
    }

    /**
     * Saves the settings to JSON file on the filesystem.
     */
    public void saveSettings() {
        // Save to a JSON file on the filesystem.
        ObjectMapper mapper = new ObjectMapper();

        // Load from file.

        // First get a string of the full path of the
        // containing directory where we will store settings data.
        String containingDirectoryStr =
                CycleHunterSettings.USER_HOME_DIR + File.separator +
                CycleHunterSettings.SETTINGS_DIRECTORY;

        // Get the string of the full path of the filename where
        // we will attempt to write data.
        String filenameStr = containingDirectoryStr + File.separator +
                CycleHunterSettings.SETTINGS_FILENAME;

        try {
            logger.debug("Attempting to save settings to file '" + filenameStr + "' ...");

            // Check to see if the directory we are writing to exists.
            File containingDirectory = new File(containingDirectoryStr);
            if (containingDirectory.exists() == false) {
                logger.info("Creating directory '" + containingDirectoryStr +
                        "' for saving CycleHunterSettings ...");
                containingDirectory.mkdirs();
            }

            // Write JSON to file.
            mapper.writeValue(new File(filenameStr), this);

            logger.info("Wrote CycleHunterSettings to file: " + filenameStr);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Failed to write setting to json file: " + filenameStr, e);

            // Show popup telling the user that we were unable to
            // save their settings.
            Dialogs.create()
                    .title("Error")
                    .masthead("Settings were not saved!")
                    .message("An error occurred that prevented us from " +
                            "saving your settings.  Please see the logs " +
                            "for why this happened.  Sadly, next application " +
                            "startup will not have your settings.")
                    .showError();

        }
    }

    /**
     * Loads from JSON file on the filesystem, a CycleHunterSettings object.
     * If the file does not exist, then default settings are used.
     * If there is failure in reading the file, then the user is
     * prompted about what to do.  The following options can be taken from here:
     *
     * <ul>
     *   <li>Use default settings, which will overwrite previously
     *     stored values upon shutdown.</li>
     *   <li>Don't continue.  In this case null is returned
     *   instead of a CycleHunterSettings object.</li>
     * </ul>
     *
     * @return A CycleHunterSettings object, populated with settings values.
     * If the loading operation failed, then null may be returned.
     */
    public static CycleHunterSettings loadSettings() {
        // Return value.
        CycleHunterSettings settings = null;

        // Load from file.
        String filename =
                CycleHunterSettings.USER_HOME_DIR + File.separator +
                CycleHunterSettings.SETTINGS_DIRECTORY + File.separator +
                CycleHunterSettings.SETTINGS_FILENAME;

        // Check to see if the file we want to load exists.

        Path path = Paths.get(filename);
        if (Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path)) {

            // The file exists.  Try to read JSON from it, and map it to a class.
            try {
                ObjectMapper mapper = new ObjectMapper();
                settings = mapper.readValue(new File(filename), CycleHunterSettings.class);

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("IOException while trying to load settings from disk file.", e);

                Action response = Dialogs.create()
                        .title("Error")
                        .masthead("Error loading settings from file.")
                        .message("We weren't able to load the settings from file.  " +
                                "Please see log file for why this happened.  " +
                                "In the meantime, would you like to continue " +
                                "with default values as settings?  " +
                                "WARNING: This will overwrite your previous settings " +
                                "file when you quit the application!")
                        .actions(Dialog.ACTION_YES, Dialog.ACTION_NO)
                        .showError();

                // Respond to user click action on the error dialog.
                if (response == Dialog.ACTION_YES) {
                    logger.info("User decided to continue on with " +
                            "default initialization settings.");
                    settings = new CycleHunterSettings();
                    settings.initializeWithDefaultSettings();
                }
                else {
                    logger.info("User decided not to continue on.  settings set to null.");
                    settings = null;
                }
            }
        }
        else {
            // File does not exist, so we can't load settings from it yet.
            logger.info("Did not see a previous json file to read " +
                    "for loading settings.  " +
                    "The file we were looking for was: " + filename + ".  " +
                    "We will now initialize, using default values instead.");
            settings = new CycleHunterSettings();
            settings.initializeWithDefaultSettings();
        }


        return settings;
    }


    /**
     * This list returns a list of planet names that are
     * check-marked in the list.
     * @return
     */
    public List<String> getPlanetNamesEnabledList() {
        List<String> rv = new LinkedList<String>();

        for (String key : planetNamesEnabledMap.keySet()) {
            if (planetNamesEnabledMap.get(key) == true) {
                // It is enabled.
                rv.add(key);
            }
        }

        return rv;
    }

    public List<LookbackMultiple> getIntegerLookbackMultiplesEnabledList() {
        List<LookbackMultiple> rv = new LinkedList<LookbackMultiple>();

        for (LookbackMultiple key : integerLookbackMultiplesEnabledMap.keySet()) {
            if (integerLookbackMultiplesEnabledMap.get(key) == true) {
                // It is enabled.
                rv.add(key);
            }
        }

        return rv;
    }

    public String getLastOpenedCsvFilename() {
        return lastOpenedCsvFilename;
    }

    public void setLastOpenedCsvFilename(String lastOpenedCsvFilename) {
        this.lastOpenedCsvFilename = lastOpenedCsvFilename;
    }

    public Map<String, Boolean> getPlanetNamesEnabledMap() {
        return planetNamesEnabledMap;
    }

    public void setPlanetNamesEnabledMap(Map<String, Boolean> planetNamesEnabledMap) {
        this.planetNamesEnabledMap = planetNamesEnabledMap;
    }

    public Map<LookbackMultiple, Boolean> getIntegerLookbackMultiplesEnabledMap() {
        return integerLookbackMultiplesEnabledMap;
    }

    public void setIntegerLookbackMultiplesEnabledMap(Map<LookbackMultiple, Boolean> integerLookbackMultiplesEnabledMap) {
        this.integerLookbackMultiplesEnabledMap = integerLookbackMultiplesEnabledMap;
    }

    public Map<LookbackMultiple, Boolean> getGeometricLookbackMultiplesEnabledMap() {
        return geometricLookbackMultiplesEnabledMap;
    }

    public void setGeometricLookbackMultiplesEnabledMap(Map<LookbackMultiple, Boolean> geometricLookbackMultiplesEnabledMap) {
        this.geometricLookbackMultiplesEnabledMap = geometricLookbackMultiplesEnabledMap;
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
