package by.ilyin.servicesexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import by.ilyin.servicesexample.services.MainService;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    TextView textViewStopwatch = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start_menu_item: {
                startService(new Intent(MainActivity.this, MainService.class));
                return true;
            }
            case R.id.stop_menu_item: {
                stopService(new Intent(MainActivity.this, MainService.class));
                textViewStopwatch.setText("00:00:00");
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStopwatch = (TextView)findViewById(R.id.textViewTimer);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(MainService.STOPWATCH_BROADCAST));
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MainService.class));
        super.onDestroy();
    }

    private void updateUI(Intent intent) {
        if (intent.getExtras() != null) {
            String hours = intent.getStringExtra("hours");
            String minutes = intent.getStringExtra("minutes");
            String seconds = intent.getStringExtra("seconds");
            textViewStopwatch.setText(hours + ":" + minutes + ":" + seconds);
        }
    }
}
