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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<Tip> list;
    private Context context;
    private OnItemClickListener recyclerItemClickListener;

    public interface OnItemClickListener {
        void onItemClick (Tip tip);
    }

    public SearchAdapter(List<Tip> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void setItemClickListener (OnItemClickListener listener){
        this.recyclerItemClickListener = listener;
    }

    public void getData (List<Tip> list){
        if (list == null)return;
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.recyclerview_item,null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Tip tip = list.get(position);
        holder.textView.setText(tip.getName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);

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
