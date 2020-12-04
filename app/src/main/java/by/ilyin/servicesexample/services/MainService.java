package by.ilyin.servicesexample.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {

    public static final String TAG = "MainService";
    public static final String STOPWATCH_BROADCAST = "by.ilyin.servicesexample";
    final static int REFRESH_RATE = 1000;
    Intent broadcastIntent = new Intent(STOPWATCH_BROADCAST);

    private final Handler mHandler = new Handler();
    private long startTime;
    private long elapsedTime;

    private boolean isStopped = false;

    public MainService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (isStopped) {
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else {
            startTime = System.currentTimeMillis();
        }
        mHandler.removeCallbacks(startStopwatch);
        mHandler.postDelayed(startStopwatch, 0);
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(startStopwatch);
        isStopped = true;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateTimer (float time){
        long seconds = (long) (time / 1000);
        long minutes = (long) ((time / 1000) / 60);
        long hours = (long) (((time / 1000) / 60) / 60);

        seconds = seconds % 60;
        String secondsString = String.valueOf(seconds);
        if(seconds <10 && seconds >= 0){
            secondsString = "0"+ secondsString;
        }

        minutes = minutes % 60;
        String minutesString = String.valueOf(minutes);
        if(minutes <10 && minutes >= 0){
            minutesString = "0"+ minutesString;
        }

        String hoursString = String.valueOf(hours);
        if(hours <10 && hours >= 0){
            hoursString = "0"+ hoursString;
        }
        broadcastIntent.putExtra("hours", hoursString);
        broadcastIntent.putExtra("minutes", minutesString);
        broadcastIntent.putExtra("seconds", secondsString);
        sendBroadcast(broadcastIntent);
    }

    private final Runnable startStopwatch = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateTimer(elapsedTime);
            mHandler.postDelayed(this, REFRESH_RATE);
        }
    };

}