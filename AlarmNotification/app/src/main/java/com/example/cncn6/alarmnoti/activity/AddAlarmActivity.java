package com.example.cncn6.alarmnoti.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.cncn6.alarmnoti.R;
import com.example.cncn6.alarmnoti.common.AlarmUtils;
import com.example.cncn6.alarmnoti.common.Constants;

public class AddAlarmActivity extends Activity {

    private TimePicker mTimePicker;

    private LinearLayout mLLAlarmSave;

    /**
     * 복약/측정 구분하는 플래그
     */
    private String mAlarmFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        Intent intent = getIntent();
        mAlarmFlag = intent.getStringExtra(Constants.COLUMN_NAME_MEDICINE_MEASURE_ALARM_TYPE);

        // mLLAlarmSave = (LinearLayout) findViewById(R.id.ll_alarm_save); // 저장버튼
        mLLAlarmSave.setVisibility(View.VISIBLE);

        mLLAlarmSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlarmUtils.getInstance(AddAlarmActivity.this).setMedicineAlarmData(AddAlarmActivity.this,
                        mTimePicker.getCurrentHour(),
                        mTimePicker.getCurrentMinute(),
                        mAlarmFlag);
                finish();
            }
        });
    }
}
