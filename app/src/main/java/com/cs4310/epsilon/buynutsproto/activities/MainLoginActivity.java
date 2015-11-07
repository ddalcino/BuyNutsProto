package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;

/**
 * The entry point of the app: The first thing the app does is create a
 * MainLoginActivity and run onCreate(). I called this MainLoginActivity
 * instead of MainActivity just to be more specific about the purpose of the
 * activity.
 */
public class MainLoginActivity extends AppCompatActivity {
    /**
     * A tag used to put messages in the Log. To see these messages while the
     * app is running, go to the 'logcat' tab in Android Monitor (it pops up
     * automatically for me), and type this string into the search filter.
     */
    static final String TAG = "myTag";
    /**
     * The minimum valid user id.
     */
    static final long MIN_USERID = 0;
    /**
     * Used to denote that a user id is invalid
     */
    static final long INVALID_USERID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }

    /**
     * Starts up a NewsActivity if login credentials are valid. This function
     * is bound to the onClick property of the login button in the
     * activity_main_login.xml file.
     * Pre: 'etUsername' contains an integer value greater than 0, which
     *      corresponds to the userID for the user you want to login. This
     *      precondition will change before release!
     * Post:    Starts a NewsActivity that is aware of the userID supplied by
     *          the user in this MainLoginActivity
     * @param view  the View object that called this function
     */
    public void onClickLogin(View view){
        Log.i(TAG, "onClickLogin()");

        try{
            //get username: should hold an int
            EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
            //this will throw NumberFormatException if input is not an int
            long uid = Long.parseLong( etUsername.getText().toString().trim() );
            if(uid < MIN_USERID){
                Log.i(TAG, "user entered a negative value for username");
                Toast.makeText(this.getApplicationContext(),
                        "Please enter a positive integer for username",
                        Toast.LENGTH_LONG).show();
            } else {
                //create another activity
                Intent intent = new Intent(MainLoginActivity.this, NewsActivity.class);
                //send it the user id
                intent.putExtra("uid", uid);
                //launch NewsActivity
                MainLoginActivity.this.startActivity(intent);
            }
        } catch (NumberFormatException e) { //Long.parseLong throws this exception
            Log.i(TAG, "user entered a non-integer value for username");
            Toast.makeText(this.getApplicationContext(),
                    "Please enter a userID (positive integer) for username",
                    Toast.LENGTH_LONG).show();
        }
    }
}
