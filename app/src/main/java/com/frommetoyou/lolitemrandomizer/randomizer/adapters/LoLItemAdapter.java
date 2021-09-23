package com.frommetoyou.lolitemrandomizer.randomizer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.frommetoyou.lolitemrandomizer.R;
import com.frommetoyou.lolitemrandomizer.databinding.LolItemListItemBinding;
import com.frommetoyou.lolitemrandomizer.randomizer.pojo.ItemPOJO;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LoLItemAdapter extends RecyclerView.Adapter<LoLItemAdapter.ViewHolder> {
    private List<ItemPOJO> loLItemList;


    public LoLItemAdapter(List<ItemPOJO> loLItemList) {
        this.loLItemList = loLItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lol_item_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemPOJO loLItem=loLItemList.get(position);
        holder.binding.tvItemName.setText(loLItem.getName());
        RequestOptions options=new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        Glide.with(holder.binding.getRoot())
                .load(loLItem.getImageURL())
                .apply(options)
                .into(holder.binding.ivImage);
    }

    @Override
    public int getItemCount() {
        return loLItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LolItemListItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding=LolItemListItemBinding.bind(itemView);
        }
    }
}
