package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jianxiong Rao on 2017/9/28.
 */

public class WatchTable extends View {
    private Paint mPaint = new Paint();
    private int mWidth,mHeight;
    public WatchTable(Context context) {
        this(context,null);
    }

    public WatchTable(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WatchTable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(30);
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

        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,400,mPaint);
        canvas.drawCircle(0,0,380,mPaint);

        canvas.drawLine(0,0,0,-350,mPaint);
        canvas.drawLine(0,0,300,0,mPaint);
        int m = 3;
        canvas.drawText("12",0,-360,mPaint);
        for(int i = 0;i<=360;i+=30){
            if(m == 12){
                m = 0;
            }
            canvas.drawLine(380,0,400,0,mPaint);
            if(m %3==0 && m !=9){
                canvas.drawText((m+3)+"",0,370,mPaint);
            }
            canvas.rotate(30);
            m++;
        }
    }
}
