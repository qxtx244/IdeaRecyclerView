package com.qxtx.idea.recyclerview.layoutmanager;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * CreateDate 2020/4/22 22:36
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 瀑布流列表风格
 */
public class StaggeredGrid extends BaseStyle {

    public StaggeredGrid( Context context, int spanCount, @Orientation int orientation) {
        super(context);
        spanCount = spanCount <= 0 ? 1 : spanCount;

        setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
    }

    @Override
    public <T extends RecyclerView.LayoutManager>T getLayoutManager() {
        return super.getLayoutManager();
    }
}
