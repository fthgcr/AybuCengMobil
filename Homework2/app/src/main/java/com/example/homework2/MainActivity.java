package com.example.homework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.drm.DrmStore;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTabLayout;
    TabItem firstItem, secondItem, thirdItem;
    PagerAdapter adapter;

    First first;
    Second second;
    Third third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tablayout);

        first = new First();
        second = new Second();
        third = new Third();

        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);
        thirdItem = findViewById(R.id.thirdItem);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());



        pager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                switch (item.getItemId()){
                    case R.id.facebookItem:
                        i.setData(Uri.parse("https://www.facebook.com/ybuankara/"));
                        startActivity(i);
                        return true;
                    case R.id.twitterItem:
                        i.setData(Uri.parse("https://twitter.com/ybuankara"));
                        startActivity(i);
                        return true;
                    case R.id.instagramItem:
                        i.setData(Uri.parse("https://www.instagram.com/ybuankara/"));
                        startActivity(i);
                        return true;
                    case R.id.youtubeItem:
                        i.setData(Uri.parse("https://www.youtube.com/channel/UCI4nCAzEuDyeikqmfUsBEew"));
                        startActivity(i);
                        return true;
                    case R.id.linkedItem:
                        i.setData(Uri.parse("https://www.linkedin.com/school/ybuankara/"));
                        startActivity(i);
                        return true;
                }
                return true;
            }
        });

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }


}
