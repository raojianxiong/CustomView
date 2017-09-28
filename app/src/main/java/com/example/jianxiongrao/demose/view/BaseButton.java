package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.jianxiongrao.demose.R;
import com.example.jianxiongrao.demose.utils.Utils;

/**
 * Created by Jianxiong Rao on 2017/9/26.
 */

public class BaseButton extends Button {
    private Bitmap mIcon;
    private Paint mPaint;
    private Rect mSrcRect;
    private int mIconPadding;
    private int mIconSize;
    private int mRoundedCornerRadius;
    private boolean mIconCenterAligned;
    private boolean mRoundedCorner;
    private boolean mTransparentBackground;

    public BaseButton(Context context) {
        super(context);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr,int color,int logo) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, logo);
        setStyle(color, context);
    }


    public BaseButton(Context context, AttributeSet attrs, int color, int logo) {
        super(context, attrs);
        init(context, attrs, logo);
        setStyle(color, context);
    }

    private void init(Context context, AttributeSet attrs, int logo) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IconButton);

        //initialize variables to default value
        setDefaultValues(context,logo);

        //Don't add padding when text isn't present
        if(attrs.getAttributeValue("http://schemas.android.com/apk/res/android","text")!=null){
              mIconPadding = Utils.convertDpToPixel(20,context);
        }
        //load the custom properties and assing values
        for(int i = 0;i < array.getIndexCount();++i){
            int attr = array.getIndex(i);
            if(attr == R.styleable.IconButton_iconPadding){
                mIconPadding = array.getDimensionPixelSize(attr,Utils.convertDpToPixel(20,context));
            }
            if(attr == R.styleable.IconButton_iconCenterAligned){
                mIconCenterAligned = array.getBoolean(attr,true);
            }
            if(attr == R.styleable.IconButton_iconSize){
                mIconSize = array.getDimensionPixelSize(attr,Utils.convertDpToPixel(20,context));
            }
            if(attr == R.styleable.IconButton_roundedCorner){
                mRoundedCorner = array.getBoolean(attr,false);
            }
            if(attr == R.styleable.IconButton_roundedCornerRadius){
                mRoundedCornerRadius = array.getDimensionPixelSize(attr,Utils.convertDpToPixel(20,context));
            }
            if(attr == R.styleable.IconButton_transparentBackground){
                mTransparentBackground = array.getBoolean(attr,false);
            }
        }
        array.recycle();

        if(mIcon != null){
            mPaint = new Paint();
            mSrcRect = new Rect(0,0,mIcon.getWidth(),mIcon.getHeight());
        }
    }


    private void setStyle(int color, Context context) {
        setTextColor(Color.WHITE);
        setBackgroundResource(R.drawable.round_corner);
        GradientDrawable drawable = (GradientDrawable)getBackground().mutate();
        drawable.setColor(getResources().getColor(color));
        drawable.setCornerRadius(0);

        if(mRoundedCorner){
            drawable.setCornerRadius(mRoundedCornerRadius);
        }
        if(mTransparentBackground){
            drawable.setColor(Color.TRANSPARENT);
            drawable.setStroke(4,getResources().getColor(color));
        }
        drawable.invalidateSelf();
        setPadding(Utils.convertDpToPixel(30,context),0,Utils.convertDpToPixel(30,context),0);
    }

    private void setDefaultValues(Context context , int logo){
        mIcon = Utils.drawableToBitmap(getResources().getDrawable(logo));
        mIconSize = Utils.convertDpToPixel(20,context);
        mIconCenterAligned = true;
        mRoundedCorner = false;
        mTransparentBackground = false;
        mRoundedCornerRadius = Utils.convertDpToPixel(40,context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //recalculate width and amount shift by, taking into account icon size
        int shift = (mIconSize + mIconPadding) / 2;
        canvas.save();
        canvas.translate(shift,0);
        super.onDraw(canvas);

        float textWidth = getPaint().measureText((String)getText());
        int left = (int)((getWidth() / 2f) - (textWidth / 2f) - mIconSize - mIconPadding);
        int top = getHeight()/2 - mIconSize/2;

        if(!mIconCenterAligned)
            left = 0;

        Rect destRect = new Rect(left, top, left + mIconSize, top + mIconSize);
        canvas.drawBitmap(mIcon, mSrcRect, destRect, mPaint);

        canvas.restore();
    }
}
