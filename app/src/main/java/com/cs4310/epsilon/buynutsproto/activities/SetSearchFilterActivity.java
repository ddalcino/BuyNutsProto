package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.guiHelpers.FillSpinner;
import com.cs4310.epsilon.buynutsproto.talkToBackend.SetFilterAsyncTask;
import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.cs4310.epsilon.nutsinterface.UnitsWt;


public class SetSearchFilterActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_ACTIVITY_PREFIX + "SetSearchFilter";

    public static final String SEARCH_FILTER_KEY = "filter";

    /**
     * Spinner objects used to obtain commodity type and weight unit type from
     * the user
     */
    private Spinner spinCommodityType, spinUnitWt;
    private long mUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_search_filter);

        mUid = this.getIntent().getLongExtra(Constants.USER_ID_KEY, MainLoginActivity.INVALID_USERID);


        spinUnitWt = (Spinner) findViewById(R.id.spinnerWeightUnits_SF);
        FillSpinner.fill(this, R.array.array_wt_units, spinUnitWt);

        spinCommodityType = (Spinner) findViewById(R.id.spinnerCommodityType_SF);
        FillSpinner.fill(this, R.array.array_commodities, spinCommodityType);

        Button btnSetSearch = (Button) findViewById(R.id.btnSetSearchFilter_SF);
        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clickedSetSearch");

                //create RequestFilteredSellOffer
                RequestFilteredSellOffer newFilter = getRequestFilteredSellOffer();

                // If we have a real RequestFilteredSellOffer object, send it to the server
                if(newFilter != null) {
//                   Toast.makeText(
//                            SetSearchFilterActivity.this.getApplicationContext(),
//                            "RequestFilteredSellOffer is: " + newFilter.toString(),
//                            Toast.LENGTH_LONG).show();
                    Log.i(TAG, "SetSearchFilterActivity made filter: " + newFilter.toString());

                    // make new Intent to send back to NewsActivity
                    Intent intent = new Intent();

                    // fill intent with search filter criteria
                    intent.putExtra(SEARCH_FILTER_KEY, newFilter);

                    setResult(RESULT_OK, intent);
                    // now NewsActivity has an idea of what's in the search filter

                    //Also, send a RequestFilteredSellOffer object to the server
                    new SetFilterAsyncTask(SetSearchFilterActivity.this).execute(newFilter);

                    // Close down the activity and send the user back to NewsActivity
                    SetSearchFilterActivity.this.finish();
            }

            }
        });

        // Set an OnClickListener that turns off all the other controls if
        // "My Offers Only" is turned on
        final CheckBox chkMyOffers = (CheckBox) findViewById(R.id.chkMyOffersOnly);

        chkMyOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setControlsActive(!chkMyOffers.isChecked(), true);
            }
        });

        // Set an OnClickListener that turns off all the other controls except
        // spinCommodityType if "Show all offers for one nut type" is turned on
        final CheckBox chkShowAll = (CheckBox) findViewById(R.id.chkShowAll);
        chkShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setControlsActive(!chkShowAll.isChecked(), false);
            }
        });

    }

    /**
     * Reads all the input fields in the UI and generates a new
     * RequestFilteredSellOffer object. Also performs input validation
     * and returns null for invalid offers.
     *
     * <p>If the user has clicked the checkbox for 'My Offers Only', then input
     * validation is short-circuited, and numeric input for min and max PPU and
     * weight are ignored.</p>
     *
     * @return  A new SellOfferFront object made from UI input fields. If the
     *          SellOfferFront is invalid in some way, returns null.
     */
    public RequestFilteredSellOffer getRequestFilteredSellOffer(){

        String cType = spinCommodityType.getSelectedItem().toString().toLowerCase();
        Log.i(TAG, "UserFront selected " + cType +" from spinner");

        CheckBox chkMyOffers = (CheckBox) findViewById(R.id.chkMyOffersOnly);
        boolean myOwnOffersOnly = chkMyOffers.isChecked();
        CheckBox chkExpiredOffers = (CheckBox) findViewById(R.id.chkShowExpired);
        boolean expiredOffersOnly = chkExpiredOffers.isChecked();
        CheckBox chkShowAll = (CheckBox) findViewById(R.id.chkShowAll);
        boolean showAllForOneNut = chkShowAll.isChecked();

        if (myOwnOffersOnly) {
            return new RequestFilteredSellOffer(
                    mUid, cType, 0.0, 0.0,
                    0.0, 0.0,
                    expiredOffersOnly, myOwnOffersOnly);
        } else if (showAllForOneNut) {
            return new RequestFilteredSellOffer(
                    mUid, cType, 0.0, 0.0,
                    0.0, 0.0,
                    expiredOffersOnly, myOwnOffersOnly);
        }

        double minPpu=-1, maxPpu=-1;
        double minWt=-1, maxWt=-1;
        try {
            EditText etMinPPU = (EditText) findViewById(R.id.etMinPPU_SF);
            EditText etMaxPPU = (EditText) findViewById(R.id.etMaxPPU_SF);
            minPpu = Double.parseDouble(etMinPPU.getText().toString().trim());
            maxPpu = Double.parseDouble(etMaxPPU.getText().toString().trim());

            EditText etMinWeight = (EditText) findViewById(R.id.etMinWeight_SF);
            minWt = Double.parseDouble(etMinWeight.getText().toString().trim());

            EditText etMaxWeight = (EditText) findViewById(R.id.etMaxWeight_SF);
            maxWt = Double.parseDouble(etMaxWeight.getText().toString().trim());
        } catch (NumberFormatException e){
            Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(),
                    "Non-numeric input", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (minPpu < 0 || maxPpu < minPpu || minWt < 0 || maxWt < minWt) {
            Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(),
                    "Invalid numeric input", Toast.LENGTH_SHORT).show();
            return null;
        }

        UnitsWt.Type unitsWeight = UnitsWt.toType(
                spinUnitWt.getSelectedItem().toString());
        if(cType == null || unitsWeight == null){
            Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(),
                    "Invalid spinner input", Toast.LENGTH_SHORT).show();
            return null;
        }

        double unitConversion = UnitsWt.unitConversion(unitsWeight, UnitsWt.Type.LB);

        //create RequestFilteredSellOffer
        return new RequestFilteredSellOffer(
                mUid, cType, minWt * unitConversion, maxWt * unitConversion,
                minPpu / unitConversion, maxPpu / unitConversion,
                expiredOffersOnly, myOwnOffersOnly);
    }

    public void setControlsActive(boolean active, boolean affectCommodityType) {
        EditText etMinPPU = (EditText) findViewById(R.id.etMinPPU_SF);
        EditText etMaxPPU = (EditText) findViewById(R.id.etMaxPPU_SF);
        EditText etMinWeight = (EditText) findViewById(R.id.etMinWeight_SF);
        EditText etMaxWeight = (EditText) findViewById(R.id.etMaxWeight_SF);

        etMaxPPU.setEnabled(active);
        etMinPPU.setEnabled(active);
        etMaxWeight.setEnabled(active);
        etMinWeight.setEnabled(active);
        spinUnitWt.setEnabled(active);
        if(affectCommodityType) {
            spinCommodityType.setEnabled(active);
            final CheckBox chkShowAll = (CheckBox) findViewById(R.id.chkShowAll);
            chkShowAll.setEnabled(active);
        }
    }
}
