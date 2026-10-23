package com.example.smartpantrymanager;
// Adapter for binding pantry items to the RecyclerView

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryList;
    private OnItemClickListener listener;

    // Interface to handle clicks on the item or the delete button
    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
        void onDeleteClick(PantryItem item);
    }

    public PantryAdapter(List<PantryItem> pantryList, OnItemClickListener listener) {
        this.pantryList = pantryList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_pantry.xml layout we just created
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem currentItem = pantryList.get(position);

        // Set the text for the name and details
        holder.tvItemName.setText(currentItem.getName());

        String details = currentItem.getQuantity() + " " + currentItem.getUnit();
        if (currentItem.getExpiryDate() != null && !currentItem.getExpiryDate().isEmpty()) {
            details += " • Expires: " + currentItem.getExpiryDate();
        }
        holder.tvItemDetails.setText(details);

        // Handle clicks
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(currentItem);
            }
        });

        holder.btnDeleteItem.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryList.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemDetails;
        ImageButton btnDeleteItem;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDetails = itemView.findViewById(R.id.tvItemDetails);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
        }
    }
}