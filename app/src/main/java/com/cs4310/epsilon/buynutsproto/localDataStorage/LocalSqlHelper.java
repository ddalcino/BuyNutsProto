package com.cs4310.epsilon.buynutsproto.localDataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A class that manages a SQLite database stored locally on the Android device.
 * Created by dave on 11/25/15.
 */
public class LocalSqlHelper extends SQLiteOpenHelper {
    /**
     * Database version number; increment this any time you need to change
     * the way the table is built
     */
    public static final int DATABASE_VERSION = 2;
    /** The name of the database */
    public static final String DATABASE_NAME = "BuyNutsStorage.db";

    /**
     * Inner class Contract: defines a contract for a table called Preferences
     * and provides static variable names for each column of data in the table
     */
    public static final class Contract {
        static final String
                TABLE_NAME = "Preferences",
                _ID = "entryID",
                SELF_UID = "ownUID",
                FILTER_ASSOC_UID = "assocUID",
                UNITS_WEIGHT = "unitsWt",
                COMMODITY = "commod",
                MIN_WT = "minWt",
                MAX_WT = "maxWt",
                MIN_PPU = "minPPU",
                MAX_PPU = "maxPPU",
                SINGLE_SELLER_ONLY = "singleSellerOnly",
                SINGLE_COMMODITY_ONLY = "singleCommodityOnly";
    }

    /** Definitions to help convert Java datatypes to SQLite datatypes */
    private static final String
            TEXT_TYPE = " TEXT",
            LONG_TYPE = " INTEGER",
            DOUBLE_TYPE = " REAL",
            BOOLEAN_TYPE = " INTEGER",
    /** Comma separator */
            COMMA_SEP = ",";

    /** SQL statement to create the SQL table */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract.TABLE_NAME + " (" +
                    Contract._ID + LONG_TYPE + " PRIMARY KEY," +
                    Contract.SELF_UID + LONG_TYPE + COMMA_SEP +
                    Contract.FILTER_ASSOC_UID + LONG_TYPE + COMMA_SEP +
                    Contract.UNITS_WEIGHT + TEXT_TYPE + COMMA_SEP +
                    Contract.COMMODITY + TEXT_TYPE + COMMA_SEP +
                    Contract.MIN_WT + DOUBLE_TYPE + COMMA_SEP +
                    Contract.MAX_WT + DOUBLE_TYPE + COMMA_SEP +
                    Contract.MIN_PPU + DOUBLE_TYPE + COMMA_SEP +
                    Contract.MAX_PPU + DOUBLE_TYPE + COMMA_SEP +
                    Contract.SINGLE_SELLER_ONLY + BOOLEAN_TYPE + COMMA_SEP +
                    Contract.SINGLE_COMMODITY_ONLY + BOOLEAN_TYPE +
                    " )";

    /** SQL statement to delete the SQL table */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.TABLE_NAME;

    /**
     * constructor, ensures that the database knows what version of the SQL
     * database we're using, and uses that information to decide when to
     * upgrade
     */
    public LocalSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database has no meaningful upgrade path, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Called when the version you're using is lower than the version that
     * already exists
     * @param db         The database
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}