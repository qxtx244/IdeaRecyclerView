package com.qxtx.idea.recyclerview.item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.util.List;

/**
 * CreateDate 2020/4/19 23:23
 * <p>
 *
 * @author QXTX-WIN
 * Description: 列表项的具体动作描述接口
 */
public interface ItemAction<D> {

    /**
     * 当item被绑定到列表时，回调一次此方法。这通常发生在item被刷新或者列表被刷新时
     * @param holder item的holder类，通过此类，可以找到item的控件
     * @param data 列表数据集，可能是空的
     * @param itemPos item在列表中的位置
     */
    void onItemBind(@NonNull IdeaViewHolder holder, @Nullable List<D> data, int itemPos);

    /**
     * 当item处于被列表显示的位置，回调一次此方法
     */
    void onItemAttach();

    /**
     * 当item处于被列表移除的位置，回调一次此方法
     */
    void onItemDetach();
}
