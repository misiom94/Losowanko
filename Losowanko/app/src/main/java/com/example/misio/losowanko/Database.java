package com.example.misio.losowanko;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Misio on 2018-02-17.
 */

public class Database extends SQLiteOpenHelper{

    private final static String DB_NAME = "losowanie.db";
    private final static int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
