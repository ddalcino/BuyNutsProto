package com.cs4310.epsilon.nutsinterface;

import java.util.Calendar;

/**
 * Holds all pertinent data that a user or seller could want regarding an
 * offer to sell a commodity. Meant to be made by sellers, and read by buyers.
 *
 * Created by dave on 11/2/15.
 */
public class SellOffer {
    // REMOVE IN RELEASE CANDIDATE
    // ONLY USED FOR PROTOTYPE
    //static long curr_id = 0;

    // Instance Data Members:
    /**
     * A unique identifier for each SellOffer object; set by the backend
     * when it is added to the database. No two SellOffer objects can have
     * the same id.
     */
    Long id;
    /** that the frontend can determine on its own
     * An identifier that indicates a unique seller. Many SellOffer objects
     * can have the same sellerId, but only one Seller can have that id.
     */
    Long sellerId;
    /**
     * The date/time at which the offer was created. Should probably be set by
     * the backend as soon as the SellOffer is received, not by the frontend.
     */
    Calendar offerBirthday;
    /**
     * Price (in USD) per unit weight, which is defined by this.units
     */
    Double pricePerUnit;
    /**
     * Minimum weight for a shipment. The seller will not sell less than this
     * amount for this price.
     */
    Double minWeight;
    /**
     * Maximum weight for a shipment. The seller is unwilling to sell more than
     * this amount in one shipment.
     */
    Double maxWeight;
    /**
     * Any other terms or specifications defined by the seller; this is a
     * chance for the seller to write whatever they want to say about their nuts
     */
    String terms;

    /**
     * The type of nuts: Commodity.Type.ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    Commodity.Type cType;
    /**
     * The units of weight; helps to define pricePerUnit, minWeight, and
     * maxWeight in concrete terms.,br/>
     * UnitsWt.LB, KG, NET_TON etc.
     */
    UnitsWt.Type units;
    /**
     * If the SellOffer is a record of an offer that is sold out or no longer
     * available, expired is set to true. If it's still available, it's false.
     */
    Boolean expired;

    /**
     * SellOffer constructor, meant to be used by the backend, or anyone who
     * knows everything that should be in SellOffer. Parameters are provided
     * for all data members.<br/>
     *
     * @param id            the SellOffer's id number
     * @param offerBirthday the date/time at which the offer was created
     * @param expired       whether or not the offer is expired
     * @param sellerId      the seller's id number
     * @param pricePerUnit  price per unit
     * @param minWeight     minimum weight
     * @param maxWeight     maximum weight
     * @param terms         any other terms or specifications defined by the
     *                      seller; this is a chance for the seller to write
     *                      whatever they want to say about their nuts
     * @param cType         Commodity.Type.ALMONDS, WALNUTS, PECANS, or CASHEWS
     * @param units         UnitsWt.LB, KG, NET_TON etc.
     */
    public SellOffer(Long id, Long sellerId, Calendar offerBirthday,
                     Double pricePerUnit, Double minWeight, Double maxWeight,
                     String terms, Commodity.Type cType, UnitsWt.Type units,
                     Boolean expired) {
        this.id = id;
        this.sellerId = sellerId;
        this.offerBirthday = offerBirthday;
        this.pricePerUnit = pricePerUnit;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.terms = terms;
        this.cType = cType;
        this.units = units;
        this.expired = expired;
    }

    /**
     * SellOffer constructor, meant to be used by the frontend; parameters are
     * used to fill all data members that the frontend can determine on its own.<br/>
     *
     * id is set to null by this function, since a real value can only be set by the backend.<br/>
     * expired is set to false, since it is unlikely that a user would want to
     * generate a new offer that's already expired.<br/>
     * offerBirthday is set to the date/time at which the object was made.
     * @param sellerId      the seller's id number
     * @param pricePerUnit  price per unit
     * @param minWeight     minimum weight
     * @param maxWeight     maximum weight
     * @param terms         any other terms or specifications defined by the
     *                      seller; this is a chance for the seller to write
     *                      whatever they want to say about their nuts
     * @param cType         Commodity.Type.ALMONDS, WALNUTS, PECANS, or CASHEWS
     * @param units         UnitsWt.LB, KG, NET_TON etc.
     */
    public SellOffer(Long sellerId, Double pricePerUnit, Double minWeight,
                     Double maxWeight, String terms, Commodity.Type cType,
                     UnitsWt.Type units) {
        this.sellerId = sellerId;
        this.pricePerUnit = pricePerUnit;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.terms = terms;
        this.cType = cType;
        this.units = units;

        // These three aren't used by the front end until they come back from the backend
        this.id = null; //can't be known by app yet
        this.offerBirthday = Calendar.getInstance();
        this.expired = false; //if it's new it's not expired
    }

    /**
     * Turns the SellOffer into a string.
     * @return  A string representation of the SellOffer object
     */
    @Override
    public String toString(){
        return  "Seller id=" + sellerId +
                "\nPPU=" + pricePerUnit +
                "\nbetween " + minWeight + " and " + maxWeight +
                    " in units " + units.toString() +
                "\nType: " + cType.toString() +
                "\nTerms: " + terms;
    }
}
