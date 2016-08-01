package com.mlm.hypemeter.cardstream;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

/**
 * Created by Martin on 2/29/2016.
 */
public class CardActionButton extends Button {

    public CardActionButton(Context context) {
        super(context);
    }

    public CardActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardActionButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                setPressed(true);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    animate().scaleX(0.98f).scaleY(0.98f).setDuration(100)
                            .setInterpolator(new DecelerateInterpolator());
                } else {
                    ViewCompat.setElevation(this, 8.f);
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                setPressed(false);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    animate().scaleX(1.f).scaleY(1.f).setDuration(50)
                            .setInterpolator(new BounceInterpolator());
                } else {
                    ViewCompat.setElevation(this, 0.f);
                }
                break;
            }
        }

        return super.onTouchEvent(event);
    }

}
