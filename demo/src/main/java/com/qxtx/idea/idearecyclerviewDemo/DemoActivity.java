package com.qxtx.idea.idearecyclerviewDemo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qxtx.idea.idearecyclerview.item.ItemAction;
import com.qxtx.idea.idearecyclerview.item.ItemLayoutFactory;
import com.qxtx.idea.idearecyclerview.layoutmanager.Linear;
import com.qxtx.idea.idearecyclerview.tool.IdeaRvLog;
import com.qxtx.idea.idearecyclerview.view.IdeaRecyclerView;
import com.qxtx.idea.idearecyclerview.viewHolder.IdeaViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private IdeaRecyclerView<String> rvList;

    private int specItemSeq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);

        ArrayList<String> listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listData.add("item " + i);
        }

        rvList = findViewById(R.id.rvList);
        IdeaRecyclerView<String>.Impl<String> impl = rvList.option(new TestLayoutFactory())
                .setListStyle(new Linear(this, Linear.VER))
                .setItemAction(new TestItemAction());

        Button btnAddListData = findViewById(R.id.btnAddListData);
        btnAddListData.setOnClickListener(v ->  {
            impl.setListData(listData);
        });

        Button btnChangeItemAction = findViewById(R.id.btnAddItemAction);
        btnChangeItemAction.setOnClickListener(v ->  {
            specItemSeq++;
            rvList.addItem(3, "特殊项" + specItemSeq, R.layout.spec_item, (holder, data, itemPos) -> {
                if (data == null) {
                    return ;
                }

                TextView tvText = (TextView) holder.getViewById(R.id.tvText);
                tvText.setText(data.get(itemPos));
            });
        });
    }

    private final class TestItemAction implements ItemAction<String> {

        @Override
        public void onItemAction(@NonNull IdeaViewHolder holder, @Nullable List<String> data, final int itemPos) {
            if (data == null) {
                return ;
            }

            TextView tvText = (TextView) holder.getViewById(R.id.tvItemText);
            tvText.setText(data.get(itemPos));

            holder.setItemListener((View.OnClickListener) v -> {
                int pos = rvList.getChildAdapterPosition(v);
                IdeaRvLog.TEST("点击了%s项", pos);
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
