package com.qxtx.idea.recyclerview.layoutmanager;

import android.content.Context;

import androidx.annotation.IntDef;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * CreateDate 2020/4/22 22:23
 * <p>
 *
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Description</b></p>: 列表风格基类，建立{@link IStyle}的实现类继承此基类，而不是直接实现{@link IStyle}接口
 */
public abstract class BaseStyle implements IStyle {

    /** 持有对宿主context的弱引用 */
    protected WeakReference<Context> mContextWeak;

    /** 真正的列表布局管理器 */
    protected RecyclerView.LayoutManager mLayoutManager;

    /**
     * 线性列表布局方向，等同于LinearLayoutManager#HORIZONTAL 和 LinearLayoutManager#VERTICAL。
     * @see LinearLayoutManager
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({HOR, VER})
    public @interface Orientation {}

    /** 水平方向 */
    public static final int HOR = LinearLayoutManager.HORIZONTAL;

    /** 垂直方向 */
    public static final int VER = LinearLayoutManager.VERTICAL;

    protected BaseStyle(Context context) {
        mContextWeak = new WeakReference<>(context);
        mLayoutManager = null;
    }

    @Override
    public <T extends RecyclerView.LayoutManager>T getLayoutManager() {
        return (T)mLayoutManager;
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    /**
     * 获取持有的宿主context对象
     * @return 当context对象已经被gc回收，说明此时宿主已经死亡，后续的操作已经没有意义，此时返回null，否则返回宿主的context。
     */
    public Context getContext() {
        if (mContextWeak == null) {
            return null;
        }
        return mContextWeak.get();
    }
}
