package com.Project.yasejuega.Fragments;


import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.Project.yasejuega.Activitys.ReservationActivity;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {
    private Button  btnZona,btnTipo,btnFecha,btnHora,btnBuscar;

    private boolean[] checkedZona,checkedTipo,checkedHora,checkedFecha;
    private ArrayList<Integer> mZona,mTipo,mHora,mFecha;
    private ArrayList<String> listaZona,listaTipo,listaHora,listaFecha,listaTipoAux;
    private ArrayList<Long> timestamp;
    private RequestQueue queue;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private String url;
    private Long aux;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;


    private ContentValues values;


    private ConexionSQLiteHelper conSQL;
    private SQLiteDatabase db;
    private Cursor cursor;


    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_start, container, false);
        btnZona = v.findViewById(R.id.btnZona);
        btnTipo = v.findViewById(R.id.btnTipo);
        btnFecha = v.findViewById(R.id.btnFecha);
        btnHora = v.findViewById(R.id.btnHora);
        btnBuscar = v.findViewById(R.id.btnBuscar);

        queue = Volley.newRequestQueue(getActivity());
        jsonObject = new JSONObject();
        values= new ContentValues();

        timestamp = new ArrayList<>();

        listaZona = new ArrayList<>();
        listaTipo = new ArrayList<>();
        listaFecha = new ArrayList<>();
        listaHora = new ArrayList<>();
        listaTipoAux = new ArrayList<>();



        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);


        btnZona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                mBuilder.setTitle(R.string.LABEL_SELECCION);

                mBuilder.setMultiChoiceItems(listaZona.toArray(new String[0]), checkedZona, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mZona.add(position);
                        }else{
                            mZona.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.LABEL_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                mBuilder.setNeutralButton(R.string.LABEL_CLEAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedZona.length; i++) {
                            checkedZona[i] = false;
                            mZona.clear();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnTipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                mBuilder.setTitle(R.string.LABEL_SELECCION);
                mBuilder.setMultiChoiceItems(listaTipo.toArray(new String[0]), checkedTipo, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mTipo.add(position);
                        }else{
                            mTipo.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.LABEL_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                mBuilder.setNeutralButton(R.string.LABEL_CLEAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedTipo.length; i++) {
                            checkedTipo[i] = false;
                            mTipo.clear();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                mBuilder.setTitle(R.string.LABEL_SELECCION);
                mBuilder.setMultiChoiceItems(listaFecha.toArray(new String[0]), checkedFecha, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mFecha.add(position);
                        }else{
                            mFecha.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.LABEL_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                mBuilder.setNeutralButton(R.string.LABEL_CLEAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedFecha.length; i++) {
                            checkedFecha[i] = false;
                            mFecha.clear();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });



        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                mBuilder.setTitle(R.string.LABEL_SELECCION);

                mBuilder.setMultiChoiceItems(listaHora.toArray(new String[0]), checkedHora, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            mHora.add(position);
                        }else{
                            mHora.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(true);
                mBuilder.setPositiveButton(R.string.LABEL_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                mBuilder.setNeutralButton(R.string.LABEL_CLEAR, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedHora.length; i++) {
                            checkedHora[i] = false;
                            mHora.clear();
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonArray = new JSONArray();
                if(mFecha.size()>0) {
                    for (int i = 0; i < mFecha.size(); i++) {
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("FECHA", listaFecha.get(mFecha.get(i)));
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(mHora.size()>0) {
                    for (int i = 0; i < mHora.size(); i++) {
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("HORA", mHora.toArray()[i].toString());
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(mZona.size()>0) {
                    for (int i = 0; i < mZona.size(); i++) {
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("ZONA", listaZona.get(mZona.get(i)));
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(mTipo.size()>0) {
                    for (int i = 0; i < mTipo.size(); i++) {
                        try {
                            jsonObject = new JSONObject();
                            jsonObject.put("TIPO", listaTipoAux.get(mTipo.get(i)));
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                cargarBusqueda();

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        timestamp.clear();
        listaFecha.clear();
        listaHora.clear();
        listaTipo.clear();
        listaZona.clear();

        db = conSQL.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CAMPO_ZONA+" ORDER BY "+Utilidades.CAMPO_CZ_ZONA+" ASC",null);
            cursor.moveToFirst();
            do{
                listaZona.add(cursor.getString(0));
            }while(cursor.moveToNext());


        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A ZONA", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        mZona = new ArrayList<>();
        checkedZona = new boolean[listaZona.toArray().length];

        try {
            cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_CAMPO_TIPO+" ORDER BY "+Utilidades.CAMPO_CT_TIPO+" ASC",null);
            cursor.moveToFirst();
            do{
                listaTipo.add(getString(R.string.TXT_FOOTBALL)+":  "+cursor.getString(0));
                listaTipoAux.add(cursor.getString(0));
            }while(cursor.moveToNext());


        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A TIPO", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        mTipo = new ArrayList<>();
        checkedTipo = new boolean[listaTipo.toArray().length];


        try {
            cursor = db.rawQuery("SELECT "+Utilidades.CAMPO_CH_HORA+" FROM "+Utilidades.TABLA_CAMPO_HORA+" GROUP BY "+Utilidades.CAMPO_CH_HORA,null);
            cursor.moveToFirst();
            do{
                listaHora.add(cursor.getString(0));
            }while(cursor.moveToNext());

        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A HORA", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        mHora = new ArrayList<>();
        checkedHora = new boolean[listaHora.toArray().length];

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for(int i=0;i<7;i++){
            aux=calendar.getTimeInMillis();
            timestamp.add(aux);
            listaFecha.add(dateFormat.format(calendar.getTimeInMillis()+(1000*60*60*24*i)));
        }
        checkedFecha = new boolean[listaFecha.toArray().length];
        mFecha = new ArrayList<>();



    }

    private void cargarBusqueda() {
        final ProgressDialog nDialog;
        nDialog = new ProgressDialog(getContext());
        nDialog.setMessage(getString(R.string.PB_TIT));
        Drawable draw=getActivity().getDrawable(R.drawable.custom_progressbar);
        nDialog.setProgressDrawable(draw);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_BUSQUEDA);
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_BUSQUEDA);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_BUSQUEDA);
        db = conSQL.getWritableDatabase();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject busqueda = response.getJSONObject(i);


                                values.put(Utilidades.CAMPO_LB_PREDIO_PK,busqueda.getInt(getString(R.string.URL_JSON_BQ_PREDIO_PK)));
                                values.put(Utilidades.CAMPO_LB_PREDIO,busqueda.getString(getString(R.string.URL_JSON_BQ_PREDIO)));
                                values.put(Utilidades.CAMPO_LB_DIRECCION,busqueda.getString(getString(R.string.URL_JSON_BQ_DIRECCION)));
                                values.put(Utilidades.CAMPO_LB_ZONA,busqueda.getString(getString(R.string.URL_JSON_BQ_ZONA)));
                                values.put(Utilidades.CAMPO_LB_TELEFONO,busqueda.getString(getString(R.string.URL_JSON_BQ_TELEFONO)));
                                values.put(Utilidades.CAMPO_LB_PRECIO,busqueda.getString(getString(R.string.URL_JSON_BQ_PRECIO)));
                                values.put(Utilidades.CAMPO_LB_VALORACION,busqueda.getInt(getString(R.string.URL_JSON_BQ_VAL)));
                                values.put(Utilidades.CAMPO_LB_N_VALORACION,busqueda.getInt(getString(R.string.URL_JSON_BQ_CANTIDAD)));
                                values.put(Utilidades.CAMPO_LB_EXTRA,busqueda.getInt(getString(R.string.URL_JSON_BQ_EXTRA)));
                                values.put(Utilidades.CAMPO_LB_GPS,busqueda.getString(getString(R.string.URL_JSON_BQ_GPS)));
                                values.put(Utilidades.CAMPO_LB_URL,busqueda.getString(getString(R.string.URL_JSON_BQ_URL)));

                                db.insert(Utilidades.TABLA_LISTA_BUSQUEDA,null,values);

                                values.clear();


                            }

                            nDialog.dismiss();
                            Intent intent = new Intent(getActivity(), ReservationActivity.class);
                            intent.putExtra(getString(R.string.INTENT_USER_PK),getActivity().getIntent().getIntExtra(getString(R.string.INTENT_USER_PK),0));
                            startActivity(intent);
                        }catch (JSONException e){
                            nDialog.dismiss();
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                        nDialog.dismiss();
                    }
                });
        queue.add(jsonArrayRequest);
    }
}
