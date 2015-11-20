package com.cs4310.epsilon.nutsinterface;

import android.widget.EditText;
import android.widget.Spinner;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.activities.MakeOfferActivity;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dave on 11/17/15.
 */
public class MakeOfferActivityTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void testGetSellOfferFromUI() throws Exception {

        // Find all the data entry widgets in the MakeOfferActivity, and fill
        // them with test data. Then run getSellOfferFromUI() and verify the result.
        MakeOfferActivity activity = new MakeOfferActivity();

        EditText etMaxWt = (EditText) activity.findViewById(R.id.etMaxWeight_MO);
        EditText etMinWt = (EditText) activity.findViewById(R.id.etMinWeight_MO);
        EditText etPPU = (EditText) activity.findViewById(R.id.etPPU_MO);
        EditText etTerms = (EditText) activity.findViewById(R.id.etTerms_MO);
        Spinner spinnerCommodity = (Spinner) activity.findViewById(R.id.spinnerCommodityType_MO);
        Spinner spinnerUnitsWt = (Spinner) activity.findViewById(R.id.spinnerWeightUnits_MO);

        spinnerCommodity.setSelection(0); // 0 is Walnut, 1 is Pecan, 2 is Almond, 3 is Cashew
        spinnerUnitsWt.setSelection(0); // 0 is lb, 1 is kg, 2 is gross ton (2240lb),
                                // 3 is net ton (2000lb), 4 is metric tonne (1000kg)

        etMaxWt.setText("-500");
        etMinWt.setText("20000");
        etPPU.setText("99.1234567890");
        etTerms.setText("obscene language!!!! insults to potential users!!!!");

        SellOfferFront sellOfferFront = activity.getSellOfferFromUI();
        assertTrue(sellOfferFront == null);



    }
}