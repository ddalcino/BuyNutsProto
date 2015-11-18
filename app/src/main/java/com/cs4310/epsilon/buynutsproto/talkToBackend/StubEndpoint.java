package com.cs4310.epsilon.buynutsproto.talkToBackend;

import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is a multipurpose stub class, meant to stand in for any Endpoint
 * class that hasn't been written yet. This enables us to call Endpoint
 * services using the proper syntax and receive results of the expected types,
 * without having to use something that isn't there yet. Once the real
 * Endpoints have been made, it should be a simple matter to switch over to
 * using them. <br/>
 * <br/>
 * Currently, the StubEndpoint class provides two services:<br/>
 *
 *      setFilter(): takes a RequestFilteredSellOffer as a parameter,
 *          and is meant to store RequestFilteredSellOffer objects in the
 *          backend. The execute() method returns a string that describes
 *          what it did.
 * <br/>
 *      getContactInfo(): takes a String as a parameter, which contains a
 *          sellerID. The execute() method returns an array of 3 Strings:
 *          the seller name, the seller phone number, and the seller email.
 *
 *
 * Created by dave on 11/13/15.
 */
public class StubEndpoint {
    static String sellerID = null;

    public static class Builder {

        Builder(HttpTransport t, AndroidJsonFactory j, HttpRequestInitializer i){

        }
        Builder setRootUrl (String s) {
            return this;
        }
        StubEndpoint build () {
            return new StubEndpoint();
        }
    }

    public class GetContactInfo {
        String[] execute() {
            return new String[] {"Jenny (name provided by stub) with id (from SellOffer) "+ sellerID,"5108675309","jenny@nuts.com"};
        }
    }

    GetContactInfo getContactInfo(String sellerId) throws IOException {
        sellerID = sellerId;
        return new GetContactInfo();
    }

    public class SetFilter {
        String execute() {
            return "Filter set by StubEndpoint, not real Endpoint";
        }
    }

    SetFilter setFilter(RequestFilteredSellOffer filter) throws IOException {
        return new SetFilter();
    }

    public class List {
        List execute() {
            return new List();
        }
        java.util.List getItems(){
            java.util.List list = new ArrayList<SellOffer>();
            list.add(new SellOfferFront(6l, "1234", 1234567l, 5.0, 100.0,
                    1000.0, "Buy a lot please", "walnut", false).toSellOffer());
            list.add(new SellOfferFront(7l, "12345", 12345678l, 5.0, 100.0,
                    1000.0, "Buy a lot more please", "cashew", false).toSellOffer());
            return list;
        }
    }

    List list(Long userID) throws IOException {
        return new List();
    }
}
