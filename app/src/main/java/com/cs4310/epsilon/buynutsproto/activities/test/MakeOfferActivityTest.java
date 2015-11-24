package com.cs4310.epsilon.buynutsproto.activities.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.activities.MakeOfferActivity;

/**
 * Created by Mike on 11/20/2015.
 */
public class MakeOfferActivityTest extends ActivityInstrumentationTestCase2<MakeOfferActivity> {

    private MakeOfferActivity mMakeOfferActivity;
    private TextView PPUTextView;


    public MakeOfferActivityTest() {
        super(MakeOfferActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMakeOfferActivity = getActivity();
        PPUTextView =
                (TextView) mMakeOfferActivity
                        .findViewById(R.id.tvPricePerUnit_MO);

    }
}

