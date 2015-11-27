package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.guiHelpers.FillSpinner;
import com.cs4310.epsilon.buynutsproto.localDataStorage.LocalDataHandler;
import com.cs4310.epsilon.buynutsproto.talkToBackend.DeleteOfferAsyncTask;
import com.cs4310.epsilon.buynutsproto.talkToBackend.MakeOfferAsyncTask;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

/**
 * An activity that allows a user to create a new SellOfferFront object and send
 * it to the backend
 */
public class MakeOfferActivity extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    /** A tag for logs, specific to this class */
    private static final String TAG = Constants.TAG_ACTIVITY_PREFIX + "MakeOffer";


    ///////////////////////////////////////////////////////////////////////////
    // instance data members

    /** Stores the current user's userID */
    private long mUid;
    /**
     * Remembers whether or not the user is making a new offer, or editing an
     * offer that has already been posted
     */
    private boolean mIsEditingOwnOffer;
    /** If we're editing an old offer, this holds what the old offer was */
    private SellOfferFront mOldOffer;

    /** Spinner objects that let the user select units and commodity type */
    Spinner spinUnitWt,
            spinCommodityType;


    ///////////////////////////////////////////////////////////////////////////
    // member methods

    /**
     * Entry point of the activity: builds the UI
     * @param savedInstanceState    data from the previous instance, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);

        // build UI
        spinUnitWt = (Spinner) findViewById(R.id.spinnerWeightUnits_MO);
        FillSpinner.fill(this, R.array.array_wt_units, spinUnitWt);

        // Set preferred units:
        // figure out what the units are
        String units = LocalDataHandler.getPrefUnitsWt(this, mUid);
        // set them
        this.setUnitsSelection(units);

        spinCommodityType = (Spinner) findViewById(R.id.spinnerCommodityType_MO);
        FillSpinner.fill(this, R.array.array_commodities, spinCommodityType);

        //get mUid from intent
        Intent intent = this.getIntent();
        mUid = intent.getLongExtra(Constants.USER_ID_KEY,
                Constants.INVALID_USER_ID);
        Log.i(TAG, (mUid == Constants.INVALID_USER_ID ?
                "Didn't receive mUid" : "Received mUid=" + mUid));

        // Look for a SellOfferFront passed in arguments
        mOldOffer = intent.getParcelableExtra(Constants.EDIT_OFFER_KEY);
        // If it was null, then we're not editing the user's old offer
        mIsEditingOwnOffer = mOldOffer != null;

        if (mIsEditingOwnOffer) {
            // Fill in UI elements with data from offerFront

            // Default units conversion; we'll figure that out later
            double unitConversion = UnitsWt.unitConversion(UnitsWt.Type.LB, UnitsWt.toType(units));

            EditText etPPU = (EditText) findViewById(R.id.etPPU_MO);
            etPPU.setText("" + mOldOffer.getPricePerUnit()*unitConversion);

            EditText etMinWeight = (EditText) findViewById(R.id.etMinWeight_MO);
            etMinWeight.setText("" + mOldOffer.getMinWeight()/unitConversion);

            EditText etMaxWeight = (EditText) findViewById(R.id.etMaxWeight_MO);
            etMaxWeight.setText("" + mOldOffer.getMaxWeight() / unitConversion);

            EditText etTerms = (EditText) findViewById(R.id.etTerms_MO);
            etTerms.setText(mOldOffer.getTerms());

            String[] commodities = getResources().getStringArray(R.array.array_commodities);
            String chosenCommod = mOldOffer.getCommodity();
            for (int i = 0; i < commodities.length; i++) {
                if (commodities[i].toLowerCase().startsWith(chosenCommod)) {
                    spinCommodityType.setSelection(i);
                    break;
                }
            }

            // Set title
            setTitle(getResources().getString(R.string.activity_title_edit_sell_offer));
            Button btnMakeOffer = (Button) findViewById(R.id.btnMakeOffer_MO);
            btnMakeOffer.setText(getResources().getString(R.string.btn_submit_edited_offer));

            Button btnDeleteOffer = (Button) findViewById(R.id.btnDeleteOffer);
            btnDeleteOffer.setVisibility(View.VISIBLE);

            btnDeleteOffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button btnDeleteOffer = (Button) findViewById(R.id.btnDeleteOffer);
                    btnDeleteOffer.setVisibility(View.GONE);
                    Button btnConfirmDelete = (Button) findViewById(R.id.btnConfirmDelete);
                    btnConfirmDelete.setVisibility(View.VISIBLE);
                }
            });

            Button btnConfirmDelete = (Button) findViewById(R.id.btnConfirmDelete);
            btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Long offerID = mOldOffer.getId();
                    // actually delete the offer
                    new DeleteOfferAsyncTask(MakeOfferActivity.this).execute(offerID);

                    // close the activity
                    MakeOfferActivity.this.finish();

                }
            });
        }


        // Set onclickListener
        Button btnMakeOffer = (Button) findViewById(R.id.btnMakeOffer_MO);
        btnMakeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create SellOfferFront
                SellOfferFront newOffer = getSellOfferFromUI();
                if (newOffer != null) {
                    new MakeOfferAsyncTask(MakeOfferActivity.this).execute(newOffer);
                }
            }
        });



        spinUnitWt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isOkToCheck = false;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isOkToCheck) {
                    String units = parent.getItemAtPosition(position).toString();

                    Log.i(TAG, "user selected units=" + units);

                    // Store that item
                    LocalDataHandler.storeUnitsWeight(MakeOfferActivity.this, units, mUid);
                } else {
                    isOkToCheck = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    public SellOfferFront getSellOfferFromUI() {
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

        if (mIsEditingOwnOffer) {
            // make
            SellOfferFront offer = new SellOfferFront("" + mUid, ppu, minWt, maxWt, terms, cType.toLowerCase());
            offer.setId(mOldOffer.getId());
            return offer;
        } else {
            //make the new SellOfferFront and return it
            return new SellOfferFront("" + mUid, ppu, minWt, maxWt, terms, cType.toLowerCase());
        }
    }

    public void reportInsertion (boolean insertionSucceeded) {
        if (insertionSucceeded) {
            // Set the status message on the NewsActivity

            // make new Intent to send back to NewsActivity
            Intent intent = new Intent();

            intent.putExtra(Constants.COMMODITY_KEY, spinCommodityType.getSelectedItem().toString());

            setResult(RESULT_OK, intent);
            // now NewsActivity knows that a new SellOffer was properly recorded

            // Close down the activity and send the user back to NewsActivity
            this.finish();
        } else {
            Toast.makeText(this, "Failed to make offer", Toast.LENGTH_SHORT).show();
        }
    }

    public void setUnitsSelection(String chosenUnits) {
        String[] unitTypes = getResources().getStringArray(R.array.array_wt_units);
        for (int i = 0; i < unitTypes.length; i++) {
            if (unitTypes[i].equals(chosenUnits)) {
                spinUnitWt.setSelection(i);
                return;
            }
        }
    }

}
