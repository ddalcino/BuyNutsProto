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
 * LocalDataHandler is a class full of static methods that allow for persistent
 * local data storage on the Android device. Currently, these methods allow for
 * the storage and retrieval of username and password (using Android
 * SharedPreferences); storage and retrieval of preferred units of weight, and
 * storage and retrieval of a search filter.
 *
 * Created by dave on 11/25/15.
 */
public class LocalDataHandler {
    /** A tag for logs, specific to this class */
    private static final String TAG = "tagLocalData";

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
     * Retrieves the username and password if they were stored on this device;
     * otherwise returns blank strings
     * @param activity  The Activity that called this method
     * @return          A String array; the first element is the username, and
     *                  the second element is the password.
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
     * Stores preferred units of weight as a string in a SQL database, paired
     * with the userID; this database will have different entries for each
     * user that has logged in on this device.
     * @param context   The Activity that called this method
     * @param units     A string that corresponds to one of the units in strings.xml
     *                  under array_wt_units
     * @param mUid      The userID of the user that prefers these weight units
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

        Log.i(TAG, "uid=" + mUid + " saved units=" );

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
            Log.i(TAG, "uid=" + mUid + " saved units=" + oldUnits + " new units=" + units );


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
     * Retrieves preferred units of weight as a string from the SQL database,
     * paired with a userID; this database has unique entries for each
     * user that has logged in on this device.
     * @param context   The Activity that called this method
     * @param mUid      The userID of the user that prefers these weight units
     * @return          A string that corresponds to one of the units in strings.xml
     *                  under array_wt_units
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

    /**
     * Saves a RequestFilteredSellOffer object to local SQL storage, paired
     * with a userID
     * @param context   The Activity that called this method
     * @param filter    The RequestFilteredSellOffer to save
     * @param mUid      The userID of the user that prefers these weight units
     * @return          The number of rows in the SQL database affected by the
     *                  storage operation
     */
    public static int saveLocalFilter(Context context,
                                      RequestFilteredSellOffer filter,
                                      Long mUid) {

        Log.i(TAG, "Saving filter to SQL for mUid=" + mUid);

        int numRowsAffected = 0;
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
            // insert() returns -1 if the row was not inserted
            newRowId = db.insert(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    null,
                    values);

            if (newRowId != -1) {
                // if the row was inserted, record it
                numRowsAffected += 1;
            }
        } else {
            // Update every row where uid=mUid
            numRowsAffected += db.update(
                    LocalSqlHelper.Contract.TABLE_NAME,
                    values,
                    LocalSqlHelper.Contract.SELF_UID + "=?", // 'where' clause
                    new String[] { String.valueOf(mUid) }    // arguments to 'where' clause
            );
        }
        // release all open resources
        dbRead.close();
        mDbHelper.close();

        return numRowsAffected;
    }

    /**
     * Retrieves a saved filter from local SQL database
     * @param context   The activity that needs the filter
     * @param mUid      The userID associated with the filter
     * @return          The RequestFilteredSellOffer associated with the uid,
     *                  or null if no filter is stored for that user
     */
    public static RequestFilteredSellOffer getSavedFilter(Context context, Long mUid) {
        RequestFilteredSellOffer resultFilter = null;

        LocalSqlHelper mDbHelper = new LocalSqlHelper(context);

        // Gets the data repository in read mode
        SQLiteDatabase dbRead = mDbHelper.getReadableDatabase();

        Cursor c = dbRead.query(
                LocalSqlHelper.Contract.TABLE_NAME,     // The table to query
                new String[] {                          // The columns to return
                        LocalSqlHelper.Contract.UNITS_WEIGHT,
                        LocalSqlHelper.Contract.COMMODITY,
                        LocalSqlHelper.Contract.FILTER_ASSOC_UID,
                        LocalSqlHelper.Contract.MAX_PPU,
                        LocalSqlHelper.Contract.MIN_PPU,
                        LocalSqlHelper.Contract.MAX_WT,
                        LocalSqlHelper.Contract.MIN_WT,
                        LocalSqlHelper.Contract.SINGLE_COMMODITY_ONLY,
                        LocalSqlHelper.Contract.SINGLE_SELLER_ONLY,
                },
                LocalSqlHelper.Contract.SELF_UID + "=?",// The columns for the WHERE clause
                new String[] { String.valueOf(mUid) },  // The values for the WHERE clause
                null,                                   // don't group the rows
                null,                                   // don't filter by row groups
                null                                    // The sort order
        );

        // if we have results
        if (c.getCount() > 0) {
            try {
                c.moveToFirst();
                String commod = c.getString(c.getColumnIndex(
                        LocalSqlHelper.Contract.COMMODITY));
                Long assocUID = c.getLong(c.getColumnIndex(
                        LocalSqlHelper.Contract.FILTER_ASSOC_UID));
                Double maxPPU = c.getDouble(c.getColumnIndex(
                        LocalSqlHelper.Contract.MAX_PPU));
                Double minPPU = c.getDouble(c.getColumnIndex(
                        LocalSqlHelper.Contract.MIN_PPU));
                Double maxWt =  c.getDouble(c.getColumnIndex(
                        LocalSqlHelper.Contract.MAX_WT));
                Double minWt =  c.getDouble(c.getColumnIndex(
                        LocalSqlHelper.Contract.MIN_WT));
                Boolean commodOnly = c.getInt(c.getColumnIndex(
                        LocalSqlHelper.Contract.SINGLE_COMMODITY_ONLY)) == 1;
                Boolean sellerOnly = c.getInt(c.getColumnIndex(
                        LocalSqlHelper.Contract.SINGLE_SELLER_ONLY)) == 1;
                resultFilter = new RequestFilteredSellOffer(
                        assocUID, commod, minWt, maxWt,
                        minPPU, maxPPU, false, sellerOnly);
                Log.i(TAG, "Retrieved filter from SQL for " + mUid + ":\n" + resultFilter);
            } catch (Exception e) {
                Log.i(TAG, "Couldn't retrieve saved filter from SQL dt exception:");
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "No saved filter in SQL db");
        }

        // release all open resources
        c.close();
        dbRead.close();
        mDbHelper.close();

        return resultFilter;
    }
}
