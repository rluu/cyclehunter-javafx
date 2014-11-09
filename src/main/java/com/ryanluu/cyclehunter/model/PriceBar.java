package com.ryanluu.cyclehunter.model;

import java.math.BigDecimal;

/**
 * PriceBar class represents the data encapsulated in a pricebar
 * on a graphical chart.  It includes timestamp information
 * as well as the open, high, low and close prices.
 */
public class PriceBar {

    private Double timestampJd;

    private BigDecimal openPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal closePrice;
    private Integer openInterest;
    private Integer volume;

    public PriceBar(Double timestampJd,
                    BigDecimal openPrice,
                    BigDecimal highPrice,
                    BigDecimal lowPrice,
                    BigDecimal closePrice,
                    Integer openInterest,
                    Integer volume) {
        this.timestampJd = timestampJd;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.openInterest = openInterest;
        this.volume = volume;
    }

    public PriceBar() {
        timestampJd = new Double(0.0);
        openPrice = new BigDecimal(0);
        highPrice = new BigDecimal(0);
        lowPrice = new BigDecimal(0);
        closePrice = new BigDecimal(0);
        openInterest = new Integer(0);
        volume = new Integer(0);
    }

    public Double getTimestampJd() {
        return timestampJd;
    }

    public void setTimestampJd(Double timestampJd) {
        this.timestampJd = timestampJd;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public Integer getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(Integer openInterest) {
        this.openInterest = openInterest;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceBar priceBar = (PriceBar) o;

        if (!closePrice.equals(priceBar.closePrice)) return false;
        if (!highPrice.equals(priceBar.highPrice)) return false;
        if (!lowPrice.equals(priceBar.lowPrice)) return false;
        if (!openInterest.equals(priceBar.openInterest)) return false;
        if (!openPrice.equals(priceBar.openPrice)) return false;
        if (!timestampJd.equals(priceBar.timestampJd)) return false;
        if (!volume.equals(priceBar.volume)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = timestampJd.hashCode();
        result = 31 * result + openPrice.hashCode();
        result = 31 * result + highPrice.hashCode();
        result = 31 * result + lowPrice.hashCode();
        result = 31 * result + closePrice.hashCode();
        result = 31 * result + openInterest.hashCode();
        result = 31 * result + volume.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PriceBar{" +
                "timestampJd=" + timestampJd +
                ", openPrice=" + openPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", closePrice=" + closePrice +
                ", openInterest=" + openInterest +
                ", volume=" + volume +
                '}';
    }
}
