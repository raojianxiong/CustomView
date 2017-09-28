package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jianxiong Rao on 2017/9/28.
 */

public class VsisionView extends View {
    private Paint mPaint;
    private int mWidth,mHeight;
    public VsisionView(Context context) {
        this(context,null);
    }

    public VsisionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VsisionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
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
        canvas.translate(mWidth/2,mHeight/2);
        RectF rectF = new RectF(-400,-400,400,400);
        for(int i=0;i<20;i++){
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rectF,mPaint);
        }
    }
}
