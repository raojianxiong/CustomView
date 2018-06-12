package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.jianxiongrao.demose.view.CheckView;
import com.example.jianxiongrao.demose.view.PointerView;
import com.example.jianxiongrao.demose.view.RadarView;
import com.example.jianxiongrao.demose.view.VsisionView;
import com.example.jianxiongrao.demose.view.WatchTable;

public class VisionActivity extends AppCompatActivity {

    private VsisionView visionView;
    private WatchTable watchTableView;
    private CheckView checkView;
    private RadarView radarView;
    private PointerView pointerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        visionView = (VsisionView) findViewById(R.id.vision_view);
        watchTableView = (WatchTable) findViewById(R.id.watch_view);
        checkView = (CheckView) findViewById(R.id.check_view);
        radarView = (RadarView) findViewById(R.id.radar_view);
        pointerView = (PointerView) findViewById(R.id.pointer_view);
    }

    public void goTo(View view) {
        switch (view.getId()) {
            case R.id.vision:
                pointerView.setVisibility(View.GONE);
                visionView.setVisibility(View.VISIBLE);
                watchTableView.setVisibility(View.GONE);
                checkView.setVisibility(View.GONE);
                radarView.setVisibility(View.GONE);
                break;
            case R.id.watch:
                pointerView.setVisibility(View.GONE);
                visionView.setVisibility(View.GONE);
                watchTableView.setVisibility(View.VISIBLE);
                checkView.setVisibility(View.GONE);
                radarView.setVisibility(View.GONE);
                break;
            case R.id.select:
                pointerView.setVisibility(View.GONE);
                visionView.setVisibility(View.GONE);
                watchTableView.setVisibility(View.GONE);
                radarView.setVisibility(View.GONE);
                checkView.setVisibility(View.VISIBLE);
                break;
            case R.id.radar:
                pointerView.setVisibility(View.GONE);
                radarView.setVisibility(View.VISIBLE);
                visionView.setVisibility(View.GONE);
                watchTableView.setVisibility(View.GONE);
                checkView.setVisibility(View.GONE);
                break;
            case R.id.pointer:
                pointerView.setVisibility(View.VISIBLE);
                radarView.setVisibility(View.GONE);
                visionView.setVisibility(View.GONE);
                watchTableView.setVisibility(View.GONE);
                checkView.setVisibility(View.GONE);
                break;
        }
    }

    private boolean check = false;

    public void anim(View view) {
        check = !check;
        if (check) {
            checkView.setCheck();
        } else {
            checkView.setUnCheck();
        }
    }
}
