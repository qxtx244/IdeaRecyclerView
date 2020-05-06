package com.qxtx.idea.idearecyclerview.layoutmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:13
 * <p>
 *
 * @author QXTX-WIN
 * Description: 线性列表风格
 */
public class Linear extends BaseStyle {

    public Linear(@NonNull Context context, @BaseStyle.Orientation int orientation) {
        this(context, orientation, false);
    }

    public Linear(@NonNull Context context, @BaseStyle.Orientation int orientation, boolean isReverse) {
        super(context);
        setLayoutManager(new LinearLayoutManager(context, orientation, isReverse));
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return super.getLayoutManager();
    }
}
