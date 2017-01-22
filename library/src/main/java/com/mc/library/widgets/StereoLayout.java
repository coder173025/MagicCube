package com.mc.library.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 3D立体容器
 */
public class StereoLayout extends ViewGroup {
    private static final String TAG = StereoLayout.class.getSimpleName();
    private Context mContext;
    private Camera mCamera;
    private Matrix mMatrix;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mScaledTouchSlop;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private static final int standerSpeed = 2000;
    private static final int flingSpeed = 800;
    private int mInitializationPosition = 1;
    private float mAngle = 90;
    private float mResistance = 1.8f;
    private boolean m3DEnable = true;
    private int mCurrentPosition = mInitializationPosition;
    private int addCount;//记录手离开屏幕后，需要新增的页面次数
    private int alreadyAdd;//对滑动多页时的已经新增页面次数的记录
    private boolean isAdding;//fling时正在添加新页面，在绘制时不需要开启camera绘制效果，否则页面会有闪动
    private IStereoListener mIStereoListener;
    private float mDownX, mDownY;
    private boolean isSliding;
    private State mState = State.Normal;

    public StereoLayout(Context context) {
        this(context, null);
    }

    public StereoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StereoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mCamera = new Camera();
        mMatrix = new Matrix();
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = getMeasuredWidth();
        mMeasuredHeight = getMeasuredHeight();
        scrollTo(0, mInitializationPosition * mMeasuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(0, childTop, childView.getMeasuredWidth(), childTop + childView.getMeasuredHeight());
                childTop = childTop + childView.getMeasuredHeight();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isSliding = false;
                mDownX = x;
                mDownY = y;
                if (!mScroller.isFinished()) {
                    //当上一次滑动没有结束时，再次点击，强制滑动在点击位置结束
                    mScroller.setFinalY(mScroller.getCurrY());
                    mScroller.abortAnimation();
                    scrollTo(0, getScrollY());
                    isSliding = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isSliding) {
                    isSliding = isCanSliding(ev);
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSliding;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                if (isSliding) {
                    int realDelta = (int) (mDownY - y);
                    mDownY = y;
                    if (mScroller.isFinished()) {
                        //因为要循环滚动
                        recycleMove(realDelta);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isSliding) {
                    isSliding = false;
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float yVelocity = mVelocityTracker.getYVelocity();
                    //滑动的速度大于规定的速度，或者向上滑动时，上一页页面展现出的高度超过1/2。则设定状态为State.ToPrevious
                    if (yVelocity > standerSpeed || ((getScrollY() + mMeasuredHeight / 2) / mMeasuredHeight < mInitializationPosition)) {
                        mState = State.ToPrevious;
                    } else if (yVelocity < -standerSpeed || ((getScrollY() + mMeasuredHeight / 2) / mMeasuredHeight > mInitializationPosition)) {
                        //滑动的速度大于规定的速度，或者向下滑动时，下一页页面展现出的高度超过1/2。则设定状态为State.ToNext
                        mState = State.ToNext;
                    } else {
                        mState = State.Normal;
                    }
                    //根据mState进行相应的变化
                    changeByState(yVelocity);
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean isCanSliding(MotionEvent ev) {
        float moveX;
        float moveY;
        moveX = ev.getX();
        moveY = ev.getY();
        if (Math.abs(moveY - mDownX) > mScaledTouchSlop && (Math.abs(moveY - mDownY) > (Math.abs(moveX - mDownX)))) {
            return true;
        }
        return false;
    }

    private void changeByState(float yVelocity) {
        alreadyAdd = 0;//重置滑动多页时的计数
        if (getScrollY() != mMeasuredHeight) {
            switch (mState) {
                case Normal:
                    toNormalAction();
                    break;
                case ToPrevious:
                    toPreviousAction(yVelocity);
                    break;
                case ToNext:
                    toNextAction(yVelocity);
                    break;
            }
            invalidate();
        }
    }

    /**
     * mState = State.Normal 时进行的动作
     */
    private void toNormalAction() {
        int startY;
        int delta;
        int duration;
        mState = State.Normal;
        addCount = 0;
        startY = getScrollY();
        delta = mMeasuredHeight * mInitializationPosition - getScrollY();
        duration = (Math.abs(delta)) * 4;
        mScroller.startScroll(0, startY, 0, delta, duration);
    }

    /**
     * mState = State.ToPrevious 时进行的动作
     *
     * @param yVelocity 竖直方向的速度
     */
    private void toPreviousAction(float yVelocity) {
        int startY;
        int delta;
        int duration;
        mState = State.ToPrevious;
        addPre();//增加新的页面
        //计算松手后滑动的item个数
        int flingSpeedCount = (yVelocity - standerSpeed) > 0 ? (int) (yVelocity - standerSpeed) : 0;
        addCount = flingSpeedCount / flingSpeed + 1;
        //mScroller开始的坐标
        startY = getScrollY() + mMeasuredHeight;
        setScrollY(startY);
        //mScroller移动的距离
        delta = -(startY - mInitializationPosition * mMeasuredHeight) - (addCount - 1) * mMeasuredHeight;
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
        addCount--;
    }

    /**
     * mState = State.ToNext 时进行的动作
     *
     * @param yVelocity 竖直方向的速度
     */
    private void toNextAction(float yVelocity) {
        int startY;
        int delta;
        int duration;
        mState = State.ToNext;
        addNext();
        int flingSpeedCount = (Math.abs(yVelocity) - standerSpeed) > 0 ? (int) (Math.abs(yVelocity) - standerSpeed) : 0;
        addCount = flingSpeedCount / flingSpeed + 1;
        startY = getScrollY() - mMeasuredHeight;
        setScrollY(startY);
        delta = mMeasuredHeight * mInitializationPosition - startY + (addCount - 1) * mMeasuredHeight;
        Log.e(TAG, "多后一页startY " + startY + " yVelocity " + yVelocity + " delta " + delta + "  getScrollY() " + getScrollY() + " addCount " + addCount);
        duration = (Math.abs(delta)) * 3;
        mScroller.startScroll(0, startY, 0, delta, duration);
        addCount--;
    }

    @Override
    public void computeScroll() {
        //滑动没有结束时，进行的操作
        if (mScroller.computeScrollOffset()) {
            if (mState == State.ToPrevious) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY() + mMeasuredHeight * alreadyAdd);
                if (getScrollY() < (mMeasuredHeight + 2) && addCount > 0) {
                    isAdding = true;
                    addPre();
                    alreadyAdd++;
                    addCount--;
                }
            } else if (mState == State.ToNext) {
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY() - mMeasuredHeight * alreadyAdd);
                if (getScrollY() > (mMeasuredHeight) && addCount > 0) {
                    isAdding = true;
                    addNext();
                    addCount--;
                    alreadyAdd++;
                }
            } else {
                //mState == State.Normal状态
                scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            }
            postInvalidate();
        }
        //滑动结束时相关用于计数变量复位
        if (mScroller.isFinished()) {
            alreadyAdd = 0;
            addCount = 0;
        }
    }

    /**
     * 把第一个item移动到最后一个item位置
     */
    private void addNext() {
        mCurrentPosition = (mCurrentPosition + 1) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(0);
        removeViewAt(0);
        addView(view, childCount - 1);
        if (mIStereoListener != null) {
            mIStereoListener.toNext(mCurrentPosition);
        }
    }

    /**
     * 把最后一个item移动到第一个item位置
     */
    private void addPre() {
        mCurrentPosition = ((mCurrentPosition - 1) + getChildCount()) % getChildCount();
        int childCount = getChildCount();
        View view = getChildAt(childCount - 1);
        removeViewAt(childCount - 1);
        addView(view, 0);
        if (mIStereoListener != null) {
            mIStereoListener.toPrevious(mCurrentPosition);
        }
    }

    private void recycleMove(int delta) {
        delta = delta % mMeasuredHeight;
        delta = (int) (delta / mResistance);
        if (Math.abs(delta) > mMeasuredHeight / 4) {
            return;
        }
        scrollBy(0, delta);
        if (getScrollY() < 5 && mInitializationPosition != 0) {
            addPre();
            scrollBy(0, mMeasuredHeight);
        } else if (getScrollY() > (getChildCount() - 1) * mMeasuredHeight - 5) {
            addNext();
            scrollBy(0, -mMeasuredHeight);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!isAdding && m3DEnable) {
            //当开启3D效果并且当前状态不属于 computeScroll中 addPre() 或者addNext()
            //如果不做这个判断，addPre() 或者addNext()时页面会进行闪动一下
            //我当时写的时候就被这个坑了，后来通过log判断，原来是computeScroll中的onlayout,和子Child的draw触发的顺序导致的。
            //知道原理的朋友希望可以告知下
            for (int i = 0; i < getChildCount(); i++) {
                drawScreen(canvas, i, getDrawingTime());
            }
        } else {
            isAdding = false;
            super.dispatchDraw(canvas);
        }
    }

    private void drawScreen(Canvas canvas, int i, long drawingTime) {
        int curScreenY = mMeasuredHeight * i;
        //屏幕中不显示的部分不进行绘制
        if (getScrollY() + mMeasuredHeight < curScreenY) {
            return;
        }
        if (curScreenY < getScrollY() - mMeasuredHeight) {
            return;
        }
        float centerX = mMeasuredWidth / 2;
        float centerY = (getScrollY() > curScreenY) ? curScreenY + mMeasuredHeight : curScreenY;
        float degree = mAngle * (getScrollY() - curScreenY) / mMeasuredHeight;
        if (degree > 90 || degree < -90) {
            return;
        }
        canvas.save();
        mCamera.save();
        mCamera.rotateX(degree);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(-centerX, -centerY);
        mMatrix.postTranslate(centerX, centerY);
        canvas.concat(mMatrix);
        drawChild(canvas, getChildAt(i), drawingTime);
        canvas.restore();
    }

    public enum State {
        Normal, ToPrevious, ToNext
    }

    public StereoLayout initPosition(int position) {
        if (position <= 0 || position >= (getChildCount() - 1)) {
            throw new IndexOutOfBoundsException("position ∈ (0, getChildCount() - 1)");
        }
        mInitializationPosition = position;
        mCurrentPosition = position;
        return this;
    }

    public StereoLayout setAngle(float angle) {
        if (angle < 0.0f || angle > 180.0f) {
            throw new IndexOutOfBoundsException("angle ∈ [0.0f, 180.0f]");
        }
        mAngle = 180.0f - angle;
        return this;
    }

    public StereoLayout setResistance(float resistance) {
        if (resistance <= 0) {
            throw new IllegalArgumentException("resistance ∈ (0, ...)");
        }
        mResistance = resistance;
        return this;
    }

    public StereoLayout set3DEnable(boolean enable) {
        m3DEnable = enable;
        return this;
    }

    public StereoLayout setInterpolator(Interpolator interpolator) {
        if (interpolator == null) {
            throw new NullPointerException("interpolator == null");
        }
        mScroller = new Scroller(mContext, interpolator);
        return this;
    }

    public StereoLayout toPosition(int position) {
        if (position < 0 || position > (getChildCount() - 1)) {
            throw new IndexOutOfBoundsException("position ∈ [0, getChildCount() - 1]");
        }
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        if (position > mCurrentPosition) {
//            setScrollY(mInitializationPosition * mMeasuredHeight);
            toNextAction(-standerSpeed - flingSpeed * (position - mCurrentPosition - 1));
        } else if (position < mCurrentPosition) {
//            setScrollY(mInitializationPosition * mMeasuredHeight);
            toPreviousAction(standerSpeed + (mCurrentPosition - position - 1) * flingSpeed);
        }
        return this;
    }

    public StereoLayout toPrevious() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        toPreviousAction(standerSpeed);
        return this;
    }

    public StereoLayout toNext() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        toNextAction(-standerSpeed);
        return this;
    }

    public void setIStereoListener(IStereoListener stereoListener) {
        mIStereoListener = stereoListener;
    }

    public interface IStereoListener {
        void toPrevious(int position);

        void toNext(int position);
    }
}