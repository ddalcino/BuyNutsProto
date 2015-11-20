package com.cs4310.epsilon.buynutsproto.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.talkToBackend.GetContactInfoAsyncTask;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

/**
 * An activity used to provide a detailed look at a SellOfferFront object.
 */
public class ViewSellOfferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sell_offer);

        Log.i(Constants.TAG, "onCreate ViewSellOfferActivity");

        final SellOfferFront sellOffer = this.getIntent()
                .getParcelableExtra("SellOffer");

        String weightUnits = "lbs";

        TextView tvCommod = (TextView) findViewById(R.id.tvCommod_VSO);
        tvCommod.setText(sellOffer.getCommodity().toString());
        TextView tvPPU = (TextView) findViewById(R.id.tvPPU_VSO);
        tvPPU.setText(String.format(
                "$%.2f/%s",
                sellOffer.getPricePerUnit(),
                weightUnits));
        TextView tvWeightRange = (TextView) findViewById(R.id.tvWeightRange_VSO);
        tvWeightRange.setText(String.format(
                "%.2f %s - %.2f %s",
                sellOffer.getMinWeight(),
                weightUnits,
                sellOffer.getMaxWeight(),
                weightUnits));
        TextView tvTerms = (TextView) findViewById(R.id.tvTerms_VSO);
        String terms = sellOffer.getTerms();
        if(terms == null || terms.equals("")){
            tvTerms.setText(getResources().getString(R.string.terms_not_specified));
        } else {
            tvTerms.setText(terms);
        }

        // Set OnClickListener for the 'contact seller' button
        Button btnContactSeller = (Button) findViewById(R.id.btnContactSeller);
        btnContactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start an AsyncTask that gets seller contact info and passes it into the next activity
                new GetContactInfoAsyncTask(ViewSellOfferActivity.this).execute(sellOffer.getSellerId());

                //startActivity(new Intent(ViewSellOfferActivity.this, ContactSellerActivity.class));
            }
        });
    }
}
