package com.cs4310.epsilon.buynutsproto.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.guiHelpers.MyArrayAdapter;
import com.cs4310.epsilon.nutsinterface.SellOfferFront;

import java.util.ArrayList;


/**
 * Activity that displays a list of SellOffers in a ListView that occupies
 * most of the screen.
 */
public class NewsActivity extends AppCompatActivity {
    static final String TAG = "myTag";
    static final int REQUEST_CODE_SEARCH_FILTER = 0;
    private long mUid = MainLoginActivity.INVALID_USERID;
    private ListView listView;
    private MyArrayAdapter myArrayAdapter;
    private ArrayList<SellOfferFront> sellOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //get mUid from intent
        mUid = this.getIntent().getLongExtra("mUid", MainLoginActivity.INVALID_USERID);
        Log.i(TAG, (mUid == MainLoginActivity.INVALID_USERID ? "Didn't receive mUid" : "Received mUid=" + mUid));

        //get listView
        listView = (ListView) findViewById(R.id.listView);
        updateListView();

        // set OnClickListeners:
        Button btnMakeNewOffer = (Button) findViewById(R.id.btnMakeOffer_News);
        btnMakeNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnMakeOffer_News");

                //create and launch MakeOfferActivity
                Intent intent = new Intent(NewsActivity.this, MakeOfferActivity.class);
                intent.putExtra("mUid", mUid);
                NewsActivity.this.startActivity(intent);
            }
        });
        Button btnSetSearch = (Button) findViewById(R.id.btnSetFilter_News);
        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnSetFilter_News");

                //create and launch SetSearchFilterActivity
                NewsActivity.this.startActivityForResult(
                        new Intent(NewsActivity.this, SetSearchFilterActivity.class),
                        REQUEST_CODE_SEARCH_FILTER);
            }
        });
        Button btnRefreshNews = (Button) findViewById(R.id.btnRefreshNews_News);
        btnRefreshNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick btnRefreshNews_News");

                new SellOfferAsyncTask(NewsActivity.this).execute();
                /*Toast.makeText(NewsActivity.this.getApplicationContext(),
                        "Not yet implemented. Hahaha no news for you",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //anonymous inner class that implements OnItemClickListener
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                SellOfferFront choice = NewsActivity.this.myArrayAdapter.getItem(position);
                //intent is the only parameter passed to the new activity
                Intent intent = new Intent(NewsActivity.this, ViewSellOfferActivity.class);
                intent.putExtra("SellOffer", (Parcelable) choice);

                Log.i(TAG, "User clicked SellOffer at position=" + position + ", id=" + id);
                //create another activity
                NewsActivity.this.startActivity(intent);
                //refer to parent reference - can't just say "this", that's the inner class
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i(TAG, "onActivityResult()");
        if(resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "didn't get an intent from SetSearchFilterActivity");
        } else if(requestCode == REQUEST_CODE_SEARCH_FILTER) {
            // now we know that a SetSearchFilterActivity has sent us this intent
            Log.i(TAG, "successfully obtained intent from SetSearchFilterActivity");
            // we can retrieve info from intent 'data' here
        } else {
            // handle other requestCode values here
        }
    }

    public void fillListView(ArrayList<SellOfferFront> sellOffers){
        this.sellOffers = sellOffers;
    }
    public void updateListView() {
        if(sellOffers != null) {
            myArrayAdapter = new MyArrayAdapter(this,
                    R.layout.list_item_news, sellOffers);
            listView.setAdapter(myArrayAdapter);
        }
    }
    public void setStatusMsg(String s) {
        TextView tv = (TextView) findViewById(R.id.tvStatus_News);
        tv.setText(s);
    }
}
