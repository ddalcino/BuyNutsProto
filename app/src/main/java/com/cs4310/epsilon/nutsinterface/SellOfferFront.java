package com.cs4310.epsilon.nutsinterface;

import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

/**
 * Holds all pertinent data that a user or seller could want regarding an
 * offer to sell a commodity. Meant to be made by sellers, and read by buyers.
 *
 * Created by dave on 11/2/15.
 */
public class SellOfferFront {
    //public static final String INVALID_ID = -1l;

    // Instance Data Members:
    /**
     * A unique identifier for each SellOfferFront object; set by the backend
     * when it is added to the database. No two SellOfferFront objects can have
     * the same id.
     */
    Long id;
    /** that the frontend can determine on its own
     * An identifier that indicates a unique seller. Many SellOfferFront objects
     * can have the same sellerId, but only one Seller can have that id.
     */
    String sellerId;
    /**
     * The date/time at which the offer was created. Should probably be set by
     * the backend as soon as the SellOfferFront is received, not by the frontend.
     */
    Long offerBirthday;
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
    //UnitsWt.Type units;
    /**
     * If the SellOfferFront is a record of an offer that is sold out or no longer
     * available, expired is set to true. If it's still available, it's false.
     */
    Boolean expired;

    /**
     * SellOfferFront constructor, meant to be used by the backend, or anyone who
     * knows everything that should be in SellOfferFront. Parameters are provided
     * for all data members.<br/>
     *
     * @param id            the SellOfferFront's id number
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
    public SellOfferFront(Long id, String sellerId, Long offerBirthday,
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
        //this.units = units;
        this.expired = expired;
    }

    /**
     * SellOfferFront constructor, meant to be used by the frontend; parameters are
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
    public SellOfferFront(String sellerId, Double pricePerUnit, Double minWeight,
                          Double maxWeight, String terms, Commodity.Type cType,
                          UnitsWt.Type units) {
        this.sellerId = sellerId;
        this.pricePerUnit = pricePerUnit;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.terms = terms;
        this.cType = cType;
        //this.units = units;

        // These three aren't used by the front end until they come back from the backend
        this.id = null; //can't be known by app yet
        this.offerBirthday = null; // Calendar.getInstance();
        this.expired = false; //if it's new it's not expired
    }


    /**
     * Placeholder function to convert backend-type SellOffer objects to
     * frontend-type SellOffer objects
     * @param s Backend-type SellOffer object
     */
    public SellOfferFront(SellOffer s) {
        try {
            this.sellerId = s.getSellerId();
        } catch (NumberFormatException e) {
            this.sellerId = "";
        }
        this.pricePerUnit = s.getPricePerUnit();
        this.minWeight = s.getMinWeight();
        this.maxWeight = s.getMaxWeight();
        this.terms = s.getTerms();
        this.cType = Commodity.toType(s.getCommodity());
        //this.units = null;

        this.id = s.getId(); // null; //can't be known by app yet
        this.offerBirthday = null; // Calendar.getInstance();
        this.expired = s.getExpired(); //if it's new it's not expired
    }

    /**
     * Turns the SellOfferFront into a string.
     * @return  A string representation of the SellOfferFront object
     */
    @Override
    public String toString(){
        return  "Seller id=" + sellerId +
                "\nPPU=" + pricePerUnit +
                "\nbetween " + minWeight + " and " + maxWeight +
                //    " in units " + units.toString() +
                "\nType: " + cType.toString() +
                "\nTerms: " + terms;
    }

    // Getters:

    public Double getMinWeight() {
        return minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public String getTerms() {
        return terms;
    }

    public Commodity.Type getcType() {
        return cType;
    }

//    public UnitsWt.Type getUnits() {
//        return units;
//    }

    public Boolean getExpired() {
        return expired;
    }

    public Long getOfferBirthday() {
        return offerBirthday;
    }

    public String getSellerId() {
        return sellerId;
    }

    public Long getId() {
        return id;
    }

    public Double getPricePerUnit() {

        return pricePerUnit;
    }

}
