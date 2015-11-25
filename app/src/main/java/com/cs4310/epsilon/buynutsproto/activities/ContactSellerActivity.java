package com.cs4310.epsilon.buynutsproto.activities;

import com.cs4310.epsilon.buynutsproto.R;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactSellerActivity extends AppCompatActivity {

    // instance data members

    String mPhoneNumber;
    String mSellerName;
    String mEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_seller);


        // Retrieve seller name, phone number, and email from the intent
        // (an intent or an async task), and fit them into mPhoneNumber,
        // mSellerName, and mEmailAddress.

        Intent intent = this.getIntent();
        mPhoneNumber = intent.getStringExtra("sellerPhone");
        mSellerName = intent.getStringExtra("sellerName");
        mEmailAddress = intent.getStringExtra("sellerEmail");


        TextView tvSellerName = (TextView) findViewById(R.id.tvSellerName);
        tvSellerName.setText(mSellerName);

        String strEmail = getResources().getString(R.string.email);
        Button btnEmail = (Button) findViewById(R.id.btnEmail);
        btnEmail.setText(String.format("%s: %s", strEmail, mEmailAddress));

        String strTelephone = getResources().getString(R.string.telephone);
        Button btnPhone = (Button) findViewById(R.id.btnPhone);
        btnPhone.setText(String.format("%s: %s", strTelephone, mPhoneNumber));

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We can change ACTION_DIAL to ACTION_CALL; that would
                // cause the phone to actually call instead of just send
                // you to the phone dialer; requires adding permission to
                // application manifest "android.permission.CALL_PHONE"

                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mPhoneNumber));

                startActivity(i);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto"));

                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mEmailAddress});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I WANT TO BUY YOUR NUTS!");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "I WANT TO BUY YOUR NUTS RIGHT NOW!!!");
                    startActivity(emailIntent);
                } catch (android.content.ActivityNotFoundException e) {
                    Log.i(Constants.TAG, "Can't launch email provider");
                    Toast.makeText(ContactSellerActivity.this,
                            "Can't launch email provider; please check that your email app is set up properly",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
