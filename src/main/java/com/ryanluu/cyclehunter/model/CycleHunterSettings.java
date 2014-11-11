package com.ryanluu.cyclehunter.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import javafx.scene.paint.Color;

import org.apache.log4j.Logger;

import com.ryanluu.cyclehunter.model.LookbackMultiple;


/**
 * Contains various settings that are used during the
 * running of the application.  This class provides functions
 * for loading settings from the system, and also saving it back.
 *
 */
public class CycleHunterSettings {

    private static final long serialVersionUID = 1L;

    /**
     * Log4j static logger instance.
     */
    private static final Logger logger =
            Logger.getLogger(CycleHunterSettings.class);

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

        // G.Sun is selected by default.
        planetNamesEnabledMap.put("G.Ascendant", false);

        planetNamesEnabledMap.put("G.CalendarDay", false);

        planetNamesEnabledMap.put("G.Sun", true);
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

        for (int i = 1; i < 15; i++) {
            integers.add(i);
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

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
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

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
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

            // Geometric LookbackMultiples will start out unchecked/disabled.
            Boolean enabled = false;
            geometricLookbackMultiplesEnabledMap.put(lm, enabled);
        }


        initialized = true;
    }

    /**
     * Saves the settings.
     */
    public void saveSettings() {
        // Saving is not supported (yet).
        logger.info("Save operation invoked, but saving " +
                "has not been implemented.  Nothing saved.");
    }

    /**
     * Loads the CycleHunterSettings object.
     *
     * @return A CycleHunterSettings object, populated with settings values.
     */
    public static CycleHunterSettings loadSettings() {

        logger.info("Using default initialization settings.");

        CycleHunterSettings settings = new CycleHunterSettings();
        settings.initializeWithDefaultSettings();

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
