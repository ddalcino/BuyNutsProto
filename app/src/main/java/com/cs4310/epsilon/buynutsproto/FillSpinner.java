package com.cs4310.epsilon.buynutsproto;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Contains a method that fills a Spinner object with choices.
 *
 * Created by dave on 11/3/15.
 */
public class FillSpinner {
    /**
     * Fills a Spinner object with an array of strings that exists somewhere
     * in a resource xml file.
     * @param context   A reference to the Activity that the spinner exists in.
     * @param resourceStringArray   An integer that points to a resource that
     *                              holds an array of strings. Two examples of
     *                              this are in app/res/values/strings.xml, near
     *                              the bottom.
     * @param spinner   A reference to the Spinner object that you want to
     *                  populate with the string array resource.
     * @return  Whether or not the fill operation succeeded. Right now, it only
     *          returns true; I haven't yet figured out how to detect a failure.
     */
    static boolean fill(Context context, int resourceStringArray, Spinner spinner){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                resourceStringArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        return true;
    }
}
