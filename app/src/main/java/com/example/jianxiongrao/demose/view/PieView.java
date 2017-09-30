package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.jianxiongrao.demose.model.PieData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jianxiong Rao on 2017/9/27.
 */

public class PieView extends View {
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private int mWidth, mHeight;
    private float mStartAngle = 0;
    private ArrayList<PieData> mData;
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null) {
            return;
        }
        float currentAngle = this.mStartAngle;
        //中心点
        canvas.translate(mWidth / 2, mHeight / 2);
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
        RectF rectF = new RectF(-r, -r, r, r);
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF, currentAngle, pieData.getAngle(), true, mPaint);

            //弧中点坐标
            mPaint.setColor(Color.BLUE);
            mPaint.setTextSize(40);
            float arcWidth = (float)( r * Math.cos((currentAngle+pieData.getAngle()/2) / 360 * Math.PI * 2) * 0.8);
            float arcHeight = (float)(r * Math.sin((currentAngle+pieData.getAngle()/2) / 360 * Math.PI * 2) * 0.8);
            canvas.drawText(pieData.getName(),arcWidth,arcHeight,mPaint);
            canvas.drawText(String.format("%.1f",pieData.getPercentage()*100)+"%",arcWidth*0.6f,arcHeight*0.6f,mPaint);

            currentAngle += pieData.getAngle();
        }
    }

    //设置起始角度
    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> data) {
        this.mData = data;
        initData(mData);
        invalidate();
    }

    private void initData(ArrayList<PieData> datas) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        float sumValue = 0;
        //设置百分比
        for (int i = 0; i < datas.size(); i++) {
            sumValue += datas.get(i).getValue();
            //为图方便，颜色直接在这设置
            datas.get(i).setColor(mColors[i % mColors.length]);
        }
       float sumAngle = 0;
        for (int i = 0; i < datas.size(); i++) {
            float precent = datas.get(i).getValue() / sumValue;
            PieData pieData = datas.get(i);
            pieData.setPercentage(precent);
            float angle = precent * 360;
            pieData.setAngle(angle);
            sumAngle += angle;
        }
    }
}
