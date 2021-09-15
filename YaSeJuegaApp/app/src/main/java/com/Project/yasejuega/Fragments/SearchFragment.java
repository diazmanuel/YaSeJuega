package com.Project.yasejuega.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Project.yasejuega.Adapters.RecyclerViewAdapter;
import com.Project.yasejuega.Classes.SearchClass;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    ConexionSQLiteHelper conSQL;
    SQLiteDatabase db;
    Cursor cursor;
    Integer user;


    private RecyclerView recyclerViewSearch;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SearchClass searchItem;
    private ArrayList<SearchClass> searchList = new ArrayList<>();
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search, container, false);
        recyclerViewSearch = v.findViewById(R.id.rvSearch);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        searchList = new ArrayList<>();
        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);
        db = conSQL.getReadableDatabase();
        try {
            cargarClase();
            cursor.close();
            db.close();
        }catch (Exception e){
            Toast.makeText(getActivity(), "ERROR ACCEDIENDO A LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }

        recyclerViewAdapter = new RecyclerViewAdapter(searchList,getContext(),getActivity().getSupportFragmentManager());
        recyclerViewSearch.setAdapter(recyclerViewAdapter);
        recyclerViewSearch.setRecyclerListener(recyclerViewAdapter.getRecycleListener());


        return v;
    }




    private void cargarClase() {
        cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_LISTA_BUSQUEDA, null);
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "ESTA VACIO", Toast.LENGTH_SHORT).show();

        } else {
            cursor.moveToFirst();
            do {


                searchItem = new SearchClass();

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

                searchList.add(searchItem);


            } while (cursor.moveToNext());
        }

    }

}
