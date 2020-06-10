package com.qxtx.idea.idearecyclerview.adapter;

import android.support.annotation.Nullable;

import com.qxtx.idea.idearecyclerview.item.ItemAction;
import com.qxtx.idea.idearecyclerview.item.ItemLayoutFactory;

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

    /**
     * 在列表的指定位置添加一个项，这个项如果带有独立的行为描述，则它必须存在一个列表项唯一标识。
     * 当未使用{@link com.qxtx.idea.idearecyclerview.view.IdeaRecyclerView#option(ItemLayoutFactory)}来完成配置而是以{@link android.support.v7.widget.RecyclerView}的常规方法配置时，此方法无效。
     * @param pos 列表的目标添加位置，不得小于0。当大于列表当前长度时，默认添加到列表末尾
     * @param data 目标列表项的数据
     * @param action item的行为描述对象。第一个成员为item在列表中的唯一标识符，第二个成员为item的独有行为描述。如果存在行为描述对象，则唯一标识符不能为null。
     */
    void addItem(int pos, D data, @Nullable ItemAction<D> action);

    /**
     * 从列表中移除指定位置的项。注意，当项被移除后，此项的独立行为描述也不再可用
     * @param pos 项在列表中的位置
     */
    void removeItem(int pos);

    /**
     * 移除列表中所有的项
     */
    void removeAll();
}
