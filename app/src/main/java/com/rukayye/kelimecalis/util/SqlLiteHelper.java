package com.rukayye.kelimecalis.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rukayye.kelimecalis.model.KelimeModel;

import java.util.ArrayList;

public class SqlLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "kelimeler_db";

    private final String TABLE_NAME = "Kelimeler";
    private final String COLUMN_ID = "id";
    private final String COLUMN_KELIME = "Kelime";
    private final String COLUMN_ANLAM = "Anlam";

    private final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_KELIME + " TEXT,"
                    + COLUMN_ANLAM + " TEXT )";

    public SqlLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // delete by kelime
    public boolean deleteByKelime(String kelime) {
        String[] whereArgs = new String[] { kelime };
        SQLiteDatabase db = this.getWritableDatabase();
        int affected = db.delete(TABLE_NAME, COLUMN_KELIME + "=?", whereArgs);
        db.close();
        return affected > 0;
    }

    public boolean insertKelime(String kelime, String anlam) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KELIME, kelime);
        values.put(COLUMN_ANLAM, anlam);
        long _id = db.insert(TABLE_NAME, null, values);
        return _id > 0;
    }

    public ArrayList<KelimeModel> getDatas() {
        ArrayList<KelimeModel> mkelimeList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            KelimeModel modul = new KelimeModel();

            modul.setKelime(res.getString(res.getColumnIndex(COLUMN_KELIME)));
            modul.setAnlam(res.getString(res.getColumnIndex(COLUMN_ANLAM)));

            mkelimeList.add(modul);

            res.moveToNext();
        }
        res.close();
        return mkelimeList;
    }

    public boolean isCheckedFav(String kelime) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_KELIME + " LIKE '" + kelime + "'", null);
        boolean isCheckedFav = res.getCount() > 0;
        res.close();
        return isCheckedFav;
    }
}
