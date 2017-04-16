package com.example.cncn6.alarmnoti.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cncn6.alarmnoti.R;
import com.example.cncn6.alarmnoti.common.AlarmUtils;
import com.example.cncn6.alarmnoti.common.Constants;
import com.example.cncn6.alarmnoti.common.PreferenceUtil;
import com.example.cncn6.alarmnoti.services.MedicineMeasureAlarmService;

import java.util.ArrayList;
import java.util.Map;

public class AlarmActivity extends Activity implements View.OnClickListener {

    /**
     * service
     */
    private MedicineMeasureAlarmService mAlarmService;

    /**
     * 복약 알림 리스트
     */
    private ArrayList<Map<String, String>> mDBMedicineArrayList;

    /**
     * 측정 알림 리스트
     */
    private ArrayList<Map<String, String>> mDBMeasureArrayList;

    /**
     * 토글 ON/OFF
     */
    private boolean mIsTrue = false;

    //    private SwitchCompat isSwitchOn;

    private Switch isSwitchOn;

    private Context context;

    @Override
    protected void onPause() {
        super.onPause();

        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        mAlarmService = new MedicineMeasureAlarmService(AlarmActivity.this);
        context = AlarmActivity.this;
        LinearLayout llSwitchAlarm = (LinearLayout) findViewById(R.id.ll_sc_alarm);
        llSwitchAlarm.setVisibility(View.VISIBLE);
        //        isSwitchOn = (SwitchCompat)findViewById(R.id.sc_alarm);
        isSwitchOn = (Switch) findViewById(R.id.sc_alarm);
        //                 알림 플래그 ON/OFF 리스너
        isSwitchOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecking) {
                PreferenceUtil.setWholeToggleState(AlarmActivity.this, isChecking);
                setAlarmFlagState(isChecking);
            }
        });

        RelativeLayout medicineLayout = (RelativeLayout) findViewById(R.id.rl_alarm_medicine);
        medicineLayout.setOnClickListener(this);

        RelativeLayout messureLayout = (RelativeLayout) findViewById(R.id.rv_alarm_messure);
        messureLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id) {
            // 복약알림으로 이동
            case R.id.rl_alarm_medicine:
                intent = new Intent(AlarmActivity.this, SettingAlarmActivity.class);
                intent.putExtra(Constants.ALARM_FLAG,
                        Constants.MEDICINE_SYNC_Y);
                startActivity(intent);
                break;

            // 측정알림으로 이동
            case R.id.rv_alarm_messure:
                intent = new Intent(AlarmActivity.this, SettingAlarmActivity.class);
                intent.putExtra(Constants.ALARM_FLAG,
                        Constants.MEASURE_SYNC_Y);
                startActivity(intent);
                break;
        }
    }

}

    /**
     * 알람 플래그 셋팅하는 메소드
     */
    private void setAlarmFlagState(boolean isChecking) {

        if (isChecking) {
            // 복약 알림 플래그 ON
            if (mDBMedicineArrayList != null && mDBMedicineArrayList.size() > 0) {
                for (int i = 0; i < mDBMedicineArrayList.size(); i++) {

                    if (mDBMedicineArrayList.get(i)
                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)
                            .equals(Constants.ALARM_TOGGLE_ON)) {
                        mIsTrue = true;

                    } else {
                        mIsTrue = false;
                    }

                    if (mIsTrue) {
                        AlarmUtils.getInstance(AlarmActivity.this).setAlarmManager(AlarmActivity.this,
                                mDBMedicineArrayList.get(i)
                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME),
                                Constants.MEDICINE_SYNC_Y,
                                mDBMedicineArrayList.get(i)
                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ),
                                mIsTrue);
                    }
                }
            }

            // 측정 알림 플래그 ON
            if (mDBMeasureArrayList != null && mDBMeasureArrayList.size() > 0) {
                for (int i = 0; i < mDBMeasureArrayList.size(); i++) {

                    if (mDBMeasureArrayList.get(i)
                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)
                            .equals(Constants.ALARM_TOGGLE_ON)) {
                        mIsTrue = true;

                    } else {
                        mIsTrue = false;
                    }

                    if (mIsTrue) {
                        AlarmUtils.getInstance(AlarmActivity.this).setAlarmManager(AlarmActivity.this,
                                mDBMeasureArrayList.get(i)
                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME),
                                Constants.MEASURE_SYNC_Y,
                                mDBMeasureArrayList.get(i)
                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ),
                                mIsTrue);
                    }
                }
            }

        } else {
            // 복약 알림 플래그 OFF
            if (mDBMedicineArrayList != null && mDBMedicineArrayList.size() > 0) {
                for (int i = 0; i < mDBMedicineArrayList.size(); i++) {

                    AlarmUtils.getInstance(AlarmActivity.this)
                            .setCancelAlarm(AlarmActivity.this,
                                    Integer.parseInt(mDBMedicineArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                }
            }

            // 측정 알림 플래그 OFF
            if (mDBMeasureArrayList != null && mDBMeasureArrayList.size() > 0) {
                for (int i = 0; i < mDBMeasureArrayList.size(); i++) {

                    AlarmUtils.getInstance(AlarmActivity.this)
                            .setCancelAlarm(AlarmActivity.this,
                                    Integer.parseInt(mDBMeasureArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBMedicineArrayList = new ArrayList<>();
        mDBMeasureArrayList = new ArrayList<>();

        // DB에 저장된 복약 값 가져오기
        if (mAlarmService.searchMedicineData() != null) {
            mDBMedicineArrayList.addAll(mAlarmService.searchMedicineData());
        }

        if (mAlarmService.searchMeasureData() != null) {
            mDBMeasureArrayList.addAll(mAlarmService.searchMeasureData());
        }

        // 전체 (Switch)플래그 디폴트값 받아오기 
        if (PreferenceUtil.getWholeToggleState(AlarmActivity.this)) {
            isSwitchOn.setChecked(true);
            PreferenceUtil.setWholeToggleState(AlarmActivity.this, true);

            if (mDBMedicineArrayList != null && mDBMedicineArrayList.size() > 0) {
                for (int i = 0; i < mDBMedicineArrayList.size(); i++) {
                    if (mDBMedicineArrayList.get(i)
                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)
                            .equals(Constants.ALARM_TOGGLE_ON)) {

                        PendingIntent pendingIntent =
                                AlarmUtils.getInstance(AlarmActivity.this)
                                        .setCheckAlarm(AlarmActivity.this,
                                                Integer.parseInt(mDBMedicineArrayList.get(i)
                                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                        if (pendingIntent == null) { // 이미 설정된 알람이 없는 경우
                            AlarmUtils.getInstance(AlarmActivity.this).setAlarmManager(AlarmActivity.this,
                                    mDBMedicineArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME),
                                    Constants.MEASURE_SYNC_Y,
                                    mDBMedicineArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ),
                                    true);
                        }
                    }
                }
            }

            if (mDBMeasureArrayList != null && mDBMeasureArrayList.size() > 0) {
                for (int i = 0; i < mDBMeasureArrayList.size(); i++) {
                    if (mDBMeasureArrayList.get(i)
                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_STATUS)
                            .equals(Constants.ALARM_TOGGLE_ON)) {

                        PendingIntent pendingIntent =
                                AlarmUtils.getInstance(AlarmActivity.this)
                                        .setCheckAlarm(AlarmActivity.this,
                                                Integer.parseInt(mDBMeasureArrayList.get(i)
                                                        .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                        if (pendingIntent == null) { // 이미 설정된 알람이 없는 경우
                            AlarmUtils.getInstance(AlarmActivity.this).setAlarmManager(AlarmActivity.this,
                                    mDBMeasureArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_NOTIFY_TIME),
                                    Constants.MEASURE_SYNC_Y,
                                    mDBMeasureArrayList.get(i)
                                            .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ),
                                    true);
                        }
                    }
                }
            }

        } else {
            isSwitchOn.setChecked(false);
            PreferenceUtil.setWholeToggleState(AlarmActivity.this, false);
            if (mDBMedicineArrayList != null && mDBMedicineArrayList.size() > 0) {
                for (int i = 0; i < mDBMedicineArrayList.size(); i++) {
                    PendingIntent pendingIntent =
                            AlarmUtils.getInstance(AlarmActivity.this)
                                    .setCheckAlarm(AlarmActivity.this,
                                            Integer.parseInt(mDBMedicineArrayList.get(i)
                                                    .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));

                    if (pendingIntent != null) { // 이미 설정된 알람이 있는 경우
                        AlarmUtils.getInstance(AlarmActivity.this)
                                .setCancelAlarm(AlarmActivity.this,
                                        Integer.parseInt(mDBMedicineArrayList.get(i)
                                                .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                    }
                }
            }

            if (mDBMeasureArrayList != null && mDBMeasureArrayList.size() > 0) {
                for (int i = 0; i < mDBMeasureArrayList.size(); i++) {
                    PendingIntent pendingIntent =
                            AlarmUtils.getInstance(AlarmActivity.this)
                                    .setCheckAlarm(AlarmActivity.this,
                                            Integer.parseInt(mDBMeasureArrayList.get(i)
                                                    .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));

                    if (pendingIntent != null) { // 이미 설정된 알람이 있는 경우
                        AlarmUtils.getInstance(AlarmActivity.this)
                                .setCancelAlarm(AlarmActivity.this,
                                        Integer.parseInt(mDBMeasureArrayList.get(i)
                                                .get(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_SEQ)));
                    }
                }
            }
        }
    }
}
