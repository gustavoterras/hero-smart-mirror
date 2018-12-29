package br.com.terras.hero.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.terras.hero.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Woop
 * Created by Gustavo Terras on 11/05/2018.
 * Copyright © 2017 Woop. All rights reserved.
 */

@SuppressWarnings("unchecked")
public class RecyclerBindingAdapter<T> extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingHolder> {

    private int headerLayout, itemLayout, footerLayout;
    private int headerVarId, itemVarId, footerVarId;
    private boolean isHeaderAdded, isFooterAdded;

    private BaseViewHolder headerViewHolder, footerViewHolder;

    private List<T> items = new ArrayList<>();
    private RecyclerBindingAdapter.OnItemClickListener<T> onItemClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(int position, View view, T item);
    }

    public void setOnItemClickListener(RecyclerBindingAdapter.OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public List<T> getItems(){
        return this.items;
    }

    public void addItem(T item) {
        this.items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addAll(List<T> items) {
        for (T item : items) {
            addItem(item);
        }
    }

    public void clear() {
        int starRemovePosition = isHeaderAdded ? 1 : 0;
        while (getItemCount() > starRemovePosition) {
            removeItem(getItemCount() -1);
        }
    }

    public void removeItem(int position) {
        this.items.remove(position);
        notifyItemRemoved(position);
    }

    public void addHeader(int headerLayout, int headerVarId, BaseViewHolder headerViewHolder) {
        this.headerViewHolder = headerViewHolder;
        this.headerLayout = headerLayout;
        this.headerVarId = headerVarId;
        this.isHeaderAdded = true;

        addItem((T) new Object());
    }

    public void addFooter(int footerLayout, int footerVarId, BaseViewHolder footerViewHolder) {
        this.footerViewHolder = footerViewHolder;
        this.footerLayout = footerLayout;
        this.footerVarId = footerVarId;
        this.isFooterAdded = true;

        addItem((T) new Object());
    }

    public void removeHeader() {
        this.isHeaderAdded = false;

        if (items != null && !items.isEmpty()) {
            removeItem(0);
        }
    }

    public void removeFooter() {
        this.isFooterAdded = false;

        int position = getItemCount() - 1;

        if(isHeaderAdded && position == 0) return;

        if(position >= 0) {
            T item = items.get(position);

            if (item != null)
                removeItem(position);
        }
    }

    public RecyclerBindingAdapter(int itemLayout, int itemVarId, List<T> items) {
        this.itemLayout = itemLayout;
        this.itemVarId = itemVarId;
        this.items = items;
    }

    public RecyclerBindingAdapter(int itemLayout, int itemVarId) {
        this.itemLayout = itemLayout;
        this.itemVarId = itemVarId;
    }

    @NonNull
    @Override
    public RecyclerBindingAdapter.BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerBindingAdapter.BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerBindingAdapter.BindingHolder holder, final int position) {

        if (position == 0 && isHeaderAdded) {
            holder.getBinding().setVariable(headerVarId, headerViewHolder);
            holder.getBinding().executePendingBindings();
        } else if (position == getItemCount() - 1 && isFooterAdded) {
            holder.getBinding().setVariable(footerVarId, footerViewHolder);
            holder.getBinding().executePendingBindings();
        } else {

            T item = items.get(position);

            holder.getBinding().getRoot().setOnClickListener(view -> {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(holder.getAdapterPosition(), view, item);
            });

            holder.getBinding().setVariable(itemVarId, item);
            holder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderAdded && isFooterAdded)
            return position == 0 ? headerLayout : position == getItemCount() - 1 ? footerLayout : itemLayout;
        else if (isHeaderAdded)
            return position == 0 ? headerLayout : itemLayout;
        else if (isFooterAdded)
            return position == getItemCount() - 1 ? footerLayout : itemLayout;
        else
            return itemLayout;
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        BindingHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}