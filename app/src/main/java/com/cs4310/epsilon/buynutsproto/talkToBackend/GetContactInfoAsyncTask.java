package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.activities.ContactSellerActivity;
import com.cs4310.epsilon.buynutsproto.activities.MakeOfferActivity;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;

/**
 * An Asynchronous Task that runs in the background. It sends the backend a
 * sellerID string, and expects to receive an array of 3 Strings, in this order:
 *      Seller Name
 *      Seller's Phone Number
 *      Seller's Email
 * When it has received these three things, it will launch a ContactSellerActivity
 * that has access to these items.
 *
 * TODO: This class uses a StubEndpoint and won't do anything for real until a real endpoint is created!
 *
 * Created by Dave on 11/13/2015.
 */
public class GetContactInfoAsyncTask extends AsyncTask<String, Void, String[]> {

    private static StubEndpoint contactInfoEndpoint = null;
    private Context context;
    public GetContactInfoAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected String[] doInBackground(String... params) {
        if (params == null || params[0] == null) {
            return (new String[]{"GetContactInfo Failed"});
        }
        String sellerID = params[0];


        if (contactInfoEndpoint == null) {
            StubEndpoint.Builder builder = new StubEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl("https://buynutsproto.appspot.com/_ah/api/");

            contactInfoEndpoint = builder.build();
        }
        try {
            String[] contactInfo;
            contactInfo = contactInfoEndpoint.getContactInfo(sellerID).execute();
            return contactInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return(new String[]{"GetContactInfo Failed\n" + e.getLocalizedMessage()});
        }
    }
    @Override
    protected void onPostExecute(String[] result) {

        if (result != null && result.length == 3) {
            Intent i = new Intent(context, ContactSellerActivity.class);
            i.putExtra("sellerName", result[0]);
            i.putExtra("sellerPhone", result[1]);
            i.putExtra("sellerEmail", result[2]);

            context.startActivity(i);
        } else if (result != null && result.length == 1) {
            Toast.makeText(context, result[0], Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Bad seller info", Toast.LENGTH_SHORT).show();
        }
    }
}

