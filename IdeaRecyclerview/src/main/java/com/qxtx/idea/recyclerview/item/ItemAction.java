package com.qxtx.idea.recyclerview.item;

import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.util.List;

/**
 * CreateDate 2020/4/19 23:23
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 列表项的具体行为描述接口
 */
public interface ItemAction<D> {

    /**
     * 每一个item被绑定到列表时，回调一次此方法。
     * @param holder item的holder类，通过此类，可以找到item的控件
     * @param data 列表数据集
     * @param pos item在列表中的位置
     */
    void onItemAction(IdeaViewHolder holder, List<D> data, int pos);
}
