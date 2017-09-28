package com.example.jianxiongrao.demose.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Jianxiong Rao on 2017/9/26.
 */

public class Utils {
    /**
     *  px = dp * density
     * @param dp
     * @param context
     * @return
     */
    public static int convertDpToPixel(float dp, Context context){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(dp * density +0.5f);
    }
    public static float convertPixelsToDp(float px,Context context){
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(px / density + 0.5f);
    }
    public static Bitmap drawableToBitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return  ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
