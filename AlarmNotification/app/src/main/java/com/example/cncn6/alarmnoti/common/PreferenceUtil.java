package com.example.cncn6.alarmnoti.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cncn6 on 2017-04-16.
 */
// SharedPreference 클래스
public class PreferenceUtil {

    private static SharedPreferences sharedPref = null;

    private static SharedPreferences.Editor sharedPrefEditor = null;

    public static void getPreferenceEditor(Context context) {

        if (sharedPref == null) {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        }

        if (sharedPrefEditor == null) {
            sharedPrefEditor = sharedPref.edit();
        }
    }

    /**
     * Preferences 초기화
     */
    public static void removeAllPreferences(Context context) {
        getPreferenceEditor(context);
        sharedPrefEditor.clear();
        sharedPrefEditor.commit();
    }

    /**
     * 복약/측정 알람 전체 플래그 STATE (ON/OFF)
     */
    public static void setWholeToggleState(Context context, boolean isToggleOn) {
        getPreferenceEditor(context);
        sharedPrefEditor.putBoolean(Constants.SHARED_ALARM_WHOLE_TOGGLE, isToggleOn);
        sharedPrefEditor.commit();
    }

    public static boolean getWholeToggleState(Context context) {
        getPreferenceEditor(context);
        return sharedPref.getBoolean(Constants.SHARED_ALARM_WHOLE_TOGGLE, false);
    }
}
