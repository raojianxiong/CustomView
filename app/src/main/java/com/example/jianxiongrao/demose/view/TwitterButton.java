package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.jianxiongrao.demose.R;

/**
 * Created by Jianxiong Rao on 2017/9/26.
 */

public class TwitterButton extends BaseButton {

    public TwitterButton(Context context, AttributeSet attrs) {
        super(context,attrs,R.color.twitter,R.drawable.twitter_logo);
    }

    public TwitterButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle, R.color.twitter,R.drawable.twitter_logo);
    }

    public TwitterButton(Context context) {
        super(context);
    }
}
