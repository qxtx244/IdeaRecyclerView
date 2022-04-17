package com.qxtx.idea.recyclerview.item;

/**
 * CreateDate 2020/6/25 13:03
 * <p>
 *
 * @author QXTX-WIN
 * Description: 默认的item描述对象。可选是否描述item的attach和detach行为
 */
public abstract class DefaultItemAction<D> implements ItemAction<D> {

    @Override
    public void onItemAttach() {
        //do nothing.
    }

    @Override
    public void onItemDetach() {
        //do nothing.
    }
}
