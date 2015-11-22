package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.activities.MainLoginActivity;
import com.cs4310.epsilon.nutsinterface.UserFront;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
//import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.CollectionResponseNutsUser\
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.NutsUserApi;
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.NutsUser;

import java.io.IOException;

/**
 * An Asynchronous Task that runs in the background. It sends a username and
 * password to the backend to login a user. It expects to receive an id number for that new user.
 *
 * Created by dave on 11/20/15.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, Long> {

    private static NutsUserApi nutsUserEndpoint = null;
    private Context context;
    public LoginAsyncTask(Context context) {
        this.context = context;
    }
    @Override
    protected Long doInBackground(String... Params) {
        if (Params == null || Params.length != 2 ||
                Params[0] == null || Params[0].equals("") ||
                Params[1] == null || Params[1].equals("") ) {
            return (Constants.INVALID_UID);
        }

        String username = Params[0];
        String password = Params[1];

        Log.i(Constants.ASYNC_TAG, "User attempted login with username=" +
                username + ", password=" + password);

        if (nutsUserEndpoint == null) {
            NutsUserApi.Builder builder = new NutsUserApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            nutsUserEndpoint = builder.build();
        }
        try {
            Log.i(Constants.ASYNC_TAG, "Calling login");
            NutsUser result = nutsUserEndpoint.login(username, password).execute();
            if (result != null) {
                Log.i(Constants.ASYNC_TAG, "User logged in with id=" +
                        result.getId() + "\nuser=" + result.toString());
                return (result.getId());
            } else {
                Log.i(Constants.ASYNC_TAG, "Login yields no matching user");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return(Constants.INVALID_UID);
    }
    @Override
    protected void onPostExecute(Long resultId) {
        MainLoginActivity mainLoginActivity = (MainLoginActivity) context;

        if (resultId != Constants.INVALID_UID) {
            Toast.makeText(mainLoginActivity, "User logged in with id="+resultId,
                    Toast.LENGTH_LONG).show();
            Log.i(Constants.ASYNC_TAG, "User logged in with id=" + resultId);
        }
        mainLoginActivity.login(resultId);
    }
}
