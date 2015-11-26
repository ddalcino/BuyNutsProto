package com.cs4310.epsilon.buynutsproto.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.localDataStorage.LocalDataHandler;
import com.cs4310.epsilon.buynutsproto.talkToBackend.LoginAsyncTask;

/**
 * The entry point of the app: The first thing the app does is create a
 * MainLoginActivity and run onCreate(). I called this MainLoginActivity
 * instead of MainActivity just to be more specific about the purpose of the
 * activity.
 */
public class MainLoginActivity extends AppCompatActivity {

    private static final String TAG = Constants.TAG_ACTIVITY_PREFIX + "MainLoginActivity";

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

        String info[] = LocalDataHandler.getUNamePassword(this);
        String userName = info[0];
        String password = info[1];

        // Fill in the empty EditText fields with stored data
        EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
        etUsername.setText(userName);
        EditText etPassword = (EditText) this.findViewById(R.id.etPassword);
        etPassword.setText(password);

        // If there was stored data, then set the checkbox
        CheckBox chkSaveInfo = (CheckBox) this.findViewById(R.id.chkSaveLoginInfo);
        chkSaveInfo.setChecked(!userName.equals("") && !password.equals(""));


        // Register an OnClickListener for the Register User button
        Button btnRegisterNewUser = (Button) findViewById(R.id.btnRegisterNewUser);
        btnRegisterNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // To test ViewSellOfferActivity, uncomment the next block:
//                Intent intent = new Intent(MainLoginActivity.this, ViewSellOfferActivity.class);
//                intent.putExtra("SellOffer", new SellOfferFront(123l, "123", 123l, 5.0, 60.0, 600.0, null, "walnut", false));
//                MainLoginActivity.this.startActivity(intent);
                Log.i(TAG, "onClickRegister()");
                startActivity(new Intent(MainLoginActivity.this, RegistrationActivity.class));
            }
        });

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

        // This is the fake code that bypasses all login procedures.
        if (false) {
            login(1l);
        } else {
            // This is the real code that logs users in. Until the backend is
            // capable of logging users in successfully, it will be unreachable.

            Log.i(TAG, "onClickLogin()");

            EditText etUsername = (EditText) this.findViewById(R.id.etUsername);
            EditText etPassword = (EditText) this.findViewById(R.id.etPassword);
            CheckBox chkSaveInfo = (CheckBox) this.findViewById(R.id.chkSaveLoginInfo);

            String userName = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Only accept input if there's something there
            if (!userName.equals("") && !password.equals("")) {

                LocalDataHandler.saveUserInfo(this, chkSaveInfo.isChecked(), userName, password);

                Log.i(TAG, "Calling LoginAsyncTask with username="+userName+", password="+password);
                new LoginAsyncTask(MainLoginActivity.this).execute(
                        userName, password
                );
            } else {
                Log.i(TAG, "User entered no text for username or password");
                Toast.makeText(this.getApplicationContext(),
                        "Please enter a valid username and password",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Completes the action of logging in: Given a valid userID, this function
     * launches the NewsActivity and tells it what the user's ID is.
     * PRE: uid parameter is a valid user id
     * POST: NewsActivity is launched and knows the user id
     * @param uid   The userID that corresponds to the user trying to login;
     *              should be provided by the backend during LoginAsyncTask.
     *              This parameter IS NOT CHECKED AND IS ASSUMED TO BE VALID
     */
    public void login(Long uid) {
        if (uid != Constants.INVALID_USER_ID) {
            //create another activity
            Intent intent = new Intent(MainLoginActivity.this, NewsActivity.class);
            //send it the user id
            intent.putExtra(Constants.USER_ID_KEY, uid);
            //launch NewsActivity
            MainLoginActivity.this.startActivity(intent);

        }
    }
}
