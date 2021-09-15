package com.Project.yasejuega.Activitys;

import androidx.fragment.app.FragmentActivity;



import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;


import android.os.Bundle;
import android.widget.Toast;

import com.Project.yasejuega.Classes.MapsClass;

import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback{

    private GoogleMap mMap;
    MapsClass maps;
    ArrayList<MapsClass> listMaps;
    ConexionSQLiteHelper conSQL;
    SQLiteDatabase db;
    Cursor cursor;
    Integer key;

    private FusedLocationProviderClient fusedLocationClient;
    private double latitud,longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        key=getIntent().getIntExtra(getString(R.string.INTENT_USER_PK_MAP),0);

        if(ConnectionResult.SUCCESS == (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this))) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        conSQL= new ConexionSQLiteHelper(this, Utilidades.BD_YASEJUEGA,null,1);
        db = conSQL.getReadableDatabase();
        try {
            cargarClase();
            cursor.close();
        }catch (Exception e){
            Toast.makeText(this, "ERROR ACCEDIENDO A LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }

        mMap = googleMap;
        for(int i=0;i<listMaps.size();i++) {
            mMap.addMarker(new MarkerOptions().position(listMaps.get(i).getLatLng()).title(listMaps.get(i).getPredio())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark)));
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if(key==0) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {

                        longitud = (location.getLongitude());
                        latitud = (location.getLatitude());

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitud, longitud), 16));

                    }
                }
            });
        }else{
            for(int i=0;i<listMaps.size();i++) {
                if (listMaps.get(i).getPredio_pk()==key){
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listMaps.get(i).getLatLng(), 16));
                }
            }
        }

    }



    private void cargarClase() {

        listMaps = new ArrayList<>();
        cursor = db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MAPA, null);
        if (cursor.getCount() == 0) {

        } else {
            cursor.moveToFirst();
            do {
                maps = new MapsClass();
                maps.setPredio_pk(cursor.getInt(0));
                maps.setPredio(cursor.getString(1));
                maps.setLatLng(cursor.getString(2));
                listMaps.add(maps);
            } while (cursor.moveToNext());
        }

    }


}