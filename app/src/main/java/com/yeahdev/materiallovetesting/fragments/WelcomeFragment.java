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
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.melnykov.fab.FloatingActionButton;
import com.williammora.snackbar.Snackbar;
import com.yeahdev.materiallovetesting.R;
import com.yeahdev.materiallovetesting.adapter.CoveredTechnicAdapter;
import com.yeahdev.materiallovetesting.interfaces.IPassSelectedFragmentFromFAB;


public class WelcomeFragment extends Fragment {
    /**
     * List for Material Dialog Example
     */
    private String[] selectableFragments;
    /**
     * G+ Profile Page
     */
    private String personGooglePlusProfile;
    /**
     * Interface variable
     */
    private IPassSelectedFragmentFromFAB selectedFragmentFromFAB;
    /**
     * Default Constructor
     */
    public WelcomeFragment() {}
    /**
     * Return Welcome Fragment Instance
     */
    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        /**
         * Set G+ Data to Profile Card
         * Init TextViews
         */
        TextView tvCardPersonName = (TextView) v.findViewById(R.id.tvWelcomePersonName);
        TextView tvCardEmail = (TextView) v.findViewById(R.id.tvWelcomeEmail);
        /**
         * Get Data
         */
        String personName = this.getArguments().getString("personName");
        String email = this.getArguments().getString("email");
        personGooglePlusProfile = this.getArguments().getString("personGooglePlusProfile");
        selectableFragments = this.getArguments().getStringArray("menuItems");
        /**
         * Set Data
         */
        tvCardPersonName.setText(personName);
        tvCardEmail.setText(email);
        /**
         * Set Onclicklistener to Email Textview
         */
        tvCardEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("Go to G+ Profile Page?")
                        .theme(Theme.DARK)
                        .positiveText("OK")
                        .negativeText("Nope")
                        .callback(new MaterialDialog.FullCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                if (personGooglePlusProfile == null){
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com")));
                                } else {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(personGooglePlusProfile)));
                                }
                            }
                            @Override
                            public void onNeutral(MaterialDialog dialog) {
                            }
                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .build()
                        .show();
            }
        });
        /**
         * Floating Action Button Love
         * Material Dialog Love
         */
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
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