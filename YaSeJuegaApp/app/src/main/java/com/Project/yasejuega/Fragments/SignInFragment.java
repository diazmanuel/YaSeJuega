package com.Project.yasejuega.Fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.yasejuega.Activitys.LoginActivity;
import com.Project.yasejuega.Databases.ConexionSQLiteHelper;
import com.Project.yasejuega.Databases.Utilidades;
import com.Project.yasejuega.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements View.OnClickListener{


    Button btnRegistrate;
    Button btnBack;
    TextView emNombre;
    TextView emApellido;
    TextView emEmail;
    TextView emUsuario;
    TextView emContra;
    TextView emConfContra;
    TextView emCelular;
    ConexionSQLiteHelper conSQL;
    ContentValues values;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);


        conSQL= new ConexionSQLiteHelper(getContext(), Utilidades.BD_YASEJUEGA,null,1);
        values= new ContentValues();

        emNombre = v.findViewById(R.id.emNombre);
        emApellido = v.findViewById(R.id.emApellido);
        emEmail = v.findViewById(R.id.emEmail);
        emUsuario = v.findViewById(R.id.emUsuario);
        emContra = v.findViewById(R.id.emContra);
        emConfContra = v.findViewById(R.id.emConfContra);
        emCelular = v.findViewById(R.id.emCelular);

        btnBack = v.findViewById(R.id.btnBack);
        btnRegistrate = v.findViewById(R.id.btnRegistrate);

        btnBack.setOnClickListener(this);
        btnRegistrate.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
            switch(view.getId()){
                case R.id.btnBack:
                    getActivity().finish();
                    break;
                case R.id.btnRegistrate:
                   // addUser();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
        }
    }
/*
    private void addUser() {

        SQLiteDatabase db=conSQL.getWritableDatabase();
        values.put(Utilidades.CAMPO_NOMBRE,emNombre.getText().toString());
        values.put(Utilidades.CAMPO_APELLIDO,emApellido.getText().toString());
        values.put(Utilidades.CAMPO_EMAIL,emEmail.getText().toString());
        values.put(Utilidades.CAMPO_USUARIO,emUsuario.getText().toString());
        values.put(Utilidades.CAMPO_CONTRA,emContra.getText().toString());
        values.put(Utilidades.CAMPO_CELULAR,emCelular.getText().toString());
        db.insert(Utilidades.TABLA_USUARIOS,null,values);
        db.close();

    }

 */
}
