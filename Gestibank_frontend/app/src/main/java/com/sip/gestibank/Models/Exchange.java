package com.sip.gestibank.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class Exchange {

    @SerializedName("base")
    private String base;
    @SerializedName("date")
    private String date;
    @SerializedName("rates")
    //private List<Rate> rates;
    private SortedMap<String, Double> rates;

    public Exchange() {}

    public Exchange(String base, String date, SortedMap<String, Double> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    /*public Exchange(String base, String date, List<Rate> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }*/

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }*/

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(SortedMap<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "Exchange{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
