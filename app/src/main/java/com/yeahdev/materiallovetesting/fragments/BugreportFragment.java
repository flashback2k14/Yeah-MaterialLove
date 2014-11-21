package com.yeahdev.materiallovetesting.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devspark.robototextview.widget.RobotoEditText;
import com.melnykov.fab.FloatingActionButton;
import com.williammora.snackbar.Snackbar;
import com.yeahdev.materiallovetesting.R;
import com.yeahdev.materiallovetesting.interfaces.IPassSelectedFragmentFromFAB;


public class BugreportFragment extends Fragment {
    /**
     * List for Material Dialog Example
     */
    private String[] selectableFragments;
    /**
     * Feedback Message
     */
    private RobotoEditText etBugreport;
    /**
     * Activity Request Code
     */
    private static final int BUGREPORT_SEND_REQUEST = 1;
    /**
     * Interface variable
     */
    private IPassSelectedFragmentFromFAB selectedFragmentFromFAB;
    /**
     * Default Constructor
     */
    public BugreportFragment(){}
    /**
     * Return Welcome Fragment Instance
     */
    public static BugreportFragment newInstance() {
        return new BugreportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bugreport, container, false);
        /**
        * Get Data
        */
        selectableFragments = this.getArguments().getStringArray("menuItems");
        /**
         * Get Email Content
         */
        etBugreport = (RobotoEditText) v.findViewById(R.id.etBugreportkMessage);
        /**
         * Floating Action Button Love
         * Material Dialog Love
         */
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabBugreport);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_content_send));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBugreport();
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
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

                return true;
            }
        });
        /**
         * Return View
         */
        return v;
    }
    /**
     *
     */
    private void sendBugreport() {
        String emailMessage = etBugreport.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"yeahdev@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Bugreport - Material Love Demo");
        i.putExtra(Intent.EXTRA_TEXT, emailMessage);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivityForResult(Intent.createChooser(i, "Send Bugreport to developer..."), BUGREPORT_SEND_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar.with(getActivity())
                    .text("There are no email clients installed.")
                    .show(getActivity());
        }
    }
    /**
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //
        if (requestCode == BUGREPORT_SEND_REQUEST) {
            etBugreport.setText("");
            etBugreport.setText("Enter your Bugreport:\nuseful information\nPhone:\nAndroid-OS:\nPro:\nCon:\nSuggestions:\nBugs:");
        }
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