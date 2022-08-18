package com.qxtx.idea.recyclerview.layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:13
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 线性列表风格。支持启用/禁用列表滚动
 */
public class Linear extends BaseStyle {

    public Linear( Context context, @Orientation int orientation) {
        this(context, orientation, false);
    }

    public Linear(Context context, @Orientation int orientation, boolean isReverse) {
        super(context);
        setLayoutManager(new ScrollControlLayoutManager(context, orientation, isReverse));
    }

    @Override
    public <T extends RecyclerView.LayoutManager>T getLayoutManager() {
        return super.getLayoutManager();
    }

    /**
     * 是否允许列表滚动
     * @return
     */
    public boolean isScrollEnable() {
        ScrollControlLayoutManager llm = getLayoutManager();
        return llm.getOrientation() == LinearLayoutManager.VERTICAL ? llm.isScrollVerEnable() : llm.isScrollHorEnable();
    }

    /**
     * 是否允许列表滚动。当置为false时，列表的滚动总是被禁止，即使它还未到达两端。
     * @param scrollEnable 列表滚动启用/禁用
     */
    public void setScrollEnable(boolean scrollEnable) {
        ScrollControlLayoutManager llm = getLayoutManager();
        if (llm.getOrientation() == LinearLayoutManager.VERTICAL) {
            llm.setScrollVerEnable(scrollEnable);
        } else {
            llm.setScrollHorEnable(scrollEnable);
        }
    }

    private static final class ScrollControlLayoutManager extends LinearLayoutManager {

        private boolean scrollHorEnable = true;
        private boolean scrollVerEnable = true;

        private ScrollControlLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public boolean canScrollHorizontally() {
            return scrollHorEnable && super.canScrollHorizontally();
        }

        @Override
        public boolean canScrollVertically() {
            return scrollVerEnable && super.canScrollVertically();
        }

        private boolean isScrollHorEnable() {
            return scrollHorEnable;
        }

        private void setScrollHorEnable(boolean scrollHorEnable) {
            this.scrollHorEnable = scrollHorEnable;
        }

        private boolean isScrollVerEnable() {
            return scrollVerEnable;
        }

        private void setScrollVerEnable(boolean scrollVerEnable) {
            this.scrollVerEnable = scrollVerEnable;
        }
    }
}
