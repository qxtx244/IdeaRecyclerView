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
import com.qxtx.idea.idearecyclerview.view.IdeaRecyclerView;
import com.qxtx.idea.idearecyclerview.viewHolder.IdeaViewHolder;

import java.lang.ref.WeakReference;
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
 * 一些开源的RecyclerView的LayoutManager：
 * 1、FanLayoutManager：https://github.com/Cleveroad/FanLayoutManager 扇叶转动
 * 2、CarouselLayoutManager：https://github.com/Azoft/CarouselLayoutManager 传送带（卡片轮播）效果
 * 3、ChipsLayoutManager：https://github.com/BelooS/ChipsLayoutManager 流式布局效果（标签云）
 * 4、HiveLayoutManager：https://github.com/Chacojack/HiveLayoutManager 蜂巢效果（国人作品）
 * 5、vLayout：https://github.com/alibaba/vlayout 布局混排效果（天猫app所使用）
 * 6、flexbox-layout https://github.com/google/flexbox-layout flexbox效果（谷歌的东西，原本不支持recyclerView）
 * 7、LondonEyeLayoutManager https://github.com/danylovolokh/LondonEyeLayoutManager 环形菜单效果
 *
 * </pre>
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
    public IdeaAdapter(@NonNull Context context, @NonNull ItemLayoutFactory layoutFactory) {
        this.mContext = new WeakReference<>(context);
        mLayoutFactory = layoutFactory;
        mListData = null;
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
    public void onBindViewHolder(@NonNull IdeaViewHolder viewHolder, int itemPos) {
        if (mListData == null || mListData.size() == 0) {
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

    private void logI(@NonNull String msg) {
        Log.i(getClass().getSimpleName(), msg);
    }

    private void logE(@NonNull String msg) {
        Log.e(getClass().getSimpleName(), msg);
    }
}
