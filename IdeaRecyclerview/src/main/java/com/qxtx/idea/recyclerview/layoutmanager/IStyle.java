package com.qxtx.idea.recyclerview.layoutmanager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:15
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 对列表布局的描述类。
 *   {@link com.qxtx.idea.recyclerview.view.IRecyclerView}必须使用此接口的实现类进行列表布局的描述。
 */
public interface IStyle {

    /**
     * 获取真正的RecyclerView.LayoutManager对象
     * @return RecyclerView.LayoutManager对象
     */
    <T extends RecyclerView.LayoutManager>T getLayoutManager();
}
