package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.jianxiongrao.demose.R;

/**
 * Created by Jianxiong Rao on 2017/9/26.
 */

public class GooglePlusButton extends BaseButton {


    public GooglePlusButton(Context context, AttributeSet attrs) {
        super(context,attrs, R.color.google,R.drawable.google_logo);

    }

    public GooglePlusButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle,R.color.google,R.drawable.google_logo);

    }

    public GooglePlusButton(Context context) {
        super(context);
    }
}
