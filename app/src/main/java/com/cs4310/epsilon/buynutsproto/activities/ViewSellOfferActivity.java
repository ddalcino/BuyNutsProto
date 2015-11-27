package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.talkToBackend.GetContactInfoAsyncTask;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

/**
 * An activity used to provide a detailed look at a SellOfferFront object.
 */
public class ViewSellOfferActivity extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    /** A tag for logs, specific to this class */
    private static final String TAG = Constants.TAG_ACTIVITY_PREFIX + "ViewSellOfr";


    ///////////////////////////////////////////////////////////////////////////
    // member methods

    /**
     * Entry point of the activity: builds the UI
     * @param savedInstanceState    data from the previous instance, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sell_offer);

        Log.i(TAG, "onCreate ViewSellOfferActivity");

        Intent intent = this.getIntent();
        final SellOfferFront sellOffer = intent
                .getParcelableExtra(Constants.VIEW_OFFER_KEY);

        String weightUnits = intent.getStringExtra(Constants.PREF_UNITS_WT);
        if (weightUnits == null) {
            weightUnits = "lb";
        }

        double unitConversion = UnitsWt.unitConversion(
                UnitsWt.Type.LB, UnitsWt.toType(weightUnits));

        TextView tvCommod = (TextView) findViewById(R.id.tvCommod_VSO);
        tvCommod.setText(sellOffer.getCommodityPretty());
        TextView tvPPU = (TextView) findViewById(R.id.tvPPU_VSO);
        tvPPU.setText(String.format(
                "$%.2f/%s",
                sellOffer.getPricePerUnit() / unitConversion,
                weightUnits));
        TextView tvWeightRange = (TextView) findViewById(R.id.tvWeightRange_VSO);
        tvWeightRange.setText(String.format(
                "%.2f %s - %.2f %s",
                sellOffer.getMinWeight() * unitConversion,
                weightUnits,
                sellOffer.getMaxWeight() * unitConversion,
                weightUnits));
        TextView tvTerms = (TextView) findViewById(R.id.tvTerms_VSO);
        String terms = sellOffer.getTerms();
        if(terms == null || terms.equals("")){
            tvTerms.setText(getResources().getString(R.string.str_terms_not_specified_VSO));
        } else {
            tvTerms.setText(terms);
        }

        // Set OnClickListener for the 'contact seller' button
        Button btnContactSeller = (Button) findViewById(R.id.btnContactSeller);
        btnContactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked Contact Seller with sellerID=" + sellOffer.getSellerId());
                try {
                    Long sellerId = Long.parseLong(sellOffer.getSellerId());

                    // Start an AsyncTask that gets seller contact info
                    // and passes it into the next activity
                    new GetContactInfoAsyncTask(ViewSellOfferActivity.this).execute(sellerId);

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set OnClickListener for 'View Seller's Other Offers"
        Button btnViewSellersOffers = (Button) findViewById(R.id.btnViewSellersOffers);
        btnViewSellersOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send the sellerID back to News
                try {
                    Long sellerId = Long.parseLong(sellOffer.getSellerId());

                    // make new Intent to send back to NewsActivity
                    Intent intent = new Intent();

                    // fill intent with seller's id
                    intent.putExtra(Constants.SELLER_ID_KEY, sellerId);

                    setResult(RESULT_OK, intent);
                    // now NewsActivity has an idea of what's in the search filter

                    Log.i(TAG, String.format("Sending sellerID=%d to NewsActivity", sellerId));

                    // Close down the activity and send the user back to NewsActivity
                    ViewSellOfferActivity.this.finish();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.i(TAG, "Seller ID is not a valid Long");
                    Toast.makeText(ViewSellOfferActivity.this,
                            "Cannot determine who this seller is",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
