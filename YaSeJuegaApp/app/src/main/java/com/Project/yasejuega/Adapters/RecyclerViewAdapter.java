package com.Project.yasejuega.Adapters;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Project.yasejuega.Activitys.MapsActivity;
import com.Project.yasejuega.Classes.SearchClass;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.Fragments.PredioFragment;
import com.Project.yasejuega.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;



public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PredioHolder>  {

    private ArrayList<SearchClass> searchList;
    private Context context;
    private FragmentManager fragmentManager;
    private Bundle args;
    private PredioFragment predioFragment;
    private ConexionSQLiteHelper conSQL;
    private ContentValues values;
    private SQLiteDatabase db;
    private RequestQueue queue;
    private String url;
    private ProgressDialog nDialog;


    public RecyclerViewAdapter(ArrayList<SearchClass> searchList, Context context, FragmentManager fragmentManager){
        this.searchList = searchList;
        this.context=context;
        this.fragmentManager=fragmentManager;
    }

    @NonNull
    @Override
    public PredioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_search, parent, false);
        return new PredioHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PredioHolder viewHolder, final int position) {

        viewHolder.setPredio(searchList.get(position).getPredio());
        viewHolder.setDireccion(searchList.get(position).getDireccion());
        viewHolder.setPrecio(searchList.get(position).getPrecio());
        viewHolder.setValoration(searchList.get(position).getValoracion(),searchList.get(position).getnVal());
        viewHolder.setExtra(searchList.get(position).getExtra());
        viewHolder.setTelefono(searchList.get(position).getTelefono());
        viewHolder.setImagen(searchList.get(position).getUrl());

        viewHolder.bindView(position);

        viewHolder.getBtnReservarAccion().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

                nDialog = new ProgressDialog(context);
                nDialog.setMessage(context.getString(R.string.PB_TIT));
                Drawable draw=context.getDrawable(R.drawable.custom_progressbar);
                nDialog.setIndeterminateDrawable(draw);
                nDialog.setIndeterminate(false);
                nDialog.setCancelable(false);
                nDialog.show();
                disponibilidad(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    private void disponibilidad(final Integer position) {

        queue = Volley.newRequestQueue(context);
        conSQL= new ConexionSQLiteHelper(context, Utilidades.BD_YASEJUEGA,null,1);
        db=conSQL.getWritableDatabase();
        values= new ContentValues();
        url=context.getString(R.string.URL_BASE);
        url+=context.getString(R.string.URL_REQ_DISPONIBILIDAD);
        url+="?"+context.getString(R.string.URL_VAR_PREDIO)+searchList.get(position).getPredio_pk().toString();
        db.execSQL(Utilidades.ACTUALIZAR_TABLA_LISTA_DISPONIBILIDAD);
        db.execSQL(Utilidades.CREAR_TABLA_LISTA_DISPONIBILIDAD);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                                JSONObject predio = response.getJSONObject(i);

                                values.put(Utilidades.CAMPO_LD_CANCHA_PK,predio.getString(context.getString(R.string.URL_JSON_D_CANCHA_PK)));
                                values.put(Utilidades.CAMPO_LD_CANCHA,predio.getString(context.getString(R.string.URL_JSON_D_CANCHA)));
                                values.put(Utilidades.CAMPO_LD_HORA,predio.getString(context.getString(R.string.URL_JSON_D_HORA)));
                                values.put(Utilidades.CAMPO_LD_FECHA,predio.getString(context.getString(R.string.URL_JSON_D_FECHA)));

                                db.insert(Utilidades.TABLA_LISTA_DISPONIBILIDAD,null,values);
                                values.clear();
                            }
                            nDialog.dismiss();
                            Bundle args = new Bundle();
                            args.putString(context.getString(R.string.BUNDLE_PREDIO_PK), searchList.get(position).getPredio_pk().toString());
                            predioFragment = new PredioFragment();
                            predioFragment.setArguments(args);
                            fragmentManager.beginTransaction().replace(R.id.fg, predioFragment).addToBackStack(null).commit();

                        }catch (JSONException e){
                            Toast.makeText(context, "ERROR PROCESANDO DATOS", Toast.LENGTH_SHORT).show();
                            nDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR ACCEDIENDO AL SERVIDOR", Toast.LENGTH_SHORT).show();
                        nDialog.dismiss();
                    }
                });
        queue.add(jsonArrayRequest);
    }




    public class PredioHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
        private View view;
        private float aux;
        private MapView mapView;
        private GoogleMap map;
        private Intent intent;

        public PredioHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mapView = view.findViewById(R.id.mapPredio);
            if (mapView != null) {
                // Initialise the MapView
                mapView.onCreate(null);
                mapView.onResume();
                mapView.setClickable(true);

                // Set the map ready callback to receive the GoogleMap object
                mapView.getMapAsync(this);
            }

        }
        @Override
        public void onMapReady(GoogleMap googleMap) {
            MapsInitializer.initialize(context.getApplicationContext());
            map = googleMap;
            map.getUiSettings().setAllGesturesEnabled(false);
            map.getUiSettings().setMapToolbarEnabled(false);
            setMapLocation();
            map.setOnMapClickListener(PredioHolder.this);
            map.setOnMarkerClickListener(PredioHolder.this);

        }

        private void setMapLocation() {
            if (map == null) return;

            SearchClass data = (SearchClass) mapView.getTag();
            if (data == null) return;

            // Add a marker for this item and set the camera
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.getLatLng(), 16));
            map.addMarker(new MarkerOptions().position(data.getLatLng()));
            // Set the map type back to normal.
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }

        private void bindView(int pos) {
            // Store a reference of the ViewHolder object in the layout.
            view.setTag(this);
            // Store a reference to the item in the mapView's tag. We use it to get the
            // coordinate of a location, when setting the map location.
            mapView.setTag(searchList.get(pos));
            setMapLocation();
        }
        public Button getBtnReservarAccion()
        {
            return view.findViewById(R.id.btnReservar);
        }

        public ImageView getImgPredioAccion(){return view.findViewById(R.id.imgPredio);}

        public void setPredio(String pre)
        {
            TextView txtPredio = view.findViewById(R.id.txtPredio);
            aux=((float)20/pre.length());
            if(aux<1) {
                txtPredio.setTextScaleX(aux);
            }
            txtPredio.setText(pre);
        }

        public void setDireccion(String dir)
        {
            TextView txtDir = view.findViewById(R.id.txtDir);
            aux=((float)16/dir.length());
            if(aux<1) {
                txtDir.setTextScaleX(aux);
            }
            txtDir.setText(dir);
        }
        public void setTelefono(String tel)
        {
            TextView txtTel = view.findViewById(R.id.txtTel);
            aux=((float)16/tel.length());
            if(aux<1) {
                txtTel.setTextScaleX(aux);
            }
            txtTel.setText(tel);
        }
        public void setPrecio(String pre)
        {
            TextView txtPre = view.findViewById(R.id.txtPrecio);
            aux=((float)16/pre.length());
            if(aux<1) {
                txtPre.setTextScaleX(aux);
            }
            txtPre.setText(pre);
        }

        public void setImagen(String url) {
            ImageView imgPredio = view.findViewById(R.id.imgPredio);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);
            Glide.with(context).load(url).apply(options).into(imgPredio);
        }


        public void setExtra(Boolean[] bExtra){
            ArrayList<String> extra = new ArrayList<>();
            ImageView imgItem1 = view.findViewById(R.id.imgItem1);
            ImageView imgItem2 = view.findViewById(R.id.imgItem2);
            ImageView imgItem3 = view.findViewById(R.id.imgItem3);
            ImageView imgItem4 = view.findViewById(R.id.imgItem4);
            ImageView imgItem5 = view.findViewById(R.id.imgItem5);
            TextView txtItem1 = view.findViewById(R.id.txtItem1);
            TextView txtItem2 = view.findViewById(R.id.txtItem2);
            TextView txtItem3 = view.findViewById(R.id.txtItem3);
            TextView txtItem4 = view.findViewById(R.id.txtItem4);
            TextView txtItem5 = view.findViewById(R.id.txtItem5);

            if(bExtra[0]) extra.add(context.getString(R.string.TXT_PARRILLA));
            if(bExtra[1]) extra.add(context.getString(R.string.TXT_VESTUARIOS));
            if(bExtra[2]) extra.add(context.getString(R.string.TXT_DUCHAS));
            if(bExtra[3]) extra.add(context.getString(R.string.TXT_BUFFET));
            if(bExtra[4]) extra.add(context.getString(R.string.TXT_ESTACIONAMIENTO));

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
            ImageView imgStar = view.findViewById(R.id.imgStar);
            ImageView imgStar1 = view.findViewById(R.id.imgStar1);
            ImageView imgStar2 = view.findViewById(R.id.imgStar2);
            ImageView imgStar3 = view.findViewById(R.id.imgStar3);
            ImageView imgStar4 = view.findViewById(R.id.imgStar4);
            ImageView imgStar5 = view.findViewById(R.id.imgStar5);
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
        public void onMapClick(LatLng latLng) {
            SearchClass data = (SearchClass) mapView.getTag();

            if (data == null) return;

            intent = new Intent(context, MapsActivity.class);
            intent.putExtra(context.getString(R.string.INTENT_USER_PK_MAP),data.getPredio_pk());
            context.startActivity(intent);

        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            SearchClass data = (SearchClass) mapView.getTag();

            if (data == null) return true;

            intent = new Intent(context, MapsActivity.class);
            intent.putExtra(context.getString(R.string.INTENT_USER_PK_MAP),data.getPredio_pk());
            context.startActivity(intent);
            return false;
        }


    }
    private RecyclerView.RecyclerListener mRecycleListener = new RecyclerView.RecyclerListener() {

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            RecyclerViewAdapter.PredioHolder mapHolder = (RecyclerViewAdapter.PredioHolder) holder;
            if (mapHolder != null && mapHolder.map != null) {
                // Clear the map and free up resources by changing the map type to none.
                // Also reset the map when it gets reattached to layout, so the previous map would
                // not be displayed.
                mapHolder.map.clear();
                mapHolder.map.setMapType(GoogleMap.MAP_TYPE_NONE);
            }
        }
    };

    public RecyclerView.RecyclerListener getRecycleListener() {
        return mRecycleListener;
    }


}
