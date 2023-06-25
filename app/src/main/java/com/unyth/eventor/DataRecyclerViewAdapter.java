package com.unyth.eventor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class DataRecyclerViewAdapter extends RecyclerView.Adapter<DataRecyclerViewAdapter.ViewHolder> {

    private List<DataItem> dataItems;
    private static EditButtonClickListener editButtonClickListener;
    private static DeleteButtonClickListener deleteButtonClickListener;

    public DataRecyclerViewAdapter(List<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dataItem = dataItems.get(position);
        holder.titleTextView.setText(dataItem.getTitle());
        holder.subtitleTextView.setText(dataItem.getSubtitle());
        holder.dateTextView.setText(dataItem.getDate());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public DataItem getItem(int position) {
        return dataItems.get(position);
    }

    public void removeItem(int position) {
        dataItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView dateTextView;
        public TextView editTextView;
        public TextView deleteTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            subtitleTextView = itemView.findViewById(R.id.sub_title);
            dateTextView = itemView.findViewById(R.id.date);
            editTextView = itemView.findViewById(R.id.edit);
            deleteTextView = itemView.findViewById(R.id.delete);

            editTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editButtonClickListener != null) {
                        editButtonClickListener.onEditButtonClick(getAdapterPosition());
                    }
                }
            });

            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteButtonClickListener != null) {
                        deleteButtonClickListener.onDeleteButtonClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface EditButtonClickListener {
        void onEditButtonClick(int position);
    }

    public interface DeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }
    public void setEditButtonClickListener(EditButtonClickListener listener) {
        this.editButtonClickListener = listener;
    }

    public void setDeleteButtonClickListener(DeleteButtonClickListener listener) {
        this.deleteButtonClickListener = listener;
    }
}
