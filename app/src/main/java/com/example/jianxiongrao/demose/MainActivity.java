package com.example.jianxiongrao.demose;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private Button play, pause;
    private SeekBar sb;
    private MediaPlayer player;
    Handler handler = new Handler();
    int duration;

    private TextView mLabelText;
    private Fade mFade;
    private ViewGroup mRootView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Transition fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setExitTransition(fade);
            getWindow().setEnterTransition(fade);
        }

        setContentView(R.layout.activity_main);

        initViews();

        initEvents();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void click(View view){
        ActivityOptions option = ActivityOptions.makeSceneTransitionAnimation(this, view, "shareName");
        switch (view.getId()){
            case R.id.google:
                startActivity(new Intent(this,PieChartActivity.class),option.toBundle());
                break;
            case R.id.facebook:
                startActivity(new Intent(this,VisionActivity.class),option.toBundle());
                break;
            case R.id.twitter:
                break;
            case R.id.linkedin:
                break;
        }
    }
    private void initViews() {
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        sb = (SeekBar) findViewById(R.id.sb);
    }

    private void initEvents() {
        player = MediaPlayer.create(this,R.raw.stranger);
        duration = player.getDuration();
        sb.setMax(duration);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        sb.setOnSeekBarChangeListener(this);

        mLabelText = new TextView(this);


    }
    Runnable start = new Runnable() {
        @Override
        public void run() {
            player.start();
            handler.post(update);
        }
    };
    Runnable update = new Runnable() {
        @Override
        public void run() {
            sb.setProgress(player.getCurrentPosition());
            handler.postDelayed(update,1000);
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                handler.post(start);
                break;
            case R.id.pause:
                player.pause();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
            player.seekTo(seekBar.getProgress());
    }
}
