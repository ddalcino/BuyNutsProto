package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;

import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;

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
            return null;
        }
        //SellOfferFront newSellOffer = new SellOfferFront();
        SellOfferFront sf = Params[0];
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            //).setRootUrl("http://10.0.2.2:8080/_ah/api/");
            ).setRootUrl("https://backupnuts-1129.appspot.com/_ah/api/");


            sellOfferEndpoint = builder.build();
        }
/*

        newSellOffer.setPricePerUnit(sf.getPricePerUnit());
        newSellOffer.setCommodity(sf.getCommodity());
        newSellOffer.setMaxWeight(sf.getMaxWeight());
        newSellOffer.setMinWeight(sf.getMinWeight());
        newSellOffer.setTerms(sf.getTerms());
        */
        String offer = sf.getPricePerUnit().toString() + "#" + sf.getCommodity() + "#" + sf.getMaxWeight().toString() + "#" + sf.getMinWeight().toString() + "#" + sf.getTerms();
        //String[] offer_array = {sf.getPricePerUnit().toString(),sf.getCommodity(),sf.getMaxWeight().toString(),sf.getMinWeight().toString(),sf.getTerms()};
        //newSellOffer.setOfferBirthday(sf.getOfferBirthday());
        //newSellOffer.setSpecification(sf.getSpecification())
        try {
            //List<String[]> retpar = Collections.EMPTY_LIST;
            //retpar.add(offer);


            //List<String> offerList = Collections.EMPTY_LIST;
            //offerList.add(offer);
                // MakeOfferActivity makeOfferActivity = (MakeOfferActivity) context;
            //   CharSequence text = offer;
            // int duration = Toast.LENGTH_LONG;
            //Toast.makeText(makeOfferActivity,text,duration);
            sellOfferEndpoint.insert(offer).execute();
            /*CharSequence text = "Complete"+ offer;
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context,text,duration);
            */
            return ("Complete" + offer);
        } catch (IOException e) {
            return ("Failure");
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
        //CharSequence text = (CharSequence) result;
        //int duration = Toast.LENGTH_LONG;
        //Toast.makeText(makeOfferActivity,text,duration).show();


    }
}

