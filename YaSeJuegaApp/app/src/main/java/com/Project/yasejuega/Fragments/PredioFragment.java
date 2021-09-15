package com.Project.yasejuega.Fragments;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.yasejuega.Activitys.MainActivity;
import com.Project.yasejuega.Activitys.MapsActivity;
import com.Project.yasejuega.Adapters.RecyclerViewAdapter;
import com.Project.yasejuega.Classes.SearchClass;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PredioFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private TextView btnFecha,btnHora,btnCancha,btnReservar,txtItem1,txtItem2,txtItem3,txtItem4,txtItem5,txtPredio,txtDir,txtTel,txtPre  ;

    private ImageView imgPredio,imgStar,imgStar1,imgStar2,imgStar3,imgStar4,imgStar5,imgItem1,imgItem2,imgItem3,imgItem4,imgItem5;
    private ArrayList<String> listaFecha,listaHora,listaHoraAux,listaCancha,listaCancha_pk;
    private MapView mapView;
    private GoogleMap map;
    private String[] resultado;
    private SearchClass searchItem;
    private ConexionSQLiteHelper conSQL;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ContentValues values;
    private RequestQueue queue;
    private String url,predio_pk;
    private String[] aux;
    private float aux2;
    public PredioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_predio, container, false);
        // Inflate the layout for this fragment
        btnFecha = v.findViewById(R.id.btnFecha);
        btnHora = v.findViewById(R.id.btnHora);
        btnCancha= v.findViewById(R.id.btnCancha);
        btnReservar = v.findViewById(R.id.btnReservar);
        imgPredio = v.findViewById(R.id.imgPredio);

        imgStar = v.findViewById(R.id.imgStar);
        imgStar1 = v.findViewById(R.id.imgStar1);
        imgStar2 = v.findViewById(R.id.imgStar2);
        imgStar3 = v.findViewById(R.id.imgStar3);
        imgStar4 = v.findViewById(R.id.imgStar4);
        imgStar5 = v.findViewById(R.id.imgStar5);

        imgItem1 = v.findViewById(R.id.imgItem1);
        imgItem2 = v.findViewById(R.id.imgItem2);
        imgItem3 = v.findViewById(R.id.imgItem3);
        imgItem4 = v.findViewById(R.id.imgItem4);
        imgItem5 = v.findViewById(R.id.imgItem5);
        txtItem1 = v.findViewById(R.id.txtItem1);
        txtItem2 = v.findViewById(R.id.txtItem2);
        txtItem3 = v.findViewById(R.id.txtItem3);
        txtItem4 = v.findViewById(R.id.txtItem4);
        txtItem5 = v.findViewById(R.id.txtItem5);


        txtPredio = v.findViewById(R.id.txtPredio);
        txtDir = v.findViewById(R.id.txtDir);
        txtTel = v.findViewById(R.id.txtTel);
        txtPre = v.findViewById(R.id.txtPrecio);

        mapView = v.findViewById(R.id.mapPredio);

        btnFecha.setEnabled(true);
        btnHora.setEnabled(false);
        btnCancha.setEnabled(false);
        btnReservar.setEnabled(false);
        btnFecha.setBackgroundResource(R.drawable.custom_spinner_on);
        btnHora.setBackgroundResource(R.drawable.custom_spinner_off);
        btnCancha.setBackgroundResource(R.drawable.custom_spinner_off);
        btnReservar.setBackgroundResource(R.drawable.button_round5);
        btnFecha.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
        btnHora.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
        btnCancha.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
        btnReservar.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));

        aux= getResources().getStringArray(R.array.HORARIOS);


        resultado = new String[3];
        listaCancha_pk=new ArrayList<>();
        listaFecha = new ArrayList<>();
        listaHora = new ArrayList<>();
        listaHoraAux = new ArrayList<>();
        listaCancha = new ArrayList<>();
        searchItem = new SearchClass();

        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);
        values= new ContentValues();

        queue = Volley.newRequestQueue(getActivity());


        predio_pk=getArguments().getString(getString(R.string.BUNDLE_PREDIO_PK));

        cargarPredio(predio_pk);

        cargarFechas();

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);
                mBuilder.setTitle(R.string.LABEL_SELECCION);
                mBuilder.setCancelable(true);
                mBuilder.setSingleChoiceItems(listaFecha.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[0]=listaFecha.get(i);
                        btnFecha.setTextSize(18);
                        btnFecha.setText(listaFecha.get(i));
                        cargarHora(resultado[0]);
                        btnHora.setEnabled(true);
                        btnHora.setBackgroundResource(R.drawable.custom_spinner_on);
                        btnHora.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton(getString(R.string.LABEL_CLEAR), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[0]="";
                        resultado[1]="";
                        resultado[2]="";
                        btnFecha.setEnabled(true);
                        btnHora.setEnabled(false);
                        btnCancha.setEnabled(false);
                        btnReservar.setEnabled(false);
                        btnFecha.setBackgroundResource(R.drawable.custom_spinner_on);
                        btnHora.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnCancha.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnReservar.setBackgroundResource(R.drawable.button_round5);
                        btnFecha.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
                        btnHora.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnReservar.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextSize(14);
                        btnCancha.setText(getString(R.string.LABEL_CANCHA));
                        btnHora.setTextSize(14);
                        btnHora.setText(getString(R.string.LABEL_HORARIO));
                        btnFecha.setTextSize(14);
                        btnFecha.setText(getString(R.string.LABEL_FECHA));
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);
                mBuilder.setTitle(R.string.LABEL_SELECCION);
                mBuilder.setCancelable(true);
                mBuilder.setSingleChoiceItems(listaHoraAux.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[1]=listaHora.get(i);
                        btnHora.setTextSize(18);
                        btnHora.setText(listaHoraAux.get(i));
                        cargarCancha(resultado[0],resultado[1]);
                        btnFecha.setEnabled(false);
                        btnCancha.setEnabled(true);
                        btnFecha.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnCancha.setBackgroundResource(R.drawable.custom_spinner_on);
                        btnFecha.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton(getString(R.string.LABEL_CLEAR), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[0]="";
                        resultado[1]="";
                        resultado[2]="";
                        btnFecha.setEnabled(true);
                        btnHora.setEnabled(false);
                        btnCancha.setEnabled(false);
                        btnReservar.setEnabled(false);
                        btnFecha.setBackgroundResource(R.drawable.custom_spinner_on);
                        btnHora.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnCancha.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnReservar.setBackgroundResource(R.drawable.button_round5);
                        btnFecha.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
                        btnHora.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnReservar.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextSize(14);
                        btnCancha.setText(getString(R.string.LABEL_CANCHA));
                        btnHora.setTextSize(14);
                        btnHora.setText(getString(R.string.LABEL_HORARIO));
                        btnFecha.setTextSize(14);
                        btnFecha.setText(getString(R.string.LABEL_FECHA));
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        btnCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);
                mBuilder.setTitle(R.string.LABEL_SELECCION);
                mBuilder.setCancelable(true);
                mBuilder.setSingleChoiceItems(listaCancha.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[2]=listaCancha_pk.get(i);
                        btnCancha.setTextSize(18);
                        btnCancha.setText(listaCancha.get(i));
                        btnHora.setEnabled(false);
                        btnReservar.setEnabled(true);
                        btnHora.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnReservar.setBackgroundResource(R.drawable.button_round2);
                        btnHora.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnReservar.setTextColor(getResources().getColor(R.color.color0,getActivity().getTheme()));
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setNegativeButton(getString(R.string.LABEL_CLEAR), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resultado[0]="";
                        resultado[1]="";
                        resultado[2]="";
                        btnFecha.setEnabled(true);
                        btnHora.setEnabled(false);
                        btnCancha.setEnabled(false);
                        btnReservar.setEnabled(false);
                        btnFecha.setBackgroundResource(R.drawable.custom_spinner_on);
                        btnHora.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnCancha.setBackgroundResource(R.drawable.custom_spinner_off);
                        btnReservar.setBackgroundResource(R.drawable.button_round5);
                        btnFecha.setTextColor(getResources().getColor(R.color.color2,getActivity().getTheme()));
                        btnHora.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnReservar.setTextColor(getResources().getColor(R.color.color5,getActivity().getTheme()));
                        btnCancha.setTextSize(14);
                        btnCancha.setText(getString(R.string.LABEL_CANCHA));
                        btnHora.setTextSize(14);
                        btnHora.setText(getString(R.string.LABEL_HORARIO));
                        btnFecha.setTextSize(14);
                        btnFecha.setText(getString(R.string.LABEL_FECHA));
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);
                mBuilder.setTitle(R.string.TIT_CONFIRM);
                mBuilder.setMessage(R.string.MSG_CONFIRM);
                mBuilder.setCancelable(false);

                mBuilder.setPositiveButton(getString(R.string.LABEL_CONFIRM), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reserva(resultado);

                    }
                });
                mBuilder.setNeutralButton(getString(R.string.LABEL_CANCELAR), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });
        return v;
    }
    void cargarFechas(){
        db = conSQL.getReadableDatabase();
        try {
            cursor = db.rawQuery("SELECT "+ Utilidades.CAMPO_LD_FECHA+" FROM "+Utilidades.TABLA_LISTA_DISPONIBILIDAD+" GROUP BY "+Utilidades.CAMPO_LD_FECHA+" ORDER BY "+Utilidades.CAMPO_LD_FECHA+" ASC",null);
            cursor.moveToFirst();
            do{
                listaFecha.add(cursor.getString(0));
            }while(cursor.moveToNext());

        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A DISPONIBILIDAD", Toast.LENGTH_SHORT).show();
        }
        db.close();
        cursor.close();
    }
    void cargarHora(String fecha){
        db = conSQL.getReadableDatabase();
        try {
            cursor = db.rawQuery("SELECT "+ Utilidades.CAMPO_LD_HORA+" FROM "+Utilidades.TABLA_LISTA_DISPONIBILIDAD+" WHERE "+Utilidades.CAMPO_LD_FECHA+" =? "+" GROUP BY "+Utilidades.CAMPO_LD_HORA+" ORDER BY "+Utilidades.CAMPO_LD_HORA+" ASC",new String[] {fecha});
            cursor.moveToFirst();
            do{
                listaHora.add(cursor.getString(0));
                listaHoraAux.add(aux[Integer.parseInt(cursor.getString(0))]);
            }while(cursor.moveToNext());

        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A DISPONIBILIDAD", Toast.LENGTH_SHORT).show();
        }
        db.close();
        cursor.close();
    }
    void cargarCancha(String fecha,String hora){
        db = conSQL.getReadableDatabase();
        try {
            cursor = db.rawQuery("SELECT "+ Utilidades.CAMPO_LD_CANCHA+","+Utilidades.CAMPO_LD_CANCHA_PK+" FROM "+Utilidades.TABLA_LISTA_DISPONIBILIDAD+" WHERE "+Utilidades.CAMPO_LD_FECHA+"=?"+" AND "+Utilidades.CAMPO_LD_HORA+" =?"+" ORDER BY "+Utilidades.CAMPO_LD_CANCHA+" ASC",new  String[] {fecha,hora});
            cursor.moveToFirst();
            do{
                listaCancha.add("Cancha N°"+cursor.getString(0));
                listaCancha_pk.add(cursor.getString(1));
            }while(cursor.moveToNext());

        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A DISPONIBILIDAD", Toast.LENGTH_SHORT).show();
        }
        db.close();
        cursor.close();
    }
    void reserva(String args[]){
        final ProgressDialog nDialog;
        nDialog = new ProgressDialog(getContext());
        nDialog.setMessage(getString(R.string.PB_TIT));
        Drawable draw=getActivity().getDrawable(R.drawable.custom_progressbar);
        nDialog.setIndeterminateDrawable(draw);
        nDialog.setIndeterminate(false);
        nDialog.setCancelable(false);
        nDialog.show();
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_RESERVAR);
        url+="?"+getString(R.string.URL_VAR_CANCHA)+resultado[2];
        url+="&"+getString(R.string.URL_VAR_FECHA)+resultado[0];
        url+="&"+getString(R.string.URL_VAR_HORA)+resultado[1];
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final Integer res= Integer.parseInt(response.substring(0, response.length()));

                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);
                        mBuilder.setTitle(R.string.TIT_RESERVA);
                        mBuilder.setCancelable(false);

                        mBuilder.setNegativeButton(getString(R.string.LABEL_OK), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(res==1)cargarReservas();
                            }
                        });
                        if (res==1) {
                            crearNotificacion();
                            mBuilder.setMessage(R.string.MSG_RESERVADO);
                        }else {
                            mBuilder.setMessage(R.string.MSG_NORESERVADO);
                        }

                        AlertDialog mDialog = mBuilder.create();
                        nDialog.dismiss();
                        mDialog.show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                nDialog.dismiss();
            }
        });
        queue.add(stringRequest);
    }

    private void crearNotificacion() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),getString(R.string.NOTI_CHANNEL))
                .setSmallIcon(R.drawable.court_green)
                .setContentTitle(getString(R.string.NOTI_TITLE))
                .setContentText(getString(R.string.NOTI_MESSAGE))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Integer.parseInt(getString(R.string.NOTI_ID)), builder.build());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.NOTI_CHANNEL);
            String description = getString(R.string.NOTI_CHANNEL_DES);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.NOTI_CHANNEL), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void cargarReservas() {
        url=getString(R.string.URL_BASE);
        url+=getString(R.string.URL_REQ_RESERVAS);
        db = conSQL.getWritableDatabase();
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
                            db = conSQL.getWritableDatabase();
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
                            Toast.makeText(getContext(), "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();

                        }
                        getActivity().finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                });
        queue.add(jsonArrayRequest);
        db.close();
    }
    public static Drawable LoadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "predio");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public void cargarPredio(String predio){
        db = conSQL.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_LISTA_BUSQUEDA+" WHERE "+Utilidades.CAMPO_LB_PREDIO_PK+" =? LIMIT 1", new String[]{predio});
        if (cursor.getCount() == 0) {
        } else {
            cursor.moveToFirst();

            searchItem.setPredio_pk(cursor.getInt(0));
            searchItem.setPredio(cursor.getString(1));
            searchItem.setDireccion(cursor.getString(2),cursor.getString(3));
            searchItem.setTelefono(cursor.getString(4));
            searchItem.setPrecio(cursor.getString(5));
            searchItem.setValoracion(cursor.getInt(6));
            searchItem.setnVal(cursor.getInt(7));
            searchItem.setExtra(cursor.getInt(8));
            searchItem.setLatLng(cursor.getString(9));
            searchItem.setUrl(cursor.getString(10));

        }
        db.close();
        cursor.close();
        setPredio(searchItem.getPredio());
        setDireccion(searchItem.getDireccion());
        setPrecio(searchItem.getPrecio());
        setTelefono(searchItem.getTelefono());
        setValoration(searchItem.getValoracion(),searchItem.getnVal());
        setExtra(searchItem.getExtra());
        setImagen(searchItem.getUrl());
        setMap();
    }

    private void setMap() {

        if (mapView != null) {
            // Initialise the MapView
            mapView.onCreate(null);
            mapView.onResume();
            mapView.setClickable(true);

            // Set the map ready callback to receive the GoogleMap object
            mapView.getMapAsync(this);
        }
    }


    public void setPredio(String pre)
    {

        aux2=((float)20/pre.length());
        if(aux2<1) {
            txtPredio.setTextScaleX(aux2);
        }
        txtPredio.setText(pre);
    }
    public void setImagen(String url)
    {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(getContext()).load(url).apply(options).into(imgPredio);

    }

    public void setDireccion(String dir)
    {

        aux2=((float)16/dir.length());
        if(aux2<1) {
            txtDir.setTextScaleX(aux2);
        }
        txtDir.setText(dir);
    }
    public void setTelefono(String tel)
    {

        aux2=((float)16/tel.length());
        if(aux2<1) {
            txtTel.setTextScaleX(aux2);
        }
        txtTel.setText(tel);
    }
    public void setPrecio(String pre)
    {

        aux2=((float)16/pre.length());
        if(aux2<1) {
            txtPre.setTextScaleX(aux2);
        }
        txtPre.setText(pre);
    }


    public void setExtra(Boolean[] bExtra){
        ArrayList<String> extra = new ArrayList<>();


        if(bExtra[0]) extra.add(getString(R.string.TXT_PARRILLA));
        if(bExtra[1]) extra.add(getString(R.string.TXT_VESTUARIOS));
        if(bExtra[2]) extra.add(getString(R.string.TXT_DUCHAS));
        if(bExtra[3]) extra.add(getString(R.string.TXT_BUFFET));
        if(bExtra[4]) extra.add(getString(R.string.TXT_ESTACIONAMIENTO));

        switch (extra.size()){
            case 0:
                imgItem1.setVisibility(View.GONE);
                txtItem1.setVisibility(View.GONE);
                imgItem2.setVisibility(View.GONE);
                txtItem2.setVisibility(View.GONE);
                imgItem3.setVisibility(View.GONE);
                txtItem3.setVisibility(View.GONE);
                imgItem4.setVisibility(View.GONE);
                txtItem4.setVisibility(View.GONE);
                imgItem5.setVisibility(View.GONE);
                txtItem5.setVisibility(View.GONE);

                break;
            case 1:
                txtItem1.setText(extra.get(0));
                imgItem2.setVisibility(View.GONE);
                txtItem2.setVisibility(View.GONE);
                imgItem3.setVisibility(View.GONE);
                txtItem3.setVisibility(View.GONE);
                imgItem4.setVisibility(View.GONE);
                txtItem4.setVisibility(View.GONE);
                imgItem5.setVisibility(View.GONE);
                txtItem5.setVisibility(View.GONE);
                break;
            case 2:

                txtItem1.setText(extra.get(0));
                txtItem2.setText(extra.get(1));
                imgItem3.setVisibility(View.GONE);
                txtItem3.setVisibility(View.GONE);
                imgItem4.setVisibility(View.GONE);
                txtItem4.setVisibility(View.GONE);
                imgItem5.setVisibility(View.GONE);
                txtItem5.setVisibility(View.GONE);
                break;
            case 3:
                txtItem1.setText(extra.get(0));
                txtItem2.setText(extra.get(1));
                txtItem3.setText(extra.get(2));
                imgItem4.setVisibility(View.GONE);
                txtItem4.setVisibility(View.GONE);
                imgItem5.setVisibility(View.GONE);
                txtItem5.setVisibility(View.GONE);
                break;
            case 4:
                txtItem1.setText(extra.get(0));
                txtItem2.setText(extra.get(1));
                txtItem3.setText(extra.get(2));
                txtItem4.setText(extra.get(3));
                imgItem5.setVisibility(View.GONE);
                txtItem5.setVisibility(View.GONE);
                break;
            default:
                txtItem1.setText(extra.get(0));
                txtItem2.setText(extra.get(1));
                txtItem3.setText(extra.get(2));
                txtItem4.setText(extra.get(3));
                txtItem5.setText(extra.get(4));
                break;
        }



    }
    public void setValoration(Integer valoracion,Integer cantidad){
        if(cantidad>5) {
            switch (valoracion){
                case 0:
                    imgStar1.setImageResource(R.drawable.star3);
                    imgStar2.setImageResource(R.drawable.star3);
                    imgStar3.setImageResource(R.drawable.star3);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 1:
                    imgStar1.setImageResource(R.drawable.star2);
                    imgStar2.setImageResource(R.drawable.star3);
                    imgStar3.setImageResource(R.drawable.star3);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 2:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star3);
                    imgStar3.setImageResource(R.drawable.star3);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 3:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star2);
                    imgStar3.setImageResource(R.drawable.star3);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 4:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star3);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 5:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star2);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 6:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star1);
                    imgStar4.setImageResource(R.drawable.star3);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 7:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star1);
                    imgStar4.setImageResource(R.drawable.star2);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 8:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star1);
                    imgStar4.setImageResource(R.drawable.star1);
                    imgStar5.setImageResource(R.drawable.star3);
                    break;
                case 9:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star1);
                    imgStar4.setImageResource(R.drawable.star1);
                    imgStar5.setImageResource(R.drawable.star2);
                    break;
                case 10:
                    imgStar1.setImageResource(R.drawable.star1);
                    imgStar2.setImageResource(R.drawable.star1);
                    imgStar3.setImageResource(R.drawable.star1);
                    imgStar4.setImageResource(R.drawable.star1);
                    imgStar5.setImageResource(R.drawable.star1);
                    break;
            }
        }else{
            imgStar.setVisibility(View.GONE);
            imgStar1.setVisibility(View.GONE);
            imgStar2.setVisibility(View.GONE);
            imgStar3.setVisibility(View.GONE);
            imgStar4.setVisibility(View.GONE);
            imgStar5.setVisibility(View.GONE);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        map = googleMap;
        map.getUiSettings().setAllGesturesEnabled(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(searchItem.getLatLng(), 16));
        map.addMarker(new MarkerOptions().position(searchItem.getLatLng()));
        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra(getString(R.string.INTENT_USER_PK_MAP),searchItem.getPredio_pk());
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        intent.putExtra(getString(R.string.INTENT_USER_PK_MAP),searchItem.getPredio_pk());
        startActivity(intent);
        return false;
    }
}
