package com.qxtx.idea.idearecyclerview.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;

import com.qxtx.idea.idearecyclerview.adapter.IAdapter;
import com.qxtx.idea.idearecyclerview.item.ItemAction;
import com.qxtx.idea.idearecyclerview.item.ItemLayoutFactory;

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

    /** @see #addItem(int, Object, ItemAction)   */
    void addItem(int pos, D data);

    /** @see #addItem(int, Object, int, ItemAction)   */
    void addItem(int pos, D data, ItemAction<D> action);

    /**
     * 在列表的指定位置添加一个项，这个项如果带有独立的行为描述，则它必须存在一个列表项唯一标识。
     * 当未使用{@link IdeaRecyclerView#option(ItemLayoutFactory)}来完成配置而是以{@link RecyclerView}的常规方法配置时，此方法无效。
     * 在列表控件未配置好时，此api没有意义
     * @param pos 列表的目标添加位置，不得小于0。当大于列表当前长度时，默认添加到列表末尾
     * @param data 目标列表项的数据
     * @param action item的行为描述对象。第一个成员为item在列表中的唯一标识符，第二个成员为item的独有行为描述。如果存在行为描述对象，则唯一标识符不能为null。
     *
     * @see IAdapter#addItem(int, Object, int, ItemAction)
     */
    void addItem(int pos, D data, int layoutId, ItemAction<D> action);

    /**
     * 从列表中移除指定位置的项。注意，当项被移除后，此项的独立行为描述也不再可用
     * 在列表控件未配置好时，此api没有意义
     * @param pos 项在列表中的位置
     *
     * @see IAdapter#removeItem(int)
     */
    void removeItem(int pos);

    /**
     * 清空整个列表
     * @see IAdapter#removeAll()
     */
    void removeAll();

    /**
     * 获取列表数据集，可能为null
     * @see IAdapter#getListData() 
     */
    List<D> getListData();

    /**
     * 获取与{@link IdeaRecyclerView}绑定的{@link IAdapter}对象。
     * @return {@link RecyclerView#getAdapter()}不同，如果不为{@link IAdapter}的实现类，则返回null
     */
    IAdapter<D> getIdeaAdapter();

    /** 完整地刷新一次列表 */
    void refresh();
}
