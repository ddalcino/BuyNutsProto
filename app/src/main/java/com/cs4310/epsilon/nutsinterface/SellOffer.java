package com.cs4310.epsilon.nutsinterface;

import java.util.Calendar;

/**
 * Created by dave on 11/2/15.
 */
public class SellOffer {
    // REMOVE IN RELEASE CANDIDATE
    // ONLY USED FOR PROTOTYPE
    static long curr_id = 0;

    // Instance Data Members:
    Long id;
    Long sellerId;
    Calendar offerBirthday;
    Double price_per_unit;
    Double min_weight;
    Double max_weight;
    //String specification; //duplicate of terms
    String terms;
    Commodity.Type cType;
    UnitsWt.Type units;
    Boolean expired;

    public SellOffer(Long sellerId, Double price_per_unit, Double min_weight,
                     Double max_weight, String terms, Commodity.Type cType,
                     UnitsWt.Type units) {
        this.sellerId = sellerId;
        this.price_per_unit = price_per_unit;
        this.min_weight = min_weight;
        this.max_weight = max_weight;
        this.terms = terms;
        this.cType = cType;
        this.units = units;

        // These three aren't used by the front end until they come back from the backend
        this.id = null; //can't be known by app yet
        this.offerBirthday = Calendar.getInstance();
        this.expired = false; //if it's new it's not expired
    }

    public String toString(){
        String result = "Seller id=" + sellerId +
                "\nPPU=" + price_per_unit +
                "\nbetween " + min_weight + " and " + max_weight +
                    " in units " + units.toString() +
                "\nType: " + cType.toString() +
                "\nTerms: " + terms;
        return result;
    }
}
