package com.example.jianxiongrao.demose.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.jianxiongrao.demose.R;

/**
 * Created by Jianxiong Rao on 2017/9/26.
 */

public class LinkedInButton extends BaseButton {

    public LinkedInButton(Context context, AttributeSet attrs) {
        super(context,attrs,R.color.linkedin, R.drawable.linkedin_logo);
    }

    public LinkedInButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle,R.color.linkedin,R.drawable.linkedin_logo);
    }

    public LinkedInButton(Context context) {
        super(context);
    }
}
