package com.mc.library.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.mc.library.R;

public class RippleView extends View {
    private Drawable bgNormalDrawable;
    private Drawable bgPressedDrawable;
    private Bitmap bgNormalBitmap;
    private Bitmap bgPressedBitmap;
    private Paint bgPaint;
    private Paint minPaint;
    private Paint maxPaint;
    private Rect mRect;
    private Region mRegion;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private int mRadius;
    private int minRadius;
    private int maxRadius;
    private RippleAnimation mRippleAnimation;
    private float mInterpolatedTime;
    private boolean isPressed;
    private IRippleListener mIRippleListener;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        bgNormalDrawable = typedArray.getDrawable(R.styleable.RippleView_normal);
        bgPressedDrawable = typedArray.getDrawable(R.styleable.RippleView_pressed);
        typedArray.recycle();
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        minPaint = new Paint();
        minPaint.setColor(0xffffffff);
        maxPaint = new Paint();
        maxPaint.setColor(0x66666666);
        if (bgNormalDrawable != null && bgNormalDrawable instanceof BitmapDrawable) {
            bgNormalBitmap = ((BitmapDrawable) bgNormalDrawable).getBitmap();
        }
        if (bgPressedDrawable != null && bgPressedDrawable instanceof BitmapDrawable) {
            bgPressedBitmap = ((BitmapDrawable) bgPressedDrawable).getBitmap();
        }
        mRect = new Rect();
        mRegion = new Region();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        mRadius = Math.min(mMeasuredWidth / 2, mMeasuredHeight / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getPaddingLeft(), 0);
        maxRadius = (int) (mInterpolatedTime * mRadius * 1.2f);
        if (maxRadius > mRadius * 0.8f) {
            minRadius = (int) ((mInterpolatedTime - 0.6) * mRadius * 3);
        }
        if (mInterpolatedTime != 1.0f) {
            canvas.drawCircle(mMeasuredWidth / 2, mMeasuredHeight / 2, maxRadius, maxPaint);
            canvas.drawCircle(mMeasuredWidth / 2, mMeasuredHeight / 2, minRadius, minPaint);
        } else {
            clearState();
        }
        if (bgNormalBitmap != null && !isPressed) {
            Rect rect = new Rect();
            rect.set(mMeasuredWidth * 5 / 16, mMeasuredHeight * 5 / 16, mMeasuredWidth * 11 / 16, mMeasuredHeight * 11 / 16);
            canvas.drawBitmap(bgNormalBitmap, null, rect, bgPaint);
        } else if (bgPressedBitmap != null && isPressed) {
            Rect rect = new Rect();
            rect.set(mMeasuredWidth * 5 / 16, mMeasuredHeight * 5 / 16, mMeasuredWidth * 11 / 16, mMeasuredHeight * 11 / 16);
            canvas.drawBitmap(bgPressedBitmap, null, rect, bgPaint);
        }
    }

    private void clearState() {
        minRadius = 0;
        maxRadius = 0;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (contains(event)) {
                    isPressed = true;
                    startRipple();
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    private boolean contains(MotionEvent event) {
        mRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mRegion.set(mRect);
        return mRegion.contains((int) event.getX(), (int) event.getY());
    }

    private void startRipple() {
        if (mRippleAnimation == null) {
            mRippleAnimation = new RippleAnimation();
        }
        mRippleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mIRippleListener != null) {
                    mIRippleListener.onComplete(RippleView.this);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mRippleAnimation.cancel();
        mRippleAnimation.setDuration(600);
        startAnimation(mRippleAnimation);
    }

    private class RippleAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation transformation) {
            super.applyTransformation(interpolatedTime, transformation);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }

    public void setIRippleListener(IRippleListener rippleListener) {
        mIRippleListener = rippleListener;
    }

    public interface IRippleListener {
        void onComplete(View view);
    }
}