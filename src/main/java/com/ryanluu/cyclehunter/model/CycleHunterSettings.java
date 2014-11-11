package com.ryanluu.cyclehunter.model;

import java.math.BigDecimal;
import java.util.*;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import org.codehaus.jackson.map.ObjectMapper;


/**
 * Contains various settings that are used during the
 * running of the application.  This class provides functions
 * for loading settings from the system, and also saving it back.
 *
 */
public class CycleHunterSettings {

    //private static final long serialVersionUID = 1L;

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

    private List<PlanetSelection> planetSelectionList =
            new LinkedList<>();

    private List<LookbackMultiple> customLookbackMultiplesList =
            new LinkedList<>();

    private List<LookbackMultiple> fixedLookbackMultiplesList =
            new LinkedList<>();

    /**
     * Flag that indicates that this settings object has
     * been properly initialized.
     */
    private transient boolean initialized = false;

    /**
     * Default constructor.
     */
    public CycleHunterSettings() {
        CycleHunterSettings.doHomeDirCheck();
    }

    /**
     * Ensures that the USER_HOME_DIR string is not null.
     */
    private static void doHomeDirCheck() {
        // Ensure USER_HOME_DIR is not null.
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
     * Initialization function.  This will initialize
     * the settings object with hard-coded default values
     * throughout.
     */
    private void initializeWithDefaultSettings() {

        // No previous opened file by default.
        lastOpenedCsvFilename = "";

        // Variable to hold the current PlanetSelection being
        // created before adding it to the list.
        PlanetSelection ps;

        // Parameters to the PlanetSelection construtor.
        // We will be modifying these variables before creating
        // each PlanetSelection.
        String planetName;
        PlanetSelection.Centricity geocentric =
                PlanetSelection.Centricity.GEOCENTRIC;
        PlanetSelection.Centricity heliocentric =
                PlanetSelection.Centricity.HELIOCENTRIC;
        Boolean enabled = false;

        // Create PlanetSelection objects.

        planetName = "Ascendant";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);


        planetName = "CalendarDay";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);


        planetName = "Sun";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Moon";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Mercury";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Venus";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Mars";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Jupiter";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Saturn";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Uranus";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Neptune";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Pluto";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "TrueNorthNode";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);


        planetName = "Mercury";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Venus";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Earth";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Mars";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Jupiter";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Saturn";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Uranus";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Neptune";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);

        planetName = "Pluto";
        ps = new PlanetSelection(planetName, geocentric, enabled);
        planetSelectionList.add(ps);



        // Custom LookbackMulitples.
        // Put in default numbers.
        List<Integer> integers = new ArrayList<Integer>();
        for (int i = 1; i < 15; i++) {
            integers.add(i);
        }
        logger.debug("Length of integers is: " + integers.size());

        for (Integer multiple : integers) {
            
            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();
            
            lm.setName(String.valueOf(multiple));
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);
            lm.setEnabled(false);
            customLookbackMultiplesList.add(lm);
        }
        
        // Fixed LookbackMulitples.
        double phi = (1.0 + Math.sqrt(5.0)) / 2.0;

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

        for (int i = 1; i <= 8; i++) {

            double multiple = Math.pow(Math.sqrt(phi), 1.0 * i);
            String name = String.format("sqrt(phi) ^ %d", i);
            //String name = String.format("%.3f", multiple);

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(name);
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);
            lm.setEnabled(enabled);

            fixedLookbackMultiplesList.add(lm);
        }

        {
            double multiple = Math.sqrt(Math.pow(1.0, 2.0) + Math.pow(phi, 2));
            String name = "sqrt( (1^2) + (phi^2) )";
            //String name = String.format("%.3f", multiple);

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(name);
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);
            lm.setEnabled(enabled);

            fixedLookbackMultiplesList.add(lm);
        }

        for (int i : new int[] { 2, 3, 5 }) {
            double multiple = Math.sqrt(1.0 * i);
            String name = String.format("sqrt( %d )", i);
            //String name = String.format("%.3f", multiple);

            // Create a LookbackMultiple.
            LookbackMultiple lm = new LookbackMultiple();

            lm.setName(name);
            lm.setLookbackMultiple(new BigDecimal(multiple));
            lm.setColor(Color.GRAY);
            lm.setEnabled(enabled);
            
            fixedLookbackMultiplesList.add(lm);
        }


        initialized = true;
    }

    /**
     * Saves the settings.
     */
    public void saveSettings() {
        // Saving is not supported (yet).
        //logger.info("Save operation invoked, but saving " +
        //        "has not been implemented.  Nothing saved.");




        // Save to a JSON file on the filesystem.
        ObjectMapper mapper = new ObjectMapper();

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
        }

    }

    /**
     * Loads the CycleHunterSettings object.
     *
     * @return A CycleHunterSettings object, populated with settings values.
     */
    public static CycleHunterSettings loadSettings() {

        // Return value.
        CycleHunterSettings settings = null;

        // Ensure that the USER_HOME_DIR variable is not null
        // before assembling the filename.
        CycleHunterSettings.doHomeDirCheck();

        // Load from file.
        String filename =
                CycleHunterSettings.USER_HOME_DIR + File.separator +
                CycleHunterSettings.SETTINGS_DIRECTORY + File.separator +
                CycleHunterSettings.SETTINGS_FILENAME;

        // Check to see if the file we want to load exists.

        Path path = Paths.get(filename);
        if (Files.exists(path) && Files.isRegularFile(path) && Files.isReadable(path)) {
            logger.info("Attempting to load settings from file '" + filename + "' ...");

            // The file exists.  Try to read JSON from it, and map it to a class.
            try {
                ObjectMapper mapper = new ObjectMapper();
                settings = mapper.readValue(new File(filename), CycleHunterSettings.class);

                logger.info("Settings loaded.");

            } catch (IOException e) {
                e.printStackTrace();
                logger.error("IOException while trying to load settings from disk file.", e);


                logger.info("Continuing on with default settings and preferences.");
                settings = new CycleHunterSettings();
                settings.initializeWithDefaultSettings();
            }
        }
        else {
            // File does not exist, so we can't load settings from it yet.
            logger.info("The application could not find a previous json file to read " +
                    "for loading settings.  " +
                    "The file we were looking for was: " + filename + ".  " +
                    "We will now initialize, using default values instead.");
            settings = new CycleHunterSettings();
            settings.initializeWithDefaultSettings();
        }

        return settings;
    }


    /**
     * This list returns a list of PlanetSelection objects
     * that are enabled
     * (i.e. logically selected in the list).
     * @return
     */
    public List<PlanetSelection> getPlanetSelectionsEnabledList() {
        List<PlanetSelection> rv = new LinkedList<PlanetSelection>();

        for (PlanetSelection ps : planetSelectionList) {
            if (ps.getEnabled()) {
                rv.add(ps);
            }
        }

        return rv;
    }

    /**
     * This list returns a list of customizable
     * LookbackMultiple objects that are enabled
     * (i.e. logically selected in the list).
     * @return
     */
    public List<LookbackMultiple> getCustomLookbackMultiplesEnabledList() {
        List<LookbackMultiple> rv = new LinkedList<LookbackMultiple>();

        for (LookbackMultiple lm : customLookbackMultiplesList) {
            if (lm.getEnabled()) {
                rv.add(lm);
            }
        }

        return rv;
    }

    /**
     * This list returns a list of non-customizable
     * LookbackMultiple objects that are enabled
     * (i.e. logically selected in the list).
     * @return
     */
    public List<LookbackMultiple> getFixedLookbackMultiplesEnabledList() {
        List<LookbackMultiple> rv = new LinkedList<LookbackMultiple>();

        for (LookbackMultiple lm : fixedLookbackMultiplesList) {
            if (lm.getEnabled()) {
                rv.add(lm);
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

    public List<PlanetSelection> getPlanetSelectionList() {
        return planetSelectionList;
    }

    public void setPlanetSelectionList(List<PlanetSelection> planetSelectionList) {
        this.planetSelectionList = planetSelectionList;
    }

    public List<LookbackMultiple> getCustomLookbackMultiplesList() {
        return customLookbackMultiplesList;
    }

    public void setCustomLookbackMultiplesList(List<LookbackMultiple> customLookbackMultiplesList) {
        this.customLookbackMultiplesList = customLookbackMultiplesList;
    }

    public List<LookbackMultiple> getFixedLookbackMultiplesList() {
        return fixedLookbackMultiplesList;
    }

    public void setFixedLookbackMultiplesList(List<LookbackMultiple> fixedLookbackMultiplesList) {
        this.fixedLookbackMultiplesList = fixedLookbackMultiplesList;
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
