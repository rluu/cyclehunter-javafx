package com.ryanluu.cyclehunter.model;

import javafx.scene.paint.Color;
import org.codehaus.jackson.annotate.JsonIgnore;


import java.math.BigDecimal;

/**
 * Encapsulates information on how far to look back
 * to compare historical data.
 */
public class LookbackMultiple {

    private String name;
    private BigDecimal lookbackMultiple;

    // Color variables that feed into
    // javafx.scene.paint.Color object's internal representation.
    // We have these variables instead of just having a
    // Color object because Color does not have a default constructor
    // and thus we can't marshall it to a file.
    private double red;
    private double green;
    private double blue;
    private double opacity;
    //private Color color;

    private Boolean enabled;

    public LookbackMultiple() {
        this.name = "";
        this.lookbackMultiple = new BigDecimal("0.0");
        Color color = Color.GRAY;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
        this.enabled = false;
    }

    public LookbackMultiple(String name,
                            BigDecimal lookbackMultiple,
                            Color color,
                            Boolean enabled) {
        this.name = name;
        this.lookbackMultiple = lookbackMultiple;
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLookbackMultiple() {
        return lookbackMultiple;
    }

    public void setLookbackMultiple(BigDecimal lookbackMultiple) {
        this.lookbackMultiple = lookbackMultiple;
    }

    @JsonIgnore
    public Color getColor() {
        return new Color(red, green, blue, opacity);
    }

    @JsonIgnore
    public void setColor(Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.opacity = color.getOpacity();
    }

    @JsonIgnore
    private void setColor(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LookbackMultiple that = (LookbackMultiple) o;

        if (Double.compare(that.blue, blue) != 0) return false;
        if (Double.compare(that.green, green) != 0) return false;
        if (Double.compare(that.opacity, opacity) != 0) return false;
        if (Double.compare(that.red, red) != 0) return false;
        if (!enabled.equals(that.enabled)) return false;
        if (!lookbackMultiple.equals(that.lookbackMultiple)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + lookbackMultiple.hashCode();
        temp = Double.doubleToLongBits(red);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(green);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(blue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(opacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + enabled.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LookbackMultiple{" +
                "name='" + name + '\'' +
                ", lookbackMultiple=" + lookbackMultiple +
                ", red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", opacity=" + opacity +
                ", enabled=" + enabled +
                '}';
    }
}
