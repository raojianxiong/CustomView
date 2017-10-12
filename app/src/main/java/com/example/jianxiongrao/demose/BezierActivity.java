package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jianxiongrao.demose.view.BezierView;
import com.example.jianxiongrao.demose.view.Rotate3dAnimation;
import com.example.jianxiongrao.demose.view.SearchView;

public class BezierActivity extends AppCompatActivity {

    private BezierView bezierView;
    private SearchView searchView;
    private ImageView rotate_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        bezierView = (BezierView) findViewById(R.id.bezier_view);
        searchView = (SearchView) findViewById(R.id.search_view);
        rotate_view = (ImageView) findViewById(R.id.rotate_view);

        rotate_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //计算中心点
                float centerX = v.getWidth() /2.0f;
                float centerY = v.getHeight() / 2.0f;
                Rotate3dAnimation rotation = new Rotate3dAnimation(BezierActivity.this, 0, 180, centerX, centerY, 0f, true);
                rotation.setDuration(3000);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new LinearInterpolator());
                v.startAnimation(rotation);
            }
        });
    }
    public void goDie(View view){
        switch (view.getId()){
            case R.id.bezier:
                bezierView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.GONE);
                break;
            case R.id.search:
                bezierView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.roate:
                bezierView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.VISIBLE);
                break;
        }
    }
}
