package com.example.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.help.Tip;

import java.util.List;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : recyclerView 的 Adapter
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Tip> list;
    private Context context;
    private OnItemClickListener recyclerItemClickListener;

    /**
     * 定义监听接口
     */
    public interface OnItemClickListener {
        void onItemClick (Tip tip);
    }

    /**
     * 构造方法
     * @param list recyclerView结果列表
     * @param context
     */
    public SearchAdapter(List<Tip> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setItemClickListener (OnItemClickListener listener){
        this.recyclerItemClickListener = listener;
    }

    /**
     * 更新列表
     * @param list 定位信息列表
     */
    public void getData (List<Tip> list){
        if (list == null)return;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传入 recylerView 组件格式
        View view = View.inflate(context,R.layout.recyclerview_item,null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        //显示 定位名称
        Tip tip = list.get(position);
        holder.textView.setText(tip.getName());
    }

    @Override
    public int getItemCount() {
        //组件数量为列表长度
        return list == null ? 0 : list.size();
    }

    /**
     * 设置 viewHolder
     */
    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            //监听接口回调
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(list.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
