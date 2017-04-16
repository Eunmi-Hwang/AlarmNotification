package com.example.cncn6.alarmnoti.common.dao;

/**
 * Created by cncn6 on 2017-04-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cncn6.alarmnoti.common.Constants;
import com.example.cncn6.alarmnoti.common.helper.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 알람 관련 DAO
 */
public class AlarmDAO {

    /**
     * debugging
     */
    private final String TAG = getClass().getSimpleName();

    /**
     * context
     */
    private final Context context;

    /**
     * dbHelper
     */
    private final DBHelper dbHelper;

    /**
     * 생성자
     *
     * @param context
     */
    public AlarmDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(this.context);
    }


    /**
     * 복약/측정 알림 데이터 등록
     *
     * @param pMap
     * @return
     */
    public int insertMedicineMeasureAlarmData(Map<String, String> pMap) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE,
                pMap.get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE));
        values.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS,
                pMap.get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS));
        values.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME,
                pMap.get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME));

        long lRow = -1;

        try {

            lRow = db.insert(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        Log.d("TEST", "LL" + lRow + "" + " , " + values.get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE));
        return (int)lRow;
    }

    /**
     * 복약/측정 노티SEQ 조회 (가장 최근의)
     *
     * @return
     */
    public int selectMedicineMeasureData(String alarmFlag) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] strSelectionArgs = {alarmFlag};

        StringBuffer sbsql = new StringBuffer();
        sbsql.append("SELECT ");
        sbsql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(", ");
        sbsql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(", ");
        sbsql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS).append(", ");
        sbsql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME);

        sbsql.append(" FROM ").append(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM);
        sbsql.append(" WHERE ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(" = ?");
        sbsql.append(" ORDER BY ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(" DESC ");
        sbsql.append("LIMIT 1");

        Cursor cursor = null;

        int result = 0;

        try {
            cursor = db.rawQuery(sbsql.toString(), strSelectionArgs);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    result = cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }

            return result;
        }
    }

    /**
     * 복약 데이터 리스트 조회
     *
     * @return
     */
    public ArrayList<Map<String, String>> selectMedicineDataList() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] strSelectionArgs = {Constants.MEDICINE_SYNC_Y};

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME);

        sbSql.append(" FROM ").append(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM);
        sbSql.append(" WHERE ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(" = ?");
        sbSql.append(" ORDER BY ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME).append(" ASC ");

        Cursor cursor = null;

        ArrayList<Map<String, String>> arrList = null;

        try {

            // 쿼리 실행
            cursor = db.rawQuery(sbSql.toString(), strSelectionArgs);

            arrList = new ArrayList<Map<String, String>>();

            Map<String, String> rMap = null;

            while (cursor.moveToNext()) {

                rMap = new HashMap<String, String>();

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ,
                        Integer.toString(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ))));
                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE,
                        Integer.toString(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE))));

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS,
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)));

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME,
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME)));

                arrList.add(rMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {

                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }

            if (db != null) {

                if (db.isOpen()) {
                    db.close();
                }
            }
        }

        return arrList;
    }

    /**
     * 측정 데이터 리스트 조회
     *
     * @return
     */
    public ArrayList<Map<String, String>> selectMeasureDataList() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] strSelectionArgs = {Constants.MEASURE_SYNC_Y};

        StringBuffer sbSql = new StringBuffer();
        sbSql.append("SELECT ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS).append(", ");
        sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME);

        sbSql.append(" FROM ").append(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM);
        sbSql.append(" WHERE ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE).append(" = ?");
        sbSql.append(" ORDER BY ").append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME).append(" ASC ");

        Cursor cursor = null;

        ArrayList<Map<String, String>> arrList = null;

        try {

            // 쿼리 실행
            cursor = db.rawQuery(sbSql.toString(), strSelectionArgs);

            arrList = new ArrayList<Map<String, String>>();

            Map<String, String> rMap = null;

            while (cursor.moveToNext()) {

                rMap = new HashMap<String, String>();

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ,
                        Integer.toString(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ))));
                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE,
                        Integer.toString(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE))));

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS,
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)));

                rMap.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME,
                        cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME)));

                arrList.add(rMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {

                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }

            if (db != null) {

                if (db.isOpen()) {
                    db.close();
                }
            }
        }

        return arrList;
    }

    /**
     * 복약/측정 데이터 삭제
     *
     * @param strTableName
     * @param strSeq
     * @param strSeq2
     */
    public int deleteMedicineMeasureData(String strTableName, String strSeq, int strSeq2) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long lRow = -1;

        try {

            StringBuffer sbSql = new StringBuffer();
            sbSql.append("DELETE FROM ");
            sbSql.append(strTableName);
            sbSql.append(" WHERE ")
                    .append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE)
                    .append(" = ")
                    .append("'" + strSeq + "'");// 복약/측정 플래그
            sbSql.append(" AND ");
            sbSql.append(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ).append(" = ").append(strSeq2);// 노티 SEQ

            db.execSQL(sbSql.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }

        return (int)lRow;
    }

    /**
     * 복약/측정 데이터 전체 삭제
     *
     * @param strTableName
     */
    public int deleteMedicineMeasureWholeData(String strTableName) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long lRow = -1;

        try {

            StringBuffer sbSql = new StringBuffer();
            sbSql.append("DELETE FROM ");
            sbSql.append(strTableName);

            db.execSQL(sbSql.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }

        return (int)lRow;
    }

    /**
     * 복약/측정 알림 토글상태 갱신
     *
     * @param strSeq
     * @param strSeq2
     * @param toggleStatus
     * @return
     */
    public int updateMedicineMeasureToggleState(String strSeq, String strSeq2, String toggleStatus) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // SET
        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS, toggleStatus);

        // WHERE
        String strWhere = Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE + " = ?"
                + " AND "
                + Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ
                + " = ?";
        String[] strWhereArgs = {strSeq, strSeq2};

        long lRow = -1;

        try {
            lRow = db.update(Constants.TABLE_NAME_MEDICINE_MEASURE_ALARM, values, strWhere, strWhereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (db != null) {
                db.close();
            }
        }

        return (int)lRow;
    }
}
