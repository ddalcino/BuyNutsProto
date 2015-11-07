package com.cs4310.epsilon.buynutsproto.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

/**
 * An activity used to provide a detailed look at a SellOfferFront object.
 */
public class ViewSellOfferActivity extends AppCompatActivity {
    public static final String TAG = "myTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sell_offer);

        Log.i(TAG, "onCreate ViewSellOfferActivity");

        SellOfferFront sellOffer = this.getIntent()
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
                // This is where you would put code to contact the seller
                Toast.makeText(ViewSellOfferActivity.this,
                        "Not yet implemented.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
