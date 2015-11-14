package com.cs4310.epsilon.nutsinterface;

import java.util.Calendar;

/**
 * Created by Mike on 10/23/2015.
 *
 * Changed by Dave on 11/13/2015:
 *      Removed UnitsWt.Type data member
 *      Added Long associatedUserID data member
 */

//TODO: make this class implement the Parcelable interface
public class RequestFilteredSellOffer {
    Long associatedUserID;
    Double min_weight;
    Double max_weight;
    Double max_price_per_unit;
    Double min_price_per_unit;
    String commodity;
    Long earliest;
    Long latest;
    Boolean expired;
    //UnitsWt.Type units; deprecated

    public RequestFilteredSellOffer(Long userID, Double min_weight, Double max_weight,
                                    Double min_price_per_unit, Double max_price_per_unit,
                                    String commodity, Boolean expired,
                                    Long earliest) {
        this.associatedUserID = userID;
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.max_price_per_unit = max_price_per_unit;
        this.min_price_per_unit = min_price_per_unit;
        this.commodity = commodity;
        this.expired = expired;
        this.earliest = earliest;
        //this.units = units;
    }
    public RequestFilteredSellOffer(Long userID, Double min_weight, Double max_weight,
                                    Double min_price_per_unit, Double max_price_per_unit,
                                    String commodity, Boolean expired) {
        this.associatedUserID = userID;
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.max_price_per_unit = max_price_per_unit;
        this.min_price_per_unit = min_price_per_unit;
        this.commodity = commodity;
        this.expired = expired;

        //default for earliest: 1 week ago
        Calendar oneWeekAgo = Calendar.getInstance();
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -7);
        this.earliest = oneWeekAgo.getTimeInMillis();
        //this.units=units;
    }

    @Override
    public String toString(){
        return  "PPU: " + min_price_per_unit + " to " + max_price_per_unit +
                "\nbetween " + min_weight + " and " + max_weight +
                //    " in units " + units.toString() +
                "\nType: " + commodity +
                "\nExpired: " + expired +
                "\nEarliest: " + earliest +
                "\nAssociated userID: " + associatedUserID;
    }
}