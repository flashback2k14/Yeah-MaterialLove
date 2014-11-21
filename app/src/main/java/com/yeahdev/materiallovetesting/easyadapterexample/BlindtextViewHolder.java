package com.yeahdev.materiallovetesting.easyadapterexample;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.yeahdev.materiallovetesting.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.recycler_view_card_view_item)
public class BlindtextViewHolder extends ItemViewHolder<Blindtext> {

    @ViewId(R.id.cvRecyclerViewItem)
    CardView cvRecyclerViewCardViewItem;
    @ViewId(R.id.tvRecyclerViewCardViewTitle)
    RobotoTextView tvCardViewTitle;
    @ViewId(R.id.tvRecyclerViewCardViewContent)
    RobotoTextView tvCardViewContent;

    public BlindtextViewHolder(View view){
        super(view);
    }

    @Override
    public void onSetValues(Blindtext blindtext, PositionInfo positionInfo) {
        tvCardViewTitle.setText(blindtext.getTitleBlindtext());
        tvCardViewContent.setText(blindtext.getContentBlindtext());
    }

    @Override
    public void onSetListeners() {
        super.onSetListeners();
        //
        cvRecyclerViewCardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Blindtext blindtext = getItem();
                //
                if (blindtext != null) {
                    Toast.makeText(getContext(),"Title: " + blindtext.getTitleBlindtext(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}