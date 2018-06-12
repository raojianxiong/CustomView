package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Author Jianxiong Rao
 * @Date 2018/6/12
 * @Email jianxiongrao@gmail.com
 */

public class PointerView extends View {
    private Paint mPaint = new Paint();
    private int mWidth, mHeight;
    private float degree = 0;
    private String dir0 = "";
    private String dir2 = "";
    private String dir4 = "";
    private String dir6 = "";

    public PointerView(Context context) {
        this(context, null);
    }

    public PointerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.parseColor("#0c8de9"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(18);
        float radius = 200;
        canvas.drawCircle(0, 0, radius, mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(8);
        canvas.drawLine(0, 0, (float) ((radius-22)* Math.cos((degree-90)*Math.PI/180)),(float)((radius-22) * Math.sin((degree-90)*Math.PI/180)),  mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(50);

        float width0 = mPaint.measureText(dir0);
        canvas.drawText(dir0,-width0/2,-radius-18-10,mPaint);

        float width2 = mPaint.measureText(dir2);
        canvas.drawText(dir2,radius+18,0,mPaint);

        float width4 = mPaint.measureText(dir4);
        canvas.drawText(dir4,-width4/2,radius+18+40,mPaint);

        float width6 = mPaint.measureText(dir6);
        canvas.drawText(dir6,-radius-18-width6,0,mPaint);

    }

    /**
     * 设置方向
     *
     * @param direction
     */
    public void setDegree(int direction,String front,String right,String behind,String left) {
        this.dir0 = front;
        this.dir2 = right;
        this.dir4 = behind;
        this.dir6 = left;
        switch (direction) {
            case 0:
                this.degree = 0;
                break;
            case 1:
                this.degree = 90;
                break;
            case 2:
                this.degree = 180;
                break;
            case 3:
                this.degree = 270;
                break;
            case 4:
                this.degree = 360;
                break;
            default:
                break;
        }
        postInvalidate();
    }
}
