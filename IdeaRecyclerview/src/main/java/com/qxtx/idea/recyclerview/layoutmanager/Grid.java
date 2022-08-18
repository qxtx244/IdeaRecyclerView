package com.qxtx.idea.recyclerview.layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:23
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 网格列表风格。支持动态改变宫格列表项所占的位宽
 */
public class Grid extends BaseStyle {

    public Grid( Context context, int spanCount, @Orientation int orientation) {
        this(context, spanCount, orientation, false);
    }

    public Grid( Context context, int spanCount, @Orientation int orientation, boolean isReverse) {
        super(context);
        spanCount = Math.max(spanCount, 1);
        setLayoutManager(new FlexSpanLayoutManager(context, spanCount, orientation, isReverse));
    }

    @Override
    public <T extends RecyclerView.LayoutManager>T getLayoutManager() {
        return super.getLayoutManager();
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        ((GridLayoutManager)mLayoutManager).setSpanSizeLookup(spanSizeLookup);
    }

    private static final class FlexSpanLayoutManager extends GridLayoutManager {

        private FlexSpanLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        @Override
        public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
            super.setSpanSizeLookup(spanSizeLookup);
        }
    }
}
