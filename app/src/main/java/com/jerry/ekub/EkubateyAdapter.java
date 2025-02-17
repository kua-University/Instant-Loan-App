package com.jerry.ekub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EkubateyAdapter extends RecyclerView.Adapter<EkubateyAdapter.EkubateyViewHolder> {

    private List<Ekub> activeEkubs;
    private OnEkubClickListener listener;

    public EkubateyAdapter(List<Ekub> activeEkubs, OnEkubClickListener listener) {
        this.activeEkubs = activeEkubs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EkubateyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ekub, parent, false);
        return new EkubateyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EkubateyViewHolder holder, int position) {
        Ekub ekub = activeEkubs.get(position);
        holder.ekubName.setText(ekub.getName());
        holder.ekubAmount.setText("Amount: " + ekub.getAmount());
        holder.ekubDuration.setText("Duration: " + ekub.getDuration() + " months");
        holder.ekubType.setText("Type: " + ekub.getType());
        holder.ekubDate.setText("Date: " + ekub.getDate());

        holder.itemView.setOnClickListener(v -> listener.onEkubClick(position));
    }

    @Override
    public int getItemCount() {
        return activeEkubs.size();
    }

    public static class EkubateyViewHolder extends RecyclerView.ViewHolder {
        TextView ekubName, ekubAmount, ekubDuration, ekubType, ekubDate;

        public EkubateyViewHolder(@NonNull View itemView) {
            super(itemView);
            ekubName = itemView.findViewById(R.id.ekubName);
            ekubAmount = itemView.findViewById(R.id.ekubAmount);
            ekubDuration = itemView.findViewById(R.id.ekubDuration);
            ekubType = itemView.findViewById(R.id.ekubType);
            ekubDate = itemView.findViewById(R.id.ekubDate);
        }
    }

    public interface OnEkubClickListener {
        void onEkubClick(int position);
    }
}