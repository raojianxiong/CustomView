package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jianxiongrao.demose.view.BezierView;
import com.example.jianxiongrao.demose.view.RemoteControlMenu;
import com.example.jianxiongrao.demose.view.Rotate3dAnimation;
import com.example.jianxiongrao.demose.view.SearchView;

public class BezierActivity extends AppCompatActivity {

    private BezierView bezierView;
    private SearchView searchView;
    private ImageView rotate_view;
    private RemoteControlMenu remote_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        bezierView = (BezierView) findViewById(R.id.bezier_view);
        searchView = (SearchView) findViewById(R.id.search_view);
        rotate_view = (ImageView) findViewById(R.id.rotate_view);
        remote_view = (RemoteControlMenu) findViewById(R.id.remote_view);

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

        remote_view.setListener(new RemoteControlMenu.MenuListener() {
            @Override
            public void onCenterCliched() {
                Toast.makeText(BezierActivity.this, "中间", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpCliched() {
                Toast.makeText(BezierActivity.this, "上面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCliched() {
                Toast.makeText(BezierActivity.this, "右边", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownCliched() {
                Toast.makeText(BezierActivity.this, "下面", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftCliched() {
                Toast.makeText(BezierActivity.this, "左面", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goDie(View view){
        switch (view.getId()){
            case R.id.bezier:
                bezierView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.GONE);
                remote_view.setVisibility(View.GONE);
                break;
            case R.id.search:
                bezierView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.GONE);
                remote_view.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                break;
            case R.id.roate:
                bezierView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                remote_view.setVisibility(View.GONE);
                rotate_view.setVisibility(View.VISIBLE);
                break;
            case R.id.remote:
                bezierView.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                rotate_view.setVisibility(View.GONE);
                remote_view.setVisibility(View.VISIBLE);
                break;
        }
    }
}
