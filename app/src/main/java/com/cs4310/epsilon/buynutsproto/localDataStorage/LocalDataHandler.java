package com.cs4310.epsilon.buynutsproto.localDataStorage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cs4310.epsilon.buynutsproto.R;
import com.cs4310.epsilon.nutsinterface.RequestFilteredSellOffer;

/**
 * Created by dave on 11/25/15.
 */
public class LocalDataHandler {

    /**
     * If okToStoreInfo is true, this method saves the user's username and
     * password to SharedPreferences. Otherwise, it overwrites the stored
     * values for username and password with blank strings.
     * @param activity      The Activity calling this method
     * @param okToStoreInfo Whether or not the method should store username and password
     * @param userName      The username to store
     * @param password      The password to store
     */
    public static void saveUserInfo(Activity activity, boolean okToStoreInfo, String userName, String password) {
        // Get a reference to the Android Device's shared preferences, keeping
        // this data private
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        // Get a reference to an object that can write to SharedPreferences
        SharedPreferences.Editor editor = sharedPref.edit();

        // If the user has checked "remember my login info", save that info to SharedPreferences
        if (okToStoreInfo) {
            editor.putString(activity.getString(R.string.key_saved_username), userName);
            editor.putString(activity.getString(R.string.key_saved_password), password);
        } else {
            // Otherwise, overwrite saved data with empty strings
            editor.putString(activity.getString(R.string.key_saved_username), "");
            editor.putString(activity.getString(R.string.key_saved_password), "");
        }
        // Save the changes
        editor.apply();
    }

    /**
     *
     * @param activity
     * @return
     */
    public static String[] getUNamePassword(Activity activity) {
        // Look for stored data for username and password:
        // If nothing is stored, then save blank strings for both values
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String userName = sharedPref.getString(activity.getString(R.string.key_saved_username), "");
        String password = sharedPref.getString(activity.getString(R.string.key_saved_password), "");
        return new String[] {userName, password};
    }

    /**
     *
     * @param context
     * @param units
     */
    public static void storeUnitsWeight(Context context, String units, Long mUid) {

        LocalSqlHelper mDbHelper = new LocalSqlHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();
        // Check number of entries in table with Self_UID = mUid
        Cursor c = dbRead.query(
                LocalSqlHelper.Contract.TABLE_NAME,     // The table to query
                new String[] { LocalSqlHelper.Contract.UNITS_WEIGHT }, // The columns to return
                LocalSqlHelper.Contract.SELF_UID + "=?",    // The columns for the WHERE clause
                new String[] { String.valueOf(mUid) },      // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );

        int numEntries = c.getCount();

        Log.i("myTagSQL", "uid=" + mUid + " saved units=" );

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocalSqlHelper.Contract.SELF_UID, mUid);
        values.put(LocalSqlHelper.Contract.UNITS_WEIGHT, units);

        // There's only one row allowed: if there are no rows yet, then insert this:
        if (numEntries < 1) {
            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    null,
                    values);
        } else {
            c.moveToFirst();
            String oldUnits = c.getString(c.getColumnIndex(LocalSqlHelper.Contract.UNITS_WEIGHT));
            Log.i("myTagSQL", "uid=" + mUid + " saved units=" + oldUnits + " new units=" + units );


            // Update every row where SELF_UID = mUid
            db.update(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    values,
                    LocalSqlHelper.Contract.SELF_UID + "=?",   // 'where' clause: if null, affects all rows
                    new String[] { String.valueOf(mUid) }    // arguments to 'where' clause: empty
            );
        }

        // release all open resources
        c.close();
        dbRead.close();
        db.close();
        mDbHelper.close();
    }

    /**
     *
     * @param context
     * @return
     */
    public static String getPrefUnitsWt(Context context, Long mUid) {
        LocalSqlHelper mDbHelper = new LocalSqlHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();

        Cursor c = dbRead.query(
                LocalSqlHelper.Contract.TABLE_NAME,     // The table to query
                new String[] {                          // The columns to return
                        LocalSqlHelper.Contract.UNITS_WEIGHT },
                LocalSqlHelper.Contract.SELF_UID + "=?",// The columns for the WHERE clause
                new String[] { String.valueOf(mUid) },  // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );

        String result;
        if (c.getCount() > 0) {
            c.moveToFirst();

            result = c.getString(c.getColumnIndex(LocalSqlHelper.Contract.UNITS_WEIGHT));
        } else {
            // return default
            result = "lb";
        }
        // release all open resources
        c.close();
        dbRead.close();
        mDbHelper.close();

        return result;
    }

    public static void saveLocalFilter(Context context, RequestFilteredSellOffer filter, Long mUid) {

        LocalSqlHelper mDbHelper = new LocalSqlHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();
        // Check number of entries in table
        Long numEntries = DatabaseUtils.queryNumEntries(dbRead, LocalSqlHelper.Contract.TABLE_NAME);

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocalSqlHelper.Contract.SELF_UID, mUid);
        values.put(LocalSqlHelper.Contract.FILTER_ASSOC_UID, filter.getAssociatedUserID());
        values.put(LocalSqlHelper.Contract.COMMODITY, filter.getCommodity());
        values.put(LocalSqlHelper.Contract.MIN_WT, filter.getMinWeight());
        values.put(LocalSqlHelper.Contract.MAX_WT, filter.getMaxWeight());
        values.put(LocalSqlHelper.Contract.MIN_PPU, filter.getMinPricePerUnit());
        values.put(LocalSqlHelper.Contract.MAX_PPU, filter.getMaxPricePerUnit());
        values.put(LocalSqlHelper.Contract.SINGLE_SELLER_ONLY,
                (filter.getMyOwnOffersOnly() ? 1 : 0));  // boolean value
        values.put(LocalSqlHelper.Contract.SINGLE_COMMODITY_ONLY,
                (filter.getMaxWeight() > 0 ? 0 : 1)); // boolean value

        // There's only one row allowed: if there are no rows yet, then insert this:
        if (numEntries < 1) {
            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    null,
                    values);
        } else {
            // Update every row (there can be only one)
            db.update(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    values,
                    null,   // 'where' clause: if null, affects all rows
                    null    // arguments to 'where' clause: empty
            );
        }
        // release all open resources
        dbRead.close();
        mDbHelper.close();
    }
}
