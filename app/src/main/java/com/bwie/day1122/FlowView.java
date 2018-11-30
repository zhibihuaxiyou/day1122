package com.bwie.day1122;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author：张腾
 * date：2018/11/22
 */
public class FlowView extends ViewGroup {
    private List<List<View>> mChildViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();
    public FlowView(Context context) {
        this(context, null);
    }
    public FlowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;
        int height = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;

            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mChildViews.clear();
        mLineHeight.clear();
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineVies = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            if (childWidth + lineWidth + lp.rightMargin + lp.leftMargin > width) {
                mLineHeight.add(lineHeight);
                mChildViews.add(lineVies);
                lineWidth = 0;
                lineHeight = childHeight + lp.bottomMargin + lp.topMargin;
                lineVies = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, lineHeight + lp.topMargin + lp.bottomMargin);
            lineVies.add(child);
        }
        mLineHeight.add(lineHeight);
        mChildViews.add(lineVies);
        int left = 0;
        int top = 0;
        int lineCount = mChildViews.size();
        for (int i = 0; i < lineCount; i++) {
            lineVies = mChildViews.get(i);
            lineHeight = mLineHeight.get(i);
            for (int j = 0; j < lineVies.size(); j++) {
                View child = lineVies.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cleft = left + lp.leftMargin;
                int ctop = top + lp.topMargin;
                int cright = cleft + child.getMeasuredWidth();
                int cbottom = ctop + child.getMeasuredHeight();
                child.layout(cleft, ctop, cright, cbottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

}

