package com.cs4310.epsilon.buynutsproto;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;

public class NewsActivity extends AppCompatActivity {
    static final String TAG = "myTag";
    static final int REQUEST_CODE_SEARCH_FILTER = 0;
    private long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //get uid from intent
        uid = this.getIntent().getLongExtra("uid", -1);
        Log.i(TAG, (uid==-1 ? "Didn't receive uid" : "Received uid="+uid));

        // set onclick listeners:
        Button btnMakeNewOffer = (Button) findViewById(R.id.btnMakeOffer_News);
        btnMakeNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickMakeNewOffer()");

                //create and launch MakeOfferActivity
                Intent intent = new Intent(NewsActivity.this, MakeOfferActivity.class);
                intent.putExtra("uid", uid);
                NewsActivity.this.startActivity(intent);
            }
        });
        Button btnSetSearch = (Button) findViewById(R.id.btnSetFilter_News);
        btnSetSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickSetSearch()");

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
                Log.i(TAG, "onClickRefreshNews()");
                Toast.makeText(NewsActivity.this.getApplicationContext(),
                        "Not yet implemented. Hahaha no news for you",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i(TAG, "onActivityResult()");
        if(resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "didn't get an intent from SetSearchFilterActivity");
            return;
        } else if(requestCode == REQUEST_CODE_SEARCH_FILTER) {
            // now we know that a SetSearchFilterActivity has sent us this intent
            Log.i(TAG, "successfully obtained intent from SetSearchFilterActivity");
            // we can retrieve info from intent 'data' here
        } else {
        }
    }
}
