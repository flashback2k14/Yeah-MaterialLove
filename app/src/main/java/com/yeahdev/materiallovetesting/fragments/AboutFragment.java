package com.yeahdev.materiallovetesting.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devspark.robototextview.widget.RobotoTextView;
import com.melnykov.fab.FloatingActionButton;
import com.williammora.snackbar.Snackbar;
import com.yeahdev.materiallovetesting.R;
import com.yeahdev.materiallovetesting.adapter.AboutAdapter;
import com.yeahdev.materiallovetesting.adapter.CoveredTechnicAdapter;
import com.yeahdev.materiallovetesting.interfaces.IPassSelectedFragmentFromFAB;


public class AboutFragment extends Fragment {
    /**
     * List for Material Dialog Example
     */
    private String[] selectableFragments;
    /**
     * Interface variable
     */
    private IPassSelectedFragmentFromFAB selectedFragmentFromFAB;
    /**
     * Default Constructor
     */
    public AboutFragment() {}
    /**
     * Return RecyclerView Fragment Instance
     */
    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        /**
         * Get Data
         */
        selectableFragments = this.getArguments().getStringArray("menuItems");
        /**
         *
         */
        final RobotoTextView tvAboutSourceZero = (RobotoTextView) v.findViewById(R.id.tvAboutSourceZero);
        tvAboutSourceZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tvAboutSourceZero.getText().toString())));
            }
        });
        /**
         * ListView Covered Technics
         */
        ListView lvCoveredThings = (ListView) v.findViewById(R.id.lvCoveredThings);
        lvCoveredThings.setAdapter(new CoveredTechnicAdapter(getActivity()));
        /**
         * ListView Libraries
         */
        ListView lvAbout = (ListView) v.findViewById(R.id.lvAbout);
        lvAbout.setAdapter(new AboutAdapter(getActivity()));
        /**
         * Floating Action Button Love
         * Material Dialog Love
         */
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabAbout);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_navigation_menu));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // build material dialog
                new MaterialDialog.Builder(getActivity())
                        .title("Fragment List")
                        .items(selectableFragments)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, String text) {
                                // Send selected Fragment to Activity
                                selectedFragmentFromFAB.passSelectedFragmentFromFAB(which);
                            }
                        })
                        .build()
                        .show();
            }
        });
        fab.attachToListView(lvAbout);
        /**
         * Return View
         */
        return v;
    }
    /**
     * Check if Interface implemented in the Mainactivity if the Fragment is on attaching to Mainactivity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //
        try {
            selectedFragmentFromFAB = (IPassSelectedFragmentFromFAB) activity;
        } catch (ClassCastException e) {
            Snackbar.with(getActivity())
                    .text(activity.toString() + " must implement IPassSelectedFragmentFromFAB")
                    .show(getActivity());
        }
    }
    /**
     * Dispose Interface on detaching fragment from Mainactivity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        //
        selectedFragmentFromFAB = null;
    }
}