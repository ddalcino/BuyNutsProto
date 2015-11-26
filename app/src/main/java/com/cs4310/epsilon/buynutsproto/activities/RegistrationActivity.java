package com.cs4310.epsilon.buynutsproto.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.buynutsproto.talkToBackend.RegistrationAsyncTask;
import com.cs4310.epsilon.nutsinterface.UserFront;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets layout to activity_registration
        setContentView(R.layout.activity_registration);

        EditText etPhone = (EditText) findViewById(R.id.et2Phone);
        etPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

    }

    public void onClickRegisterButton(View view) {
        try {
            //et2Username holds user input for Username on Registration Activity Screen
            EditText et2Username = (EditText) findViewById(R.id.et2Username);

            //et2Password holds user input for Password on Registration Activity Screen
            EditText et2Password = (EditText) findViewById(R.id.et2Password);

            //et2Email holds user input for Email on Registration Activity Screen
            EditText et2Email = (EditText) findViewById(R.id.et2Email);

            //et2Name holds user input for Name on Registration Activity Screen
            EditText et2Name = (EditText) findViewById(R.id.et2Name);

            //et2Phone holds user input for Phone on Registration Activity Screen
            EditText et2Phone = (EditText) findViewById(R.id.et2Phone);

            //et2Phone holds user input for Phone on Registration Activity Screen
            EditText et2Confirm = (EditText) findViewById(R.id.et2Confirm);

            //display whether or not password input and confirm input matches
            if(!et2Confirm.getText().toString().equals(et2Password.getText().toString()))
            {
                // Log the entered password and confirmed passwords to the logs under tag constants.TAG
                String msg = "PASSWORD: " + et2Password.getText().toString() + " doesn't match CONFIRM: " + et2Confirm.getText().toString();
                Log.i(Constants.TAG, msg);
                // Display standardized error message to user
                String errorMsg = getResources().getString(R.string.error_confirm_password);
                Toast.makeText(RegistrationActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            } else {
                //TODO: Perform entry validation

                UserFront user = new UserFront(
                        et2Username.getText().toString().trim(),
                        et2Password.getText().toString().trim(),
                        et2Email.getText().toString().trim(),
                        et2Phone.getText().toString().trim(),
                        et2Name.getText().toString().trim()
                );


                //Toast displays user inputted entry just making sure register button is responsive
                Toast.makeText(RegistrationActivity.this, user.toString(), Toast.LENGTH_LONG).show();

                //TODO: send user to RegistrationAsyncTask
                new RegistrationAsyncTask(RegistrationActivity.this).execute(user);

            }
        }
        catch(Exception invalid)
        {
            invalid.printStackTrace();
        }
    }
}
