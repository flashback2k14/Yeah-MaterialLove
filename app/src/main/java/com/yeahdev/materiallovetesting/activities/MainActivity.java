package com.yeahdev.materiallovetesting.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.devspark.robototextview.widget.RobotoTextView;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.williammora.snackbar.Snackbar;
import com.yeahdev.materiallovetesting.R;
import com.yeahdev.materiallovetesting.adapter.DrawerListAdapter;
import com.yeahdev.materiallovetesting.fragments.AboutFragment;
import com.yeahdev.materiallovetesting.fragments.BugreportFragment;
import com.yeahdev.materiallovetesting.fragments.FeedbackFragment;
import com.yeahdev.materiallovetesting.fragments.GithubIssusFragment;
import com.yeahdev.materiallovetesting.fragments.RecyclerViewFragment;
import com.yeahdev.materiallovetesting.fragments.WelcomeFragment;
import com.yeahdev.materiallovetesting.interfaces.IPassSelectedFragmentFromFAB;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends ActionBarActivity implements IPassSelectedFragmentFromFAB {
    // declare variables
    // toolbar
    private Toolbar mToolbar;
    // drawer layout
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    // drawer listview
    private LinearLayout mLlvDrawerContent;
    private ListView mLsvDrawerMenu;
    // data for drawer listview
    private static final int[] ICON_ITEMS = new int[] {
            R.drawable.ic_action_action_home,
            R.drawable.ic_action_action_view_day,
            R.drawable.ic_action_communication_email,
            R.drawable.ic_action_action_bug_report
    };
    private static final String[] MENU_ITEMS = new String[] {
            "Welcome",
            "Recyclerview",
            "Feedback",
            "Bugreport"
    };
    // FragmentTransaction
    private FragmentTransaction ft;
    // Fragments
    private Fragment welcomeFragment = null;
    private Fragment recyclerViewFragment = null;
    private Fragment feedbackFragment = null;
    private Fragment bugreportFragment = null;
    private GithubIssusFragment githubIssueFragment = null;
    private Fragment aboutFragment = null;
    // G+ Profile Data
    private String personName;
    private String email;
    private String personGooglePlusProfile;
    // flag for back press event
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Tint Statusbar API 19 Devices
         */
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            SystemBarTintManager stm = new SystemBarTintManager(this);
            stm.setStatusBarTintEnabled(true);
            stm.setStatusBarTintColor(getResources().getColor(R.color.theme_default_primary_dark));
            //
            LinearLayout llvMainContent = (LinearLayout) findViewById(R.id.llvMainContent);
            SystemBarTintManager.SystemBarConfig config = stm.getConfig();
            llvMainContent.setPadding(0, config. getPixelInsetTop(false), config.getPixelInsetRight(), config.getPixelInsetBottom());
        }
        /**
         * Toolbar Love
         */
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("Material Love");
            mToolbar.setSubtitle("Welcome");
            setSupportActionBar(mToolbar);
        }
        /**
         * Drawer Layout Love
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // 3 Lines
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Arrow
        //getSupportActionBar().setHomeButtonEnabled(true);
        setDrawerMenu();
        /**
         * Set G+ Profile Data zu Drawer Menu Header
         */
        setGPlusProfileDataToDrawerMenu();
        /**
         * Init Fragments
         */
        this.welcomeFragment = WelcomeFragment.newInstance();
        this.recyclerViewFragment = RecyclerViewFragment.newInstance();
        this.feedbackFragment = FeedbackFragment.newInstance();
        this.bugreportFragment = BugreportFragment.newInstance();
        this.githubIssueFragment = GithubIssusFragment.newInstance();
        this.aboutFragment = AboutFragment.newInstance();
        /**
         * Set Welcome Fragment as first Fragment
         */
        setFirstFragment();
        /**
         * implement!?
         * ShowCase Demo
         */
        //setShowCaseDemo();
    }
    /**
     * Drawer Menu Logic
     */
    private void setDrawerMenu() {
        // init listview + linearlayout
        mLsvDrawerMenu = (ListView) findViewById(R.id.lsv_drawer_menu);
        mLlvDrawerContent = (LinearLayout) findViewById(R.id.llv_left_drawer);
        // init header + footer area for drawer menu
        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.drawer_menu_header, mLsvDrawerMenu, false);
        final ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.drawer_menu_footer, mLsvDrawerMenu, false);
        // set header + footer to listview
        mLsvDrawerMenu.addHeaderView(header, null, false); // true = clickable
        mLsvDrawerMenu.addFooterView(footer, null, true); // true = clickable
        // listview item click listener
        mLsvDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // call method
                selectMenuItem(position);
            }
        });
        // fill listview with data
        mLsvDrawerMenu.setAdapter(new DrawerListAdapter(this, ICON_ITEMS, MENU_ITEMS));
    }
    // get the clicked listview item + close drawer menu + show a new fragment
    private void selectMenuItem(int position) {
        // Declare local Variables
        int currentPositionFix = position - 1;
        this.ft = getSupportFragmentManager().beginTransaction();
        // Fragment Items clicked
        switch (currentPositionFix) {
            //
            case 0:
                ft.replace(R.id.contentFrame, this.welcomeFragment);
                mToolbar.setSubtitle("Welcome");
                break;

            case 1:
                setArgumentsToFragmentAndReplace(this.ft, this.recyclerViewFragment, "RecyclerView");
                break;

            case 2:
                setArgumentsToFragmentAndReplace(this.ft, this.feedbackFragment, "Feedback");
                break;

            case 3:
                new MaterialDialog.Builder(this)
                        .title("Bugreport")
                        .content("Have you a Github Account?")
                        .positiveText("Yeah")
                        .negativeText("Nope")
                        .callback(new MaterialDialog.FullCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                setArgumentsToFragmentAndReplace(ft, githubIssueFragment, "Github Issues");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.contentFrame, githubIssueFragment)
                                        .commit();
                            }
                            @Override
                            public void onNeutral(MaterialDialog dialog) {}
                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                setArgumentsToFragmentAndReplace(ft, bugreportFragment, "Bugreport");
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.contentFrame, bugreportFragment)
                                        .commit();
                            }
                        })
                        .build()
                        .show();
                break;

            case 4:
                setArgumentsToFragmentAndReplace(ft, this.aboutFragment, "About");
                break;

            default:
                break;
        }
        //
        this.ft.commit();
        //
        mLsvDrawerMenu.setItemChecked(currentPositionFix, true);
        mDrawerLayout.closeDrawer(mLlvDrawerContent);
    }
    /**
     * Implement Interface from Welcome Fragment
     */
    @Override
    public void passSelectedFragmentFromFAB(int position) {
        selectMenuItem(position + 1);
    }
    /**
     * Snackbar Builder helper method
     */
    private void buildSnackBar(String msg) {
        Snackbar.with(getApplicationContext())
                .text(msg)
                .textColor(getResources().getColor(R.color.theme_window_background))
                .show(MainActivity.this);
    }
    /**
     * Set G+ Profile Data zu Drawer Menu Header
     */
    private void setGPlusProfileDataToDrawerMenu() {
        /**
         * local Variable
         */
        String personPhotoUrl;
        /**
         * Drawer Menu
         */
        RobotoTextView tvDrawerMenuName = (RobotoTextView) findViewById(R.id.tvDrawerMenuName);
        RobotoTextView tvDrawerMenuEmail = (RobotoTextView) findViewById(R.id.tvDrawerMenuEmail);
        CircleImageView cvImageView = (CircleImageView) findViewById(R.id.cvImagePic);
        /**
         * Get and Set Data
         */
        try {
            /**
             * Get
             */
            personName = getIntent().getExtras().getString("personName");
            personPhotoUrl = getIntent().getExtras().getString("personPhotoUrl");
            email = getIntent().getExtras().getString("email");
            personGooglePlusProfile = getIntent().getExtras().getString("personGooglePlusProfile");
            /**
             * Set
             */
            tvDrawerMenuName.setText(personName);
            tvDrawerMenuEmail.setText(email);
            // by default the profile url gives 50x50 px image only
            // we can replace the value with whatever dimension we want by
            // replacing sz=X
            if (personPhotoUrl != null) {
                personPhotoUrl = personPhotoUrl.substring(0, personPhotoUrl.length() - 2) + 200;
                new LoadProfileImage(cvImageView).execute(personPhotoUrl);
            }
            /**
             * Set Onclicklistener to CircleViewImage
             */
             cvImageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     new MaterialDialog.Builder(MainActivity.this)
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
                                 public void onNeutral(MaterialDialog dialog) {}
                                 @Override
                                 public void onNegative(MaterialDialog dialog) {
                                     dialog.dismiss();
                                 }
                             })
                             .build()
                             .show();
                 }
             });

        } catch (Exception e) {
            buildSnackBar(e.getMessage());
        }
    }
    /**
     * Set Welcome Fragment as first Fragment
     * Attach G+ Profile Data to Fragment
     */
    private void setFirstFragment() {
        Bundle mGplusProfileData = new Bundle();
        mGplusProfileData.putString("personName", personName);
        mGplusProfileData.putString("email", email);
        mGplusProfileData.putString("personGooglePlusProfile", personGooglePlusProfile);
        mGplusProfileData.putStringArray("menuItems", MENU_ITEMS);
        this.welcomeFragment.setArguments(mGplusProfileData);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, this.welcomeFragment).commit();
    }
    /**
     * Set Arguments to Fragment
     */
    private void setArgumentsToFragmentAndReplace(FragmentTransaction ft,Fragment frag, String toolbarSubtitle){
        Bundle itemList = new Bundle();
        itemList.putStringArray("menuItems", MENU_ITEMS);
        //
        try {
            frag.setArguments(itemList);
        } catch (Exception e) {
            buildSnackBar("Fragment ís already in use.");
        }
        //
        ft.replace(R.id.contentFrame, frag);
        mToolbar.setSubtitle(toolbarSubtitle);
    }
    /**
     * implement!?
     * Showcase Demo
     */
    private void setShowCaseDemo() {
        //ViewTarget target = new ViewTarget(mDrawerLayout);
        PointTarget pointTarget = new PointTarget(90,70);
        ShowcaseView scv = new ShowcaseView.Builder(this, true)
                .setTarget(pointTarget)
                .setContentTitle("New Drawer Menu")
                .setContentText("Content text")
                .setStyle(R.style.CustomShowcaseTheme)
                //.singleShot(2000)
                .hideOnTouchOutside()
                .build();
        scv.show();
    }
    /**
     * Background Async task to load user profile picture from url
     * */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        CircleImageView bmImage;

        public LoadProfileImage(CircleImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
    /**
     * Override standard methods
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // sync drawer icon state
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // sync drawer icon config
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // drawer icon - if drawer open close him
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if(mDrawerLayout.isDrawerOpen(Gravity.END)) {
                mDrawerLayout.closeDrawer(Gravity.END);
            }
            return true;
        }
        // settings - demo for material dialog
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            // build material dialog
            new MaterialDialog.Builder(this)
                    .title("Settings")
                    .content("Nothing to see here!")
                    .positiveText("Agree")
                    .negativeText("Disagree")
                    .neutralText("More info")
                    .callback(new MaterialDialog.FullCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Toast.makeText(getApplicationContext(), "Thanks!", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNeutral(MaterialDialog dialog) {
                            Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            Toast.makeText(getApplicationContext(), "Not my problem. :-)", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.githubIssueFragment != null && this.githubIssueFragment.canGoBack()) {
            this.githubIssueFragment.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           super.onBackPressed();
           return;
        } else {
            if (mDrawerLayout.isDrawerOpen(mLlvDrawerContent)){
                mDrawerLayout.closeDrawer(mLlvDrawerContent);
            } else {
                mDrawerLayout.openDrawer(mLlvDrawerContent);
            }
        }

        this.doubleBackToExitPressedOnce = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000); //Waiting time for secound back button press
    }
}