package com.cs4310.epsilon.buynutsproto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;

public class MainLoginActivity extends AppCompatActivity {
    static final String TAG = "myTag";
    static final long MIN_USERID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }

    /**
     * Starts up a NewsActivity if login credentials are valid.
     * Pre: 'etUsername' contains an integer value greater than 0, which corresponds to the userID
     *      for the user you want to login. This precondition will change before release!
     * Post:    Starts a NewsActivity that is aware of the userID supplied by the user in this
     *          MainLoginActivity
     * @param view  the View object that called this function
     */
    public void onClickLogin(View view){
        Log.i(TAG, "onClickLogin()");

        long uid = -1;

        try{
            //get username: should hold an int
            EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
            //this will throw NumberFormatException if input is not an int
            uid = Long.parseLong( etUsername.getText().toString().trim() );
            if(uid < MIN_USERID){
                uid = -1;
            }
        } catch (NumberFormatException e){
            Log.i(TAG, "user entered a non-integer value for username");
            Toast.makeText(this.getApplicationContext(),
                    "Please enter a userID (positive integer) for username",
                    Toast.LENGTH_LONG).show();
        }
        if(uid >= MIN_USERID) {
            //create another activity
            Intent intent = new Intent(MainLoginActivity.this, NewsActivity.class);
            //send it the user id
            intent.putExtra("uid", uid);
            //launch NewsActivity
            MainLoginActivity.this.startActivity(intent);
        }
    }
}
