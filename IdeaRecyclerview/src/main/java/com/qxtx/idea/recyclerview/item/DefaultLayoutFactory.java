package com.qxtx.idea.recyclerview.item;

import android.support.annotation.NonNull;

import com.qxtx.idea.recyclerview.adapter.IdeaAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * CreateDate 2020/6/11 22:58
 * <p>
 *
 * @author QXTX-WIN
 * Description: {@link ItemLayoutFactory}的实现类，通过List对象来保存列表项的布局id，不可修改
 */
public class DefaultLayoutFactory implements ItemLayoutFactory {
    private final List<Integer> layoutList;

    public DefaultLayoutFactory(@NonNull List<Integer> layoutList) {
        this.layoutList = new ArrayList<>();
        this.layoutList.addAll(layoutList);
    }

    @Override
    public int getLayoutId(int position) {
        if (position < 0 || position >= layoutList.size()) {
            return IdeaAdapter.NO_LAYOUT_ID;
        }

        return layoutList.get(position);
    }
}
