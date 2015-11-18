package com.cs4310.epsilon.nutsinterface;

// Used to allow Android OS to package the object and send it between activities
import android.os.Parcel;
import android.os.Parcelable;

// Used to convert between backend objects and frontend objects
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.util.regex.Pattern;

//import com.googlecode.objectify.annotation.Entity;
//import com.googlecode.objectify.annotation.Id;
//import com.googlecode.objectify.annotation.Index;

/**
 * Holds all pertinent data that a user or seller could want regarding an
 * offer to sell a commodity. Meant to be made by sellers, and read by buyers.
 *
 * Created by dave on 11/2/15.
 */
//@Entity
public class SellOfferFront implements Parcelable{

    static final long INVALID_OFFER_BIRTHDAY = -1l;

    // Instance Data Members:
    /**
     * A unique identifier for each SellOfferFront object; set by the backend
     * when it is added to the database. No two SellOfferFront objects can have
     * the same id.
     */
    //@Id
    Long id;
    /**
     * The date/time at which the offer was created, measured in milliseconds
     * since 12:00AM January 1, 1970, in Universal Coordinated Time (UTC).
     * Because we're using UTC, this value should not be affected by timezone
     * differences. The current UTC time in milliseconds can be accessed by
     * calling System.currentTimeMillis() or Date.getTime(). We cannot be
     * certain that every Android device's system clock will be synchronized
     * and report the same UTC time, so we will rely on the backend to set this
     * value.
     */
    Long offerBirthday;
    /**
     * An identifier that indicates a unique seller. Many SellOfferFront objects
     * can have the same sellerId, but only one Seller can have that id.
     */
    //@Index
    String sellerId;
    /**
     * Price in USD per pound. Users using different weight units will be
     * allowed to view this figure in different units on the frontend, because
     * the frontend will use the code in UnitsWt to convert this value to their
     * preferred unit types. Alternate currency types are not supported.
     */
    //@Index
    Double pricePerUnit;
    /**
     * Minimum weight, in lbs, for a shipment. The seller will not sell less
     * than this amount for this price.
     */
    //@Index
    Double minWeight;
    /**
     * Maximum weight, in lbs, for a shipment. The seller is unwilling to sell
     * more than this amount in one shipment.
     */
    //@Index
    Double maxWeight;
    /**
     * Any other terms or specifications defined by the seller; this is a
     * chance for the seller to write whatever they want to say about their nuts
     */
    String terms;

    /**
     * The type of nuts: ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    //@Index
    String commodity;

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
     * @param sellerId      the seller's id number
     * @param offerBirthday the date/time at which the offer was created
     * @param pricePerUnit  price per unit
     * @param minWeight     minimum weight
     * @param maxWeight     maximum weight
     * @param terms         any other terms or specifications defined by the
     *                      seller; this is a chance for the seller to write
     *                      whatever they want to say about their nuts
     * @param commodity         String: ALMONDS, WALNUTS, PECANS, or CASHEWS
     * @param expired       whether or not the offer is expired
     */
    public SellOfferFront(Long id, String sellerId, Long offerBirthday,
                          Double pricePerUnit, Double minWeight, Double maxWeight,
                          String terms, String commodity, Boolean expired) {
        this.id = id;
        this.sellerId = sellerId;
        this.offerBirthday = offerBirthday;
        this.pricePerUnit = pricePerUnit;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.terms = terms;
        this.commodity = commodity;
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
     * @param commodity         String: ALMONDS, WALNUTS, PECANS, or CASHEWS
     */
    public SellOfferFront(String sellerId, Double pricePerUnit, Double minWeight,
                          Double maxWeight, String terms, String commodity) {
        this.sellerId = sellerId;
        this.pricePerUnit = pricePerUnit;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.terms = terms;
        this.commodity = commodity;
        //this.units = units;

        // These three aren't used by the front end until they come back from the backend
        this.id = null; //can't be known by app yet
        this.offerBirthday = null; // Calendar.getInstance();
        this.expired = false; //if it's new it's not expired
    }


    /**
     * Placeholder function to convert backend-type SellOffer objects to
     * frontend-type SellOfferFront objects
     * At this point, the only incompatible datamember in SellOffer is offerBirthday
     *
     * @param s Backend-type SellOffer object
     */
    public SellOfferFront(SellOffer s) {
        this.sellerId = s.getSellerId();
        this.pricePerUnit = s.getPricePerUnit();
        this.minWeight = s.getMinWeight();
        this.maxWeight = s.getMaxWeight();
        this.terms = s.getTerms();
        this.commodity = s.getCommodity();
        //this.units = null;

        this.id = s.getId();
        /**
         * This is the only datamember that needs to be handled differently right now
         */
        this.offerBirthday = INVALID_OFFER_BIRTHDAY; // s.getOfferBirthday()
        this.expired = s.getExpired();
//        if(expired == null) {
//            expired = true;
//        }
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
                "\nType: " + commodity +
                "\nTerms: " + terms;
    }

    /**
     * Turns the SellOfferFront object into an array of strings, of the
     * following form:<br/>
     * (note that items marked as being doubles or longs are actually the
     * string representation of that form, and not an actual numeric type)
     * @return  id              // a long; the unique id for this SellOffer<br/>
     *          sellerId        // a string of characters 0-9;<br/>
     *          offerBirthday;  // a long, represents milliseconds since Jan 1 1970<br/>
     *          pricePerUnit;   // a double<br/>
     *          minWeight;      // a double<br/>
     *          maxWeight;      // a double<br/>
     *          commodity;      // a string; can be "walnut", "pecan", "cashew", or "almond"<br/>
     *          expired;        // a string, either "true" or "false"<br/>
     *          terms;          // a string, unchecked<br/>
     */
    public String[] toStringArray() {
        return new String[]{
                id.toString(),
                sellerId,
                offerBirthday.toString(),
                pricePerUnit.toString(),
                minWeight.toString(),
                maxWeight.toString(),
                commodity.toLowerCase(),
                (expired ? "true" : "false"),
                terms
        };
    }

    /**
     * Turns a SellOfferFront into a SellOffer object
     * @return A SellOffer version of the SellOfferFront
     */
    public SellOffer toSellOffer() {
        // should be using a constructor with a parameter list, but it doesn't exist
        SellOffer so = new SellOffer();
        if(id != null) so.setId(id);
        if(sellerId != null) so.setSellerId(sellerId);
        // SellOffer requires a String for this field, right now
        if(offerBirthday != null) so.setOfferBirthday(offerBirthday.toString());
        if(pricePerUnit != null) so.setPricePerUnit(pricePerUnit);
        if(minWeight != null) so.setMinWeight(minWeight);
        if(maxWeight != null) so.setMaxWeight(maxWeight);
        if(commodity != null) so.setCommodity(commodity);
        if(expired != null) so.setExpired(expired);
        if(terms != null) so.setTerms(terms);

        return so;
    }

    /**
     * An exception, thrown only by the constructor for SellOfferFront(String[]).
     * It exists for type safety and to hold specific messages about what went
     * wrong with the constructor call.
     */
    public class SellOfferStringArrayException extends Exception {
        SellOfferStringArrayException(String msg) {
            super(msg);
        }
    }

    /**
     * Constructor meant to build a SellOfferFront object out of an array of strings.
     * @param stringArray   An array of strings, in the following form:<br/><br/>
     *          id              // a long; the unique id for this SellOffer<br/>
     *          sellerId        // a string of characters 0-9;<br/>
     *          offerBirthday;  // a long, represents milliseconds since Jan 1 1970<br/>
     *          pricePerUnit;   // a double<br/>
     *          minWeight;      // a double<br/>
     *          maxWeight;      // a double<br/>
     *          commodity;      // a string; can be "walnut", "pecan", "cashew", or "almond"<br/>
     *          expired;        // a string, either "true" or "false"<br/>
     *          terms;          // a string, unchecked<br/>
     * @throws SellOfferStringArrayException    this contains a message indicating the
     *          first item in the parameter array that
     */
    public SellOfferFront(String[] stringArray) throws SellOfferStringArrayException {
        try {
            this.id = Long.parseLong(stringArray[0]);
            if (id < 0) {
                throw new SellOfferStringArrayException("User ID less than zero");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("User ID is not a long");
        }
        this.sellerId = stringArray[1];
        if (!Pattern.matches("[0-9]+", stringArray[1])) {
            throw new SellOfferStringArrayException("SellerId is not a number");
        }

        try {
            this.offerBirthday = Long.parseLong(stringArray[2]);
            if (offerBirthday < 0) {
                throw new SellOfferStringArrayException("offerBirthday less than zero");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("OfferBirthday is not a long");
        }
        try {
            this.pricePerUnit = Double.parseDouble(stringArray[3]);
            if (pricePerUnit < 0) {
                throw new SellOfferStringArrayException("pricePerUnit less than zero");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("pricePerUnit is not a double");
        }
        try {
            this.minWeight = Double.parseDouble(stringArray[4]);
            if (minWeight < 0) {
                throw new SellOfferStringArrayException("minWeight less than zero");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("minWeight is not a double");
        }
        try {
            this.maxWeight = Double.parseDouble(stringArray[5]);
            if (maxWeight < minWeight) {
                throw new SellOfferStringArrayException("maxWeight less than minWeight");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("maxWeight is not a double");
        }
        this.commodity = stringArray[6].toLowerCase();
        if (    !commodity.equals("walnut") &&
                !commodity.equals("cashew") &&
                !commodity.equals("pecan") &&
                !commodity.equals("almond") ) {
            throw new SellOfferStringArrayException("Invalid commodity type");
        }
        if (stringArray[7].equals("true")) {
            this.expired = true;
        } else if (stringArray[7].equals("false")) {
            this.expired = false;
        } else {
            throw new SellOfferStringArrayException("Expired field improperly set");
        }

        // the last item is terms; this isn't checked because it's allowed to
        // be anything
        this.terms = stringArray[8];
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

    public String getCommodity() {
        return commodity;
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
        dest.writeString(commodity);
        if (expired == null) {
            dest.writeByte((byte) -1);
        } else if (expired) {
            dest.writeByte((byte) 1);
        } else {
            dest.writeByte((byte) 0);
        }
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
        commodity = in.readString();
        byte b = in.readByte();
        if (b == -1) {
            expired = null;
        } else if (b == 0) {
            expired = false;
        } else if(b == 1) {
            expired = true;
        }
    }

    /**
     * Required by Android OS to create new SellOfferFront objects from parcel
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
