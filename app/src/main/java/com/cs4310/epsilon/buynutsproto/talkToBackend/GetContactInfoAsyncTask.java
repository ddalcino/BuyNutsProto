package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
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
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.NutsUserApi;
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.NutsUser;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An Asynchronous Task that runs in the background. It sends the backend a
 * sellerID Long, and expects to receive an array of 3 Strings, in this order:
 *      Seller Name
 *      Seller's Phone Number
 *      Seller's Email
 * When it has received these three things, it will launch a ContactSellerActivity
 * that has access to these items.
 *
 *
 * Created by Dave on 11/13/2015.
 */
public class GetContactInfoAsyncTask extends AsyncTask<Long, Void, ArrayList<String>> {

    private static NutsUserApi contactInfoEndpoint = null;
    private Context context;
    public GetContactInfoAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected ArrayList<String> doInBackground(Long... params) {
        if (params == null || params[0] == null) {
            Log.i(Constants.ASYNC_TAG, "No params passed to GetContactInfoAsyncTask");
            return (null);
        }
        Long sellerID = params[0];


        if (contactInfoEndpoint == null) {
            NutsUserApi.Builder builder = new NutsUserApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            contactInfoEndpoint = builder.build();
        }
        try {
            //String[] contactInfo;
            ArrayList<String> contactInfo = new ArrayList<String>();

            NutsUser user = contactInfoEndpoint.get(sellerID).execute();
            contactInfo.add(user.getName());
            contactInfo.add(user.getTelephone());
            contactInfo.add(user.getEmail());

            return contactInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    protected void onPostExecute(ArrayList<String> result) {

        if (result != null && result.size() == 3) {
            Log.i(Constants.ASYNC_TAG, "Received contactinfo from backend: " +
                            "\nsellerName=" + result.get(0) +
                            "\nsellerPhone=" + result.get(1) +
                            "\nsellerEmail=" + result.get(2)
            );

            Intent i = new Intent(context, ContactSellerActivity.class);
            i.putExtra("sellerName", result.get(0));
            i.putExtra("sellerPhone", result.get(1));
            i.putExtra("sellerEmail", result.get(2));

            context.startActivity(i);
        } else if (result != null) {
            String contents = "";

            for(String s : result) contents += s + "\n";

            Log.i(Constants.ASYNC_TAG,
                    "Received unexpected contactinfo from backend with size="+
                            result.size() +"\nContents=" + contents);
            Toast.makeText(context, "Bad seller info", Toast.LENGTH_SHORT).show();
        }
    }
}

