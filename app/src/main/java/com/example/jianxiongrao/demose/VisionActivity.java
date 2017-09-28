package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jianxiongrao.demose.view.VsisionView;
import com.example.jianxiongrao.demose.view.WatchTable;

public class VisionActivity extends AppCompatActivity {

    private VsisionView visionView;
    private WatchTable watchTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        visionView = (VsisionView) findViewById(R.id.vision_view);
        watchTableView = (WatchTable) findViewById(R.id.watch_view);
    }

    public void goTo(View view) {
        switch (view.getId()){
            case R.id.vision:
                visionView.setVisibility(View.VISIBLE);
                watchTableView.setVisibility(View.GONE);
                break;
            case R.id.watch:
                visionView.setVisibility(View.GONE);
                watchTableView.setVisibility(View.VISIBLE);
                break;
            case R.id.select:
                break;
        }
    }
}
