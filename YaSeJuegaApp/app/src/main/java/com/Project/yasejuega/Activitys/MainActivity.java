package com.Project.yasejuega.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.Project.yasejuega.Adapters.ViewPagerAdapter;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.Fragments.ReservationsFragment;
import com.Project.yasejuega.Fragments.StartFragment;
import com.Project.yasejuega.Fragments.TournamentsFragment;
import com.Project.yasejuega.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    public ViewPager viewPager;
    Toolbar toolbar;
    private TabLayout tabLayout;
    ConexionSQLiteHelper conSQL;
    ContentValues values;
    SQLiteDatabase db;
    private RequestQueue queue;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout =findViewById(R.id.tabMain);
        viewPager =findViewById(R.id.pagerMain);
        toolbar =  findViewById(R.id.toolbar);
        queue = Volley.newRequestQueue(this);
        conSQL= new ConexionSQLiteHelper(this, Utilidades.BD_YASEJUEGA,null,1);
        db=conSQL.getWritableDatabase();
        values= new ContentValues();


        callAsynchronousTask();
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false); // elimina el titulo por defecto de la actionbar
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(R.drawable.home);
                tabLayout.getTabAt(1).setIcon(R.drawable.court);
                tabLayout.getTabAt(2).setIcon(R.drawable.worldcup);


    }

    @Override
    public void onBackPressed() {
    }
    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartFragment(),getString(R.string.TAB_START));
        adapter.addFragment(new ReservationsFragment(),getString(R.string.TAB_RESERVATION));
        adapter.addFragment(new TournamentsFragment(),getString(R.string.TAB_TOURNAMENTS));
        viewPager.setAdapter(adapter);
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
                logout();
                editor.clear();
                editor.apply();
                startActivity(intent);
                finish();
                break;
            case R.id.Maps:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                             AsyncTaskReLoad taskReload = new AsyncTaskReLoad();
                             taskReload.execute();

                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(task, 300000, 600000);

    }
    private void cargarBD(){
        cargarBuscador();
        cargarTorneos();
        cargarReservas();
        cargarMapa();
    }

    private void logout(){
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_LOGOUT);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }

    private void cargarBuscador() {
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_BUSCADOR);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_HORA);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_HORA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_TIPO);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_TIPO);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_CAMPO_ZONA);
        db.execSQL(Utilidades.CREAR_TABLA_CAMPO_ZONA);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject buscador = response.getJSONObject(i);
                                if (buscador.has(getString(R.string.URL_JSON_B_HORA))){
                                    values.put(Utilidades.CAMPO_CH_HORA,buscador.getString(getString(R.string.URL_JSON_B_HORA)));
                                    db.insert(Utilidades.TABLA_CAMPO_HORA,null,values);
                                }
                                if (buscador.has(getString(R.string.URL_JSON_B_ZONA))){
                                    values.put(Utilidades.CAMPO_CZ_ZONA,buscador.getString(getString(R.string.URL_JSON_B_ZONA)));
                                    db.insert(Utilidades.TABLA_CAMPO_ZONA,null,values);
                                }
                                if (buscador.has(getString(R.string.URL_JSON_B_TIPO))){
                                    values.put(Utilidades.CAMPO_CT_TIPO,buscador.getString(getString(R.string.URL_JSON_B_TIPO)));
                                    db.insert(Utilidades.TABLA_CAMPO_TIPO,null,values);
                                }
                                values.clear();
                            }
                        }catch (JSONException e){
                            Toast.makeText(MainActivity.this, "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void cargarMapa() {
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_MAPA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_MAPA);
        db.execSQL(Utilidades.CREAR_TABLA_MAPA);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject mapa = response.getJSONObject(i);

                                values.put(Utilidades.CAMPO_M_GPS,mapa.getString(getString(R.string.URL_JSON_M_GPS)));
                                values.put(Utilidades.CAMPO_M_PREDIO,mapa.getString(getString(R.string.URL_JSON_M_PREDIO)));
                                values.put(Utilidades.CAMPO_M_PREDIO_PK,mapa.getInt(getString(R.string.URL_JSON_M_PREDIO_PK)));
                                db.insert(Utilidades.TABLA_MAPA,null,values);
                                values.clear();
                            }
                        }catch (JSONException e){
                            Toast.makeText(MainActivity.this, "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void cargarReservas() {
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_RESERVAS);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_RESERVA);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_RESERVA);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject reservas = response.getJSONObject(i);

                                values.put(Utilidades.CAMPO_LR_CANCHA,reservas.getString(getString(R.string.URL_JSON_R_CANCHA)));
                                values.put(Utilidades.CAMPO_LR_DIRECCION,reservas.getString(getString(R.string.URL_JSON_R_DIRECCION)));
                                values.put(Utilidades.CAMPO_LR_ESTADO,reservas.getString(getString(R.string.URL_JSON_R_ESTADO)));
                                values.put(Utilidades.CAMPO_LR_FECHA,reservas.getString(getString(R.string.URL_JSON_R_FECHA)));
                                values.put(Utilidades.CAMPO_LR_HORA,reservas.getString(getString(R.string.URL_JSON_R_HORA)));
                                values.put(Utilidades.CAMPO_LR_PRECIO,reservas.getString(getString(R.string.URL_JSON_R_PRECIO)));
                                values.put(Utilidades.CAMPO_LR_PREDIO,reservas.getString(getString(R.string.URL_JSON_R_PREDIO)));
                                values.put(Utilidades.CAMPO_LR_RESERVA_PK,reservas.getInt(getString(R.string.URL_JSON_R_RESERVA_PK)));
                                values.put(Utilidades.CAMPO_LR_SENA,reservas.getString(getString(R.string.URL_JSON_R_SENA)));
                                values.put(Utilidades.CAMPO_LR_SUPERFICIE,reservas.getString(getString(R.string.URL_JSON_R_SUPERFICIE)));
                                values.put(Utilidades.CAMPO_LR_TECHADO,reservas.getString(getString(R.string.URL_JSON_R_TECHADO)));
                                values.put(Utilidades.CAMPO_LR_TELEFONO,reservas.getString(getString(R.string.URL_JSON_R_TELEFONO)));
                                values.put(Utilidades.CAMPO_LR_TIMESTAMP,reservas.getString(getString(R.string.URL_JSON_R_TIMESTAMP)));
                                values.put(Utilidades.CAMPO_LR_TIPO,reservas.getString(getString(R.string.URL_JSON_R_TIPO)));
                                values.put(Utilidades.CAMPO_LR_ZONA,reservas.getString(getString(R.string.URL_JSON_R_ZONA)));
                                int aux=reservas.getString(getString(R.string.URL_JSON_R_RESERVA_PK)).length();
                                StringBuffer aux2= new StringBuffer(8);
                                if(aux<8){
                                    aux=8-aux;
                                    for(int j=0;j<aux;j++){
                                        aux2.append("0");
                                    }
                                }
                                aux2.append(reservas.getString((getString(R.string.URL_JSON_R_RESERVA_PK))));
                                values.put(Utilidades.CAMPO_LR_NRESERVA,aux2.toString());
                                db.insert(Utilidades.TABLA_LISTA_RESERVA,null,values);
                                values.clear();
                            }
                        }catch (JSONException e){
                            Toast.makeText(MainActivity.this, "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private void cargarTorneos() {
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_TORNEOS);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_TORNEOS);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_TORNEOS);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject torneos = response.getJSONObject(i);

                                values.put(Utilidades.CAMPO_LT_TORNEO_PK,torneos.getString(getString(R.string.URL_JSON_T_TORNEO_PK)));
                                values.put(Utilidades.CAMPO_LT_DIRECCION,torneos.getString(getString(R.string.URL_JSON_T_DIRECCION)));
                                values.put(Utilidades.CAMPO_LT_EQUIPO,torneos.getString(getString(R.string.URL_JSON_T_EQUIPO)));
                                values.put(Utilidades.CAMPO_LT_FECHA_INICIO,torneos.getString(getString(R.string.URL_JSON_T_FECHA_INICIO)));
                                values.put(Utilidades.CAMPO_LT_PRECIO,torneos.getString(getString(R.string.URL_JSON_T_PRECIO)));
                                values.put(Utilidades.CAMPO_LT_PRECIOxPARTIDO,torneos.getString(getString(R.string.URL_JSON_T_PRECIOxPARTIDO)));
                                values.put(Utilidades.CAMPO_LT_PREDIO,torneos.getString(getString(R.string.URL_JSON_T_PREDIO)));
                                values.put(Utilidades.CAMPO_LT_TELEFONO,torneos.getString(getString(R.string.URL_JSON_T_TELEFONO)));
                                values.put(Utilidades.CAMPO_LT_ZONA,torneos.getString(getString(R.string.URL_JSON_T_ZONA)));

                                db.insert(Utilidades.TABLA_LISTA_TORNEOS,null,values);
                                values.clear();
                            }
                        }catch (JSONException e){
                            Toast.makeText(MainActivity.this, "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
    }

    private class AsyncTaskReLoad extends AsyncTask<Void,Void,String>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params)
        {

            cargarBD();
            return "";

        }


        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);


        }
    }


}
