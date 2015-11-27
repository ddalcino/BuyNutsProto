package com.cs4310.epsilon.buynutsproto.activities;

/**
 * A class to hold constants, accessible to all classes in this package
 * Created by dave on 11/18/15.
 */
public class Constants {
    /**
     * A tag used to put messages in the Log. To see these messages while the
     * app is running, go to the 'logcat' tab in Android Monitor (it pops up
     * automatically for me), and type this string into the search filter.
     */
    static final String TAG_ACTIVITY_PREFIX = "tagActivity";

    /**
     * Keys used to store data in the intents passed between Activities.
     */
    static final String
            USER_ID_KEY = "uid",
            PREF_UNITS_WT = "unitsWt",
            COMMODITY_KEY = "commodity",
            EDIT_OFFER_KEY = "editSellOffer",
            VIEW_OFFER_KEY = "viewSellOffer",
            SELLER_ID_KEY = "sellerId";

    /** An invalid user id */
    public static final long INVALID_USER_ID = -1l;
}
