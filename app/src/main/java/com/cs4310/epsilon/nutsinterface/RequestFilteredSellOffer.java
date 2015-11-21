package com.cs4310.epsilon.nutsinterface;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Mike on 10/23/2015.
 *
 * Changed by Dave on 11/13/2015:
 *      Removed UnitsWt.Type data member
 *      Added Long associatedUserID data member
 * Changed by Dave on 11/18/2015:
 *      Implemented Parcelable
 *      Added field: Boolean myOwnOffersOnly
 */

public class RequestFilteredSellOffer implements Parcelable {
    public static final Long INVALID_TIME = -1l;

    Long associatedUserID;
    String commodity;
    Double minWeight;
    Double maxWeight;
    Double minPricePerUnit;
    Double maxPricePerUnit;
    Boolean expired;
    Boolean myOwnOffersOnly;
    Long earliest;
    Long latest;

    /**
     * Constructor that fills in all the parameters
     * @param associatedUserID
     * @param commodity
     * @param minWeight
     * @param maxWeight
     * @param minPricePerUnit
     * @param maxPricePerUnit
     * @param expired
     * @param myOwnOffersOnly
     * @param earliest
     * @param latest
     */
    public RequestFilteredSellOffer(Long associatedUserID, String commodity,
                                    Double minWeight, Double maxWeight,
                                    Double minPricePerUnit, Double maxPricePerUnit,
                                    Boolean expired, Boolean myOwnOffersOnly,
                                    Long earliest, Long latest) {
        this.associatedUserID = associatedUserID;
        this.commodity = commodity;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minPricePerUnit = minPricePerUnit;
        this.maxPricePerUnit = maxPricePerUnit;
        this.expired = expired;
        this.myOwnOffersOnly = myOwnOffersOnly;
        this.earliest = earliest;
        this.latest = latest;
    }

    /**
     *
     * @param associatedUserID
     * @param commodity
     * @param minWeight
     * @param maxWeight
     * @param minPricePerUnit
     * @param maxPricePerUnit
     * @param expired
     * @param myOwnOffersOnly
     * @param earliest
     */
    public RequestFilteredSellOffer(Long associatedUserID, String commodity,
                                    Double minWeight, Double maxWeight,
                                    Double minPricePerUnit, Double maxPricePerUnit,
                                    Boolean expired, Boolean myOwnOffersOnly,
                                    Long earliest) {
        this.associatedUserID = associatedUserID;
        this.commodity = commodity;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minPricePerUnit = minPricePerUnit;
        this.maxPricePerUnit = maxPricePerUnit;
        this.expired = expired;
        this.myOwnOffersOnly = myOwnOffersOnly;
        this.earliest = earliest;
        this.latest = INVALID_TIME;
    }

    /**
     *
     * @param associatedUserID
     * @param commodity
     * @param minWeight
     * @param maxWeight
     * @param minPricePerUnit
     * @param maxPricePerUnit
     * @param expired
     * @param myOwnOffersOnly
     */
    public RequestFilteredSellOffer(Long associatedUserID, String commodity,
                                    Double minWeight, Double maxWeight,
                                    Double minPricePerUnit, Double maxPricePerUnit,
                                    Boolean expired, Boolean myOwnOffersOnly) {
        this.associatedUserID = associatedUserID;
        this.commodity = commodity;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.minPricePerUnit = minPricePerUnit;
        this.maxPricePerUnit = maxPricePerUnit;
        this.expired = expired;
        this.myOwnOffersOnly = myOwnOffersOnly;

        //default for earliest: 1 week ago
        Calendar oneWeekAgo = Calendar.getInstance();
        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -7);
        this.earliest = oneWeekAgo.getTimeInMillis();
        this.latest = INVALID_TIME;
    }

    //    public RequestFilteredSellOffer(Long userID, Double minWeight, Double maxWeight,
//                                    Double minPricePerUnit, Double maxPricePerUnit,
//                                    String commodity, Boolean expired,
//                                    Long earliest, Boolean myOwnOffersOnly) {
//        this.associatedUserID = userID;
//        this.minWeight = minWeight;
//        this.maxWeight = maxWeight;
//        this.maxPricePerUnit = maxPricePerUnit;
//        this.minPricePerUnit = minPricePerUnit;
//        this.commodity = commodity;
//        this.expired = expired;
//        this.earliest = earliest;
//        this.myOwnOffersOnly = myOwnOffersOnly;
//    }
//    public RequestFilteredSellOffer(Long userID, Double minWeight, Double maxWeight,
//                                    Double minPricePerUnit, Double maxPricePerUnit,
//                                    String commodity, Boolean expired, Boolean myOwnOffersOnly) {
//        this.associatedUserID = userID;
//        this.minWeight = minWeight;
//        this.maxWeight = maxWeight;
//        this.maxPricePerUnit = maxPricePerUnit;
//        this.minPricePerUnit = minPricePerUnit;
//        this.commodity = commodity;
//        this.expired = expired;
//        this.myOwnOffersOnly = myOwnOffersOnly;
//
//        //default for earliest: 1 week ago
//        Calendar oneWeekAgo = Calendar.getInstance();
//        oneWeekAgo.add(Calendar.DAY_OF_MONTH, -7);
//        this.earliest = oneWeekAgo.getTimeInMillis();
//        //this.units=units;
//    }

    /**
     * Required by Android OS to create new RequestFilteredSellOffer objects from parcel
     */
    public static final Creator<RequestFilteredSellOffer> CREATOR
            = new Creator<RequestFilteredSellOffer>() {
        @Override
        public RequestFilteredSellOffer createFromParcel(Parcel in) {
            return new RequestFilteredSellOffer(in);
        }

        @Override
        public RequestFilteredSellOffer[] newArray(int size) {
            return new RequestFilteredSellOffer[size];
        }
    };

    @Override
    public String toString(){
        return  "PPU: " + minPricePerUnit + " to " + maxPricePerUnit +
                "\nbetween " + minWeight + " and " + maxWeight +
                //    " in units " + units.toString() +
                "\nType: " + commodity +
                "\nExpired: " + expired +
                "\nMy own offers only: " + myOwnOffersOnly +
                "\nEarliest: " + earliest +
                "\nAssociated userID: " + associatedUserID;
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
        dest.writeLong(associatedUserID);
        dest.writeDouble(minWeight);
        dest.writeDouble(maxWeight);
        dest.writeDouble(minPricePerUnit);
        dest.writeDouble(maxPricePerUnit);
        dest.writeString(commodity);
        dest.writeLong(earliest);
        dest.writeLong(latest);
        dest.writeBooleanArray(new boolean[]{expired, myOwnOffersOnly});
    }

    private RequestFilteredSellOffer(Parcel in) {
        associatedUserID = in.readLong();
        minWeight = in.readDouble();
        maxWeight = in.readDouble();
        minPricePerUnit = in.readDouble();
        maxPricePerUnit = in.readDouble();
        commodity = in.readString();
        earliest = in.readLong();
        latest = in.readLong();

        boolean[] booleans = new boolean[2];
        in.readBooleanArray(booleans);
        expired = booleans[0];
        myOwnOffersOnly = booleans[1];
    }

    public Long getAssociatedUserID() {
        return associatedUserID;
    }

    public String getCommodity() {
        return commodity;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public Double getMinPricePerUnit() {
        return minPricePerUnit;
    }

    public Double getMaxPricePerUnit() {
        return maxPricePerUnit;
    }

    public Boolean getExpired() {
        return expired;
    }

    public Boolean getMyOwnOffersOnly() {
        return myOwnOffersOnly;
    }

    public Long getEarliest() {
        return earliest;
    }

    public Long getLatest() {
        return latest;
    }
}