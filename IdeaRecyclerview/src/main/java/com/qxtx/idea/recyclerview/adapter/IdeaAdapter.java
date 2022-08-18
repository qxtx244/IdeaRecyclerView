package com.qxtx.idea.recyclerview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxtx.idea.recyclerview.item.ItemAction;
import com.qxtx.idea.recyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.recyclerview.view.IdeaRecyclerView;
import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Create Date</b></p> 2020/4/15 23:34
 *
 * <p>
 * <p><b>Description</b></p>: {@link com.qxtx.idea.recyclerview.view.IRecyclerView} 的adapter
 *   此适配器可以适配大多数RecyclerView
 * @param <D> 数据集类型
 */
public final class IdeaAdapter<D>
        extends IdeaRecyclerView.Adapter<IdeaViewHolder>
        implements IAdapter<D> {

    /**
     * 外部宿主的上下文
     */
    private WeakReference<Context> mContext;

    /**
     * 列表项的布局配置类，定义了列表中每一项的布局方案
     */
    private ItemLayoutFactory mLayoutFactory;

    /**
     * 列表绑定的数据集。
     * @see #setListData(List)
     */
    private List<D> mListData;

    /**
     * item的行为描述抽出来，交由外部完成
     *
     * @see #setItemAction(ItemAction)
     * @see #onBindViewHolder(IdeaViewHolder, int)
     */
    private ItemAction<D> mItemAction;

    /** 非法的layoutId */
    private final int NO_LAYOUT_ID = Integer.MIN_VALUE;

    /**
     * 构造方法
     * @param context 外部宿主上下文
     * @param layoutFactory 列表项的布局配置类，定义了列表中每一项的布局方案
     */
    public IdeaAdapter( Context context,  ItemLayoutFactory layoutFactory) {
        this.mContext = new WeakReference<>(context);
        mLayoutFactory = layoutFactory;
        mListData = new ArrayList<>();
    }

    /** 当item的行为描述被改变，adapter自动刷新一次。允许动态配置 */
    @Override
    public void setItemAction(ItemAction<D> action) {
        if (mItemAction == action) {
            return ;
        }

        mItemAction = action;
        notifyDataSetChanged();
    }

    @Override
    public List<D> getListData() {
        if (mListData == null) {
            mListData = new ArrayList<>();
        }
        return mListData;
    }

    @Override
    public void setListData(List<D> data) {
        if (mListData == data) {
            logI("当前数据集和目标数据集相同，忽略本次设置");
            return ;
        }
        if (data == null) {
            mListData.clear();
        } else {
            mListData = data;
        }
        notifyDataSetChanged();
    }

    /**
     * RecyclerView调用此方法来获得一个ViewHolder对象，去和一个新item绑定
     *
     * @param viewGroup 和返回的ViewoHolder绑定的item所添加到的目标父布局
     * @param viewType “The view type of the new View.” 通过{@link #getItemViewType(int)}获得
     * @return ViewHolder对象，和viewType唯一对应
     *
     * @see #getItemViewType(int)
     */

    @Override
    public IdeaViewHolder onCreateViewHolder( ViewGroup viewGroup, int viewType) {
        if (mContext == null || mContext.get() == null) {
            throw new IllegalStateException("ERROR! Get a null context. It usually means the host activity is death");
        }

        Context context = mContext.get();

        View item = null;
        try {
            item = LayoutInflater.from(context).inflate(viewType, viewGroup, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (item == null) {
            throw new IllegalStateException("ERROR! Get a unavailable viewType. Can't not create the ViewHolder for item. viewType=" + viewType);
        }

        return new IdeaViewHolder(item);
    }

    @Override
    public void onBindViewHolder( IdeaViewHolder viewHolder, int itemPos) {
        if (mListData == null || mListData.isEmpty()) {
            logI("列表没有数据!");
            return ;
        }

        if (mItemAction != null) {
            mItemAction.onItemAction(viewHolder, mListData, itemPos);
        }
    }

    /**
     * 返回item的viewType，根据源码注释，这个viewType应该具备唯一找到对应item的能力，因此，这个viewType可以是item的layoutId
     * @param position position to query
     * @return viewType
     *
     * @see #onCreateViewHolder(ViewGroup, int)
     */
    @Override
    public int getItemViewType(int position) {
        if (position < 0) {
            return super.getItemViewType(position);
        }

        return mLayoutFactory == null ? NO_LAYOUT_ID : mLayoutFactory.getLayoutId(position);
    }

    /**
     * 通过position，获取到item的layoutId，如果RecyclerView.Adapter.hasStableIds()返回true，则直接使用超类逻辑，
     * @param position item的位置
     * @return layout id
     */
    @Override
    public long getItemId(int position) {
        if (hasStableIds()) {
            return super.getItemId(position);
        }

        return mLayoutFactory.getLayoutId(position);
    }

    /** 获取item的个数，
     * “注意此值不一定就是列表的item个数（偶然网上看到的，待验证）”
     */
    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    private void logI( String msg) {
        Log.i(getClass().getSimpleName(), msg);
    }

    private void logE( String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }
}
