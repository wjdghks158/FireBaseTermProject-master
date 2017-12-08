package org.techtown.firebasetermproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmHATT {
    private Context context;
    public AlarmHATT(Context context) {
        this.context=context;
    }
    public void Alarm(int Hours, int Min) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BroadcastD.class);

        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Calendar calendar = Calendar.getInstance();
        //알람시간 calendar에 set해주기

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), Hours, Min, 0);

        Log.d("f", calendar.toString());
        //알람 예약
        am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
    }
}



