package com.dclee.recovery.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<T> datas;
    private LayoutInflater mInflater;
    private Activity mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public LayoutInflater getInflater() {
        return mInflater;
    }

    public Activity getContext() {
        return mContext;
    }

    public BaseAdapter(Activity context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setDatas(T[] datas) {
        this.datas = Arrays.asList(datas);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        datas.remove(position);
        notifyDataSetChanged();
    }

    public void addData(T data) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(data);
        notifyDataSetChanged();
    }

    public void addDatas(List<T> data) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(getItemLayout(), parent, false);
        return new BaseViewHolder(itemView);
    }

    public abstract int getItemLayout();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        onBindViewHolder(holder, position, datas.get(position));
    }

    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, T data);

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
