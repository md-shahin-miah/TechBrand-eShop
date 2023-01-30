package com.shahin.techbrandeshop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.navigation.NavigationView;
import com.shahin.techbrandeshop.R;
import com.shahin.techbrandeshop.fragment.HomeFragment;


import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private CircleImageView btnFollowFb, btnFollowGit, btnFollowYT;
    private NavigationView mNavigationView;
    private FrameLayout mFragmentContainer;

    private CircleImageView[] socialMedia = null;

    private static final String FACEBOOK_URL = "Demo: Follow me on facebook";
    private static final String GITHUB_URL = "Demo: Follow me on github";
    private static final String YTB_URL = "Demo: Follow me on youtube";

    private String[] followUrls = {FACEBOOK_URL, GITHUB_URL, YTB_URL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        initUI();
        initNavigationDrawer();

        for (int i = 0; i < socialMedia.length; i++) {
            int finalI = i;
            socialMedia[i].setOnClickListener(view -> openApp(followUrls[finalI]));
        }

        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        getSupportFragmentManager().beginTransaction()
                .add(mFragmentContainer.getId(), new HomeFragment())
                .addToBackStack(HomeFragment.class.getName())
                .commit();
    }

    private void initUI() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.my_toolbar);

        btnFollowFb = mDrawerLayout.findViewById(R.id.follow_us_on_fb);
        btnFollowGit = mDrawerLayout.findViewById(R.id.follow_us_on_git);
        btnFollowYT = mDrawerLayout.findViewById(R.id.follow_us_on_yt);

        socialMedia = new CircleImageView[]{btnFollowFb, btnFollowGit, btnFollowYT};

        mNavigationView = findViewById(R.id.my_navigation_view);

        mFragmentContainer = findViewById(R.id.homepage_fragment_container);
    }

    private void initNavigationDrawer() {
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_navigation_drawer, R.string.close_navigation_drawer);

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //Change NavigationView Toggle drawable icon
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menubar);
    }

    private void openApp(String appName) {
        Toast.makeText(this, appName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_cart) {
            Intent intent = new Intent(this, ContainerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(ContainerActivity.CONTAINER_TOOLBAR_MENU_MODE, 0);
            intent.putExtra(ContainerActivity.CONTAINER_KEY, bundle);
            startActivity(intent);
        }
        return true;
    }

    @SuppressLint("UnsafeOptInUsageError")
    public void updateCartQuantityNotification(int quantity) {
        BadgeDrawable badgeDrawable = BadgeDrawable.create(HomeActivity.this);
        badgeDrawable.setNumber(quantity);
        badgeDrawable.setBackgroundColor(Color.parseColor("#ee4d2d"));
        BadgeUtils.attachBadgeDrawable(badgeDrawable, mToolbar, R.id.toolbar_cart);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}