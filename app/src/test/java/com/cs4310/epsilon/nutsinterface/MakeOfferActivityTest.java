package com.cs4310.epsilon.nutsinterface;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.activities.MakeOfferActivity;
import com.cs4310.epsilon.buynutsproto.guiHelpers.FillSpinner;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;

/**
 * Created by dave on 11/17/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class MakeOfferActivityTest {

    @Mock
    Context mMockContext;

    MakeOfferActivity mActivity;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testGetSellOfferFromUI() throws Exception {



        // Start a MakeOfferActivity activity that we can test
        mMockContext = mActivity = Mockito.mock(MakeOfferActivity.class);

        Spinner spinnerUnitsWt = (Spinner) mActivity.findViewById(R.id.spinnerWeightUnits_MO);
        FillSpinner.fill(mMockContext, R.array.array_wt_units, spinnerUnitsWt);

        Spinner spinnerCommodity = (Spinner) mActivity.findViewById(R.id.spinnerCommodityType_MO);
        FillSpinner.fill(mMockContext, R.array.array_commodities, spinnerCommodity);


        // Find all the data entry widgets in the MakeOfferActivity, and fill
        // them with test data. Then run getSellOfferFromUI() and verify the result.
        EditText etMaxWt = (EditText) mActivity.findViewById(R.id.etMaxWeight_MO);
        EditText etMinWt = (EditText) mActivity.findViewById(R.id.etMinWeight_MO);
        EditText etPPU = (EditText) mActivity.findViewById(R.id.etPPU_MO);
        EditText etTerms = (EditText) mActivity.findViewById(R.id.etTerms_MO);
        //Spinner spinnerCommodity = (Spinner) mActivity.findViewById(R.id.spinnerCommodityType_MO);
        //Spinner spinnerUnitsWt = (Spinner) mActivity.findViewById(R.id.spinnerWeightUnits_MO);

        spinnerCommodity.setSelection(0); // 0 is Walnut, 1 is Pecan, 2 is Almond, 3 is Cashew
        spinnerUnitsWt.setSelection(0); // 0 is lb, 1 is kg, 2 is gross ton (2240lb),
                                // 3 is net ton (2000lb), 4 is metric tonne (1000kg)

        etMaxWt.setText("-500");
        etMinWt.setText("20000");
        etPPU.setText("99.1234567890");
        etTerms.setText("obscene language!!!! insults to potential users!!!!");

        // Make a SellOfferFront object from the UI input. If any input was
        // invalid, this line will store a 'null' in sellOfferFront
        SellOfferFront sellOfferFront = mActivity.getSellOfferFromUI();
        // Check to see if it's null:
        assertTrue(sellOfferFront == null);



    }
}