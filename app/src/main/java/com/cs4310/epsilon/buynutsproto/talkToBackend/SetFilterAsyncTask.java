package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

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

    private static StubEndpoint setFilterEndpoint = null;
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



        if (setFilterEndpoint == null) {
            StubEndpoint.Builder builder = new StubEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            setFilterEndpoint = builder.build();
        }
        try {
            return setFilterEndpoint.setFilter(newFilter).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return "SetFilterEndpoint Failed\n" + e.getLocalizedMessage();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
