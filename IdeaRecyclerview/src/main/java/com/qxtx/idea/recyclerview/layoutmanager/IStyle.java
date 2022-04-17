package com.qxtx.idea.recyclerview.layoutmanager;

import android.support.v7.widget.RecyclerView;

/**
 * CreateDate 2020/4/22 22:15
 * <p>
 *
 * @author QXTX-WIN
 * Description: 对列表布局的描述类。
 *   {@link com.qxtx.idea.recyclerview.view.IRecyclerView}必须使用此接口的实现类进行列表布局的描述。
 */
public interface IStyle {

    /** 获取真正的{@link android.support.v7.widget.RecyclerView.LayoutManager}对象 */
    RecyclerView.LayoutManager getLayoutManager();
}
