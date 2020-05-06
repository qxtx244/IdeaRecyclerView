package com.qxtx.idea.idearecyclerview.layoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * CreateDate 2020/4/22 22:36
 * <p>
 *
 * @author QXTX-WIN
 * Description: 瀑布流列表风格
 */
public class StaggeredGrid extends BaseStyle {

    public StaggeredGrid(@NonNull Context context, int spanCount, @Orientation int orientation) {
        super(context);
        spanCount = spanCount <= 0 ? 1 : spanCount;

        setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }
}
