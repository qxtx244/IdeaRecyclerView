package com.qxtx.idea.recyclerview.view;

import android.view.View;

import androidx.annotation.NonNull;

import com.qxtx.idea.recyclerview.adapter.IdeaAdapter;
import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.util.List;

/**
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Create Date</b></p> 2020/4/18 13:02
 * <p>
 * <p><b>Description</b></p>: {@link IdeaRecyclerView}控件实现的接口
 * @param <D> 数据集类型
 *
 * @see IdeaRecyclerView
 */
public interface IRecyclerView<D> {

    /**
     * 获取封装的ViewHolder对象
     * @param child 列表项控件
     * @return
     */
    IdeaViewHolder getIdeaViewHolder(@NonNull View child);

    /**
     * 获取适配器
     * @return
     */
    IdeaAdapter<D> getIdeaAdapter();

    /**
     * 获取列表数据集
     * @return
     */
    List<D> getListData();

    /** 完整地刷新一次列表 */
    void refresh();

    /** 清空整个列表 */
    void removeAllItem();
}
