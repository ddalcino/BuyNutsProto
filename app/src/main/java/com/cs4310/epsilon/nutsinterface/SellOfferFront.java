package com.cs4310.epsilon.nutsinterface;

import android.os.Parcel;
import android.os.Parcelable;

import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

/**
 * Holds all pertinent data that a user or seller could want regarding an
 * offer to sell a commodity. Meant to be made by sellers, and read by buyers.
 *
 * Created by dave on 11/2/15.
 */
public class SellOfferFront implements Parcelable{

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
     * The type of nuts: ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    String cType;

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
     * @param cType         String: ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    public SellOfferFront(Long id, String sellerId, Long offerBirthday,
                          Double pricePerUnit, Double minWeight, Double maxWeight,
                          String terms, String cType,
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
     * @param cType         String: ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    public SellOfferFront(String sellerId, Double pricePerUnit, Double minWeight,
                          Double maxWeight, String terms, String cType) {
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
        this.cType = s.getCommodity();
        //this.units = null;

        this.id = s.getId(); // null; //can't be known by app yet
        this.offerBirthday = -1l; // Calendar.getInstance();
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
                "\nType: " + cType +
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

    public String getcType() {
        return cType;
    }

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

    // Setters:
    public void setId(Long id) {
        this.id = id;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public void setOfferBirthday(Long offerBirthday) {
        this.offerBirthday = offerBirthday;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(sellerId);
        dest.writeLong(offerBirthday);
        dest.writeDouble(pricePerUnit);
        dest.writeDouble(minWeight);
        dest.writeDouble(maxWeight);
        dest.writeString(terms);
        dest.writeString(cType);
        dest.writeByte((byte) (expired ? 1 : 0));
    }

    /**
     * Constructor that generates a SellOfferFront from a Parcel
     * @param in    The input Parcel
     */
    private SellOfferFront(Parcel in){
        id = in.readLong();
        sellerId = in.readString();
        offerBirthday = in.readLong();
        pricePerUnit = in.readDouble();
        minWeight = in.readDouble();
        maxWeight = in.readDouble();
        terms = in.readString();
        cType = in.readString();
        expired = in.readByte() != 0;
    }

    /**
     * Required by Android to create new SellOfferFront objects from parcel
     */
    public static final Parcelable.Creator<SellOfferFront> CREATOR
            = new Parcelable.Creator<SellOfferFront>() {
        public SellOfferFront createFromParcel(Parcel in) {
            return new SellOfferFront(in);
        }

        public SellOfferFront[] newArray(int size) {
            return new SellOfferFront[size];
        }
    };

}
