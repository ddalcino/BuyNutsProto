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
public class MakeOfferAsyncTask extends AsyncTask<SellOfferFront, Void, String> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    public MakeOfferAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(SellOfferFront... Params) {
        if (Params == null || Params[0] == null) {
            return ("MakeOffer Failed");
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
        try {
            //sellOfferEndpoint.insert(newSellOffer).execute();
            String offer = newSellOffer.toInsertString();
            sellOfferEndpoint.insert(offer).execute();
            return ("Inserted");
        } catch (IOException e) {
            e.printStackTrace();
            return("Insertion Failed\n" + e.getLocalizedMessage());
        }
    }
    @Override
    protected void onPostExecute(String result) {
         MakeOfferActivity makeOfferActivity = (MakeOfferActivity) context;
        CharSequence text = (CharSequence) result;
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(makeOfferActivity,text,duration).show();

        Log.i("myTag", result);


    }
}

