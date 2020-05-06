package com.qxtx.idea.idearecyclerview.view;

import android.support.annotation.Nullable;

import com.qxtx.idea.idearecyclerview.item.ItemAction;

import java.util.List;

/**
 * @author QXTX-WIN
 * @date 2020/4/18 13:02
 * Description: {@link IdeaRecyclerView}控件实现的接口
 * @param <D> 数据集类型
 *
 * @see IdeaRecyclerView
 */
public interface IRecyclerView<D> {

    /** 获取列表数据集 */
    List<D> getListData();

    /** 完整地刷新一次列表 */
    void refresh();

    /** 清空整个列表 */
    void removeAllItem();
}
