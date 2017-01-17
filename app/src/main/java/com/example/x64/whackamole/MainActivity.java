package com.example.x64.whackamole;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton[] holes;
    TextView score_txt;
    ProgressBar progressBar;
    int countDown = 15000;
    int time_left = countDown;
    int score = 0;
    Random r = new Random();
    MediaPlayer bgsong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.createHoles();
        score_txt = (TextView) findViewById(R.id.score_txt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(countDown);
        bgsong = MediaPlayer.create(this, R.raw.bgsong);
        bgsong.setLooping(true);
        bgsong.start();
        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() { // Timer
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        score_txt.setText(score + "");
                        time_left -= 100;
                        progressBar.setProgress(time_left);
                        if (time_left <= 0) {
                            timer.cancel();
                            timer.purge();
                            return;
                        }
                    }
                });

            }
        }, 0, 100);

        timer.scheduleAtFixedRate(new TimerTask() { //gameloop
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = r.nextInt(9);
                        switch (r.nextInt(3)) {
                            case 0:
                                holes[index].setTag(R.drawable.mole1);
                                holes[index].setImageResource(R.drawable.mole1);
                                break;
                            case 1:
                                holes[index].setTag(R.drawable.mole2);
                                holes[index].setImageResource(R.drawable.mole2);
                                break;
                            case 2:
                                holes[index].setTag(R.drawable.mole3);
                                holes[index].setImageResource(R.drawable.mole3);
                                break;
                        }
                    }
                });
            }
        }, 0, 300);
    }

    private void createHoles() {

        ImageButton hole1 = (ImageButton) findViewById(R.id.hole1);
        ImageButton hole2 = (ImageButton) findViewById(R.id.hole2);
        ImageButton hole3 = (ImageButton) findViewById(R.id.hole3);
        ImageButton hole4 = (ImageButton) findViewById(R.id.hole4);
        ImageButton hole5 = (ImageButton) findViewById(R.id.hole5);
        ImageButton hole6 = (ImageButton) findViewById(R.id.hole6);
        ImageButton hole7 = (ImageButton) findViewById(R.id.hole7);
        ImageButton hole8 = (ImageButton) findViewById(R.id.hole8);
        ImageButton hole9 = (ImageButton) findViewById(R.id.hole9);
        holes = new ImageButton[]{hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8, hole9};
        for (ImageButton hole : holes) {
            hole.setOnClickListener(this);
            hole.setTag(R.drawable.hole2);
            hole.setImageResource(R.drawable.hole2);
        }

    }

    @Override
    public void onClick(View v) {
        ImageButton source = (ImageButton) v;
        Integer tag = (Integer) v.getTag();
        if (tag == R.drawable.mole1) {
            editTime(1000);
            score += 3;
        } else if (tag == R.drawable.mole2) {
            score += 8;
        } else if (tag == R.drawable.mole3) {
            score -= 5;
            editTime(-5000);
        } else if (tag == R.drawable.hole2) {
            score -= 2;
        }

        source.setTag(R.drawable.hole2);
        source.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.hole2));
    }

    private void editTime(int amount) {
        time_left += amount;
        if (time_left > countDown) {
            time_left = countDown;
        }
    }

}
