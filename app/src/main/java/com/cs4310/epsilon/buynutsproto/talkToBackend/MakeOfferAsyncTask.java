package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
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
public class MakeOfferAsyncTask extends AsyncTask<SellOfferFront, Void, Boolean> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    public MakeOfferAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(SellOfferFront... Params) {
        if (Params == null || Params[0] == null) {
            return false;
        }

        //context.getResources().getString(R.string.backend_url);

        SellOfferFront newSellOffer = Params[0];
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            sellOfferEndpoint = builder.build();
        }

        Long offerID = newSellOffer.getId();
        if (offerID != null) {
            Log.i(Constants.ASYNC_TAG, "This was an edited offer with id=" + offerID);
            Log.i(Constants.ASYNC_TAG, "SellOfferFront=" + newSellOffer.toString());
            Log.i(Constants.ASYNC_TAG, "SellOffer=" + newSellOffer.toSellOffer().toString());

            try {
                sellOfferEndpoint.update(offerID, newSellOffer.toSellOffer()).execute();
                Log.i(Constants.ASYNC_TAG, "Update sellOffer succeeded");
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            //sellOfferEndpoint.insert(newSellOffer).execute();
            String offer = newSellOffer.toInsertString();
            sellOfferEndpoint.insert(offer).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // ("Insertion Failed\n" + e.getLocalizedMessage());
        }
    }
    @Override
    protected void onPostExecute(Boolean insertionSucceeded) {
        MakeOfferActivity makeOfferActivity = (MakeOfferActivity) context;
        makeOfferActivity.reportInsertion(insertionSucceeded);

//        CharSequence text = (CharSequence) result;
//        int duration = Toast.LENGTH_LONG;
//        Toast.makeText(makeOfferActivity,text,duration).show();
    }
}

