package com.cs4310.epsilon.buynutsproto.talkToBackend;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * An Asynchronous Task that runs in the background. It deletes a SellOffer.
 *
 * Created by Dave on 11/24/2015.
 */
public class DeleteOfferAsyncTask extends AsyncTask<Long, Void, Boolean> {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    /**
     * Tag used in logs; starts with the same prefix as all other AsyncTasks
     * in the project, but with a suffix unique to this class
     */
    private static final String TAG = Constants.ASYNC_TAG_PREFIX + "DeleteOffer";

    ///////////////////////////////////////////////////////////////////////////
    // data members

    /** An Endpoint, used to communicate with the backend */
    private static SellOfferEndpoint sellOfferEndpoint = null;
    /** The Activity that called this AsyncTask */
    private Context context;

    ///////////////////////////////////////////////////////////////////////////
    // member methods

    public DeleteOfferAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected Boolean doInBackground(Long... params) {

        // Get sellOfferID from params
        Long sellOfferID = null;
        if(params != null && params[0] != null) {
            sellOfferID = params[0];
        }

        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);
            sellOfferEndpoint = builder.build();
        }
        try {
            Log.i(TAG, "Attempting to remove SellOffer at id=" + sellOfferID);
            sellOfferEndpoint.remove(sellOfferID).execute();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean result) {

        //Toast.makeText(context, "Deleted offer", Toast.LENGTH_SHORT).show();

        //NewsActivity newsActivity = (NewsActivity) context;
        if (result) {
            Toast.makeText(context, "Deleted offer", Toast.LENGTH_SHORT).show();

           // newsActivity.setStatusMsg("Successfully deleted offer");
        } else {
            Toast.makeText(context, "Failed to delete offer", Toast.LENGTH_SHORT).show();

            //newsActivity.setStatusMsg("Failed to delete offer");
        }
    }
}


