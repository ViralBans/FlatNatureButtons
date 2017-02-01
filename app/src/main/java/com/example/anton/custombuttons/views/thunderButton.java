package com.example.anton.custombuttons.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;

import com.example.anton.custombuttons.R;

/**
 * Created by anton on 03.01.2017.
 */

public class thunderButton extends View {

    private int width = 0;
    private int height = 0;

    private int backgroundColour = 0;

    private Paint paintFab = new Paint();
    private Paint paintIcon = new Paint();
    private Paint paintBlink = new Paint();

    private int iconWidthAnimation = 0;
    private int iconHeightAnimation = 0;

    private Bitmap bitmapIcon;

    private boolean isAnimationRunning = false;

    private void init() {
        paintFab.setFlags(Paint.ANTI_ALIAS_FLAG);

        if (backgroundColour != 0)
            paintFab.setColor(backgroundColour);
        else
            paintFab.setColor(Color.parseColor("#5A5A5A"));

        paintBlink.setColor(Color.argb(0, 0, 0, 0));
        paintBlink.setAntiAlias(true);
    }

    public thunderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.thunderButton);

        try {
            backgroundColour = typedArray.getInt(R.styleable.thunderButton_thunder_background_color, 0);
        } finally {
            typedArray.recycle();
        }

        init();

    }

    public thunderButton(Context context) {
        super(context);
        init();
    }

    public void clickAnimation() {

        final Bitmap bitmapIconSmall = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.thunder_icon_white), 0.40 * width, true);
        final Bitmap bitmapIconBig = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.thunder_icon_white), 0.53 * width, true);
        final Bitmap bitmapIconDefault = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.thunder_icon_white), 0.50 * width, true);

        ValueAnimator blinkAnimation = ValueAnimator.ofFloat(0, 0.5f);

        blinkAnimation.setDuration(400);
        blinkAnimation.setStartDelay(200);

        blinkAnimation.setInterpolator(new LinearInterpolator());

        blinkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();

                if (currentValue < 0.1) {
                    paintBlink.setColor(Color.argb(150, 255, 255, 255));
                } else if (currentValue < 0.15) {
                    paintBlink.setColor(Color.argb(0, 0, 0, 0));
                    bitmapIcon = bitmapIconSmall;
                    iconWidthAnimation = width / 4;
                    iconHeightAnimation = -width / 5;
                } else if (currentValue < 0.27) {
                    paintBlink.setColor(Color.argb(240, 255, 255, 255));
                } else if (currentValue < 0.3) {
                    paintBlink.setColor(Color.argb(0, 0, 0, 0));
                    bitmapIcon = bitmapIconBig;
                    iconWidthAnimation = -width / 4;
                    iconHeightAnimation = width / 6;
                } else if (currentValue < 0.35) {
                    paintBlink.setColor(Color.argb(0, 0, 0, 0));
                    iconWidthAnimation = width / 15;
                    iconHeightAnimation = -width / 15;
                } else {
                    bitmapIcon = bitmapIconDefault;
                    iconWidthAnimation = 0;
                    iconHeightAnimation = 0;
                }

                invalidate();
            }
        });

        blinkAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationRunning = false;

            }
        });

        if (isAnimationRunning == false) {
            blinkAnimation.start();
            isAnimationRunning = true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.i("fireButton", "thunder width: " + w);
        Log.i("fireButton", "thunder height: " + h);
        if (w != h) {
            if (w < h) {
                width = w;
                height = w;
            } else {
                width = h;
                height = h;
            }
        } else {
            width = w;
            height = h;
        }
        bitmapIcon = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.thunder_icon_white), 0.50 * width, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutline(width, height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paintFab);
        canvas.drawBitmap(bitmapIcon, ((width - bitmapIcon.getWidth()) / 2f) + iconWidthAnimation, ((height - bitmapIcon.getHeight()) / 2f) + iconHeightAnimation, paintIcon);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paintBlink);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private class CustomOutline extends ViewOutlineProvider {

        int width;
        int height;

        CustomOutline(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, width, height);
        }
    }

    private Bitmap scaleDown(Bitmap realImage, Double maxImageSize, Boolean filter) {
        float ratio = Math.min(
                maxImageSize.floatValue() / realImage.getWidth(),
                maxImageSize.floatValue() / realImage.getHeight());

        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);

        return newBitmap;
    }
}
