package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.offerFilterEndpoint.OfferFilterEndpoint;
import com.nutsinterface.mike.myapplication.backend.offerFilterEndpoint.model.OfferFilter;

import java.io.IOException;

/**
 * An AsyncTask that sends the backend a RequestFilteredSellOffer
 * object, with the expectation that the server will remember those filter
 * settings and use them each time it receives a request for SellOffers
 * and the user has the userID associated with that filter.
 *
 * TODO: This class uses a StubEndpoint and won't do anything for real until a real endpoint is created!
 *
 * Created by dave on 11/13/15.
 */
public class SetFilterAsyncTask extends AsyncTask<RequestFilteredSellOffer, Void, String> {

    private static OfferFilterEndpoint offerFilterEndpoint = null;
    private Context context;
    public SetFilterAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(RequestFilteredSellOffer... params) {
        if (params == null || params[0] == null) {
            return "SetFilter Failed";
        }
        RequestFilteredSellOffer newFilter = params[0];



        if (offerFilterEndpoint == null) {
            OfferFilterEndpoint.Builder builder = new OfferFilterEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            offerFilterEndpoint = builder.build();
        }/*
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
        */

        OfferFilter offerFilter = new OfferFilter();
        offerFilter.setAssociatedUserID(newFilter.getAssociatedUserID());
        offerFilter.setCommodity(newFilter.getCommodity());
        offerFilter.setMinWeight(newFilter.getMinWeight());
        offerFilter.setMaxWeight(newFilter.getMaxWeight());
        offerFilter.setMinPricePerUnit(newFilter.getMinPricePerUnit());
        offerFilter.setMaxPricePerUnit(newFilter.getMaxPricePerUnit());
        offerFilter.setExpired(newFilter.getExpired());
        offerFilter.setMyOwnOffersOnly(newFilter.getMyOwnOffersOnly());
        offerFilter.setEarliest(newFilter.getEarliest());
        offerFilter.setLatest(newFilter.getLatest());

        try {
            offerFilter = offerFilterEndpoint.submitFilter(offerFilter).execute();
            Log.i(Constants.ASYNC_TAG, "offerFilter on commodity : " + offerFilter.getCommodity());
            return("Complete");
        } catch (IOException e) {
            e.printStackTrace();
            return "SetFilterEndpoint Failed\n" + e.getLocalizedMessage();
        }
    }
    /*
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
    */
}
