package com.Project.yasejuega.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.Project.yasejuega.Classes.MessageClass;
import com.Project.yasejuega.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements View.OnClickListener {
    TextView emMail,emNombre,emAsunto,emMensaje;
    Button btnEnviar;
    MessageClass Message;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_contact, container, false);
        emAsunto=v.findViewById(R.id.emAsunto);
        emMail = v.findViewById(R.id.emMail);
        emMensaje =v.findViewById(R.id.emMensaje);
        emNombre = v.findViewById(R.id.emNombre);
        btnEnviar = v.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(this);



        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnEnviar:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                mBuilder.setTitle(getString(R.string.TXT_TITLE_SEND));
                mBuilder.setMessage(R.string.TXT_MESSAGE_SEND);
                Message = new MessageClass(emNombre.getText().toString(),emAsunto.getText().toString(),emMensaje.getText().toString(),emMail.getText().toString());
                //enviar mensaje
                //eliminar el fragment
                break;
                //back action bar
            
        }
    }
}
