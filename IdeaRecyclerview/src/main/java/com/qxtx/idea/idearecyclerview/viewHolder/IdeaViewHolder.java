package com.qxtx.idea.idearecyclerview.viewHolder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qxtx.idea.idearecyclerview.tool.IdeaRvLog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author QXTX-WIN
 * @date 2020/4/15 23:35
 * Description: 每个item绑定一个{@link RecyclerView.ViewHolder}对象
 */
public final class IdeaViewHolder extends RecyclerView.ViewHolder implements IViewHolder {

    private final View itemView;

    /** 缓存item的view对象 */
    private final HashMap<Integer, View> idMap;

    /**
     * @param item 父类构造方法不允许传入null，否则抛出异常。
     */
    public IdeaViewHolder(@NonNull View item) {
        super(item);
        itemView = item;
        idMap = new HashMap<>();
    }

    @Override
    public void setItemVisibility(int visibility) {
        if (itemView == null) {
            return ;
        }

        itemView.setVisibility(visibility);
    }

    @Override
    public void setItemListener(@Nullable Object listener) {

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
        } catch (NoSuchMethodException | IllegalAccessException
                | InvocationTargetException | ClassNotFoundException e) {
            IdeaRvLog.I("异常！设置监听器失败。目标View=" + itemView.getClass().getName() + ", listener=" + listener.getClass().getName());
            e.printStackTrace();
        }
    }

    @Override
    public View getViewById(int resId) {
        View ret;
        if (idMap.containsKey(resId)) {
            return idMap.get(resId);
        }

        ret = itemView.findViewById(resId);
        if (ret != null) {
            idMap.put(resId, ret);
        }
        return ret;
    }

    @Override
    public View getItem() {
        return itemView;
    }

    /**
     * 分析用作item事件监听器的对象，此对象不一定是有效的监听器对象
     * @param listener 作为item事件监听器的目标对象
     * @return 如果传入的对象是属于{@link View}的事件监听器实现类，则判定此对象有效，返回对于对应接口全限定类名，否则返回null
     */
    private String parseListenerObj(@NonNull Object listener) {
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
