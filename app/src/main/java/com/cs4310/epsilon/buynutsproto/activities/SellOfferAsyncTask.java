package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Context;
import android.os.AsyncTask;

import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mike on 11/5/2015.
 */
public class SellOfferAsyncTask extends AsyncTask<Void, Void, List<SellOffer>> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    SellOfferAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected List<SellOffer> doInBackground(Void... Params) {
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl("https://buynutsproto.appspot.com/_ah/api/");



        sellOfferEndpoint = builder.build();
    }
        try {
            return sellOfferEndpoint.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }
    @Override
    protected void onPostExecute(List<SellOffer> result) {
        ArrayList<SellOfferFront> sellOffers = new ArrayList<SellOfferFront>();
        for (SellOffer s : result) {
            sellOffers.add(new SellOfferFront(s));
        }

        NewsActivity newsActivity = (NewsActivity) context;
        newsActivity.setStatusMsg("Received " + sellOffers.size() + " SellOffers from backend");
        newsActivity.fillListView(sellOffers);
        newsActivity.updateListView();
    }
}

