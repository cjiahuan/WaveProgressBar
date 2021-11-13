package cjh.waveprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

import cjh.WaveProgressBarlibrary.WaveProgressBar;


public class MainActivity extends AppCompatActivity {

    private WaveProgressBar waveProgressbar;

    private SeekBar changeWave, changeProgress, changeText, changeMargin;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveProgressbar = (WaveProgressBar) findViewById(R.id.waveProgressbar);

        changeWave = (SeekBar) findViewById(R.id.changeWave);
        changeProgress = (SeekBar) findViewById(R.id.changeProgress);
        changeText = (SeekBar) findViewById(R.id.changeText);
        changeMargin = (SeekBar) findViewById(R.id.changeMargin);


        changeWave.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waveProgressbar.setDwave(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        changeProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waveProgressbar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        changeText.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waveProgressbar.setTextSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        changeMargin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                waveProgressbar.setTextMarginTop(4 * i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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

                if (progress == 33) {
                    progress = 0;
                }
            }
        };


    }


    public void start(View view) {
        mTimer.schedule(mTimerTask, 0, 80);
    }

    public void stop(View view) {
        mTimer.cancel();
    }
}
