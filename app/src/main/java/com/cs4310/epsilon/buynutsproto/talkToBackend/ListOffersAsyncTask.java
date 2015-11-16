package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;

import com.cs4310.epsilon.buynutsproto.activities.NewsActivity;
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
 * An Asynchronous Task that runs in the background. It asks the backend for a
 * list of SellOffer objects, and when it receives that list, it sends the list
 * to the activity that created the task and tells it to update its view.
 *
 * Created by Mike on 11/5/2015.
 */
public class ListOffersAsyncTask extends AsyncTask<Void, Void, List<SellOffer>> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    public ListOffersAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected List<SellOffer> doInBackground(Void... Params) {
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl("https://backupnuts-1129.appspot.com/_ah/api/");
            //).setRootUrl("http://10.0.2.2:8080/_ah/api/");



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

