package com.ryanluu.cyclehunter;

import org.apache.log4j.Logger;

/**
 * This class basically provides various utility functions for
 * operating with the Swiss Ephemeris library.
 */
public class Ephemeris {

    private static final Logger logger = Logger.getLogger(Ephemeris.class);

    /**
     * Returns the Swiss Ephemeris library ID for the given
     * string planet name that we use in our application.
     *
     * @param planetName String that describes the planet.
     * @return int ID that maps to the constants in swisseph.SweConst.SE_*.
     */
    public static int getPlanetIdForName(String planetName) {
        // Return value.  Start with an invalid ID.
        int rv = -9999;

        switch (planetName) {
            case "Sun":
                rv = swisseph.SweConst.SE_SUN;
                break;
            case "Moon":
                rv = swisseph.SweConst.SE_MOON;
                break;
            case "Mercury":
                rv = swisseph.SweConst.SE_MERCURY;
                break;
            case "Venus":
                rv = swisseph.SweConst.SE_VENUS;
                break;
            case "Mars":
                rv = swisseph.SweConst.SE_MARS;
                break;
            case "Jupiter":
                rv = swisseph.SweConst.SE_JUPITER;
                break;
            case "Saturn":
                rv = swisseph.SweConst.SE_SATURN;
                break;
            case "Uranus":
                rv = swisseph.SweConst.SE_URANUS;
                break;
            case "Neptune":
                rv = swisseph.SweConst.SE_NEPTUNE;
                break;
            case "Pluto":
                rv = swisseph.SweConst.SE_PLUTO;
                break;
            case "MeanNorthNode":
                rv = swisseph.SweConst.SE_MEAN_NODE;
                break;
            case "TrueNorthNode":
                rv = swisseph.SweConst.SE_TRUE_NODE;
                break;
            case "MeanLunarApogee":
                rv = swisseph.SweConst.SE_MEAN_APOG;
                break;
            case "OsculatingLunarApogee":
                rv = swisseph.SweConst.SE_OSCU_APOG;
                break;
            case "InterpolatedLunarApogee":
                rv = swisseph.SweConst.SE_INTP_APOG;
                break;
            case "InterpolatedLunarPerigee":
                rv = swisseph.SweConst.SE_INTP_PERG;
                break;
            case "Earth":
                rv = swisseph.SweConst.SE_EARTH;
                break;
            case "Chiron":
                rv = swisseph.SweConst.SE_CHIRON;
                break;
            case "Pholus":
                rv = swisseph.SweConst.SE_PHOLUS;
                break;
            case "Ceres":
                rv = swisseph.SweConst.SE_CERES;
                break;
            case "Pallas":
                rv = swisseph.SweConst.SE_PALLAS;
                break;
            case "Juno":
                rv = swisseph.SweConst.SE_JUNO;
                break;
            case "Vesta":
                rv = swisseph.SweConst.SE_VESTA;
                break;
            case "Isis":
                rv = swisseph.SweConst.SE_ISIS;
                break;
            case "Nibiru":
                rv = swisseph.SweConst.SE_NIBIRU;
                break;
            default:
                // Use an invalid planet ID.
                //
                // (Note: The number chosen has no meaning.
                // I couldn't use -1, because -1 stands for SE_ECL_NUT.
                // See documentation of the Swiss Ephemeris, in
                // file: pyswisseph-1.77.00-0/doc/swephprg.htm)
                //
                rv = -9999;
        }
        return rv;
    }


    /**
     * Obtains the planet name, given the Swiss Ephemeris
     * library ID number.
     * @param id
     * @return
     */
    public static String getPlanetNameForId(int id) {
        // Return value.
        String rv = "";

        switch (id) {
            case swisseph.SweConst.SE_SUN:
                rv = "Sun";
                break;
            case swisseph.SweConst.SE_MOON:
                rv = "Moon";
                break;
            case swisseph.SweConst.SE_MERCURY:
                rv = "Mercury";
                break;
            case swisseph.SweConst.SE_VENUS:
                rv = "Venus";
                break;
            case swisseph.SweConst.SE_MARS:
                rv = "Mars";
                break;
            case swisseph.SweConst.SE_JUPITER:
                rv = "Jupiter";
                break;
            case swisseph.SweConst.SE_SATURN:
                rv = "Saturn";
                break;
            case swisseph.SweConst.SE_URANUS:
                rv = "Uranus";
                break;
            case swisseph.SweConst.SE_NEPTUNE:
                rv = "Neptune";
                break;
            case swisseph.SweConst.SE_PLUTO:
                rv = "Pluto";
                break;
            case swisseph.SweConst.SE_MEAN_NODE:
                rv = "MeanNorthNode";
                break;
            case swisseph.SweConst.SE_TRUE_NODE:
                rv = "TrueNorthNode";
                break;
            case swisseph.SweConst.SE_MEAN_APOG:
                rv = "MeanLunarApogee";
                break;
            case swisseph.SweConst.SE_OSCU_APOG:
                rv = "OsculatingLunarApogee";
                break;
            case swisseph.SweConst.SE_INTP_APOG:
                rv = "InterpolatedLunarApogee";
                break;
            case swisseph.SweConst.SE_INTP_PERG:
                rv = "InterpolatedLunarPerigee";
                break;
            case swisseph.SweConst.SE_EARTH:
                rv = "Earth";
                break;
            case swisseph.SweConst.SE_CHIRON:
                rv = "Chiron";
                break;
            case swisseph.SweConst.SE_PHOLUS:
                rv = "Pholus";
                break;
            case swisseph.SweConst.SE_CERES:
                rv = "Ceres";
                break;
            case swisseph.SweConst.SE_PALLAS:
                rv = "Pallas";
                break;
            case swisseph.SweConst.SE_JUNO:
                rv = "Juno";
                break;
            case swisseph.SweConst.SE_VESTA:
                rv = "Vesta";
                break;
            case swisseph.SweConst.SE_ISIS:
                rv = "Isis";
                break;
            case swisseph.SweConst.SE_NIBIRU:
                rv = "Nibiru";
                break;
            default:
                logger.error("Unknown planet id given: " + id);
                rv = "";

        }

        return rv;
    }
}
