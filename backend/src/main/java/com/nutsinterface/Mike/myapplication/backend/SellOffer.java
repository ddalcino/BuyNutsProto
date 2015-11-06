package com.nutsinterface.Mike.myapplication.backend;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
/**
 * Created by Mike on 10/28/2015.
 */
@Entity
public class SellOffer {
    // REMOVE IN RELEASE CANDIDATE
    // ONLY USED FOR PROTOTYPE
    //static long curr_id = 0;
    @Id
    Long id;
    String offerBirthday;
    @Index
    Double price_per_unit;
    @Index
    Double min_weight;
    @Index
    Double max_weight;
    String specification;
    String terms;
    @Index
    String commodity;
    @Index
    String seller_id;
    Boolean expired;

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getOfferBirthday() {
       return offerBirthday;
    }

    public void setOfferBirthday(String offerBirthday) {
        this.offerBirthday = offerBirthday;
    }

    public Double getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(Double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public void setMax_weight(Double max_weight) {
        this.max_weight = max_weight;
    }

    public Double getMax_weight() {
        return max_weight;
    }

    public void setMin_weight(Double min_weight) {
        this.min_weight = min_weight;
    }

    public Double getMin_weight() {
        return min_weight;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getCommodity() {
        return commodity;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
    public SellOffer() {
        // id = new Long(curr_id);
        //curr_id+=1;
    }
    /*
    public SellOffer(Double ppu, Integer max_weight, Integer min_weight, Integer commodity) {
        this.expired = false;
        offerBirthday = Calendar.getInstance();
        this.max_weight = max_weight;
        this.min_weight = min_weight;
        this.price_per_unit = ppu;
        this.commodity = commodity;
        id = new Long(curr_id);
        curr_id += 1;
    }
    */
}
