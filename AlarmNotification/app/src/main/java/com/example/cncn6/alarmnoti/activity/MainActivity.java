package com.example.cncn6.alarmnoti.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cncn6.alarmnoti.R;
import com.example.cncn6.alarmnoti.common.PreferenceUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 알람 전체 플래그 상태 셋팅
        PreferenceUtil.setWholeToggleState(MainActivity.this,
                PreferenceUtil.getWholeToggleState(MainActivity.this));
    }
}
