package com.Project.yasejuega.Fragments;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.yasejuega.Activitys.MainActivity;
import com.Project.yasejuega.Activitys.SignInActivity;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements View.OnClickListener {

Button btnLogin;
TextView txtContacto, txtRegistro,txtOlvido;
TextView emUser,emPass;
String text1,text2,text3;
CheckedTextView ctvIniciar;
Fragment frContacto;
    private int countQueue;
    private RequestQueue queue;
    private String url;
    ConexionSQLiteHelper conSQL;
    ContentValues values;
    SQLiteDatabase db;
    SpannableString ss1,ss2,ss3;
    ClickableSpan cs1,cs2,cs3;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user, container, false);
        btnLogin = v.findViewById(R.id.btnLogin);
        ctvIniciar = v.findViewById(R.id.ctvIniciar);
        btnLogin.setOnClickListener(this);
        ctvIniciar.setOnClickListener(this);
        txtContacto = v.findViewById(R.id.txtContacto);
        txtRegistro = v.findViewById(R.id.txtRegistro);
        txtOlvido = v.findViewById(R.id.txtOlvido);

        emPass = v.findViewById(R.id.emPass);
        emUser = v.findViewById(R.id.emUser);
        text1 = getString(R.string.TXT_TEXTO_CONTACTO)+ " " +getString(R.string.TXT_CONTACTO);
        text2 = getString(R.string.TXT_TEXTO_REGISTRO)+ " " +getString(R.string.TXT_REGISTRO);
        text3 = getString(R.string.TXT_TEXTO_OLVIDO)+ " " +getString(R.string.TXT_OLVIDO);

        countQueue=0;
        queue = Volley.newRequestQueue(getActivity());
        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);
        db=conSQL.getWritableDatabase();
        values= new ContentValues();
        ss1 = new SpannableString(text1);
        cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(getActivity(), "CONTACTO", Toast.LENGTH_SHORT).show();
                frContacto = new ContactFragment();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.add(R.id.pagerMain, frContacto).commit();

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getActivity(), R.color.text2));
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        ss2 = new SpannableString(text2);
        cs2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(getActivity(), "REGISTRO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getActivity(), R.color.text2));
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        ss3 = new SpannableString(text3);
        cs3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(getActivity(), "OLVIDO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getActivity(), R.color.text2));
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        ss1.setSpan(cs1,(text1.length())-(getString(R.string.TXT_CONTACTO).length()),text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(cs2,(text2.length())-(getString(R.string.TXT_REGISTRO).length()),text2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss3.setSpan(cs3,(text3.length())-(getString(R.string.TXT_OLVIDO).length()),text3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtContacto.setText(ss1);
        txtContacto.setMovementMethod(LinkMovementMethod.getInstance());
        txtRegistro.setText(ss2);
        txtRegistro.setMovementMethod(LinkMovementMethod.getInstance());
        txtOlvido.setText(ss3);
        txtOlvido.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                Drawable draw=getActivity().getDrawable(R.drawable.custom_progressbar);
                final ProgressDialog nDialog;
                nDialog = new ProgressDialog(getContext());
                nDialog.setMessage(getString(R.string.PB_TIT));
                nDialog.setIndeterminateDrawable(draw);
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.show();
                url=getString(R.string.URL_BASE);
                url+=getString(R.string.URL_REQ_LOGIN);
                url+="?"+getString(R.string.URL_VAR_USER)+emUser.getText().toString().toUpperCase();
                url+="&"+getString(R.string.URL_VAR_PASS)+emPass.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                final Integer res= Integer.parseInt(response.substring(0, response.length()));
                                if (res>0){
                                    if (ctvIniciar.isChecked()){
                                         savePreferences(emUser.getText().toString().toUpperCase(),emPass.getText().toString());
                                    }
                                    cargarBD();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            nDialog.dismiss();
                                            if(countQueue==0){
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                intent.putExtra(getString(R.string.INTENT_USER_PK),res);
                                                startActivity(intent);
                                                getActivity().finish();
                                            }else{
                                                countQueue=0;
                                                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        },3000);

                                }else{
                                    emPass.setError(getString(R.string.ERROR));
                                    emUser.setError(getString(R.string.ERROR));
                                    nDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                        nDialog.dismiss();
                    }
                });
                queue.add(stringRequest);
                break;
            case R.id.ctvIniciar:
                if(ctvIniciar.isChecked()){
                    ctvIniciar.setChecked(false);
                }else{
                    ctvIniciar.setChecked(true);
                }
                break;
        }
    }

    private void savePreferences(String user,String pass) {
        SharedPreferences preferences = getContext().getSharedPreferences(getString(R.string.SP_USER),Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor =preferences.edit();
        editor.putString(getString(R.string.SP_USERNAME),user);
        editor.putString(getString(R.string.SP_PASSWORD),pass);
        editor.apply();
    }
    private void cargarBD(){
        cargarBuscador();
        cargarTorneos();
        cargarReservas();
        cargarMapa();
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
                            countQueue--;
                        }catch (JSONException e){
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
        countQueue++;
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
                            countQueue--;
                        }catch (JSONException e){
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
        countQueue++;
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
                            countQueue--;
                        }catch (JSONException e){
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
        countQueue++;
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
                            countQueue--;
                        }catch (JSONException e){
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(jsonArrayRequest);
        countQueue++;
    }
}
