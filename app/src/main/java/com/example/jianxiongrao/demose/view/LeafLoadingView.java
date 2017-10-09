package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.jianxiongrao.demose.R;
import com.example.jianxiongrao.demose.model.Leaf;
import com.example.jianxiongrao.demose.model.StartType;
import com.example.jianxiongrao.demose.utils.Utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jianxiong Rao on 2017/9/30.
 */

public class LeafLoadingView extends View {
    private static final String TAG = "LeafLoadingView";
    //淡白色
    private static final int WHITE_COLOR = 0xfffde399;
    //橙色
    private static final int ORANGE_COLOR = 0xffffa800;
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;

    public static final int MAX_LEAFS = 8;
    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 叶子飘动一个周期所花的时间
    public static final long LEAF_FLOAT_TIME = 3000;
    // 叶子旋转一周需要的时间
    public static final long LEAF_ROTATE_TIME = 2000;

    // 叶子飘动一个周期所花的时间
    private long mLeafFloatTime = LEAF_FLOAT_TIME;
    // 叶子旋转一周需要的时间
    private long mLeafRotateTime = LEAF_ROTATE_TIME;

    //用于控制绘制的进度条距离左/上/下的距离
    private static final int LEFT_MARGIN = 9;
    //用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;
    private int mLeftMargin, mRightMargin;
    //中等振幅大小
    private int mMiddleAmplitude = MIDDLE_AMPLITUDE;
    //振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;

    private Resources mResources;
    private Bitmap mLeafBitmap;
    private int mLeafWidth, mLeafHeight;

    private Bitmap mOuterBitmap;
    private Rect mOuterSrcRect, mOuterDestRect;
    private int mOuterWidth, mOuterHeight;

    private int mTotalWidth, mTotalHeight;

    private Paint mBitmapPaint, mWhitePaint, mOrangePaint;
    private RectF mWhiteRectf, mOrangeRectf, mArcRectf;
    //当前进度
    private int mProgress;
    //所绘制的进度条部分的宽度
    private int mProgressWidth;
    //当前所在的弧形绘制的进度条位置
    private int mCurrentProgressPosition;
    //弧形的半径
    private int mArcRadius;

    //arc 右上角的x坐标,即矩形x坐标的起始点
    private int mArcRightLocation;
    //用于产生的叶子信息
    private LeafFactory mLeafFactory;
    //产生出的叶子信息
    private List<Leaf> mLeafInfos;
    //用于控制随机增加的时间不抱团
    private int mAddTime;

    public LeafLoadingView(Context context) {
        this(context, null);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mProgressWidth = mTotalWidth - mLeftMargin - mRightMargin;
        mArcRadius = (mTotalHeight - 2 * mLeftMargin) / 2;

        mOuterSrcRect = new Rect(0, 0, mOuterWidth, mOuterHeight);
        mOuterDestRect = new Rect(0, 0, mTotalWidth, mTotalHeight);

        mWhiteRectf = new RectF(mLeftMargin + mCurrentProgressPosition, mLeftMargin, mTotalWidth - mRightMargin, mTotalHeight - mLeftMargin);
        mOrangeRectf = new RectF(mLeftMargin + mArcRadius, mLeftMargin, mCurrentProgressPosition, mTotalHeight - mLeftMargin);
        mArcRectf = new RectF(mLeftMargin, mLeftMargin, mLeftMargin + 2 * mArcRadius, mTotalHeight - mLeftMargin);

        mArcRightLocation = mLeftMargin + mArcRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制进度条和叶子
        //自所以把叶子放在进度条里绘制，主要是层级原因
        drawProgressAndLeafs(canvas);

        canvas.drawBitmap(mOuterBitmap, mOuterSrcRect, mOuterDestRect, mBitmapPaint);
        postInvalidate();
    }

    private void drawProgressAndLeafs(Canvas canvas) {
        if (mProgress >= TOTAL_PROGRESS) {
            mProgress = 0;
        }
        mCurrentProgressPosition = mProgressWidth * mProgress / TOTAL_PROGRESS;
        //既当前位置在图中所示1范围内
        if (mCurrentProgressPosition < mArcRadius) {
            //1.绘制白色ARC，绘制orange ARC
            //2.绘制白色矩形
            //白色ARC
            canvas.drawArc(mArcRectf, 90, 180, false, mWhitePaint);
            //绘制白色矩形
            mWhiteRectf.left = mArcRightLocation;
            canvas.drawRect(mWhiteRectf, mWhitePaint);

            //绘制叶子
            drawLeafs(canvas);

            //绘制棕色叶子
            //单边角度
            int angle = (int) Math.toDegrees(Math.acos((mArcRadius - mCurrentProgressPosition) / (float) mArcRadius));
            //起始位置
            int startAngle = 180 - angle;
            //扫过的角度
            int sweepAngle = 2 * angle;
            canvas.drawArc(mArcRectf, startAngle, sweepAngle, false, mOrangePaint);
        } else {
            //绘制white RECT、Orange ARC Orange Rect
            //叶子融入棕色进度条内
            mWhiteRectf.left = mCurrentProgressPosition;
            canvas.drawRect(mWhiteRectf, mWhitePaint);

            drawLeafs(canvas);

            canvas.drawArc(mArcRectf, 90, 180, false, mOrangePaint);
            mOrangeRectf.left = mArcRightLocation;
            mOrangeRectf.right = mCurrentProgressPosition;
            canvas.drawRect(mOrangeRectf, mOrangePaint);

        }
    }

    /**
     * 绘制叶子
     * @param canvas
     */
    private void drawLeafs(Canvas canvas) {
        mLeafRotateTime = mLeafRotateTime <=0 ?LEAF_ROTATE_TIME : mLeafRotateTime;
        long currentTime = System.currentTimeMillis();
        for(int i = 0;i < mLeafInfos.size();i++) {
            Leaf leaf = mLeafInfos.get(i);
            if (currentTime > leaf.startTime && leaf.startTime != 0) {
                //绘制叶子
                getLeafLocation(leaf, currentTime);

                //根据时间计算角度
                canvas.save();
                //通过Matrix控制叶子旋转
                Matrix matrix = new Matrix();
                float transX = mLeftMargin + leaf.x;
                float transY = mLeftMargin + leaf.y;
                matrix.postTranslate(transX, transY);
                // 通过时间关联旋转角度，则可以直接通过修改LEAF_ROTATE_TIME调节叶子旋转快慢
                float rotateFraction = ((currentTime - leaf.startTime) % mLeafRotateTime) / (float) mLeafFloatTime;
                int angle = (int) (rotateFraction * 360);
                //根据叶子旋转方向确定叶子旋转角度
                int rotate = leaf.rotateDirection == 0 ? angle + leaf.rotateAngle : -angle + leaf.rotateAngle;

                matrix.postRotate(rotate, transX + mLeafWidth / 2, mLeafHeight / 2);
                canvas.drawBitmap(mLeafBitmap, matrix, mBitmapPaint);
                canvas.restore();
            } else {
                continue;
            }
        }
    }
    private void getLeafLocation(Leaf leaf, long currentTime) {
        long intervalTime = currentTime - leaf.startTime;
        mLeafFloatTime = mLeafFloatTime <= 0 ?LEAF_FLOAT_TIME :mLeafFloatTime;
        if(intervalTime < 0){
            return;
        }else if(intervalTime > mLeafFloatTime){
            leaf.startTime = System.currentTimeMillis() + new Random().nextInt((int)(mLeafFloatTime));
        }
        float fraction = (float)intervalTime / mLeafFloatTime;
        leaf.x = (int)(mProgressWidth - mProgressWidth * fraction);
        leaf.y = getLoactionY(leaf);
    }

    private int getLoactionY(Leaf leaf) {
        //y = A (wx + Q) + h
        float w = (float) ((float)2 * Math.PI/mProgressWidth);
        float a = mMiddleAmplitude;
        switch (leaf.type){
            case LITTLE:
                a = mMiddleAmplitude - mAmplitudeDisparity;
                break;
            case MIDDLE:
                a = mMiddleAmplitude;
                break;
            case BIG:
                a = mMiddleAmplitude + mAmplitudeDisparity;
                break;
            default:
                break;
        }
        return (int)(a * Math.sin(w * leaf.x) + mArcRadius * 2/3);
    }

    private void init(Context context) {
        mResources = getResources();
        mLeftMargin = Utils.convertDpToPixel(LEFT_MARGIN, context);
        mRightMargin = Utils.convertDpToPixel(RIGHT_MARGIN, context);

        mLeafFloatTime = LEAF_FLOAT_TIME;
        mLeafRotateTime = LEAF_ROTATE_TIME;

        initBitmap();
        initPaint();
        mLeafFactory = new LeafFactory();
        mLeafInfos = mLeafFactory.generateLeafs();
    }

    private void initBitmap() {
        mLeafBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf)).getBitmap();
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();

        mOuterBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.leaf_kuang)).getBitmap();
        mOuterWidth = mOuterBitmap.getWidth();
        mOuterHeight = mOuterBitmap.getHeight();
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(WHITE_COLOR);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(ORANGE_COLOR);
    }

    private class LeafFactory {

        Random random = new Random();

        //生成一个叶子的信息
        public Leaf generateLeaf() {
            Leaf leaf = new Leaf();
            int randomType = random.nextInt(3);
            //随时类型 - 随机振幅
            StartType type = StartType.MIDDLE;
            switch (randomType) {
                case 0:
                    break;
                case 1:
                    type = StartType.LITTLE;
                    break;
                case 2:
                    type = StartType.BIG;
                    break;
                default:
                    break;
            }
            leaf.type = type;
            //随机起始地旋转角度
            leaf.rotateAngle = random.nextInt(360);
            //随机旋转方向(顺势正或逆时针)
            leaf.rotateDirection = random.nextInt(2);
            //为了产生交错的感觉，让一开始有一定的随机性
            mLeafFloatTime = mLeafRotateTime <= 0 ? LEAF_FLOAT_TIME : mLeafRotateTime;
            mAddTime += random.nextInt((int) (mLeafFloatTime * 2));
            leaf.startTime = System.currentTimeMillis() + mAddTime;
            return leaf;
        }

        public List<Leaf> generateLeafs() {
            return generateLeafs(MAX_LEAFS);
        }

        public List<Leaf> generateLeafs(int leafSize) {
            List<Leaf> leafs = new LinkedList<>();
            for (int i = 0; i < leafSize; i++) {
                leafs.add(generateLeaf());
            }
            return leafs;
        }
    }
    /**
     * 设置中等振幅
     *
     * @param amplitude
     */
    public void setMiddleAmplitude(int amplitude) {
        this.mMiddleAmplitude = amplitude;
    }
    /**
     * 设置振幅差
     *
     * @param dispartity
     */
    public void setMplitudeDisparity(int dispartity) {
        this.mAmplitudeDisparity = dispartity;
    }

    /**
     * 获取中等振幅
     *
     * @return
     */
    public int getMiddleAmplitude() {
        return mMiddleAmplitude;
    }

    /**
     * 获取振幅差
     *
     * @return
     */
    public int getMplitudeDisparity() {
        return mAmplitudeDisparity;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        postInvalidate();
    }

    /**
     * 设置叶子飘完一个周期所花的时间
     */
    public void setLeafFloatTime(long time) {
        this.mLeafFloatTime = time;
    }

    /**
     * 设置叶子旋转一个周期所花的时间
     */
    public void setLeafRotateTime(long time) {
        this.mLeafRotateTime = time;
    }

    /**
     * 获取叶子飘完一个周期所花的时间
     *
     * @return
     */
    public long getLeafFloatTime() {
        mLeafFloatTime = mLeafFloatTime == 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
        return mLeafFloatTime;
    }

    /**
     * 获取叶子旋转一周所花时间
     *
     * @return
     */
    public long getLeafRotateTime() {
        mLeafRotateTime = mLeafRotateTime == 0 ? LEAF_ROTATE_TIME : mLeafRotateTime;
        return mLeafRotateTime;
    }

}
