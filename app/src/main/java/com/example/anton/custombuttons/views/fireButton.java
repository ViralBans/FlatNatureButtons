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

import com.example.anton.custombuttons.R;

/**
 * Created by anton on 03.01.2017.
 */

public class fireButton extends View {

    private int width = 0;
    private int height = 0;

    private int backgroundColor = 0;

    private Paint paintFab = new Paint();
    private Paint paintIcon = new Paint();
    private Paint paintCircle1 = new Paint();
    private Paint paintCircle2 = new Paint();
    private Paint paintCircle3 = new Paint();

    private float paintCircleSize1 = 0.0f;
    private float paintCircleHeight1 = 0.0f;
    private float paintCircleSize2 = 0.0f;
    private float paintCircleHeight2 = 0.0f;
    private float paintCircleSize3 = 0.0f;
    private float paintCircleHeight3 = 0.0f;

    private Bitmap bitmapIcon;

    private boolean isAnimationRunning = false;

    private void init() {

        if (backgroundColor == 0) {
            backgroundColor = Color.parseColor("#EE3769");
        }

        paintFab.setColor(backgroundColor);
        paintCircle1.setColor(backgroundColor);
        paintCircle2.setColor(backgroundColor);
        paintCircle3.setColor(backgroundColor);

        paintCircle1.setAntiAlias(true);
        paintCircle2.setAntiAlias(true);
        paintCircle3.setAntiAlias(true);

        paintFab.setAntiAlias(true);
        paintIcon.setAntiAlias(true);
    }

    public fireButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.fireButton);
        try {
            backgroundColor = typedArray.getInt(R.styleable.fireButton_fire_background_color, 0);
        } finally {
            typedArray.recycle();
        }
        init();
    }

    public fireButton(Context context) {
        super(context);
        init();
    }

    public void clickAnimation() {

        ValueAnimator fireAnimation1 = ValueAnimator.ofFloat(0, 1f);
        ValueAnimator fireAnimation2 = ValueAnimator.ofFloat(0, 1f);
        ValueAnimator fireAnimation3 = ValueAnimator.ofFloat(0, 1f);

        fireAnimation1.setStartDelay(300);
        fireAnimation2.setStartDelay(100);
        fireAnimation3.setStartDelay(500);
        fireAnimation1.setDuration(500);
        fireAnimation2.setDuration(400);
        fireAnimation3.setDuration(500);

        fireAnimation3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimationRunning = false;
            }
        });

        fireAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();

                paintCircleHeight1 = (1.0f - currentValue) * width;

                paintCircle1.setColor(backgroundColor);

                if (currentValue >= 0.0 && currentValue < 0.5) {
                    paintCircleSize1 = currentValue * width * 0.1f;
                } else {
                    paintCircleSize1 = (1.0f - currentValue) * width * 0.1f;
                }

                if (paintCircleHeight1 < width * 0.4) {
                    paintCircle1.setColor(Color.WHITE);
                }
                invalidate();
            }
        });

        fireAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();

                paintCircleHeight2 = (1.0f - currentValue) * width;

                paintCircle2.setColor(backgroundColor);

                if (currentValue >= 0.0 && currentValue < 0.5) {
                    paintCircleSize2 = currentValue * width * 0.13f;
                } else {
                    paintCircleSize2 = (1.0f - currentValue) * width * 0.13f;
                }

                if (paintCircleHeight2 < (width - bitmapIcon.getWidth()) / 2f) {
                    paintCircle2.setColor(Color.WHITE);
                }
                invalidate();
            }
        });

        fireAnimation3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();

                paintCircle3.setColor(backgroundColor);

                paintCircleHeight3 = (1.0f - currentValue) * width;

                if (currentValue >= 0.0 && currentValue < 0.5) {
                    paintCircleSize3 = currentValue * width * 0.15f;
                } else {
                    paintCircleSize3 = (1.0f - currentValue) * width * 0.15f;
                }

                if (paintCircleHeight3 < (width - bitmapIcon.getWidth()) / 2f) {
                    paintCircle3.setColor(Color.WHITE);
                }
                invalidate();
            }
        });

        if (isAnimationRunning == false) {
            isAnimationRunning = true;
            fireAnimation1.start();
            fireAnimation2.start();
            fireAnimation3.start();
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

        bitmapIcon = scaleDown(BitmapFactory.decodeResource(getResources(), R.drawable.fire_icon), 0.50 * width, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CustomOutline(width, height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paintFab);
        canvas.drawBitmap(bitmapIcon, ((width - bitmapIcon.getWidth()) / 2f), ((height - bitmapIcon.getHeight()) / 2f), paintIcon);

        canvas.drawCircle(width * 0.33f, paintCircleHeight1, paintCircleSize1, paintCircle1);
        canvas.drawCircle(width / 2f, paintCircleHeight2, paintCircleSize2, paintCircle2);
        canvas.drawCircle(width * 0.66f, paintCircleHeight3, paintCircleSize3, paintCircle3);
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
