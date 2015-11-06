package com.cs4310.epsilon.buynutsproto;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.SellOfferEndpoint;
import com.nutsinterface.mike.myapplication.backend.sellOfferEndpoint.model.SellOffer;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mike on 11/5/2015.
 */
public class SellOfferAsyncTask extends AsyncTask<Void, Void, List<SellOffer>> {

    private static SellOfferEndpoint sellOfferEndpoint = null;
    private Context context;
    SellOfferAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected List<SellOffer> doInBackground(Void... Params) {
        if (sellOfferEndpoint == null) {
            SellOfferEndpoint.Builder builder = new SellOfferEndpoint.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://buynutsproto.appspot.com/_ah/api/");



        sellOfferEndpoint = builder.build();
    }
        try {
            return sellOfferEndpoint.list().execute().getItems();
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }
        @Override
        protected void onPostExecute(List<SellOffer> result) {
            for (SellOffer s : result) {
                //Double ppu  = s.
                //double ppu_val = ppu.doubleValue();
                String disp = s.getCommodity();
                Double ppu = s.getPricePerUnit();
                Double w_max = s.getMaxWeight();
                Double w_min = s.getMinWeight();
                disp+=",";
                disp+=Double.toString(w_max);
                disp+=",";
                disp+=Double.toString(w_min);
                disp+=",";
                disp+=Double.toString(ppu);

                Toast.makeText(context, disp, Toast.LENGTH_SHORT).show();
            }

    }
    }

