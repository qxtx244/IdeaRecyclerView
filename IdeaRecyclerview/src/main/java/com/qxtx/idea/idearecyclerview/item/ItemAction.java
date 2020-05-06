package com.qxtx.idea.idearecyclerview.item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qxtx.idea.idearecyclerview.viewHolder.IdeaViewHolder;

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
     * 每一个item被绑定到列表时，回调一次此方法。
     * @param holder item的holder类，通过此类，可以找到item的控件
     * @param data 列表数据集，可能是空的
     * @param itemPos item在列表中的位置
     */
    void onItemAction(@NonNull IdeaViewHolder holder, @Nullable List<D> data, int itemPos);
}
