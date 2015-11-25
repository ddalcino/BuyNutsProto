package com.cs4310.epsilon.buynutsproto.talkToBackend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cs4310.epsilon.buynutsproto.activities.MainLoginActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.NutsUserApi;
import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.NutsUser;

import java.io.IOException;

//import com.nutsinterface.mike.myapplication.backend.nutsUserApi.model.CollectionResponseNutsUser\

/**
 * An Asynchronous Task that runs in the background. It sends a username and
 * password to the backend to login a user. It expects to receive an id number for that new user.
 *
 * Created by dave on 11/20/15.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, Long> {
    /**
     * Tag used in logs; starts with the same prefix as all other AsyncTasks
     * in the project, but with a suffix unique to this class
     */
    private static final String TAG = Constants.ASYNC_TAG_PREFIX + "Login";

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

        Log.i(TAG, "User attempted login with username=" +
                username + ", password=" + password);

        if (nutsUserEndpoint == null) {
            NutsUserApi.Builder builder = new NutsUserApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl(Constants.BACKEND_URL);

            nutsUserEndpoint = builder.build();
        }
        try {
            Log.i(TAG, "Calling login");
            NutsUser result = nutsUserEndpoint.login(password, username).execute();
            if (result != null) {
                Log.i(TAG, "User logged in with id=" +
                        result.getId() + "\nuser=" + result.toString());
                return (result.getId());
            } else {
                Log.i(TAG, "Login yields no matching user");
                return Constants.INVALID_UID;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Constants.SERVER_ERROR;

        }
    }
    @Override
    protected void onPostExecute(Long resultId) {
        MainLoginActivity mainLoginActivity = (MainLoginActivity) context;

        if (resultId != Constants.INVALID_UID && resultId != Constants.SERVER_ERROR) {
//            Toast.makeText(mainLoginActivity, "User logged in with id="+resultId,
//                    Toast.LENGTH_LONG).show();
            mainLoginActivity.login(resultId);
        } else if (resultId == Constants.INVALID_UID) {
            Toast.makeText(mainLoginActivity,
                    "No user found with that username and password; try registering a new account",
                    Toast.LENGTH_LONG
            ).show();
        } else if (resultId == Constants.SERVER_ERROR) {
            Toast.makeText(mainLoginActivity, "Error accessing the server",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
