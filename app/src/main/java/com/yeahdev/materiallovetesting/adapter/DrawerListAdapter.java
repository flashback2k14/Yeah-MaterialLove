package com.yeahdev.materiallovetesting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;
import com.yeahdev.materiallovetesting.R;


public class DrawerListAdapter extends ArrayAdapter {

    private Context context;
    private int[] iconList;
    private String[] itemList;

    public DrawerListAdapter(Context context, int[] iconList, String[] itemList) {
        super(context, R.layout.drawer_menu_item);
        this.context = context;
        this.iconList = iconList;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return this.iconList.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_menu_item, parent, false);
        }

        ((ImageView) convertView.findViewById(R.id.ivIconDrawerItem)).setImageDrawable(this.context.getResources().getDrawable(this.iconList[position]));
        ((RobotoTextView) convertView.findViewById(R.id.tv_drawer_menu_item_2)).setText(this.itemList[position]);

        return convertView;
    }
}
