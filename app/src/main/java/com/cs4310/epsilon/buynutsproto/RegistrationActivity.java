package com.cs4310.epsilon.buynutsproto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Kyle on 11/10/2015.
 */
public class RegistrationActivity extends AppCompatActivity {
    static final String TAG = "myTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //sets layout to activity_registration
        setContentView(R.layout.activity_registration);

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
                Toast.makeText(RegistrationActivity.this, "PASSWORD: " + et2Password.getText().toString() + " doesn't match CONFIRM: " + et2Confirm.getText().toString(), Toast.LENGTH_LONG).show();
            }
            //Toast displays user inputted "username" and "password" just making sure register button is responsive
            Toast.makeText(RegistrationActivity.this, "name: " + et2Name.getText().toString() + "\nemail: " + et2Email.getText().toString() + "\nphone: " + et2Phone.getText().toString() + "\nusername: " + et2Username.getText().toString() + "\npassword: " + et2Password.getText().toString() + "\nconfirm: " + et2Confirm.getText().toString(), Toast.LENGTH_LONG).show();
        }
        catch(Exception invalid)
        {
            invalid.getMessage();
        }
    }
}
