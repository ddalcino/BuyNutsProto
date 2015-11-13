package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.activities.MakeOfferActivity;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;

/**
 * An Asynchronous Task that runs in the background. It asks the backend for a
 * list of SellOffer objects, and when it receives that list, it sends the list
 * to the activity that created the task and tells it to update its view.
 *
 * Created by Mike on 11/5/2015.
 */
public class MakeOfferAsyncTask extends AsyncTask<Pair<Context, SellOfferFront>, Void, String> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    //public MakeOfferAsyncTask(Context context) {
    //    this.context = context;
    //}
    @Override
    protected String doInBackground(Pair<Context, SellOfferFront>... params) {
        // If we don't have a SellOfferEndpoint reference yet, build a new one
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl("https://buynutsproto.appspot.com/_ah/api/");

            sellOfferEndpoint = builder.build();
        }

        if (params == null || params.length == 0) {
            return ("MakeOffer Failed");
        }

        // Get a reference to the activity that spawned this AsyncTask, so that
        // we will have somewhere to send the output
        context = params[0].first;

        // Get a reference to the SellOfferFront object passed into this AsyncTask
        SellOfferFront sf = params[0].second;

        // Make a new SellOffer object
        SellOffer newSellOffer = new SellOffer();

        // Build a SellOffer object from parts of the SellOfferFront object
        // sent here in the parameter list
        newSellOffer.setPricePerUnit(sf.getPricePerUnit());
        newSellOffer.setCommodity(sf.getCommodity());
        newSellOffer.setMaxWeight(sf.getMaxWeight());
        newSellOffer.setMinWeight(sf.getMinWeight());
        newSellOffer.setTerms(sf.getTerms());
        //newSellOffer.setOfferBirthday(sf.getOfferBirthday());
        //newSellOffer.setSpecification(sf.getSpecification())
        try {
            // Try to insert the object
            sellOfferEndpoint.insert(newSellOffer).execute();
            return("Inserted");
        } catch (IOException e) {
            e.printStackTrace();
            return("Insertion Failed");
        }
    }
    @Override
    protected void onPostExecute(String result) {
       /* ArrayList<SellOfferFront> sellOffers = new ArrayList<SellOfferFront>();
        for (SellOffer s : result) {
            sellOffers.add(new SellOfferFront(s));
        }
*/
        //MakeOfferActivity makeOfferActivity = (MakeOfferActivity) context;
        CharSequence text = (CharSequence) result;
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context,text,duration).show();


    }
}

