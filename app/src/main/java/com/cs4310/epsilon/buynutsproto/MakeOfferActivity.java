package com.cs4310.epsilon.buynutsproto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.nutsinterface.Commodity;
import com.cs4310.epsilon.nutsinterface.SellOffer;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

public class MakeOfferActivity extends AppCompatActivity {
    static final String TAG = "myTag";
    static final int INVALID_UID = -1;
    private long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_offer);

        //get uid from intent
        uid = this.getIntent().getLongExtra("uid", INVALID_UID);
        Log.i(TAG, (uid == INVALID_UID ? "Didn't receive uid" : "Received uid=" + uid));

        final Spinner spinUnitWt = (Spinner) findViewById(R.id.spinnerWeightUnits_MO);
        FillSpinner.fill(this, R.array.wt_units_array, spinUnitWt);

        final Spinner spinCommodityType = (Spinner) findViewById(R.id.spinnerCommodityType_MO);
        FillSpinner.fill(this, R.array.commodities_array, spinCommodityType);


        // Set onclickListener
        Button btnMakeOffer = (Button) findViewById(R.id.btnMakeOffer_MO);
        btnMakeOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create SellOffer
                double ppu=-1;
                double minWt=-1, maxWt=-1;
                try {
                    EditText etPPU = (EditText) findViewById(R.id.etPPU_MO);
                    ppu = Double.parseDouble(etPPU.getText().toString().trim());

                    EditText etMinWeight = (EditText) findViewById(R.id.etMinWeight_MO);
                    minWt = Double.parseDouble(etMinWeight.getText().toString().trim());

                    EditText etMaxWeight = (EditText) findViewById(R.id.etMaxWeight_MO);
                    maxWt = Double.parseDouble(etMaxWeight.getText().toString().trim());
                } catch (NumberFormatException e){
                }
                if (ppu < 0 || minWt < 0 || maxWt < minWt) {
                    Toast.makeText(MakeOfferActivity.this.getApplicationContext(),
                            "Invalid numeric input", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText etTerms = (EditText)findViewById(R.id.etTerms_MO);
                String terms = etTerms.getText().toString().trim();
                Commodity.Type cType = Commodity.toType(spinCommodityType.getSelectedItem().toString());
                UnitsWt.Type unitsWeight = UnitsWt.toType(spinUnitWt.getSelectedItem().toString());
                if(cType == null || unitsWeight == null){
                    Toast.makeText(MakeOfferActivity.this.getApplicationContext(),
                            "Invalid spinner input", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create SellOffer
                SellOffer newOffer = new SellOffer(uid, ppu, minWt, maxWt, terms, cType, unitsWeight);

                // Send SellOffer object to the server

                Toast.makeText(MakeOfferActivity.this, "SellOffer is: " + newOffer, Toast.LENGTH_LONG).show();
            }
        });
    }
}
