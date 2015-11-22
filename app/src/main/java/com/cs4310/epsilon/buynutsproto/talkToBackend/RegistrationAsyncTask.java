package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.activities.RegistrationActivity;
import com.cs4310.epsilon.nutsinterface.UserFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
//import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.CollectionResponseNutsUser\
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.NutsUserApi;
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.NutsUser;

import java.io.IOException;

/**
 * An Asynchronous Task that runs in the background. It sends a username,
 * password, name, phonenumber, email to the backend to register a new user.
 * It expects to receive an id number for that new user.
 *
 * Created by dave on 11/20/15.
 */
public class RegistrationAsyncTask extends AsyncTask<UserFront, Void, Long> {

    private static NutsUserApi nutsUserEndpoint = null;
    private Context context;
    public RegistrationAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected Long doInBackground(UserFront... Params) {
        if (Params == null || Params[0] == null) {
            return (Constants.INVALID_UID);
        }

        UserFront newUser = Params[0];
        if (nutsUserEndpoint == null) {
            NutsUserApi.Builder builder = new NutsUserApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            nutsUserEndpoint = builder.build();
        }
        try {
            String userName = newUser.getUserName();
            String password = newUser.getPassword();
            String name = newUser.getName();
            String email = newUser.getEmail();
            String telephone = newUser.getTelephone();
            Log.i(Constants.ASYNC_TAG, "Registering user=" + newUser.toString());
            NutsUser result = nutsUserEndpoint.register(email, name, password, telephone, userName).execute();
            if (result != null) {
                Log.i(Constants.ASYNC_TAG, "Registered user with id=" +
                        result.getId() + "\nuser=" + result.toString());
                return (result.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return(Constants.INVALID_UID);
    }
    @Override
    protected void onPostExecute(Long resultId) {
        RegistrationActivity registrationActivity = (RegistrationActivity) context;

        if (resultId != Constants.INVALID_UID) {
            Toast.makeText(registrationActivity, "Id assigned="+resultId,
                    Toast.LENGTH_LONG).show();
            Log.i(Constants.ASYNC_TAG, "Id assigned=" + resultId);

        } else {
            Toast.makeText(registrationActivity, "No Id assigned",
                    Toast.LENGTH_LONG).show();
            Log.i(Constants.ASYNC_TAG, "No Id assigned");

        }
    }
}
