package com.qxtx.idea.recyclerview.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.qxtx.idea.recyclerview.demo.databinding.DemoActivityBinding;
import com.qxtx.idea.recyclerview.item.ItemAction;
import com.qxtx.idea.recyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.recyclerview.layoutmanager.Linear;
import com.qxtx.idea.recyclerview.listener.IAttachStateChangeListener;
import com.qxtx.idea.recyclerview.tool.IdeaRvLog;
import com.qxtx.idea.recyclerview.view.IdeaRecyclerView;
import com.qxtx.idea.recyclerview.viewHolder.IdeaViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private DemoActivityBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DemoActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i > 2) {
                listData.add("item " + i);
            } else {
                listData.add(null);
            }
        }

        Linear listStyle = new Linear(this, Linear.VER);
        IdeaRecyclerView<String>.Impl<String> impl = binding.rvList.option(new TestLayoutFactory())
                .setListStyle(listStyle)
                .setItemAction(new TestItemAction());

        binding.rvList.addIdeaChildAttachStateListener(new IAttachStateChangeListener() {
            @Override
            public void onChildAttach(View v, int pos, Object data) {
                IdeaRvLog.D(String.format("列表项%s绑定到列表", pos));
            }

            @Override
            public void onChildDetach(View v, int pos, Object data) {
                IdeaRvLog.D(String.format("列表项%s从列表解绑", pos));
            }
        });

        binding.btnAddListData.setOnClickListener(v -> impl.setListData(listData));

        binding.btnScrollControl.setOnClickListener(v -> {
            boolean scrollEnable = v.getTag() != null && (boolean) v.getTag();
            listStyle.setScrollEnable(scrollEnable);
            String state = scrollEnable ? "禁用" : "启用";
            binding.btnScrollControl.setText(String.format("列表滚动已%s", state));
            v.setTag(!scrollEnable);
        });
    }

    private final class TestItemAction implements ItemAction<String> {

        @Override
        public void onItemAction(IdeaViewHolder holder, List<String> data, int itemPos) {
            if (data == null) {
                return ;
            }

            TextView tvText = (TextView) holder.getViewById(R.id.tvItemText);
            tvText.setText(data.get(itemPos));

            holder.setItemListener((View.OnClickListener) v -> {
                int pos = binding.rvList.getChildAdapterPosition(v);
                IdeaRvLog.D(String.format("点击了%s项", pos));
            });
        }
    }

    private final class TestLayoutFactory implements ItemLayoutFactory {

        @Override
        public int getLayoutId(int position) {
            switch (position) {
                case 0:
                    return R.layout.item_zero;
                case 1:
                    return R.layout.item_first;
                default:
                    return R.layout.item_third;
            }
        }
    }
}
