package com.example.jianxiongrao.demose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jianxiongrao.demose.model.PieData;
import com.example.jianxiongrao.demose.view.PieView;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);

        ArrayList<PieData> datas = new ArrayList<>();
        datas.add(new PieData("Java",30));
        datas.add(new PieData("C++",21));
        datas.add(new PieData("Python",19));
        datas.add(new PieData("Go",18));
        datas.add(new PieData("Php",12));

        PieView pieView = new PieView(this);
        pieView.setStartAngle(0);
        pieView.setData(datas);
        container.addView(pieView);
    }
}
