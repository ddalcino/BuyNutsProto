package com.cs4310.epsilon.nutsinterface;

import java.util.Calendar;

/**
 * Created by Mike on 10/23/2015.
 */

//TODO: make this class implement the Parcelable interface
public class RequestFilteredSellOffer {
    Double min_weight;
    Double max_weight;
    Double max_price_per_unit;
    Double min_price_per_unit;
    Commodity.Type commodity;
    Calendar earliest;
    Calendar latest;
    Boolean expired;
    UnitsWt.Type units;

    public RequestFilteredSellOffer(Double min_weight, Double max_weight,
                                    Double min_price_per_unit, Double max_price_per_unit,
                                    Commodity.Type commodity, Boolean expired,
                                    Calendar earliest, UnitsWt.Type units) {
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.max_price_per_unit = max_price_per_unit;
        this.min_price_per_unit = min_price_per_unit;
        this.commodity = commodity;
        this.expired = expired;
        this.earliest = earliest;
        this.units = units;
    }
    public RequestFilteredSellOffer(Double min_weight, Double max_weight,
                                    Double min_price_per_unit, Double max_price_per_unit,
                                    Commodity.Type commodity, Boolean expired,
                                    UnitsWt.Type units) {
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.max_price_per_unit = max_price_per_unit;
        this.min_price_per_unit = min_price_per_unit;
        this.commodity = commodity;
        this.expired = expired;
        //default for earliest: 1 week ago
        this.earliest = Calendar.getInstance();
        earliest.add(Calendar.DAY_OF_MONTH, -7);

        this.units=units;
    }

    @Override
    public String toString(){
        return  "PPU: " + min_price_per_unit + " to " + max_price_per_unit +
                "\nbetween " + min_weight + " and " + max_weight +
                    " in units " + units.toString() +
                "\nType: " + commodity.toString() +
                "\nExpired: " + expired +
                "\nEarliest: " + earliest.getTime();
    }
}