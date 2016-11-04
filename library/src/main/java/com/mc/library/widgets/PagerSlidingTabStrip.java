package com.mc.library.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mc.library.R;

import java.util.Locale;

public class PagerSlidingTabStrip extends HorizontalScrollView {
    private static final String TAG = PagerSlidingTabStrip.class.getSimpleName();
    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };

    // @formatter:on
    public interface IconTabProvider {
        int getPagerIconResId(int position);
    }

    public interface PagerChangedListener {
        void onPageSelected(int position);
    }

    public PagerChangedListener delegatePagerListener;

    public void setOnPageChangeListener(PagerChangedListener listener) {
        delegatePagerListener = listener;
    }

    private final PageChangeListener pageChangeListener = new PageChangeListener();
    private LinearLayout tabsContainer;
    private ViewPager pager;
    private int tabCount;
    private int currentPosition = 0;
    private float currentPositionOffset = 0.0f;
    private boolean checkedTabWidths = false;
    private int indicatorColor = 0x33333333;
    private int underlineColor = 0x66666666;
    private int dividerColor = 0x99999999;
    private boolean shouldExpand = false;
    private boolean textAllCaps = true;
    private int scrollOffset = 64;
    private int indicatorHeight = 1;
    private int underlineHeight = 0;
    private int dividerWidth = 0;
    private int dividerPadding = 16;
    private int tabPadding = 14;
    private int tabLineHorizontalPadding = 0;
    private int tabLineVerticalPadding = 4;
    private int tabTextSize = 13;
    private int tabTextColor = 0xFF666666;
    private int tabIndicatorTextColor = 0xFF333333;
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int lastScrollX = 0;
    private int tabBackgroundResId = R.drawable.background_tab;
    private Paint rectPaint;
    private Paint dividerPaint;
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;
    private Locale locale;

    /**
     * 串联构造器
     */
    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, displayMetrics);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, displayMetrics);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, displayMetrics);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, displayMetrics);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, displayMetrics);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, displayMetrics);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, displayMetrics);
        tabLineHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabLineHorizontalPadding, displayMetrics);
        tabLineVerticalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabLineVerticalPadding, displayMetrics);

        // get system attrs (android:textSize and android:textColor)
        TypedArray typedArray = context.obtainStyledAttributes(attrs, ATTRS);
        tabTextSize = typedArray.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = typedArray.getColor(1, tabTextColor);
        typedArray.recycle();

        // get custom attrs
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
        indicatorColor = typedArray.getColor(R.styleable.PagerSlidingTabStrip_indicatorColor, indicatorColor);
        underlineColor = typedArray.getColor(R.styleable.PagerSlidingTabStrip_underlineColor, underlineColor);
        dividerColor = typedArray.getColor(R.styleable.PagerSlidingTabStrip_dividerColor, dividerColor);
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_indicatorHeight, indicatorHeight);
        underlineHeight = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_underlineHeight, underlineHeight);
        dividerWidth = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_underlineHeight, dividerWidth);
        dividerPadding = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_dividerPadding, dividerPadding);
        tabPadding = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_tabPaddingLeftRight, tabPadding);
        scrollOffset = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_scrollOffset, scrollOffset);
        tabBackgroundResId = typedArray.getResourceId(R.styleable.PagerSlidingTabStrip_tabBackground, tabBackgroundResId);
        shouldExpand = typedArray.getBoolean(R.styleable.PagerSlidingTabStrip_shouldExpand, shouldExpand);
        textAllCaps = typedArray.getBoolean(R.styleable.PagerSlidingTabStrip_textAllCaps, textAllCaps);
        tabLineHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_scrollOffset, tabLineHorizontalPadding);
        tabLineVerticalPadding = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_scrollOffset, tabLineVerticalPadding);
        typedArray.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        if (pager != null) {
            this.pager = pager;
            if (pager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            pager.setOnPageChangeListener(pageChangeListener);
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("ViewPager is null.");
        }
    }

    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        PagerAdapter adapter = pager.getAdapter();
        tabCount = adapter.getCount();
        if (adapter instanceof IconTabProvider) {
            for (int i = 0; i < tabCount; i++) {
                addIconTab(i, ((IconTabProvider) adapter).getPagerIconResId(i));
            }
        } else {
            for (int i = 0; i < tabCount; i++) {
                addTextTab(i, adapter.getPageTitle(i));
            }
        }
        updateTabStyles();
        checkedTabWidths = false;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                } else {
//                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                }
                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });
    }

    private void addTextTab(final int position, CharSequence title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setFocusable(true);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position, false);
            }
        });
        tabsContainer.addView(tab);
    }

    private void addIconTab(final int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setFocusable(true);
        tab.setImageResource(resId);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position, false);
            }
        });
        tabsContainer.addView(tab);
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View view = tabsContainer.getChildAt(i);
            view.setLayoutParams(defaultTabLayoutParams);
            view.setBackgroundResource(tabBackgroundResId);
            if (shouldExpand) {
                view.setPadding(0, 0, 0, 0);
            } else {
                view.setPadding(tabPadding, 0, tabPadding, 0);
            }
            if (view instanceof TextView) {
                TextView tab = (TextView) view;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tab.setTypeface(tabTypeface, tabTypefaceStyle);
                tab.setTextColor(i == 0 ? tabIndicatorTextColor : tabTextColor);
                // setAllCaps() is only available from API 14, so the upper case is made manually
                // if we are on a pre-ICS-build
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }
            }
        }
    }

    private void scrollToChild(int position, int offset) {
        if (tabCount == 0) {
            return;
        }
        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;
        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }
        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!shouldExpand || MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int childWidth = 0;
        for (int i = 0; i < tabCount; i++) {
            childWidth += tabsContainer.getChildAt(i).getMeasuredWidth();
        }
        if (!checkedTabWidths && childWidth > 0 && measuredWidth > 0) {
            if (childWidth <= measuredWidth) {
                for (int i = 0; i < tabCount; i++) {
                    tabsContainer.getChildAt(i).setLayoutParams(expandedTabLayoutParams);
                }
            }
            checkedTabWidths = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || tabCount == 0) {
            return;
        }
        final int height = getHeight();
        // draw indicator line
        rectPaint.setColor(indicatorColor);
        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();
        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
        }
        canvas.drawRect(lineLeft + tabPadding - tabLineHorizontalPadding, height - indicatorHeight - tabLineVerticalPadding, lineRight - tabPadding + tabLineHorizontalPadding, height - tabLineVerticalPadding, rectPaint);
        // draw underline
        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
        // draw divider
        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
        }
    }

    private class PageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
            invalidate();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePagerListener != null) {
                delegatePagerListener.onPageSelected(position);
            }
            for (int i = 0; i < tabCount; i++) {
                View view = tabsContainer.getChildAt(i);
                if (view instanceof TextView) {
                    TextView tab = (TextView) view;
                    tab.setTextColor(i == pager.getCurrentItem() ? tabIndicatorTextColor : tabTextColor);
                }
            }
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerWidth(int dividerWidthPx) {
        dividerWidth = dividerWidthPx;
        invalidate();
    }

    public int getDividerWidth() {
        return dividerWidth;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean isShouldExpand() {
        return shouldExpand;
    }

    public void setTextAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        tabTypeface = typeface;
        tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    private static class SavedState extends BaseSavedState {
        private int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
