package nsa.com.museum.BeaconActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import nsa.com.museum.ItemsDBHelper;

public class DBBeacon {

    public static final String DATABASE_NAME = "BEACON_DATABASE";
    public static final int DATABASE_VERSION = 1;
    SQLiteDatabase database;
    ItemsDBHelper db;

    // Code referenced from the source http://androidtuts4u.blogspot.co.uk/2013/02/android-list-view-using-custom-adapter.html.

    public DBBeacon(Context context) {

        db = new ItemsDBHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
        database = db.getWritableDatabase();
    }

    public void executeQuery(String query) {
        try {

            if (database.isOpen()) {
                database.close();
            }

            database = db.getWritableDatabase();
            database.execSQL(query);

        } catch (Exception error) {

            System.out.println("ERROR DATABASE " + error);
        }

    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;
        try {

            if (database.isOpen()) {
                database.close();

            }
            database = db.getWritableDatabase();
            c1 = database.rawQuery(query, null);

        } catch (Exception e) {

            System.out.println("DATABASE ERROR " + e);

        }
        return c1;

    }

    public void insert(String objId, String museumId, String objName, String url, byte[] img) {
        SQLiteDatabase d = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("beaconId", objId);
        cv.put("museumId", museumId);
        cv.put("objectName", objName);
        cv.put("url", url);
        cv.put("objectImage", img);
        d.insert(ItemsDBHelper.TABLE_BEACON_DETAILS, null, cv);
    }


}
