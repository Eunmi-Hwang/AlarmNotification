package com.example.cncn6.alarmnoti.common;

/**
 * Created by cncn6 on 2017-04-16.
 */

public class Constants {
    // 알람구분 플래그
    public static final String ALARM_FLAG = "alarmFlag";

    // 알림 시간
    public static final String ALARM_TIME = "time";

    // 알림 SEQ
    public static final String ALARM_SEQ = "seq";

    // 알림 HOUR
    public static final String ALARM_HOUR = "hour";

    // 알림 MINUTE
    public static final String ALARM_MINUTE = "minute";

    //////////////////////////////////////

    // 복약 플래그
    public static final String MEDICINE_SYNC_Y = "MEDICINE";

    // 측정 플래그
    public static final String MEASURE_SYNC_Y = "MEASURE";

    // 토글 ON
    public static final String ALARM_TOGGLE_ON = "ON";

    // 토글 OFF
    public static final String ALARM_TOGGLE_OFF = "OFF";


    ////////////////////////////////////////////////

    /**
     * SharedPreferences
     */
    // 복약/측정 알람 전체 플래그
    public static final String SHARED_ALARM_WHOLE_TOGGLE = "isOn";

    /**
     * DB
     */
    //Alarm DB
    public static final String NAME = "alarm.db";

    //Alarm DB version
    public static final int VERSION = 1;


    /**************************
     * Medicine/Measure Alarm
     ***************************/

    // 복약/측정 알림 테이블 이름
    public static final String TABLE_NAME_MEDICINE_MEASURE_ALARM = "BPD_MEDICINE_MEASURE_ALARM";

    // 복약/측정 알림 TEMP 테이블 이름
    public static final String TABLE_NAME_MEDICINE_MEASURE_ALARM_TEMP = "BPD_MEDICINE_MEASURE_ALARM_TEMP";

    // 복약/측정 알림 테이블 : 알림 아이디
    public static final String COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ = "SEQ";

    // 복약/측정 알림 테이블 : 복약/측정 구분 플래그
    public static final String COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE = "TYPE";

    // 복약/측정 알림 테이블 : 토글버튼 ON/OFF
    public static final String COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS = "STATUS";

    // 복약/측정 알림 테이블 : 알림 시간
    public static final String COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME = "NOTIFY_TIME";
}
