package com.cs4310.epsilon.buynutsproto;

import android.content.Intent;
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
import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.cs4310.epsilon.nutsinterface.UnitsWt;

public class SetSearchFilterActivity extends AppCompatActivity {
    static final String TAG = "myTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_search_filter);

        final Spinner spinUnitWt = (Spinner) findViewById(R.id.spinnerWeightUnits_SF);
        FillSpinner.fill(this, R.array.wt_units_array, spinUnitWt);

        final Spinner spinCommodityType = (Spinner) findViewById(R.id.spinnerCommodityType_SF);
        FillSpinner.fill(this, R.array.commodities_array, spinCommodityType);

        Button btnSetSearch = (Button) findViewById(R.id.btnSetSearchFilter_SF);
        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clickedSetSearch");


                //create RequestFilteredSellOffer

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
                }
                if (minPpu < 0 || maxPpu < minPpu || minWt < 0 || maxWt < minWt) {
                    Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(),
                            "Invalid numeric input", Toast.LENGTH_SHORT).show();
                    return;
                }

                Commodity.Type cType = Commodity.toType(spinCommodityType.getSelectedItem().toString());
                UnitsWt.Type unitsWeight = UnitsWt.toType(spinUnitWt.getSelectedItem().toString());
                if(cType == null || unitsWeight == null){
                    Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(),
                            "Invalid spinner input", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create RequestFilteredSellOffer
                RequestFilteredSellOffer newFilter = new RequestFilteredSellOffer(minWt, maxWt, minPpu, maxPpu, cType, false, unitsWeight);

                // Send SellOffer object to the server
                Toast.makeText(SetSearchFilterActivity.this.getApplicationContext(), "RequestFilteredSellOffer is: " + newFilter.toString(), Toast.LENGTH_LONG).show();

                Intent data = new Intent();
                // fill data with search filter criteria
                //data.putExtra((Parcelable) newFilter) //requires that RequestFilteredSellOffer implements Parcelable; it doesn't do that yet


                setResult(RESULT_OK, data);
                // now NewsActivity has an idea of what's in the search filter

                //Also, send a RequestFilteredSellOffer object to the server
            }
        });

    }
}
