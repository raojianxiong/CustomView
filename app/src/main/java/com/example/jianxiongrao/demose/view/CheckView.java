package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.jianxiongrao.demose.R;

/**
 * Created by Jianxiong Rao on 2017/9/28.
 * 总共13页，从0 到12 开始，初始页为 -1，让图片上(-1,0,0,h)，也就是没有，不显示勾
 * 取消选择后需要将页数置为-1
 */

public class CheckView extends View {

    private static final int ANIMAL_NULL = 0;
    private static final int ANIMAL_CHECK = 1;
    private static final int ANIMAL_UNCHECK = 2;
    private int currentAnim = ANIMAL_NULL;
    private Paint mPaint = new Paint();
    private int currentPage = -1;
    private int totalPage = 13;
    private int duration = 500;
    private Bitmap mBitmap;
    private Handler mHandler;
    private int mWidth, mHeight;
    private boolean isCheck = false;
    private int sLength;

    public CheckView(Context context) {
        this(context, null);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkmark);
        sLength = mBitmap.getHeight();

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (currentPage < totalPage && currentPage >= 0) {
                    invalidate();
                    if (currentAnim == ANIMAL_NULL) {
                        return false;
                    }
                    if (currentAnim == ANIMAL_CHECK) {
                        currentPage++;
                    }
                    if (currentAnim == ANIMAL_UNCHECK) {
                        currentPage--;
                    }
                    mHandler.sendEmptyMessageDelayed(0,duration/totalPage);
                }else{
                   if(isCheck){
                       currentPage = totalPage - 1;
                   }else{
                       currentPage = -1;
                   }
                   invalidate();
                    currentAnim = ANIMAL_NULL;
                }
                return false;
            }
        });
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
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, 240, mPaint);
        Rect rect = new Rect(sLength * currentPage, 0, sLength * (currentPage + 1), sLength);
        RectF rectF = new RectF(-200, -200, 200, 200);
        canvas.drawBitmap(mBitmap, rect, rectF, mPaint);
    }
    public void setCheck(){
        //如果当前处于开始动画状态
        if(currentAnim != ANIMAL_NULL ||isCheck){
            return;
        }
        isCheck = true;
        currentPage = 0;
        currentAnim = ANIMAL_CHECK;
        mHandler.sendEmptyMessageDelayed(0,duration/totalPage);
    }
    public void setUnCheck(){
        //如果处于结束动画状态
        if(currentAnim != ANIMAL_NULL || !isCheck){
            return;
        }
        isCheck = false;
        currentPage = totalPage - 1;
        currentAnim = ANIMAL_UNCHECK;
        mHandler.sendEmptyMessageDelayed(0,duration/totalPage);
    }
}
