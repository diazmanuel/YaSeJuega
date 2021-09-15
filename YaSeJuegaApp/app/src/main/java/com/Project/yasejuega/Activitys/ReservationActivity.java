package com.Project.yasejuega.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.Project.yasejuega.Classes.YaSeJuega;
import com.Project.yasejuega.Fragments.PredioFragment;
import com.Project.yasejuega.Fragments.SearchFragment;
import com.Project.yasejuega.R;
import com.google.android.material.navigation.NavigationView;

public class ReservationActivity extends AppCompatActivity {
    public SearchFragment searchFragment;
    public PredioFragment predioFragment;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        toolbar =  findViewById(R.id.toolbar);
        searchFragment=new SearchFragment();
        predioFragment=new PredioFragment();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // elimina el titulo por defecto de la actionbar

        getSupportFragmentManager().beginTransaction().add(R.id.fg,searchFragment).commit();
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Intent intent;
        switch (item.getItemId())
        {
            case R.id.Logout:
                intent = new Intent(getBaseContext(), LoginActivity.class);
                SharedPreferences preferences =getSharedPreferences(getString(R.string.SP_USER), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(intent);
                finish();
                break;
            case R.id.Maps:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra(YaSeJuega.getContext().getString(R.string.INTENT_USER_PK_MAP),0);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }


}
