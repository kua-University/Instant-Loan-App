package com.jerry.ekub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private List<Ekub> ekubList;
    private Context context;

    public OptionAdapter(List<Ekub> ekubList, Context context) {
        this.ekubList = ekubList;
        this.context = context;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Ekub ekub = ekubList.get(position);
        holder.optionText.setText(ekub.getName() + "\nStake: " + ekub.getStake() + "\nTotal Qty: " + ekub.getTotalQuantity() + "\nDeadline: " + ekub.getDeadline());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, EkubDetailActivity.class);
            intent.putExtra("EKUB_NAME", ekub.getName());
            intent.putExtra("EKUB_STAKE", ekub.getStake());
            intent.putExtra("EKUB_TOTAL_QTY", ekub.getTotalQuantity());
            intent.putExtra("EKUB_TYPE", ekub.getType());
            intent.putExtra("EKUB_DEADLINE", ekub.getDeadline());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ekubList.size();
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionText;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionText = itemView.findViewById(R.id.optionText);
        }
    }
}