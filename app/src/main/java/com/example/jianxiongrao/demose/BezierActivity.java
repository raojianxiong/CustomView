package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jianxiongrao.demose.view.BezierView;
import com.example.jianxiongrao.demose.view.SearchView;

public class BezierActivity extends AppCompatActivity {

    private BezierView bezierView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        bezierView = (BezierView) findViewById(R.id.bezier_view);
        searchView = (SearchView) findViewById(R.id.search_view);
    }
    public void goDie(View view){
        switch (view.getId()){
            case R.id.bezier:
                bezierView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                break;
            case R.id.search:
                bezierView.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                break;
        }
    }
}
