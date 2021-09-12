package com.bitcoin.assignment.dto;

public class TickerResponseDTO {
    private String code;
    private Double price;
    private Double volume;
    private double daily_change;
    private long last_updated;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public double getDaily_change() {
        return daily_change;
    }

    public void setDaily_change(double daily_change) {
        this.daily_change = daily_change;
    }

    public long getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(long last_updated) {
        this.last_updated = last_updated;
    }
}
