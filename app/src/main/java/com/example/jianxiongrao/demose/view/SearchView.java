package com.example.jianxiongrao.demose.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Jianxiong Rao on 2017/10/11.
 */

public class SearchView extends View {

    private Paint mPaint;
    private int mViewWidth, mViewHeight;
    //当前的状态
    private State mCurrentState = State.NONE;

    //放大镜与外部圆环
    private Path path_search;
    private Path path_circle;

    //测量Path 并截取部分工具
    private PathMeasure mMessure;

    //默认的动画周期2s
    private int defaultDuration = 2000;

    private ValueAnimator mStartingAnimator, mSearchAnimator, mEndingAnimator;

    //动画数值
    private float mAnimatorValue = 0;
    //动画监听
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    private Handler mAnimatorHandler;
    //判断是否已经搜索结束
    private boolean isOver = false;

    private int count = 0;

    public static enum State {
        NONE, STARTING, SEARCHING, ENDING
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAll();
    }

    private void initAll() {
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnim();
        //进入开始动画
        mCurrentState = State.STARTING;
        mStartingAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mViewWidth = w;
        this.mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSearch(canvas);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    private void initPath() {
        //搜索
        path_search = new Path();
        RectF oval = new RectF(-50, -50, 50, 50);
        path_search.addArc(oval, 45, 359.9f);
        //外部圆环
        path_circle = new Path();
        RectF oval_circle = new RectF(-100, -100, 100, 100);
        path_circle.addArc(oval_circle, 45, 359.9f);

        mMessure = new PathMeasure();
        mMessure.setPath(path_circle, false);
        float[] pos = new float[2];
        mMessure.getPosTan(0, pos, null);
        path_search.lineTo(pos[0], pos[1]);
    }

    private void initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
    }

    private void initHandler() {
        mAnimatorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (mCurrentState) {
                    case STARTING:
                        //开始转搜索动画
                        isOver = false;
                        mCurrentState = State.SEARCHING;
                        mStartingAnimator.removeAllListeners();
                        mSearchAnimator.start();
                        break;
                    case SEARCHING:
                        if (!isOver) {//未结束
                            mSearchAnimator.start();
                            count++;
                            if (count > 2) { //转两圈
                                //结束
                                isOver = true;
                            }
                        } else {
                            mCurrentState = State.ENDING;
                            mEndingAnimator.start();
                        }
                        break;
                    case ENDING:
                        mCurrentState = State.NONE;
                        break;
                }
            }
        };
    }

    private void initAnim() {
        mStartingAnimator = ValueAnimator.ofFloat(0, 1).setDuration(defaultDuration);
        mSearchAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defaultDuration);
        mEndingAnimator = ValueAnimator.ofFloat(1, 0).setDuration(defaultDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);
    }

    private void drawSearch(Canvas canvas){
        mPaint.setColor(Color.WHITE);
        canvas.translate(mViewWidth/2,mViewHeight/2);

        canvas.drawColor(Color.parseColor("#0082D7"));

        switch (mCurrentState){
            case NONE:
                canvas.drawPath(path_search,mPaint);
                break;
            case STARTING:
                mMessure.setPath(path_search,false);
                Path dst = new Path();
                mMessure.getSegment(mMessure.getLength() * mAnimatorValue,mMessure.getLength(),dst,true);
                canvas.drawPath(dst,mPaint);
                break;
            case SEARCHING:
                mMessure.setPath(path_circle,false);
                Path dst2 = new Path();
                float stop = mMessure.getLength() * mAnimatorValue;
                float start = (float) (stop - (0.5 - Math.abs(0.5 - mAnimatorValue))*200.0f);
                mMessure.getSegment(start,stop,dst2,true);
                Log.i("search", "drawSearch: start :"+start +" != stop:"+stop);
                canvas.drawPath(dst2,mPaint);
                break;
            case ENDING:
                mMessure.setPath(path_search,false);
                Path dst3 = new Path();
                mMessure.getSegment(mMessure.getLength()*mAnimatorValue,mMessure.getLength(),dst3,true);
                canvas.drawPath(dst3,mPaint);
                break;
        }
    }

}
