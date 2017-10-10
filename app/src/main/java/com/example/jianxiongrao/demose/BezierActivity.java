package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jianxiongrao.demose.view.BezierView;

public class BezierActivity extends AppCompatActivity {

    private BezierView bezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        bezierView = (BezierView) findViewById(R.id.bezier_view);
    }
    public void goDie(View view){
        switch (view.getId()){
            case R.id.bezier:
                bezierView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
