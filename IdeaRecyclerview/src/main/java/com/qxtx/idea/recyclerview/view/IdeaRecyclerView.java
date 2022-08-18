package com.qxtx.idea.recyclerview.view;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

import com.qxtx.idea.recyclerview.adapter.IdeaAdapter;
import com.qxtx.idea.recyclerview.item.ItemAction;
import com.qxtx.idea.recyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.recyclerview.layoutmanager.IStyle;
import com.qxtx.idea.recyclerview.tool.IdeaRvLog;
import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.util.List;

/**
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Create Date</b></p> 2020/4/15 23:32
 * <p>
 * <p><b>Description</b></p>: 对RecyclerView的二次封装，使用起来更加方便
 * @param <D> 数据集类型
 *
 * <pre>
 *   存在仅此一条路径去配置，即通过一个固定的方法作为入口，创建内部实现类，由这个实现类去完成真正的配置工作
 *
 *   配置实现：
 *       1. xml布局中使用此自定义控件
 *       2. 在得到View实例后，通过{@link #option(ItemLayoutFactory)}方法得到配置实现对象
 *       3. 调用其它如{@link Impl#setListData(List)}、{@link Impl#setItemAction(ItemAction)}
 *          和 {@link Impl#setListStyle(IStyle)}等配置方法
 * </pre>
 *
 * <em>
 *   附：多布局列表的实现
 *       通过{@link ItemLayoutFactory}对象，随着item的增删而动态的增删或选择性返回指定布局id，完成各种item布局的指定。
 *       需要注意的是，需要注意item的行为描述（即{@link ItemAction}中的行为）因此布局的变更而可能受到的影响。
 * </em>
 */
public class IdeaRecyclerView<D> extends RecyclerView implements IRecyclerView<D> {

    private Impl<D> mImpl;

    private Context mContext;

    public IdeaRecyclerView( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IdeaRecyclerView( Context context) {
        super(context);
        init(context);
    }

    public IdeaRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 在构造方法中做初始化操作
     * @see IdeaRecyclerView#IdeaRecyclerView
     */
    private void init( Context context) {
        mContext = context;
    }

    /** 对列表进行参数配置 */
    public Impl<D> option( ItemLayoutFactory factory) {
        mImpl = new Impl<>(factory);
        return mImpl;
    }

    @Override
    public List<D> getListData() {
        Adapter adapter = getAdapter();
        if (adapter instanceof IdeaAdapter) {
            IdeaAdapter<D> ideaAdapter = (IdeaAdapter<D>)adapter;
            return ideaAdapter.getListData();
        }
        throw new UnsupportedOperationException("此方法对当前的列表控件不可用");
    }

    @UiThread
    @Override
    public void refresh() {
        if (!isUiThread()) {
            IdeaRvLog.I("非UI线程不允许刷新列表操作");
            return ;
        }

        Adapter adapter = getAdapter();
        if (adapter != null) {
            if (adapter instanceof IdeaAdapter) {
                List<D> listData = getListData();
                int itemCount = listData == null ? 0 : listData.size();
                IdeaRvLog.I("当前数据列表项个数：" + itemCount);
            }
            adapter.notifyDataSetChanged();
        } else {
            IdeaRvLog.I("无法刷新");
        }
    }

    @UiThread
    public void addItem(D data) {
        post(() -> {
            mImpl.mAdapter.getListData().add(data);
            mImpl.mAdapter.notifyItemInserted(mImpl.mAdapter.getListData().size());
        });
    }

    /**
     * 执行此方法后，列表绑定的数据集也会被解绑，需要重新绑定新的数据集
     *
     * @see Impl#setListData(List)
     */
    @UiThread
    @Override
    public void removeAllItem() {
        mImpl.setListData(null);
    }

    /**
     * {@link IdeaRecyclerView}支持以原生方式对列表进行列表布局的配置
     * @param layout LayoutManager to use
     */
    @Override
    public void setLayoutManager(final LayoutManager layout) {
        LayoutManager lm = null;
        if (mImpl == null) {
            lm = layout;
        } else {
            mImpl.mStyle = new IStyle() {
                @Override
                public LayoutManager getLayoutManager() {
                    return layout;
                }
            };
            lm = mImpl.mStyle.getLayoutManager();
        }

        super.setLayoutManager(lm);
    }

    @Override
    public IdeaViewHolder getIdeaViewHolder(@NonNull View child) {
        return (IdeaViewHolder) getChildViewHolder(child);
    }

    @Nullable
    public IdeaAdapter<D> getIdeaAdapter() {
        return (IdeaAdapter<D>)super.getAdapter();
    }

    /**
     * {@link IdeaRecyclerView}仍支持使用原生方法绑定适配器
     * @param adapter The new adapter to set, or null to set no adapter.
     */
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * 内部配置类
     * 注意，此类的成员变量仅为配置{@link IdeaRecyclerView<>}而存在
     * @param <V> 数据集类型，实际上等效于{@link #<D>}，仅仅是为了方便区分两者
     */
    public final class Impl<V extends D> {

        /** 列表的布局样式 */
        private IStyle mStyle;

        /** 列表适配器 */
        private final IdeaAdapter<V> mAdapter;

        private Impl( ItemLayoutFactory factory) {
            mStyle = null;
            mAdapter = new IdeaAdapter<V>(mContext, factory);
            setAdapter(mAdapter);
        }

        /**
         * 设置列表的布局样式
         * @param style 列表样式对象
         */
        public Impl<V> setListStyle( IStyle style) {
            mStyle = style;
            setLayoutManager(mStyle.getLayoutManager());
            return this;
        }

        /**
         * 设置列表数据集
         * @param listData 数据集，即使为null，{@link IdeaRecyclerView}仍然会工作
         */
        public Impl<V> setListData(@Nullable List<V> listData) {
            mAdapter.setListData(listData);
            return this;
        }

        /**
         * 设置列表项的行为
         * @param action item的行为描述对象。当此对象为null时，意味着列表项不会有任何动作，通常不会这样做。
         */
        public Impl<V> setItemAction(@Nullable ItemAction<V> action) {
            mAdapter.setItemAction(action);
            return this;
        }

        /**
         * 获取列表样式
         * @return 描述列表样式的{@link IStyle}对象，可能为null
         */
        public IStyle getListStyle() {
            return mStyle;
        }
    }

    private boolean isUiThread() {
        boolean ret = Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper();
        if (!ret) {
            IdeaRvLog.E("无法在非主线程中操作列表界面！");
        }

        return ret;
    }
}
