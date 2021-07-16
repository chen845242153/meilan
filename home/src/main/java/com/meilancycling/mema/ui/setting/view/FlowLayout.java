package com.meilancycling.mema.ui.setting.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.meilancycling.mema.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流
 *
 * @author lion
 */
public class FlowLayout extends ViewGroup {
    /**
     * 记录所有的行，一行一行的存储，用于layout
     */
    private List<List<View>> allLines = new ArrayList<>();

    /**
     * 记录每一行的行高，用于layout
     */
    List<Integer> lineHeights = new ArrayList<>();

    private int mHorizontalSpacing = AppUtils.dipToPx(getContext(), 10);
    private int mVerticalSpacing = AppUtils.dipToPx(getContext(), 10);


    public FlowLayout(Context context) {
        super(context);
    }

    //反射
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //主题style
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void clearMeasureParams() {
        allLines.clear();
        lineHeights.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams();//内存 抖动
        //先度量孩子
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        //ViewGroup解析的父亲给我的宽度
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        // ViewGroup解析的父亲给我的高度
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);
        //保存一行中的所有的view
        List<View> lineViews = new ArrayList<>();
        //记录这行已经使用了多宽的size
        int lineWidthUsed = 0;
        // 一行的行高
        int lineHeight = 0;
        // measure过程中，子View要求的父ViewGroup的宽
        int parentNeededWidth = 0;
        // measure过程中，子View要求的父ViewGroup的高
        int parentNeededHeight = 0;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams childLP = childView.getLayoutParams();
            if (childView.getVisibility() != View.GONE) {
                //将layoutParams转变成为 measureSpec
                int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight,
                        childLP.width);
                int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom,
                        childLP.height);
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

                //获取子view的度量宽高
                int childMesauredWidth = childView.getMeasuredWidth();
                int childMeasuredHeight = childView.getMeasuredHeight();


                //如果需要换行
                if (childMesauredWidth + lineWidthUsed + mHorizontalSpacing > selfWidth) {

                    //一旦换行，我们就可以判断当前行需要的宽和高了，所以此时要记录下来
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);

                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);

                    lineViews = new ArrayList<>();
                    lineWidthUsed = 0;
                    lineHeight = 0;
                }
                // view 是分行layout的，所以要记录每一行有哪些view，这样可以方便layout布局
                lineViews.add(childView);
                //每行都会有自己的宽和高
                lineWidthUsed = lineWidthUsed + childMesauredWidth + mHorizontalSpacing;
                lineHeight = Math.max(lineHeight, childMeasuredHeight);

                //处理最后一行数据
                if (i == childCount - 1) {
                    allLines.add(lineViews);
                    lineHeights.add(lineHeight);
                    parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                    parentNeededWidth = Math.max(parentNeededWidth, lineWidthUsed + mHorizontalSpacing);
                }

            }
        }

        //再度量自己,保存
        //根据子View的度量结果，来重新度量自己ViewGroup
        // 作为一个ViewGroup，它自己也是一个View,它的大小也需要根据它的父亲给它提供的宽高来度量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int realWidth = (widthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeededWidth;
        int realHeight = (heightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();
        int curL = getPaddingLeft();
        int curT = getPaddingTop();

        for (int i = 0; i < lineCount; i++) {
            List<View> lineViews = allLines.get(i);

            int lineHeight = lineHeights.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View view = lineViews.get(j);
                int left = curL;
                int top = curT;

//                int right = left + view.getWidth();
//                int bottom = top + view.getHeight();

                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
                curL = right + mHorizontalSpacing;
            }
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();
        }
    }

}
