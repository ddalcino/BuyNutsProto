package com.cs4310.epsilon.buynutsproto.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.guiHelpers.FillSpinner;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

/**
 * An activity that allows a user to create a new SellOfferFront object and send
 * it to the backend
 */
public class MakeOfferActivity extends AppCompatActivity {
    static final String TAG = "myTag";
    private long mUid;

    Spinner spinUnitWt;
    Spinner spinCommodityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);

        //get mUid from intent
        mUid = this.getIntent().getLongExtra("mUid",
                MainLoginActivity.INVALID_USERID);
        Log.i(TAG, (mUid == MainLoginActivity.INVALID_USERID ?
                "Didn't receive mUid" : "Received mUid=" + mUid));

        spinUnitWt = (Spinner) findViewById(R.id.spinnerWeightUnits_MO);
        FillSpinner.fill(this, R.array.wt_units_array, spinUnitWt);

        spinCommodityType = (Spinner) findViewById(R.id.spinnerCommodityType_MO);
        FillSpinner.fill(this, R.array.commodities_array, spinCommodityType);


        // Set onclickListener
        Button btnMakeOffer = (Button) findViewById(R.id.btnMakeOffer_MO);
        btnMakeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create SellOfferFront
                SellOfferFront newOffer = getSellOfferFromUI();



                Toast.makeText(MakeOfferActivity.this,
                        "SellOfferFront is: " + newOffer,
                        Toast.LENGTH_LONG).show();
                if(newOffer != null){
                    //TODO: Send SellOfferFront object to the server
                }
            }
        });
    }

    /**
     * Reads all the input fields in the UI and generates a new SellOfferFront
     * object. Also performs input validation and returns null for invalid
     * offers.
     * @return  A new SellOfferFront object made from UI input fields. If the
     *          SellOfferFront is invalid in some way, returns null.
     */
    SellOfferFront getSellOfferFromUI() {
        //get commodity type and weight units
        String cType = spinCommodityType.getSelectedItem().toString();
        UnitsWt.Type unitsWeight = UnitsWt.toType(
                spinUnitWt.getSelectedItem().toString());
        double unitConversion = UnitsWt.unitConversion(unitsWeight, UnitsWt.Type.LB);

        // check that neither inputs from the spinner objects were invalid.
        // I don't think it's possible to get invalid input, given the way
        // these are set up, but this will help if something breaks
        if(cType == null || unitsWeight == null){
            Toast.makeText(MakeOfferActivity.this.getApplicationContext(),
                    "Invalid spinner input", Toast.LENGTH_SHORT).show();
            return null;
        }

        // set default (invalid) values
        double ppu=-1, minWt=-1, maxWt=-1;
        try {
            // get numeric data from the UI
            EditText etPPU = (EditText) findViewById(R.id.etPPU_MO);
            ppu = Double.parseDouble(etPPU.getText().toString().trim())
                    / unitConversion;

            EditText etMinWeight = (EditText) findViewById(R.id.etMinWeight_MO);
            minWt = Double.parseDouble(etMinWeight.getText().toString().trim())
                    * unitConversion;

            EditText etMaxWeight = (EditText) findViewById(R.id.etMaxWeight_MO);
            maxWt = Double.parseDouble(etMaxWeight.getText().toString().trim())
                    * unitConversion;
        } catch (NumberFormatException e){
            // if the user entered something that wasn't a number, we come here
            Toast.makeText(MakeOfferActivity.this.getApplicationContext(),
                    "Non-numeric input", Toast.LENGTH_SHORT).show();
            return null;
        }
        // If the numeric input doesn't make sense, don't make a SellOfferFront
        if (ppu < 0 || minWt < 0 || maxWt < minWt) {
            Toast.makeText(MakeOfferActivity.this.getApplicationContext(),
                    "Invalid numeric input", Toast.LENGTH_SHORT).show();
            return null;
        }

        // get string data from the UI
        EditText etTerms = (EditText)findViewById(R.id.etTerms_MO);
        String terms = etTerms.getText().toString().trim();

        //make the new SellOfferFront and return it
        return new SellOfferFront(""+mUid, ppu, minWt, maxWt, terms, cType);
    }
}
