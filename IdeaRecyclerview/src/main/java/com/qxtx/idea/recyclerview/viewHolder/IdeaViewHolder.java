package com.qxtx.idea.recyclerview.viewHolder;

import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.qxtx.idea.recyclerview.tool.IdeaRvLog;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * <p>
 * @author QXTX-WIN
 * <p>
 * <p><b>Create Date</b></p> 2020/4/15 23:35
 * <p>
 * <p><b>Description</b></p>:
 * 每个item绑定一个RecyclerView.ViewHolder对象
 */
public final class IdeaViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    private final View itemView;

    private final HashMap<Integer, View> idMap = new HashMap<>();

    /**
     * @param item 父类构造方法不允许传入null，否则抛出异常。
     */
    public IdeaViewHolder( View item) {
        super(item);
        itemView = item;
    }

    @Override
    public void setItemVisibility(int visibility) {
        if (itemView == null) {
            return ;
        }

        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            itemView.setVisibility(visibility);
        } else {
            itemView.post(() -> itemView.setVisibility(visibility));
        }
    }

    @Override
    public void setItemListener(Object listener) {
        String clsName = null;
        if (listener != null) {
            clsName = parseListenerObj(listener);
        }

        if (clsName == null) {
            IdeaRvLog.I("设置监听器失败。目标View=" + itemView + ", listener=" + listener.getClass().getName());
            return;
        }

        String simpleName = clsName.substring(clsName.lastIndexOf('$') + 1);
        try {
            Method method = View.class.getMethod("set" + simpleName, Class.forName(clsName));
            method.invoke(itemView, listener);
        } catch (Exception e) {
            IdeaRvLog.I("异常！设置监听器失败。目标View=" + itemView.getClass().getName() + ", listener=" + listener.getClass().getName());
            e.printStackTrace();
        }
    }

    @Override
    public <T extends View>T getViewById(int resId) {
        View view;

        if (idMap.containsKey(resId)) {
            view = idMap.get(resId);
        } else {
            view = itemView.findViewById(resId);
            if (view != null) {
                idMap.put(resId, view);
            }
        }
        return (T)view;
    }

    @Override
    public <T extends View>T getItem() {
        return (T)itemView;
    }

    /**
     * 分析用作item事件监听器的对象，此对象不一定是有效的监听器对象
     * @param listener 作为item事件监听器的目标对象
     * @return 如果传入的对象是属于{@link View}的事件监听器实现类，则判定此对象有效，返回对于对应接口全限定类名，否则返回null
     */
    //@Keep
    private String parseListenerObj(Object listener) {
        Class<?>[] interfaces = listener.getClass().getInterfaces();

        String parentClsName = View.class.getName();
        for (Class<?> c : interfaces) {
            String name = c.getName();
            if (name.startsWith(parentClsName + "$On") && name.endsWith("Listener")) {
                return name;
            }
        }

        return null;
    }
}
