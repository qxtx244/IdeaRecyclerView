package com.qxtx.idea.recyclerview.item;

/**
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Create Date</b></p> 2020/4/18 18:22
 * <p>
 * <p><b>Description</b></p>: 支持多样式item布局工厂类，使用此类配合{@link com.qxtx.idea.recyclerview.view.IRecyclerView}
 *   完成复杂item布局的设计。
 */
public interface ItemLayoutFactory {

    /**
     * 获取指定位置的item的layoutId
     *
     * @param position item的位置
     * @return item的layoutId，必须是有效的资源id
     */
    int getLayoutId(int position);
}
