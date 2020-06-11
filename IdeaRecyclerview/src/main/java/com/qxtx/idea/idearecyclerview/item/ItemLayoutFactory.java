package com.qxtx.idea.idearecyclerview.item;

import android.support.annotation.LayoutRes;

/**
 * @author QXTX-WIN
 * @date 2020/4/18 18:22
 * Description: 支持多样式item布局工厂类，使用此类配合{@link com.qxtx.idea.idearecyclerview.view.IRecyclerView}
 *   完成复杂item布局的设计。
 *
 * @see DefaultLayoutFactory
 */
public interface ItemLayoutFactory {

    /**
     * 获取指定位置的item的layoutId
     *
     * @param position item的位置
     * @return item的layoutId
     */
    @LayoutRes int getLayoutId(int position);
}
