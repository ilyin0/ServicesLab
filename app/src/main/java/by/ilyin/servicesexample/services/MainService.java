package by.ilyin.servicesexample.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

public class MainService extends Service {

    public final static String TAG = "MAIN_SERVICE";

    public MainService() {
    }

    public class MainServiceBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MainServiceBinder();
    }


    public void setText(TextView textView, String text) {
        textView.setText(text);
    }

    public void setText(TextView textView, int resId) {
        textView.setText(resId);
    }

}