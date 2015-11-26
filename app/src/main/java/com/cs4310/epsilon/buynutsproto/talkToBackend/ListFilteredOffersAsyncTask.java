
package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cs4310.epsilon.buynutsproto.activities.NewsActivity;
import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import com.cs4310.epsilon.buynutsproto.activities.Constants;

/**
 * An Asynchronous Task that runs in the background. It asks the backend for a
 * list of SellOffer objects, and when it receives that list, it sends the list
 * to the activity that created the task and tells it to update its view.
 *
 * Created by Mike on 11/5/2015.
 */
public class ListFilteredOffersAsyncTask extends AsyncTask<RequestFilteredSellOffer, Void, List<SellOffer>> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    private RequestFilteredSellOffer mFilter = null;
    public ListFilteredOffersAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected List<SellOffer> doInBackground(RequestFilteredSellOffer... params) {

        Log.i(Constants.ASYNC_TAG, "ListFilteredSellOffersAsyncTask called!");
        // Get mFilter from params
        if(params != null && params[0] != null) {
            mFilter = params[0];
        }

        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);
            sellOfferEndpoint = builder.build();
        }

            if(mFilter != null) {

                String commodity = "";
                String seller_id = "";
                if(mFilter.getMyOwnOffersOnly()) {
                    seller_id = mFilter.getAssociatedUserID().toString();
                }

                if (!mFilter.getMyOwnOffersOnly()) {
                    commodity = mFilter.getCommodity();
                }
                Double min_weight = mFilter.getMinWeight();
                Double max_weight = mFilter.getMaxWeight();
                Log.i(Constants.ASYNC_TAG, "" + seller_id + ", " + commodity + ", " + min_weight + ", " + max_weight);
                try {
                    return sellOfferEndpoint.fullQueryOffers(commodity, min_weight, max_weight, seller_id).execute().getItems();
                } catch (IOException e) {
                    return Collections.EMPTY_LIST;
                }
            }
        return null;
/*
        } catch (IOException e) {
            Log.d(Constants.ASYNC_TAG, e.getMessage());
            return Collections.EMPTY_LIST;
        }
        */

    }
    @Override
    protected void onPostExecute(List<SellOffer> result) {
        ArrayList<SellOfferFront> sellOffers = new ArrayList<SellOfferFront>();
        if (result != null) {
            for (SellOffer s : result) {
                sellOffers.add(new SellOfferFront(s));
            }

            NewsActivity newsActivity = (NewsActivity) context;
            newsActivity.setStatusMsg("Received " + sellOffers.size() + " SellOffers from backend");
            newsActivity.setmSellOffers(sellOffers);
            newsActivity.updateListView();
        } else {
            NewsActivity newsActivity = (NewsActivity) context;
            newsActivity.setStatusMsg("Received no SellOffers from backend");

        }
    }
}


