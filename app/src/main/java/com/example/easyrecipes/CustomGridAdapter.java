package com.example.easyrecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private int[] imageResources;
    private String[] names;
    private String[] descriptions;

    public CustomGridAdapter(Context context, int[] imageResources, String[] names, String[] descriptions) {
        this.context = context;
        this.imageResources = imageResources;
        this.names = names;
        this.descriptions = descriptions;
    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewItem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            gridViewItem = inflater.inflate(R.layout.grid_item_layout, null);
        } else {
            gridViewItem = convertView;
        }

        // Set views based on the grid item layout
        ImageView imageView = gridViewItem.findViewById(R.id.imageView);
        TextView textViewName = gridViewItem.findViewById(R.id.textViewName);
        TextView textViewDescription = gridViewItem.findViewById(R.id.textViewDescription);

        // Set values for the views
        imageView.setImageResource(imageResources[position]);
        textViewName.setText(names[position]);
        textViewDescription.setText(descriptions[position]);

        return gridViewItem;
    }

}
