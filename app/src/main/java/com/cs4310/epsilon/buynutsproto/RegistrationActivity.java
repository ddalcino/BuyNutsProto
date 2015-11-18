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
        //et2Username holds user input for username on Registration Activity Screen
         EditText et2Username = (EditText) findViewById(R.id.et2Username);
        //et2Password holds user input for password on Registration Activity Screen
         EditText et2Password = (EditText) findViewById(R.id.et2Password);

        //Toast displays user inputted "username" and "password" just making sure register button is responsive
        Toast.makeText(RegistrationActivity.this, "username: " + et2Username.getText().toString() + "\n password: " + et2Password.getText().toString() , Toast.LENGTH_LONG).show();

    }
}
