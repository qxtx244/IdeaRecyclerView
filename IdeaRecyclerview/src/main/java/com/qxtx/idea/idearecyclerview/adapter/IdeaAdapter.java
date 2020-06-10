package com.qxtx.idea.idearecyclerview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxtx.idea.idearecyclerview.item.ItemAction;
import com.qxtx.idea.idearecyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.idearecyclerview.tool.IdeaRvLog;
import com.qxtx.idea.idearecyclerview.view.IdeaRecyclerView;
import com.qxtx.idea.idearecyclerview.viewHolder.IdeaViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author QXTX-WIN
 * @date 2020/4/15 23:34
 *
 * <pre>
 *
 * Description: {@link com.qxtx.idea.idearecyclerview.view.IRecyclerView} 的adapter
 *   此适配器可以适配大多数{@link RecyclerView}
 * @param <D> 数据集类型
 *
 * </pre>
 */
public final class IdeaAdapter<D>
        extends IdeaRecyclerView.Adapter<IdeaViewHolder> implements IAdapter<D> {

    /**
     * 拥有独立行为描述的item对象集。当某个item被绑定了一个独立的行为描述，全局行为描述将对它无效。
     * 当列表项发生了增删，需要同步更新这个数据集
     * [String] item在列表中的唯一识别标识，带已有标识的项被添加进来，将会替换掉已存在的相同标识项
     * [ItemAction<D>] item绑定的行为描述
     */
    private final HashMap<D, ItemAction<D>> mSpecItemActionMap;

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
    private ItemAction<D> mItemActionGlobal;

    /** 非法的layoutId */
    private final int NO_LAYOUT_ID = Integer.MIN_VALUE;

    /**
     * 构造方法
     * @param context 外部宿主上下文
     * @param layoutFactory 列表项的布局配置类，定义了列表中每一项的布局方案
     */
    public IdeaAdapter(@NonNull Context context, @NonNull ItemLayoutFactory layoutFactory) {
        this.mContext = new WeakReference<>(context);
        mLayoutFactory = layoutFactory;
        mListData = null;
        mSpecItemActionMap = new HashMap<>();
    }

    /** 当item的行为描述被改变，adapter自动刷新一次。允许动态配置 */
    @Override
    public void setItemAction(ItemAction<D> action) {
        if (mItemActionGlobal == action) {
            return ;
        }

        mItemActionGlobal = action;
        notifyDataSetChanged();
    }

    @Override
    public List<D> getListData() {
        return mListData;
    }

    @Override
    public void setListData(@Nullable List<D> data) {
        if (mListData == data) {
            logI("当前数据集和目标数据集相同，忽略本次设置");
            return ;
        }
        mListData = data;
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
    @NonNull
    @Override
    public IdeaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
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
    public void onBindViewHolder(@NonNull IdeaViewHolder viewHolder, int pos) {
        if (mListData == null || mListData.size() == 0) {
            logI("列表没有数据!");
            return ;
        }

        D data = mListData.get(pos);
        //如果对应的列表项携带独立的行为描述，则忽略掉全局行为描述。
        ItemAction<D> action = mItemActionGlobal;
        if (mSpecItemActionMap.containsKey(data)) {
            action = mSpecItemActionMap.get(data);
        }

        if (action != null) {
            action.onItemAction(viewHolder, mListData, pos);
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
     * 通过position，获取到item的layoutId，默认或者{@link RecyclerView.Adapter#hasStableIds()}返回false，
     *  此方法应该返回{@link RecyclerView#NO_ID}
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

    public void addItem(int pos, D data, @Nullable ItemAction<D> action) {
        if (pos < 0) {
            IdeaRvLog.I("添加列表项失败，非法的列表目标位置");
            return ;
        }

        if (mListData == null) {
            mListData = new ArrayList<>();
        }

        pos = pos > mListData.size() ? mListData.size() : pos;

        if (data == null && action != null) {
            IdeaRvLog.I("添加列表项失败，如果指定列表项使用独立的行为描述，必须携带非空数据");
            return ;
        }

        mSpecItemActionMap.put(data, action);

        mListData.add(pos, data);
        notifyItemInserted(pos);
    }

    public void removeItem(int pos) {
        if (mListData == null) {
            return ;
        }

        if (pos < 0 || pos > mListData.size()) {
            return ;
        }

        mListData.remove(pos);
        mSpecItemActionMap.remove(mListData.get(pos));
        notifyItemRemoved(pos);
    }

    @Override
    public void removeAll() {
        if (mListData == null || mListData.size() == 0) {
            return ;
        }

        mListData.clear();
        notifyDataSetChanged();
    }

    private void logI(@NonNull String msg) {
        Log.i(getClass().getSimpleName(), msg);
    }

    private void logE(@NonNull String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }
}
