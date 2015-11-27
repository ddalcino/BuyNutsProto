package com.cs4310.epsilon.buynutsproto.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.guiHelpers.MyArrayAdapter;
import com.cs4310.epsilon.buynutsproto.localDataStorage.LocalDataHandler;
import com.cs4310.epsilon.buynutsproto.talkToBackend.ListFilteredOffersAsyncTask;
import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

import java.util.ArrayList;


/**
 * Activity that displays a list of SellOffers in a ListView that occupies
 * most of the screen.
 */
public class NewsActivity extends AppCompatActivity {
    ///////////////////////////////////////////////////////////////////////////
    // constants

    private static final String TAG = Constants.TAG_ACTIVITY_PREFIX + "News";

    private static final String DEFAULT_COMMODITY = "walnut";

    /**
     * This is a request code for a search filter, used to notify the
     * NewsActivity which activity is sending it data. For example, this
     * activity starts a new SetSearchFilterActivity when the user clicks
     * "Set Search Filter" (using startActivityForResult), and it sends that
     * activity this request code in an intent. When that activity finishes,
     * the method onActivityResult() will be called, and that method needs some
     * means of figuring out what sequence of events occurred before the start
     * of that method. This request code is used to tell that method that the
     * user clicked on "Set Search Filter" to cause this sequence of events,
     * so the method can respond appropriately to the data passed in the intent.
     */
    static final int REQUEST_CODE_SEARCH_FILTER = 0;
    static final int REQUEST_CODE_MAKE_OFFER = 1;
    static final int REQUEST_CODE_EDIT_OFFER = 2;
    static final int REQUEST_CODE_VIEW_OFFER = 3;

    ///////////////////////////////////////////////////////////////////////////
    // instance data members
    /**
     * The user ID, which is set by the MainLoginActivity when the user logs in.
     * This value is initialized by default to an invalid user ID, so the
     * program will know if the user reached this point in error.
     */
    private long mUid = MainLoginActivity.INVALID_USERID;

    /** Preferred units of weight */
    private String mUnitsWt;

    /** The filter set by the user, used to the backend for SellOffers */
    private RequestFilteredSellOffer mFilter;

    /**
     * The object that displays the list of SellOfferFront objects to the user.
     */
    private ListView mListView;
    /**
     * The ArrayAdapter object used to hold a list of SellOfferFront objects
     * in a way that the ListView can interact with them; it allows the ListView
     * to display them, and when a user clicks on an item in the list, this
     * object allows the program to figure out which item the user clicked on.
     */
    private MyArrayAdapter mArrayAdapter;
    /**
     * A list of SellOfferFront objects that the NewsActivity will display in
     * its ListView object
     */
    private ArrayList<SellOfferFront> mSellOffers;

    ///////////////////////////////////////////////////////////////////////////
    // methods

    /**
     * Creates the NewsActivity UI and initializes member variables; the first
     * thing called when the app launches a NewsActivity
     * @param savedInstanceState    Data saved from an earlier instance of this
     *                              activity: only exists if the user hasn't
     *                              clicked the back button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //get mUid from intent
        mUid = this.getIntent().getLongExtra(Constants.USER_ID_KEY,
                MainLoginActivity.INVALID_USERID);
        Log.i(TAG, (
                mUid == MainLoginActivity.INVALID_USERID ?
                        "Didn't receive mUid" :
                        "Received mUid=" + mUid
        ));

        //get mUnitsWt from sql
        mUnitsWt = LocalDataHandler.getPrefUnitsWt(this, mUid);

        //get saved filter from sql
        mFilter = LocalDataHandler.getSavedFilter(this, mUid);

        //get mListView
        mListView = (ListView) findViewById(R.id.listView);


        // set OnClickListeners:
        /**
         * btnMakeNewOffer: launches a new MakeOfferActivity that will allow a
         * user to make a new SellOffer
         */
        Button btnMakeNewOffer = (Button) findViewById(R.id.btnMakeOffer_News);
        btnMakeNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnMakeOffer_News");

                //create and launch MakeOfferActivity
                Intent intent = new Intent(NewsActivity.this, MakeOfferActivity.class);
                intent.putExtra(Constants.USER_ID_KEY, mUid);
                intent.putExtra(Constants.PREF_UNITS_WT, mUnitsWt);
                NewsActivity.this.startActivityForResult(intent, REQUEST_CODE_MAKE_OFFER);
            }
        });
        /**
         * btnSetSearch: launches a new SetSearchFilterActivity that allows the
         * user to set search settings
         */
        Button btnSetSearch = (Button) findViewById(R.id.btnSetFilter_News);
        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnSetFilter_News");

                // create intent for SetSearchFilterActivity
                Intent i = new Intent(NewsActivity.this, SetSearchFilterActivity.class);
                i.putExtra(Constants.USER_ID_KEY, mUid);
                i.putExtra(Constants.PREF_UNITS_WT, mUnitsWt);

                //create and launch SetSearchFilterActivity
                NewsActivity.this.startActivityForResult(
                        i, REQUEST_CODE_SEARCH_FILTER);
            }
        });

        /**
         * btnRefreshNews: launches a ListOffersAsyncTask that will set
         * mSellOffers and update the ListView with fresh list of SellOffers
         */
        Button btnRefreshNews = (Button) findViewById(R.id.btnRefreshNews_News);
        btnRefreshNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnRefreshNews_News");

                setStatusMsg("Requesting SellOffers from server...");

                new ListFilteredOffersAsyncTask(NewsActivity.this).execute(mFilter);

                //new ListOffersAsyncTask(NewsActivity.this).execute(mUid);
            }
        });

        /**
         * mListView: When the user clicks on an item in the ListView, a new
         * ViewSellOfferActivity is launched, which lets the user get a closer
         * look at the SellOffer
         */
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //anonymous inner class that implements OnItemClickListener
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                SellOfferFront choice = NewsActivity.this.mArrayAdapter.getItem(position);
                Log.i(TAG, "User clicked SellOffer at position=" + position + ", id=" + id);

                // Check if this offer is was made by the current user:
                if (choice.getSellerId().equals("" + mUid)) {
                    Log.i(TAG, "SellerID == UserID; need to edit SellOffer");
                    // if it was, then we need to launch MakeOfferActivity
                    // and tell it to edit the offer

                    //intent is the only parameter passed to the new activity
                    Intent intent = new Intent(NewsActivity.this, MakeOfferActivity.class);
                    intent.putExtra(Constants.USER_ID_KEY, mUid);
                    intent.putExtra(Constants.PREF_UNITS_WT, mUnitsWt);
                    intent.putExtra(Constants.EDIT_OFFER_KEY, choice);

                    //create another activity
                    NewsActivity.this.startActivityForResult(intent, REQUEST_CODE_EDIT_OFFER);
                } else {

                    //intent is the only parameter passed to the new activity
                    Intent intent = new Intent(NewsActivity.this, ViewSellOfferActivity.class);
                    intent.putExtra(Constants.PREF_UNITS_WT, mUnitsWt);
                    intent.putExtra(Constants.VIEW_OFFER_KEY, choice);

                    // In this case, we will ask the ViewSellOfferActivity for
                    // a result when it closes; this allows us to retrieve the
                    // sellerID in the case that this user wants to see all the
                    // other offers made by that seller

                    //create another activity
                    NewsActivity.this.startActivityForResult(intent, REQUEST_CODE_VIEW_OFFER);
                    //refer to parent reference - can't just say "this", that's the inner class
                }
            }
        });

        // if filter is not null, retrieve offers
        if (mFilter != null) {
            new ListFilteredOffersAsyncTask(NewsActivity.this).execute(mFilter);
        }
    }

    /**
     * Runs when the user returns to this Activity from another Activity.
     * <p>If we're returning from SetSearchFilterActivity, this method will
     * retrieve the search filter, save it, and run ListFilteredOffersAsyncTask</p>
     * <p>If we're returning from MakeOfferActivity after making a new offer,
     * this method will set the status message to reflect that the offer was
     * inserted OK. It will not refresh the ListView.</p>
     * <p>If we're returning from MakeOfferActivity after editing an old offer,
     * this method will set the status message to reflect that the offer was
     * edited OK. It will not refresh the ListView.</p>
     * <p>If we're returning from ViewSellOffer, and the user has requested a new
     * search filter from that activity, this method saves that filter and
     * runs ListFilteredOffersAsyncTask</p>
     *
     * <p>Any time this activity needs to launch a new Activity, it calls
     * '.startActivityForResult()', and sends it an integer requestCode. When
     * that activity ends (either because it called '.finish()' on its own, or
     * because the user clicked the back button), this method is called, and it
     * automatically receives these parameters:</p>
     * @param requestCode   The same requestCode that this Activity sent to the
     *                      new Activity when it was launched
     * @param resultCode    Equal to Activity.RESULT_OK if the other Activity
     *                      successfully sent data back to this Activity,
     *                      otherwise it's something else
     * @param data          An intent that holds any data sent to this Activity
     *                      from the other Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i(TAG, "NewsActivity.onActivityResult()");

        // If we don't have data from the other activity, don't do anything here
        if(resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "didn't get an intent from activity");
            return;
        }

        //Set units of weight
        String unitsWt = data.getStringExtra(Constants.PREF_UNITS_WT);
        if (unitsWt != null) {
            mUnitsWt = unitsWt;
        } else {
            mUnitsWt = LocalDataHandler.getPrefUnitsWt(this, mUid);
        }

        // If we're coming back from SetSearchFilterActivity
        if(requestCode == REQUEST_CODE_SEARCH_FILTER) {
            // now we know that a SetSearchFilterActivity has sent us this intent
            Log.i(TAG, "successfully obtained intent from SetSearchFilterActivity");
            // we can retrieve info from intent 'data' here
            mFilter = data.getParcelableExtra(
                    SetSearchFilterActivity.SEARCH_FILTER_KEY);

            Log.i(TAG, "NewsActivity received filter: " + mFilter.toString());
            setStatusMsg("Requesting " + mFilter.getCommodity() + " offers from server...");

            new ListFilteredOffersAsyncTask(NewsActivity.this).execute(mFilter);

            // Save the filter to SQL
            LocalDataHandler.saveLocalFilter(this, mFilter, mUid);

        // If we're coming back from MakeOfferActivity
        } else if (requestCode == REQUEST_CODE_MAKE_OFFER) {
            // The only way to get here is if the user made an offer successfully;
            // otherwise resultCode would not be RESULT_OK or requestCode would be something else

            String commodity = data.getStringExtra(Constants.COMMODITY_KEY);
            setStatusMsg("New offer for " + commodity +
                    " inserted ok; click 'Refresh' to see it");

        // If we're coming back from MakeOfferActivity
        } else if (requestCode == REQUEST_CODE_EDIT_OFFER) {
            // The only way to get here is if the user made an offer successfully;
            // otherwise resultCode would not be RESULT_OK or requestCode would be something else

            String commodity = data.getStringExtra(Constants.COMMODITY_KEY);
            setStatusMsg("Old offer for " + commodity +
                    " edited ok; click 'Refresh' to see it");

        // If we're coming back from ViewSellOfferActivity
        } else if (requestCode == REQUEST_CODE_VIEW_OFFER) {
            // If we've made it to this point, the user must have clicked
            // "View this seller's other offers", so there must be a userID in data
            Long sellerId = data.getLongExtra(Constants.SELLER_ID_KEY,
                    Constants.INVALID_USER_ID);

            // Make a filter that has associatedSellerId and nothing else
            mFilter = new RequestFilteredSellOffer(sellerId, DEFAULT_COMMODITY,
                    0d, 0d, 0d, 0d, false, true);

            Log.i(TAG, "NewsActivity made filter for sellerID: " + mFilter.toString());
            setStatusMsg("Requesting seller's offers from server...");

            new ListFilteredOffersAsyncTask(NewsActivity.this).execute(mFilter);

            // Save the filter to SQL
            LocalDataHandler.saveLocalFilter(this, mFilter, mUid);

        } else {
            // handle other requestCode values here
        }
    }

    /**
     * Used to set the list of SellOfferFront objects that this NewsActivity will
     * display in its ListView.
     * @param sellOffers    ArrayList of SellOfferFront object to display in
     *                      the ListView
     */
    public void setmSellOffers(ArrayList<SellOfferFront> sellOffers){
        this.mSellOffers = sellOffers;
    }

    /**
     * Displays mSellOffers in the mListView.
     */
    public void updateListView() {
        if(mSellOffers != null) {
            mArrayAdapter = new MyArrayAdapter(this,
                    R.layout.list_item_news, mSellOffers, mUnitsWt);
        } else {
            mArrayAdapter = new MyArrayAdapter(this,
                    R.layout.list_item_news, new ArrayList<SellOfferFront>(), mUnitsWt);
        }
        mListView.setAdapter(mArrayAdapter);
    }

    /**
     * Displays a message in the status bar at the bottom of the screen.
     * @param statusMsg     The message to display
     */
    public void setStatusMsg(String statusMsg) {
        TextView tv = (TextView) findViewById(R.id.tvStatus_News);
        tv.setText(statusMsg);
    }

    /**
     * Causes the activity not to lose all data if the user reorients the screen
     * @param newConfig     The new Configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // keep the same layout
    }
}
