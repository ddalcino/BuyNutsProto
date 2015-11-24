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
    //String specification;
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

//    public void setSpecification(String specification) {
//        this.specification = specification;
//    }
//
//    public String getSpecification() {
//        return specification;
//    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getTerms() {
        return terms;
    }

    public void setCommodity(String _commodity) {
        commodity = _commodity;
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


    /**
     * An exception, thrown only by the constructor for SellOffer(String stringCode).
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
     * @param stringCode   A string of the form "ppu#commodity#maxWeight#minWeight#terms"
     * @throws SellOfferStringArrayException    this contains a message indicating the
     *          first item in the parameter array that
     */
    public SellOffer(String stringCode) throws SellOfferStringArrayException {
        String[] stringArray = stringCode.split("#");
        int index = 0;
        try {
            // This statement parses a string into a Long, then turns it into
            // a string again. The whole point is for input validation.
            this.seller_id = "" + Long.parseLong(stringArray[index++]);
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("sellerID is not a Long");
        }
        try {
            this.price_per_unit = Double.parseDouble(stringArray[index++]);
            if (price_per_unit < 0) {
                throw new SellOfferStringArrayException("pricePerUnit less than zero");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("pricePerUnit is not a double");
        }
        this.commodity = stringArray[index++].toLowerCase();
        if (    !commodity.equals("walnut") &&
                !commodity.equals("cashew") &&
                !commodity.equals("pecan") &&
                !commodity.equals("almond") ) {
            throw new SellOfferStringArrayException("Invalid commodity type");
        }
        try {
            this.max_weight = Double.parseDouble(stringArray[index++]);
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("maxWeight is not a double");
        }
        try {
            this.min_weight = Double.parseDouble(stringArray[index++]);
            if (min_weight < 0) {
                throw new SellOfferStringArrayException("minWeight less than zero");
            }
            if (max_weight < min_weight) {
                throw new SellOfferStringArrayException("maxWeight less than minWeight");
            }
        } catch (NumberFormatException e) {
            throw new SellOfferStringArrayException("minWeight is not a double");
        }
        // The last part is terms. When the string code version of this was made,
        // it encoded all '#' characters as '&num;', so we can retrieve the original
        // terms list with a replacement.
        this.terms = stringArray[index++].replace("&num;", "#");
    }

    /*
    public SellOffer(Double ppu, Double max_weight, Double min_weight,
                     String commodity, String seller_id, String terms,
                     Boolean expired) {
        this.expired = false;
        //offerBirthday = Calendar.getInstance();
        this.max_weight = max_weight;
        this.min_weight = min_weight;
        this.price_per_unit = ppu;
        this.commodity = commodity;
        this.seller_id = seller_id;
        this.terms = terms;
        this.expired = expired;
//        id = new Long(curr_id);
//        curr_id += 1;
    }
    */

}
