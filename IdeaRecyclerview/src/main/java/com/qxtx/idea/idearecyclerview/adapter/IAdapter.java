package com.qxtx.idea.idearecyclerview.adapter;

import com.qxtx.idea.idearecyclerview.item.ItemAction;

import java.util.List;

/**
 * CreateDate 2020/4/20 23:45
 * <p>
 *
 * @author QXTX-WIN
 * Description: 适配器接口。{@link com.qxtx.idea.idearecyclerview.view.IRecyclerView}的适配器必须实现此接口
 *
 * @see {@link IdeaAdapter}
 */
public interface IAdapter<D> {

    /** 配置列表item的具体行为 */
    void setItemAction(ItemAction<D> action);

    /** 获得adapter的数据集 */
    List<D> getListData();

    /** 为adapter设置数据集。 */
    void setListData(List<D> listData);
}
