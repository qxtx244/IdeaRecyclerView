package com.qxtx.idea.recyclerview.viewHolder;

import android.view.View;

/**
 * CreateDate 2020/4/19 13:28
 * <p>
 *
 * @author QXTX-WIN
 * Description: 列表项实现复用的接口。
 *   {@link com.qxtx.idea.recyclerview.view.IRecyclerView}的ViewHolder必须实现此接口
 *
 * @see IdeaViewHolder
 */
public interface IViewHolder {

    /**
     * 设置view的可见性
     * @param visibility 见{@link View#VISIBLE}，{@link View#GONE}和{@link View#INVISIBLE}
     */
    void setItemVisibility(int visibility);

    /**
     * 为item设置监听器。当设置监听器之后，item布局将会自动被加上android:descendantFocusability="beforeDescendant"属性
     * @param listener item的监听器
     */
    void setItemListener(Object listener);

    /** 通过id，获取item中的子控件  */
    View getViewById(int id);

    /** 获取item对象 */
    View getItem();
}
