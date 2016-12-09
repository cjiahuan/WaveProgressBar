package cjh.waveprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cjh.waveprogressbarlibrary.WaveProgressbar;

public class MainActivity extends AppCompatActivity {

    private WaveProgressbar waveProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveProgressbar = (WaveProgressbar) findViewById(R.id.waveProgressbar);

    }
}
