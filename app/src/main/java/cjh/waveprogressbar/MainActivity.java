package cjh.waveprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import cjh.waveprogressbarlibrary.WaveProgressbar;

public class MainActivity extends AppCompatActivity {

    private WaveProgressbar waveProgressbar;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveProgressbar = (WaveProgressbar) findViewById(R.id.waveProgressbar);

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                progress++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        waveProgressbar.setProgress(progress);
                    }
                });

                if(progress == 100){
                   progress = 0;
                }
            }
        };

        mTimer.schedule(mTimerTask, 3000, 80);
    }
}
