package com.example.cncn6.alarmnoti.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.cncn6.alarmnoti.common.Constants;
import com.example.cncn6.alarmnoti.activity.MainActivity;
import com.example.cncn6.alarmnoti.R;
/**
 * Created by hwangem on 2017-01-12.
 */

/**
 * 복약/측정 알람 노티 리시버
 */
public class SettingAlarmReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // 복약/측정 구분 플래그
        String alarmFlag = intent.getStringExtra(Constants.ALARM_FLAG);
        int notifySEQ = intent.getIntExtra(Constants.ALARM_SEQ, 0);
        long hour = intent.getLongExtra(Constants.ALARM_HOUR, 0);
        long minute = intent.getLongExtra(Constants.ALARM_MINUTE, 0);
        StringBuilder builder = new StringBuilder();

        String time = null;

        // 알림시간 형태 : ex) 오전 9시 00분 -> "0900"
        if (hour < 10 && minute < 10) {
            time = builder.append("0").append(hour).append("0").append(minute).toString();

        } else if (hour < 10 && minute >= 10) {
            time = builder.append("0").append(hour).append(minute).toString();

        } else if (hour >= 10 && minute < 10) {
            time = builder.append(hour).append("0").append(minute).toString();

        } else if (hour >= 10 && minute >= 10) {
            time = builder.append(hour).append(minute).toString();
        }

        Intent alarmIntent = new Intent(context, MainActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtra(Constants.ALARM_SEQ, notifySEQ);
        alarmIntent.putExtra(Constants.ALARM_FLAG, alarmFlag);
        alarmIntent.putExtra(Constants.ALARM_TIME, time);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                notifySEQ,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int bgColor = context.getResources().getColor(android.R.color.white);

        Notification foregroundNote;
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent);

        // 복약 노티일 경우
        if (alarmFlag.equals(Constants.MEDICINE_SYNC_Y)) {
            foregroundNote = notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setLargeIcon((BitmapFactory.decodeResource(context.getResources(),
                            android.R.drawable.btn_default)))
                    .setSmallIcon(android.R.drawable.alert_dark_frame)
                    .setColor(bgColor)
                    .setContentTitle(context.getText(R.string.medicine_alarm_title))
                    .setContentText(context.getText(R.string.medicine_alarm_content))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            // 측정 노티일 경우
        } else {
            foregroundNote = notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                            android.R.drawable.btn_default))
                    .setSmallIcon(android.R.drawable.alert_dark_frame)
                    .setColor(bgColor)
                    .setContentTitle(context.getText(R.string.medicine_alarm_title))
                    .setContentText(context.getText(R.string.medicine_alarm_content))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
        }

        foregroundNote.flags = NotificationCompat.FLAG_AUTO_CANCEL;

        NotificationManager mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotifyManager.notify(notifySEQ, foregroundNote);
    }
}
