package Adapters;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchrewardsapplication.R;

import java.util.List;

import Model.Item;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Item> items;
    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.MyViewHolder holder, int position) {
        Item currItem = items.get(position);
        holder.itemName.setText(currItem.getName());
        Log.d("BindViewHolder", "Item: " + currItem.getName());
        //holder.itemListId.setText(String.valueOf(currItem.getListId()));
        //maybe display listId
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateItems(List<Item> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        //TextView itemListId;
        public MyViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            //itemListId = view.findViewById(R.id.itemListId);
        }
    }
}
