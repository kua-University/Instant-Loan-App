package com.jerry.ekub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    private List<Ekub> ekubs;
    private Context context;
    private OnEkubClickListener onEkubClickListener;

    public OptionAdapter(List<Ekub> ekubs, Context context, OnEkubClickListener onEkubClickListener) {
        this.ekubs = ekubs;
        this.context = context;
        this.onEkubClickListener = onEkubClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_option, parent, false);
        return new ViewHolder(view, onEkubClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ekub ekub = ekubs.get(position);
        holder.nameTextView.setText(ekub.getName());
        holder.amountTextView.setText("Amount: $" + ekub.getAmount());
        holder.durationTextView.setText("Duration: " + ekub.getDuration() + " days");
        holder.typeTextView.setText("Type: " + ekub.getType());
        holder.dateTextView.setText("Date: " + ekub.getDate());
    }

    @Override
    public int getItemCount() {
        return ekubs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTextView, amountTextView, durationTextView, typeTextView, dateTextView;
        OnEkubClickListener onEkubClickListener;

        public ViewHolder(@NonNull View itemView, OnEkubClickListener onEkubClickListener) {
            super(itemView);
            this.onEkubClickListener = onEkubClickListener;
            nameTextView = itemView.findViewById(R.id.nameTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEkubClickListener.onEkubClick(getAdapterPosition());
        }
    }

    public interface OnEkubClickListener {
        void onEkubClick(int position);
    }
}