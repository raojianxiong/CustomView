package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jianxiong Rao on 2017/10/10.
 */

public class RadarView extends View {
    private int count = 6;//数据个数
    private float angle = (float) (Math.PI * 2 / count);
    private float radius;//网格最大半径
    private int centerX;
    private int centerY;
    private String[] titles = {"LOL", "DOTA", "CS", "CF", "Panana", "CLP"};
    private double[] data = {100, 60, 60, 60, 100, 50, 10, 20};
    private float maxValue = 100;//数值最大值
    private Paint mainPaint, valuePaint, textPaint;//雷达，数据，文本

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(h, w) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制正多边形
        drawPolygon(canvas);

        //绘制中心点到各个点的line
        drawLines(canvas);

        //绘制文本
        drawText(canvas);

        //绘制覆盖区域
        drawRegion(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);//每个蛛丝之间的距离
        for (int i = 1; i < count; i++) {
            float cuR = r * i;
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + cuR, centerY);
                } else {
                    path.lineTo((float) (centerX + cuR * Math.cos(angle * j)), (float) (centerY + cuR * Math.sin(angle * j)));
                }
            }
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }

    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            path.lineTo((float) (centerX + radius * Math.cos(angle * i)), (float) (centerY + radius * Math.sin(angle * i)));
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 为了防止雷达区域和文本重叠，需要在第二和第三象限将文本绘制点左移
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if (angle * i >= 0 && angle * i <= Math.PI / 2) {
                canvas.drawText(titles[i], x, y, textPaint);
            } else if (angle * i > Math.PI / 2 && angle * i <= Math.PI) {
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else if (angle * i >= Math.PI && angle * i < Math.PI * 3 / 2) {
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i], x - dis, y, textPaint);
            } else if (angle * i >= Math.PI * 3 / 2 && angle * i <= Math.PI * 2) {
                canvas.drawText(titles[i], x, y, textPaint);
            }
        }
    }

    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double precent = data[i]/maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * precent);
            float y = (float)(centerY + radius * Math.sin(angle * i) * precent);
            if (i == 0) {
                path.moveTo(x,centerY);
            }else{
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,6,valuePaint);
        }
        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);//先绘制边
        valuePaint.setAlpha(127);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setStyle(Paint.Style.STROKE);
        mainPaint.setColor(Color.GRAY);
        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);
        textPaint = new Paint();
        textPaint.setColor(Color.GRAY);
    }
    //设置标题
    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    //设置数值
    public void setData(double[] data) {
        this.data = data;
    }

    //设置最大数值
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    //设置蜘蛛网颜色
    public void setMainPaintColor(int color){
        mainPaint.setColor(color);
    }

    //设置标题颜色
    public void setTextPaintColor(int color){
        textPaint.setColor(color);
    }

    //设置覆盖局域颜色
    public void setValuePaintColor(int color){
        valuePaint.setColor(color);
    }
}
