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
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.anton.custombuttons.R;

/**
 * Created by anton on 03.01.2017.
 */

public class waterButton extends View {

    private int width = 0;
    private int height = 0;

    private int backgroundColour = 0;

    private Paint paintFab = new Paint();
    private Paint paintIcon = new Paint();
    private Paint paintBlob = new Paint();

    private int iconHeightAnimation = 0;
    private int circleHeightAnimation = 0;
    private int circleSizeAnimation = 0;

    private Bitmap bitmapIcon;

    private boolean isAnimationRunning = false;

    private void init() {

        if (backgroundColour != 0)
            paintFab.setColor(backgroundColour);
        else
            paintFab.setColor(Color.parseColor("#27AAE1"));

        paintBlob.setColor(Color.WHITE);
        paintIcon.setAntiAlias(true);
        paintFab.setAntiAlias(true);
        paintBlob.setAntiAlias(true);
    }


    public waterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.waterButton);

        try {
            backgroundColour = typedArray.getInt(R.styleable.waterButton_water_background_color, 0);
        } finally {
            typedArray.recycle();
        }

        init();
    }

    public waterButton(Context context) {
        super(context);
        init();
    }

    public void clickAnimation() {

        final boolean[] smallDropAnimStarted = {false};

        ValueAnimator dropAnimation = ValueAnimator.ofFloat(width / 2f + bitmapIcon.getHeight(), 0);
        final ValueAnimator smallDropAnimationReversed = ValueAnimator.ofFloat(width / 12f, 0);

        dropAnimation.setDuration(500);
        smallDropAnimationReversed.setDuration(250);


        smallDropAnimationReversed.setInterpolator(new LinearInterpolator());
        dropAnimation.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                if (t < 0.35f) return (t);
                else if (t < 0.83f) return (t - 0.54719f) + 0.78f;
                else return 1.0f;
            }
        });

        dropAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationRunning = false;
            }
        });

        dropAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();

                iconHeightAnimation = -(int) currentValue;

                if (currentValue <= width / 2f && smallDropAnimStarted[0] == false) {
                    smallDropAnimStarted[0] = true;
                    smallDropAnimationReversed.start();
                }

                invalidate();
            }
        });

        smallDropAnimationReversed.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                circleSizeAnimation = (int) currentValue;
                circleHeightAnimation = (int) -currentValue / 2;
                invalidate();
            }
        });

        if (isAnimationRunning == false) {
            isAnimationRunning = true;
            dropAnimation.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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

        bitmapIcon = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.blob_icon_v1), 0.48 * width, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutline(width, height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paintFab);
        canvas.drawBitmap(bitmapIcon, (width - bitmapIcon.getWidth()) / 2f, (width - bitmapIcon.getWidth()) / 2f + iconHeightAnimation, paintIcon);
        canvas.drawCircle(width / 2f, 0 + circleHeightAnimation, circleSizeAnimation, paintBlob);

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
