package com.ryanluu.cyclehunter.model;

import javafx.scene.paint.Color;

import java.math.BigDecimal;

/**
 * Encapsulates information on how far to look back
 * to compare historical data.
 */
public class LookbackMultiple {
    private String name;
    private BigDecimal lookbackMultiple;
    private Color color;

    public LookbackMultiple() {
        name = "";
        lookbackMultiple = new BigDecimal("0.0");
        color = Color.GRAY;
    }

    public LookbackMultiple(String name,
                            BigDecimal lookbackMultiple,
                            Boolean enabled,
                            Color color) {
        this.name = name;
        this.lookbackMultiple = lookbackMultiple;
        this.color = color;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LookbackMultiple that = (LookbackMultiple) o;

        if (!color.equals(that.color)) return false;
        if (!lookbackMultiple.equals(that.lookbackMultiple)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + lookbackMultiple.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LookbackMultiple{" +
                "name='" + name + '\'' +
                ", lookbackMultiple=" + lookbackMultiple +
                ", color=" + color +
                '}';
    }
}
