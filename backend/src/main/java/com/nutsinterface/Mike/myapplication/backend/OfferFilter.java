package com.nutsinterface.Mike.myapplication.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Mike on 11/21/2015.
 */
@Entity
public class OfferFilter {
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Id
    Long Id;

    public Long getAssociatedUserID() {
        return associatedUserID;
    }

    public void setAssociatedUserID(Long associatedUserID) {
        this.associatedUserID = associatedUserID;
    }

    @Index
    Long associatedUserID;

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    String commodity;

    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    Double minWeight;

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Double getMinPricePerUnit() {
        return minPricePerUnit;
    }

    public void setMinPricePerUnit(Double minPricePerUnit) {
        this.minPricePerUnit = minPricePerUnit;
    }

    public Double getMaxPricePerUnit() {
        return maxPricePerUnit;
    }

    public void setMaxPricePerUnit(Double maxPricePerUnit) {
        this.maxPricePerUnit = maxPricePerUnit;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getMyOwnOffersOnly() {
        return myOwnOffersOnly;
    }

    public void setMyOwnOffersOnly(Boolean myOwnOffersOnly) {
        this.myOwnOffersOnly = myOwnOffersOnly;
    }

    public Long getEarliest() {
        return earliest;
    }

    public void setEarliest(Long earliest) {
        this.earliest = earliest;
    }

    public Long getLatest() {
        return latest;
    }

    public void setLatest(Long latest) {
        this.latest = latest;
    }

    Double maxWeight;
    Double minPricePerUnit;
    Double maxPricePerUnit;
    Boolean expired;
    Boolean myOwnOffersOnly;
    Long earliest;
    Long latest;



}
