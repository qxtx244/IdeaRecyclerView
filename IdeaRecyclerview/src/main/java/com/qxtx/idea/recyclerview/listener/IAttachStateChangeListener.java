package com.qxtx.idea.recyclerview.listener;

import android.view.View;

/**
 * @author QXTX-WORK
 * <p>
 * <b>Create Date</b><p> 2022/8/19 9:28
 * <p>
 * <b>Description</b>
 * <pre>
 * 列表项绑定/解绑事件监听器，与RecyclerView.OnChildAttachStateChangeListener属同一类事件监听器
 * </pre>
 */
public interface IAttachStateChangeListener<D> {

    /**
     * 列表项绑定到列表（屏幕上变得可见）
     * @param v 列表项控件
     * @param pos 列表项在列表中的实际位置
     * @param data 列表项绑定的数据
     */
    void onChildAttach(View v, int pos, D data);

    /**
     * 列表项从列表中解绑（屏幕上变得不可见）
     * @param v 列表项控件
     * @param pos 列表项在列表中的实际位置
     * @param data 列表项绑定的数据
     */
    void onChildDetach(View v, int pos, D data);
}
