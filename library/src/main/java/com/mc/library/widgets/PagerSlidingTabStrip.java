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

import static android.R.attr.textColor;
import static android.R.attr.textSize;

public class PagerSlidingTabStrip extends HorizontalScrollView {
    public interface IconTabProvider {
        int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            textSize,
            textColor
    };
    // @formatter:on

    private final WrapperPageChangeListener wrapperPageChangeListener = new WrapperPageChangeListener();
    private OnPageChangeListener delegatePageChangeListener;

    private LinearLayout tabsContainer;
    private ViewPager viewPager;

    private int tabCount = 0;
    private int currentPosition = 0;
    private float currentPositionOffset = 0.0f;
    private int leastScrollX = 0;
    private boolean checkedTabWidths = false;

    private Paint rectPaint;
    private Paint linePaint;

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private int indicatorColor = 0xFF00BEFF;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private int scrollOffset = 48;
    private int indicatorHeight = 1;
    private int indicatorHorizontalMargin = 0;
    private int indicatorMarginBottom = 4;
    private int underlineHeight = 0;
    private int dividerWidth = 0;
    private int dividerVerticalMargin = 10;
    private int tabHorizontalPadding = 15;

    private boolean shouldExpand = false;
    private boolean textAllCaps = false;

    private int tabTextSize = 14;
    private int tabIndicatorTextSize = tabTextSize;
    private int tabTextColor = 0xFF666666;
    //    private int tabIndicatorTextColor = indicatorColor;
    private int tabIndicatorTextColor = 0xFF12B7F5;
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;
    private int tabBackgroundResId = R.drawable.background_tab_strip;

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
        indicatorHorizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHorizontalMargin, displayMetrics);
        indicatorMarginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorMarginBottom, displayMetrics);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, displayMetrics);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, displayMetrics);
        dividerVerticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerVerticalMargin, displayMetrics);
        tabHorizontalPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabHorizontalPadding, displayMetrics);

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
        scrollOffset = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_scrollOffset, scrollOffset);
        indicatorHeight = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_indicatorHeight, indicatorHeight);
        indicatorHorizontalMargin = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_indicatorHorizontalMargin, indicatorHorizontalMargin);
        indicatorMarginBottom = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_indicatorMarginBottom, indicatorMarginBottom);
        underlineHeight = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_underlineHeight, underlineHeight);
        dividerWidth = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_dividerWidth, dividerWidth);
        dividerVerticalMargin = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_dividerVerticalMargin, dividerVerticalMargin);
        tabHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_tabHorizontalPadding, tabHorizontalPadding);
        tabBackgroundResId = typedArray.getResourceId(R.styleable.PagerSlidingTabStrip_tabBackground, tabBackgroundResId);
        shouldExpand = typedArray.getBoolean(R.styleable.PagerSlidingTabStrip_shouldExpand, shouldExpand);
        textAllCaps = typedArray.getBoolean(R.styleable.PagerSlidingTabStrip_textAllCaps, textAllCaps);
        typedArray.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager != null) {
            this.viewPager = viewPager;
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            viewPager.setOnPageChangeListener(wrapperPageChangeListener);
            notifyDataSetChanged();
        } else {
            throw new NullPointerException("ViewPager is null.");
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener pageChangeListener) {
        this.delegatePageChangeListener = pageChangeListener;
    }

    public void notifyDataSetChanged() {
        tabsContainer.removeAllViews();
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        tabCount = pagerAdapter.getCount();
        if (pagerAdapter instanceof IconTabProvider) {
            IconTabProvider iconTabProvider = (IconTabProvider) pagerAdapter;
            for (int i = 0; i < tabCount; i++) {
                addIconTab(i, iconTabProvider.getPageIconResId(i));
            }
        } else {
            for (int i = 0; i < tabCount; i++) {
                addTextTab(i, pagerAdapter.getPageTitle(i));
            }
        }
        updateTabStyles();
        checkedTabWidths = false;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                currentPosition = viewPager.getCurrentItem();
                scrollToChild(currentPosition, 0);
            }
        });
    }

    private void addTextTab(int position, CharSequence title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        addTab(position, tab);
    }

    private void addIconTab(int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        addTab(position, tab);
    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(position, false);
            }
        });
        if (shouldExpand) {
            tab.setPadding(0, 0, 0, 0);
        } else {
            tab.setPadding(tabHorizontalPadding, 0, tabHorizontalPadding, 0);
        }
        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    private void updateTabStyles() {
        for (int i = 0; i < tabCount; i++) {
            View view = tabsContainer.getChildAt(i);
            view.setBackgroundResource(tabBackgroundResId);
            if (view instanceof TextView) {
                TextView tab = (TextView) view;
                tab.setTextSize(i == currentPosition ? tabIndicatorTextSize : tabTextSize);
                tab.setTextColor(i == currentPosition ? tabIndicatorTextColor : tabTextColor);
                tab.setTypeface(tabTypeface, tabTypefaceStyle);
                // setTextAllCaps() is only available from API 14, so the upper case is made manually
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
        if (newScrollX != leastScrollX) {
            leastScrollX = newScrollX;
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
        if (currentPositionOffset > 0.0f && currentPosition < tabCount - 1) {
            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();
            lineLeft = (currentPositionOffset * nextTabLeft + (1.0f - currentPositionOffset) * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1.0f - currentPositionOffset) * lineRight);
        }
        canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
        canvas.drawRect(lineLeft + tabHorizontalPadding - indicatorHorizontalMargin,
                height - indicatorHeight - indicatorMarginBottom,
                lineRight - tabHorizontalPadding + indicatorHorizontalMargin,
                height - indicatorMarginBottom, rectPaint);

        // draw underline
        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

        // draw divider
        linePaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerVerticalMargin, tab.getRight(), height - dividerVerticalMargin, linePaint);
        }
    }

    private class WrapperPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            currentPosition = position;
            currentPositionOffset = positionOffset;
            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
            invalidate();
            if (delegatePageChangeListener != null) {
                delegatePageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(viewPager.getCurrentItem(), 0);
            }
            if (delegatePageChangeListener != null) {
                delegatePageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < tabCount; i++) {
                View view = tabsContainer.getChildAt(i);
                if (view instanceof TextView) {
                    TextView tab = (TextView) view;
                    tab.setTextSize(i == viewPager.getCurrentItem() ? tabIndicatorTextSize : tabTextSize);
                    tab.setTextColor(i == viewPager.getCurrentItem() ? tabIndicatorTextColor : tabTextColor);
                }
            }
            if (delegatePageChangeListener != null) {
                delegatePageChangeListener.onPageSelected(position);
            }
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setIndicatorHorizontalMargint(int indicatorHorizontalMargin) {
        this.indicatorHorizontalMargin = indicatorHorizontalMargin;
        invalidate();
    }

    public int getIndicatorHorizontalMargin() {
        return indicatorHorizontalMargin;
    }

    public void setIndicatorMarginBottom(int indicatorMarginBottom) {
        this.indicatorMarginBottom = indicatorMarginBottom;
        invalidate();
    }

    public int getIndicatorMarginBottom() {
        return indicatorMarginBottom;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setUnderlineHeight(int underlineHeight) {
        this.underlineHeight = underlineHeight;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setDividerWidth(int dividerWidth) {
        this.dividerWidth = dividerWidth;
        invalidate();
    }

    public int getDividerWidth() {
        return dividerWidth;
    }

    public void setDividerVerticalMargin(int dividerVerticalMargin) {
        this.dividerVerticalMargin = dividerVerticalMargin;
        invalidate();
    }

    public int getDividerVerticalMargin() {
        return dividerVerticalMargin;
    }

    public void setTabHorizontalPadding(int tabHorizontalPadding) {
        this.tabHorizontalPadding = tabHorizontalPadding;
        updateTabStyles();
    }

    public int getTabHorizontalPadding() {
        return tabHorizontalPadding;
    }

    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
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
        updateTabStyles();
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
        updateTabStyles();
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTextSize(int textSize) {
        this.tabTextSize = textSize;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setIndicatorTextSize(int indicatorTextSize) {
        this.tabIndicatorTextSize = indicatorTextSize;
        updateTabStyles();
    }

    public int getIndicatorTextSize() {
        return tabIndicatorTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setIndicatorTextColor(int indicatorTextColor) {
        this.tabIndicatorTextColor = indicatorTextColor;
        updateTabStyles();
    }

    public void setIndicatorTextColorResource(int resId) {
        this.tabIndicatorTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getIndicatorTextColor() {
        return tabIndicatorTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    public static class SavedState extends BaseSavedState {
        public int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
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