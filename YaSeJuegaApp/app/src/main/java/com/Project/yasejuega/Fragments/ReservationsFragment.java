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

import com.Project.yasejuega.Classes.ReservationsClass;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationsFragment extends Fragment {

    ListView lvReservations;
    ArrayList<String> listaPredio,listaEstado,listaFecha,listaHora;
    ArrayList<ReservationsClass> listReservations;
    ReservationsFragment.CustomAdapter adapter;
    ConexionSQLiteHelper conSQL;
    SQLiteDatabase db;
    Cursor cursor;
    ReservationsClass reservation;


    public ReservationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservations, container, false);

        lvReservations = v.findViewById(R.id.lvReservations);
        listReservations = new ArrayList<>();
        listaPredio = new ArrayList<>();
        listaEstado = new ArrayList<>();
        listaFecha = new ArrayList<>();
        listaHora = new ArrayList<>();
        conSQL = new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA, null, 1);


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
        } catch (Exception e) {
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A RESERVAS", Toast.LENGTH_SHORT).show();
        }

    }

    private void cargarClase() {

        listReservations.clear();
        cursor = db.rawQuery("SELECT * FROM "+Utilidades.TABLA_LISTA_RESERVA, null);
        if (cursor.getCount() == 0) {

        } else {
            cursor.moveToFirst();
            do {
                reservation = new ReservationsClass();
                reservation.setCancha(cursor.getString(0));
                reservation.setDireccion(cursor.getString(1));
                reservation.setEstado(cursor.getString(2));
                reservation.setFecha(cursor.getString(3));
                reservation.setHora(cursor.getString(4));
                reservation.setPrecio(cursor.getString(5));
                reservation.setPredio(cursor.getString(6));
                reservation.setReserva_pk(cursor.getInt(7));
                reservation.setSena(cursor.getString(8));
                reservation.setSuperficie(cursor.getString(9));
                reservation.setTechado(cursor.getString(10));
                reservation.setTelefono(cursor.getString(11));
                reservation.setTimestamp(cursor.getString(12));
                reservation.setTipo(cursor.getString(13));
                reservation.setZona(cursor.getString(14));
                reservation.setnReserva(cursor.getString(15));
                listReservations.add(reservation);
            } while (cursor.moveToNext());
            mostrarLista();
        }
    }

    private void mostrarLista() {
        adapter = new ReservationsFragment.CustomAdapter();
        lvReservations.setAdapter(adapter);
        lvReservations.setClickable(true);

        lvReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom2);
                String aux;
                if (Integer.parseInt(listReservations.get(position).getTechado()) == 1){
                    aux ="SI";
                }else{
                    aux = "NO";
                }
                mBuilder.setTitle(getString(R.string.LABEL_R_RESERVA));
                mBuilder.setMessage(
                                getString(R.string.LABEL_R_PREDIO) + ":  " + listReservations.get(position).getPredio() + "\n" +
                                getString(R.string.LABEL_R_CANCHA) + ":  " + listReservations.get(position).getCancha() + "\n" +
                                getString(R.string.LABEL_R_DIRECCION) + ":  " + listReservations.get(position).getDireccion() + "\n" +
                                getString(R.string.LABEL_R_FECHA) + ":  " + listReservations.get(position).getFecha() + "\n" +
                                getString(R.string.LABEL_R_HORA) + ":  " + listReservations.get(position).getHora() + "\n" +
                                getString(R.string.LABEL_R_TELEFONO) + ":  " + listReservations.get(position).getTelefono() + "\n" +
                                getString(R.string.LABEL_R_PRECIO) + ":  $" + listReservations.get(position).getPrecio() + "\n" +
                                getString(R.string.LABEL_R_SEÑA) + ":  $" + listReservations.get(position).getSena() + "\n" +
                                getString(R.string.LABEL_R_TECHADO) + ":  " + aux + "\n" +
                                getString(R.string.LABEL_R_ESTADO) + ":  " + listReservations.get(position).getEstado() + "\n" +
                                getString(R.string.LABEL_R_TIMESTAMP) + ":  " + listReservations.get(position).getTimestamp() + "\n" +
                                getString(R.string.LABEL_R_ORDEN) + ":  N° " + listReservations.get(position).getnReserva() + "\n"
                );
                mBuilder.show();

            }
        });
    }



    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listReservations.size();
        }

        @Override
        public Object getItem(int position) {
            return listReservations.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            float aux;
            view = getLayoutInflater().inflate(R.layout.listview_reservations, null);

            TextView txtPredio = view.findViewById(R.id.txtPredio);
            TextView txtEstado = view.findViewById(R.id.txtEstado);
            TextView txtFecha = view.findViewById(R.id.txtFecha);
            TextView txtHora = view.findViewById(R.id.txtHora);

            ImageView imageView = view.findViewById(R.id.imgRes);

            txtPredio.setText(listReservations.get(position).getPredio());
            txtEstado.setText(listReservations.get(position).getEstado());
            txtFecha.setText(listReservations.get(position).getFecha());
            txtHora.setText(listReservations.get(position).getHora());
            imageView.setImageResource(R.drawable.court_green);


            aux=((float)12/listReservations.get(position).getFecha().length());
            if(aux<1) {
                txtFecha.setTextScaleX(aux);
            }
            aux=((float)12/listReservations.get(position).getPredio().length());
            if(aux<1) {
                txtPredio.setTextScaleX(aux);
            }
            aux=((float)7/listReservations.get(position).getEstado().length());
            if(aux<1) {
                txtEstado.setTextScaleX(aux);
            }
            aux=((float)7/listReservations.get(position).getHora().length());
            if(aux<1) {
                txtHora.setTextScaleX(aux);
            }

            return view;
        }


    }
}