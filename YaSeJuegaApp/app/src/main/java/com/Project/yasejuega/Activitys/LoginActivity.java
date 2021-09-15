package com.Project.yasejuega.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.Project.yasejuega.Adapters.ViewPagerAdapter;
import com.Project.yasejuega.Fragments.NoUserFragment;
import com.Project.yasejuega.Fragments.UserFragment;
import com.Project.yasejuega.R;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity {


    public ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            tabLayout = findViewById(R.id.tabLogin);
            viewPager = findViewById(R.id.pagerLogin);

            setupViewPager(viewPager);

            tabLayout.setupWithViewPager(viewPager);

        }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserFragment(),getString(R.string.TAB_USER));
        adapter.addFragment(new NoUserFragment(),getString(R.string.TAB_NO_USER));
        viewPager.setAdapter(adapter);
    }

}

