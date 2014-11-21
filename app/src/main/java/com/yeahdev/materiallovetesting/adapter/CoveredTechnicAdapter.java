package com.yeahdev.materiallovetesting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.devspark.robototextview.widget.RobotoTextView;
import com.yeahdev.materiallovetesting.R;


public class CoveredTechnicAdapter extends ArrayAdapter<String> {

    private final static String[] TECHNICS = {
            "CardView",
            "Communication between Activity and Fragments",
            "DrawerMenu",
            "Extend ArrayAdapter",
            "Extend ItemViewHolder",
            "Floating Action Button",
            "Fragments",
            "Google + Login",
            "Intents",
            "ListView",
            "RecyclerView",
            "Scene Transition Animation",
            "SnackBar",
            "Toolbar",
            "Material Dialogs"
    };

    public CoveredTechnicAdapter(Context context) {
        super(context, R.layout.listview_item_covered_technic);
    }

    @Override
    public int getCount() {
        return TECHNICS.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_covered_technic, parent, false);
        }

        ((RobotoTextView) convertView.findViewById(R.id.tv_listview_item_CT)).setText(TECHNICS[position]);

        return convertView;
    }
}