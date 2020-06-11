package com.qxtx.idea.idearecyclerview.view;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.qxtx.idea.idearecyclerview.adapter.IAdapter;
import com.qxtx.idea.idearecyclerview.adapter.IdeaAdapter;
import com.qxtx.idea.idearecyclerview.item.ItemAction;
import com.qxtx.idea.idearecyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.idearecyclerview.layoutmanager.IStyle;
import com.qxtx.idea.idearecyclerview.tool.IdeaRvLog;

import java.util.List;

/**
 * @author QXTX-WIN
 * @date 2020/4/15 23:32
 * Description: 对{@link RecyclerView}的二次封装，使用起来更加方便
 * @param <D> 数据集类型
 *
 * 只能存在一条路径去配置，即通过一个固定的方法作为入口，创建内部实现类，由这个实现类去完成真正的配置工作
 *
 * 一些开源的RecyclerView的LayoutManager：
 *      1、FanLayoutManager：https://github.com/Cleveroad/FanLayoutManager 扇叶转动
 *      2、CarouselLayoutManager：https://github.com/Azoft/CarouselLayoutManager 传送带（卡片轮播）效果
 *      3、ChipsLayoutManager：https://github.com/BelooS/ChipsLayoutManager 流式布局效果（标签云）
 *      4、HiveLayoutManager：https://github.com/Chacojack/HiveLayoutManager 蜂巢效果（国人作品）
 *      5、vLayout：https://github.com/alibaba/vlayout 布局混排效果（天猫app所使用）
 *      6、flexbox-layout https://github.com/google/flexbox-layout flexbox效果（谷歌的东西，原本不支持recyclerView）
 *      7、LondonEyeLayoutManager https://github.com/danylovolokh/LondonEyeLayoutManager 环形菜单效果
 */
public class IdeaRecyclerView<D> extends RecyclerView implements IRecyclerView<D> {

    private Impl<D> mImpl;

    private Context mContext;

    public IdeaRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IdeaRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IdeaRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 在构造方法中做初始化操作
     * @see IdeaRecyclerView#IdeaRecyclerView
     */
    private void init(@NonNull Context context) {
        mContext = context;
    }

    /** 对列表进行参数配置 */
    public Impl<D> option(@NonNull ItemLayoutFactory factory) {
        mImpl = new Impl<>(factory);
        return mImpl;
    }

    @Override
    public void addItem(int pos, D data) {
        addItem(pos, data, null);
    }

    @Override
    public void addItem(int pos, D data, @Nullable ItemAction<D> action) {
        addItem(pos, data, IdeaAdapter.NO_LAYOUT_ID, action);
    }

    @Override
    public void addItem(int pos, D data, int layoutId, ItemAction<D> action) {
        mImpl.addItem(pos, data, layoutId, action);
    }

    @Override
    public void removeItem(int pos) {
        mImpl.removeItem(pos);
    }

    @Override
    public List<D> getListData() {
        return mImpl.getListData();
    }

    @Override
    public IAdapter<D> getIdeaAdapter() {
        Adapter adapter = getAdapter();
        if ((adapter instanceof IAdapter)) {
            return (IAdapter<D>) adapter;
        }
        return null;
    }

    @UiThread
    @Override
    public void refresh() {
        if (!isUiThread()) {
            return ;
        }

        Adapter adapter = getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            IdeaRvLog.I("无法刷新");
        }
    }

    /**
     * 执行此方法后，列表绑定的数据集也会被解绑，需要重新绑定新的数据集
     *
     * @see Impl#setListData(List)
     */
    @UiThread
    @Override
    public void removeAll() {
        mImpl.removeAll();
    }

    /**
     * {@link IdeaRecyclerView}支持以原生方式对列表进行列表布局的配置
     * @param layout LayoutManager to use
     */
    @Override
    public void setLayoutManager(final LayoutManager layout) {
        LayoutManager layoutManager = layout;
        if (mImpl != null) {
            IStyle style = new IStyle() {
                @Override
                public LayoutManager getLayoutManager() {
                    return layout;
                }
            };
            mImpl.mStyle = style;
            layoutManager = style.getLayoutManager();
        }
        super.setLayoutManager(layoutManager);
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
     * @param <V> 数据集类型，实际上等效于{@link #<D>}，仅仅是为了方便区分两者
     */
    public final class Impl<V extends D> {

        /** 列表的布局样式 */
        private IStyle mStyle;

        /** 列表适配器 */
        private final IdeaAdapter<V> mAdapter;

        private Impl(@NonNull ItemLayoutFactory factory) {
            mStyle = null;

            //默认不使用列表项动画效果
            setItemAnimator(null);

            mAdapter = new IdeaAdapter<V>(mContext, factory);
            setAdapter(mAdapter);
        }

        /**
         * 设置列表的布局样式
         * @param style 列表样式对象
         */
        public Impl<V> setListStyle(@NonNull IStyle style) {
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

        private List<V> getListData() {
            return mAdapter.getListData();
        }

        private void addItem(int pos, V data) {
            addItem(pos, data, null);
        }

        private void addItem(int pos, V data, @Nullable ItemAction<V> action) {
            addItem(pos, data, IdeaAdapter.NO_LAYOUT_ID, action);
        }

        private void addItem(int pos, V data, int layoutId, ItemAction<V> action) {
            mAdapter.addItem(pos, data, layoutId, action);
        }

        private void removeItem(int pos) {
            mAdapter.removeItem(pos);
        }

        private void removeAll() {
            mAdapter.removeAll();
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
