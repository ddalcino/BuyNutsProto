package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.offerFilterEndpoint.OfferFilterEndpoint;
import com.nutsinterface.mike.myapplication.backend.offerFilterEndpoint.model.OfferFilter;

import java.io.IOException;

/**
 * Created by Mike on 11/25/2015.
 * Merry Christmas
 * And a happy new year
 */
public class GetFilterAsyncTask extends AsyncTask<Long, Void, RequestFilteredSellOffer> {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    /**
     * Tag used in logs; starts with the same prefix as all other AsyncTasks
     * in the project, but with a suffix unique to this class
     */
    private static final String TAG = Constants.ASYNC_TAG_PREFIX + "GetFilter";

    ///////////////////////////////////////////////////////////////////////////
    // data members

    /** An Endpoint, used to communicate with the backend */
    private static OfferFilterEndpoint offerFilterEndpoint = null;

    /** The Activity that called this AsyncTask */
    private Context context;

    ///////////////////////////////////////////////////////////////////////////
    // member methods

    public GetFilterAsyncTask(Context context) {
            this.context = context;
        }
        @Override
        protected RequestFilteredSellOffer doInBackground(Long... params) {
            if (params == null || params[0] == null) {
                return null;
            }
            Long associatedUserID = params[0];

            if (offerFilterEndpoint == null) {
                OfferFilterEndpoint.Builder builder = new OfferFilterEndpoint.Builder(
                        AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null
                ).setRootUrl(Constants.BACKEND_URL);

                offerFilterEndpoint = builder.build();
            }

            try {
                //OfferFilter offerFilter = new OfferFilter();
                Log.i(TAG, "associatedUserID is=" + associatedUserID);
                OfferFilter offerFilter = new OfferFilter();
                offerFilter = offerFilterEndpoint.retrieveFilter(associatedUserID).execute();
                RequestFilteredSellOffer requestFilteredSellOffer = null;
                if (offerFilter != null) {

                    requestFilteredSellOffer = new RequestFilteredSellOffer(offerFilter.getAssociatedUserID(), offerFilter.getCommodity(), offerFilter.getMinWeight(), offerFilter.getMaxWeight(), offerFilter.getMinPricePerUnit(), offerFilter.getMaxPricePerUnit(), offerFilter.getExpired(), offerFilter.getMyOwnOffersOnly(), offerFilter.getEarliest(), offerFilter.getLatest());
                    Log.i(TAG, "requestFilteredSellOffer considers commodity: " + offerFilter.getCommodity());
                } else {
                    Log.i(TAG, "offerFilter was null");
                }

                return requestFilteredSellOffer;

            } catch (IOException e) {
                e.printStackTrace();
                return null;//"SetFilterEndpoint Failed\n" + e.getLocalizedMessage();
            }
        }
    @Override
    protected void onPostExecute(RequestFilteredSellOffer requestFilteredSellOffer) {
        Toast.makeText(context, "Complete", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "User requested filter =" +
                requestFilteredSellOffer.getAssociatedUserID() + ", " + requestFilteredSellOffer.getCommodity() + ", " + requestFilteredSellOffer.getMinWeight() + ", " + requestFilteredSellOffer.getMaxWeight() + ", " + requestFilteredSellOffer.getMinPricePerUnit() + ", " + requestFilteredSellOffer.getMaxPricePerUnit() + ", " + requestFilteredSellOffer.getExpired() + ", " + requestFilteredSellOffer.getMyOwnOffersOnly() + ", " + requestFilteredSellOffer.getEarliest() +", "+ requestFilteredSellOffer.getLatest());
        return;
        //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }


}

