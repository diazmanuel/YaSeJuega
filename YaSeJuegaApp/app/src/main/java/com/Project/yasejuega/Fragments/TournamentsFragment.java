package com.Project.yasejuega.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.yasejuega.Classes.TournamentsClass;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TournamentsFragment extends Fragment {


    ListView lvTournaments;
    TournamentsClass tournament;
    ArrayList<TournamentsClass> listTournament;
    TournamentsFragment.CustomAdapter adapter;
    ConexionSQLiteHelper conSQL;
    SQLiteDatabase db;
    Cursor cursor;



    public TournamentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_tournaments, container, false);
        lvTournaments= v.findViewById(R.id.lvTournaments);

        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);
        listTournament = new ArrayList<>();


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = conSQL.getReadableDatabase();
        try {
            cargarClase();
            cursor.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarClase() {
        listTournament.clear();
        cursor = db.rawQuery("SELECT * FROM "
                        +Utilidades.TABLA_LISTA_TORNEOS
                        , null);
        if (cursor.getCount() == 0) {

        } else {
            cursor.moveToFirst();
            do {
                tournament = new TournamentsClass();
                tournament.setTorneo_pk(cursor.getInt(0));
                tournament.setPredio(cursor.getString(1));
                tournament.setEquipo(cursor.getString(2));
                tournament.setZona(cursor.getString(3));
                tournament.setDireccion(cursor.getString(4));
                tournament.setTelefono(cursor.getString(5));
                tournament.setFecha_inicio(cursor.getString(6));
                tournament.setPrecio(cursor.getString(7));
                tournament.setPrecioXpartido(cursor.getString(8));
                listTournament.add(tournament);
            } while (cursor.moveToNext());
            mostrarLista();
        }

    }

    private void mostrarLista() {
        adapter = new TournamentsFragment.CustomAdapter();
        lvTournaments.setAdapter(adapter);
        lvTournaments.setClickable(true);
        lvTournaments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom2);

                mBuilder.setTitle(getString(R.string.LABEL_T_TORNEO));
                mBuilder.setMessage(
                                getString(R.string.LABEL_T_PREDIO)+":  "+listTournament.get(position).getPredio()+"\n"+
                                getString(R.string.LABEL_T_ZONA)+":  "+listTournament.get(position).getZona()+"\n"+
                                getString(R.string.LABEL_T_DIRECCION)+":  "+listTournament.get(position).getDireccion()+"\n"+
                                getString(R.string.LABEL_T_TELEFONO)+":  "+listTournament.get(position).getTelefono()+"\n"+
                                getString(R.string.LABEL_T_FECHA)+":  "+listTournament.get(position).getFecha_inicio()+"\n"+
                                getString(R.string.LABEL_T_EQUIPO)+":  "+listTournament.get(position).getEquipo()+"\n"+
                                getString(R.string.LABEL_T_PRECIO)+":  $"+listTournament.get(position).getPrecio()+"\n"+
                                getString(R.string.LABEL_T_PARTIDO)+":  $"+listTournament.get(position).getPrecioXpartido()+"\n");
                mBuilder.show();
            }
        });
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listTournament.size();
        }

        @Override
        public Object getItem(int position) {
            return listTournament.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            float aux;

            view = getLayoutInflater().inflate(R.layout.listview_tournaments,null);

            TextView txtZona = view.findViewById(R.id.txtZona);
            TextView txtPredio = view.findViewById(R.id.txtPredio);
            TextView txtEquipo = view.findViewById(R.id.txtEquipo);
            ImageView imageView = view.findViewById(R.id.imgTour);

            txtEquipo.setText(getString(R.string.TXT_FOOTBALL)+": "+listTournament.get(position).getEquipo());
            txtPredio.setText(listTournament.get(position).getPredio());
            txtZona.setText(listTournament.get(position).getZona());
            imageView.setImageResource(R.drawable.worldcup_green);

            aux=((float)12/listTournament.get(position).getPredio().length());
            if(aux<1) {
                txtPredio.setTextScaleX(aux);
            }
            aux=((float)25/listTournament.get(position).getZona().length());
            if(aux<1) {
                txtZona.setTextScaleX(aux);
            }
            aux=((float)7/listTournament.get(position).getEquipo().length());
            if(aux<1) {
                txtEquipo.setTextScaleX(aux);
            }

            return view;
        }




    }





}
