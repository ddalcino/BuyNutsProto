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
public class ListOffersAsyncTask extends AsyncTask<Long, Void, List<SellOffer>> {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    /**
     * Tag used in logs; starts with the same prefix as all other AsyncTasks
     * in the project, but with a suffix unique to this class
     */
    private static final String TAG = Constants.ASYNC_TAG_PREFIX + "ListOffers";

    ///////////////////////////////////////////////////////////////////////////
    // data members

    /** An Endpoint, used to communicate with the backend */
    private static SellOfferEndpoint sellOfferEndpoint = null;
    /** The Activity that called this AsyncTask */
    private Context context;

    ///////////////////////////////////////////////////////////////////////////
    // member methods

    public ListOffersAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected List<SellOffer> doInBackground(Long... params) {

        // Get userID from params
        Long userID = null;
        if(params != null && params[0] != null) {
            userID = params[0];
        }

        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);
            sellOfferEndpoint = builder.build();
        }
        try {
            //return new StubEndpoint().list(/*userID*/).execute().getItems();
            //TODO: Should send userID in params, so the backend only sends us items matching that user's stored filter
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
        newsActivity.setmSellOffers(sellOffers);
        newsActivity.updateListView();
    }
}

