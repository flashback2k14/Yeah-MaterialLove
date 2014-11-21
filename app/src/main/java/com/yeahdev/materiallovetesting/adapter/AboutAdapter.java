package com.yeahdev.materiallovetesting.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.devspark.robototextview.widget.RobotoTextView;
import com.yeahdev.materiallovetesting.R;

public class AboutAdapter extends ArrayAdapter<String> {
    /**
     * Tutorial:
     * http://www.androidhive.info/2014/02/android-login-with-google-plus-account-1/
     */
    private Context mContext;

    private final static String[] ABOUTTITLE = {
            "Android-RobotoTextView by johnkil",
            "CircleImageView by hdodenhof",
            "Easy Adapter by ribot",
            "FloatingActionButton by makovkastar",
            "Material Dialogs by afollestad",
            "ShowcaseView by amlcurran",
            "Snackbar by wmora",
            "SystemBarTint by jgilfelt"
    };

    private final static String[] ABOUTLINKS = {
            "https://github.com/johnkil/Android-RobotoTextView",
            "https://github.com/hdodenhof/CircleImageView",
            "https://github.com/ribot/easy-adapter",
            "https://github.com/makovkastar/FloatingActionButton",
            "https://github.com/afollestad/material-dialogs",
            "https://github.com/amlcurran/ShowcaseView",
            "https://github.com/wmora/snackbar",
            "https://github.com/jgilfelt/SystemBarTint"
    };

    public AboutAdapter(Context context) {
        super(context, R.layout.listview_item_about);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return ABOUTTITLE.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_about, parent, false);
        }

        RobotoTextView tvTitle = (RobotoTextView) convertView.findViewById(R.id.tvAboutTitle);
        tvTitle.setText(ABOUTTITLE[position]);

        RobotoTextView tvLink = (RobotoTextView) convertView.findViewById(R.id.tvAboutLink);
        tvLink.setText(ABOUTLINKS[position]);
        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ABOUTLINKS[position])));
            }
        });

        return convertView;
    }
}