package com.qxtx.idea.recyclerview.layoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:23
 * <p>
 *
 * @author QXTX-WIN
 * Description: 网格列表风格
 */
public class GridStyle extends BaseStyle {

    public GridStyle(@NonNull Context context, int spanCount, @Orientation int orientation) {
        this(context, spanCount, orientation, false);
    }

    public GridStyle(@NonNull Context context, int spanCount, @Orientation int orientation, boolean isReverse) {
        super(context);
        spanCount = spanCount < 1 ? 1 : spanCount;
        setLayoutManager(new GridLayoutManager(context, spanCount, orientation, isReverse));
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }
}
