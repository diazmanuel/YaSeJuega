package com.Project.yasejuega.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;

import com.Project.yasejuega.Adapters.ViewPagerAdapter;

import com.Project.yasejuega.Fragments.SignInFragment;
import com.Project.yasejuega.R;
import com.google.android.material.tabs.TabLayout;

public class SignInActivity extends AppCompatActivity {
    public ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        tabLayout = findViewById(R.id.tabSignIn);
        viewPager = findViewById(R.id.pagerSignIn);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // elimina el titulo por defecto de la actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager()); //Usar Child!
        adapter.addFragment(new SignInFragment(),getString(R.string.TAB_SIGNIN));
        viewPager.setAdapter(adapter);
    }
}
