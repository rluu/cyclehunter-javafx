package com.ryanluu.cyclehunter.model;

import org.apache.log4j.Logger;

/**
 * Created by rluu on 11/11/14.
 */
public class PlanetSelection {
    private static Logger logger =
            Logger.getLogger(PlanetSelection.class);

    public enum Centricity {
        HELIOCENTRIC,
        GEOCENTRIC
    }

    /**
     * Name displayed on a description String presenter.
     * This is in the format:
     * "G.Moon"
     * "H.Earth"
     *
     * Where the initial abbreviation is either "G." or "H."
     * to indicate a geocentric or heliocentric planet.
     */
    private String displayName;

    /**
     * Planet name as just the planet name without
     * the "G." or "H." abbreviation.
     */
    private String planetName;

    /**
     * Planet centricity.
     */
    private Centricity planetCentricity;

    /**
     * Boolean flag that indicates if this planet
     * is enabled or not.
     */
    private Boolean enabled;

    /**
     * Default constructor.
     * Initializes all values to default invalid values.
     */
    public PlanetSelection() {
        displayName = "";
        planetName = "";
        planetCentricity = Centricity.GEOCENTRIC;
        enabled = false;
    }

    /**
     * Constructor specifying internal parameters.
     */
    public PlanetSelection(String planetName,
                           Centricity planetCentricity,
                           Boolean enabled) {
        this.displayName = "";
        this.planetName = planetName;
        this.planetCentricity = planetCentricity;
        this.enabled = enabled;

        // Set the display name according to whether
        // the planet is heliocentric or geocentric.
        if (this.planetCentricity == Centricity.GEOCENTRIC) {
            this.displayName = "G." + this.planetName;
        }
        else if (this.planetCentricity == Centricity.HELIOCENTRIC) {
            this.displayName = "H." + this.planetName;

        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Centricity getPlanetCentricity() {
        return planetCentricity;
    }

    public void setPlanetCentricity(Centricity planetCentricity) {
        this.planetCentricity = planetCentricity;
    }

    public Boolean isHeliocentric() {
        return (planetCentricity == Centricity.HELIOCENTRIC);
    }

    public void setHeliocentric(Boolean heliocentric) {
        planetCentricity = Centricity.HELIOCENTRIC;
    }

    public Boolean isGeocentric() {
        return (planetCentricity == Centricity.GEOCENTRIC);
    }

    public void setGeocentric(Boolean geocentric) {
        planetCentricity = Centricity.GEOCENTRIC;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetSelection that = (PlanetSelection) o;

        if (!displayName.equals(that.displayName)) return false;
        if (!enabled.equals(that.enabled)) return false;
        if (!planetCentricity.equals(this.planetCentricity)) return false;
        if (!planetName.equals(that.planetName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = displayName.hashCode();
        result = 31 * result + planetName.hashCode();
        result = 31 * result + planetCentricity.hashCode();
        result = 31 * result + enabled.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PlanetSelection{" +
                "displayName='" + displayName + '\'' +
                ", planetName='" + planetName + '\'' +
                ", planetCentricity=" + planetCentricity +
                ", enabled=" + enabled +
                '}';
    }
}

