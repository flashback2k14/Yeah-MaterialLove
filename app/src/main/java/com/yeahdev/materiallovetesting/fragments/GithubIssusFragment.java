package com.yeahdev.materiallovetesting.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.afollestad.materialdialogs.MaterialDialog;
import com.melnykov.fab.FloatingActionButton;
import com.williammora.snackbar.Snackbar;
import com.yeahdev.materiallovetesting.R;
import com.yeahdev.materiallovetesting.interfaces.IPassSelectedFragmentFromFAB;


public class GithubIssusFragment extends Fragment {
    /**
     * List for Material Dialog Example
     */
    private String[] selectableFragments;
    /**
     * Interface variable
     */
    private IPassSelectedFragmentFromFAB selectedFragmentFromFAB;
    /**
     *
     */
    private WebView wv;
    /**
     * Default Constructor
     */
    public GithubIssusFragment() {}
    /**
     * Return GithubIssus Fragment Instance
     */
    public static GithubIssusFragment newInstance() {
        return new GithubIssusFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_github_issus, container, false);
        /**
         * Get Data
         */
        selectableFragments = this.getArguments().getStringArray("menuItems");
        /**
         * Webview
         * https://github.com/I-am-Reinvented/ToolbarMenudrawer/issues/new
         */
        wv = (WebView) v.findViewById(R.id.wvGithubIssuse);
        wv.loadUrl("https://github.com/flashback2k14");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.clearCache(true);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        wv.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        /**
         * Floating Action Button Love
         * Material Dialog Love
         */
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabGithubIssues);
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
     *
     */
    public boolean canGoBack() {
        return this.wv != null &&  this.wv.canGoBack();
    }
    /**
     *
     */
    public void goBack() {
        if(this.wv != null) {
            this.wv.goBack();
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