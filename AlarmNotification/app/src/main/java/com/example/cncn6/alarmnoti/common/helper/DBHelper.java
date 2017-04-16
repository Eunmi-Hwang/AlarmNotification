package com.example.cncn6.alarmnoti.common.helper;

/**
 * Created by cncn6 on 2017-04-16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cncn6.alarmnoti.common.Constants;

/**
 * DBHelper
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * debugging
     */
    private final String TAG = getClass().getSimpleName();

    /**
     *
     */
    protected SQLiteDatabase db;

    /**
     * 생성자
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, Constants.NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    /**
     * 내용 입력
     *
     * @param db
     */
    private void createTables(SQLiteDatabase db) {

        try {
            db.beginTransaction();

            StringBuffer sql = new StringBuffer();

            // Medicine/Measure Alarm
            sql = new StringBuffer();
            sql.append("CREATE TABLE ").append(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM).append(" (");
            sql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(" TEXT NOT NULL, ");
            sql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS).append(" TEXT NOT NULL, ");
            sql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME).append(" TEXT NOT NULL)");

            db.execSQL(sql.toString());

            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            db.endTransaction();
            e.printStackTrace();
        }
    }

    // TODO : DB 마이그레이션
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * DROP Table
     *
     * @param db
     */
    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM);

        createTables(db);
    }
}
