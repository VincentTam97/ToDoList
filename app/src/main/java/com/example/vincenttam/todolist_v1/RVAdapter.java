package com.example.vincenttam.todolist_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ItemsViewHolder> {

    private List<ToDoItem> items;
    private LayoutInflater inflater;

    public RVAdapter(Context context, List<ToDoItem> items){
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView itemTitle;
        TextView itemDetail;
        ImageView itemTypeImage;

        ItemsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail = (TextView)itemView.findViewById(R.id.item_detail);
            itemTypeImage = (ImageView)itemView.findViewById(R.id.item_type);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.todo_items, viewGroup, false);
        ItemsViewHolder MyVH = new ItemsViewHolder(v);
        return MyVH;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder itemsViewHolder, int i) {
        itemsViewHolder.itemTitle.setText(items.get(i).Title);
        itemsViewHolder.itemDetail.setText(items.get(i).Detail);
        if (items.get(i).itemType.equals("Work"))
            itemsViewHolder.itemTypeImage.setImageResource(R.drawable.work_selected);
        if (items.get(i).itemType.equals("Life"))
            itemsViewHolder.itemTypeImage.setImageResource(R.drawable.life_selected);
        if (items.get(i).itemType.equals("Study"))
            itemsViewHolder.itemTypeImage.setImageResource(R.drawable.study_selected);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void onItemDismiss(int position) {

        //找到对应的元素
        int getID = MainActivity.items.get(position).itemID;
        String getTitle=MainActivity.items.get(position).Title;
        System.out.println(getTitle);
        System.out.println(getID);
        MainActivity.items.remove(position);
        notifyItemRemoved(position);

        MainActivity.instance.delete(getID);
    }


}
