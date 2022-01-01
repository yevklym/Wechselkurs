package yk.wechselkurs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CurrencyModel {

    @SerializedName("base_code")
    private String baseCurrency;

    @SerializedName("target_code")
    private String exchangeCurrency;

    @SerializedName("conversion_rate")
    private double rate;

    public CurrencyModel(String baseCurrency, String exchangeCurrency, double rate) {
        this.baseCurrency = baseCurrency;
        this.exchangeCurrency = exchangeCurrency;
        this.rate = rate;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getExchangeCurrency() {
        return exchangeCurrency;
    }

    public void setExchangeCurrency(String exchangeCurrency) {
        this.exchangeCurrency = exchangeCurrency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
