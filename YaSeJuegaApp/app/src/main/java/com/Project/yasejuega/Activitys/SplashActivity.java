package com.Project.yasejuega.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SplashActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        queue = Volley.newRequestQueue(this);
        CookieHandler.setDefault(new CookieManager());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences user = getSharedPreferences((getString(R.string.SP_USER)),Context.MODE_PRIVATE);
                if((user.contains((getString(R.string.SP_USERNAME)))) && (user.contains((getString(R.string.SP_PASSWORD))))){
                    String[] arg = {user.getString(getString(R.string.SP_USERNAME),"NONE"),user.getString(getString(R.string.SP_PASSWORD),"NONE")};
                    url=getString(R.string.URL_BASE);
                    url+=getString(R.string.URL_REQ_LOGIN);
                    url+="?"+getString(R.string.URL_VAR_USER)+user.getString(getString(R.string.SP_USERNAME),"NONE").toUpperCase();
                    url+="&"+getString(R.string.URL_VAR_PASS)+user.getString(getString(R.string.SP_PASSWORD),"NONE");
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Integer res= Integer.parseInt(response.substring(0, response.length()));
                                    if (res>0){
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        intent.putExtra(getString(R.string.INTENT_USER_PK),res);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getBaseContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        },3000);
    }



}