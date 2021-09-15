package com.Project.yasejuega.Fragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Project.yasejuega.Activitys.MainActivity;
import com.Project.yasejuega.R;


/**
 * A simple {@link Fragment} subclass.
 */

public class NoUserFragment extends Fragment implements View.OnClickListener {
    Button btnLogin;
    TextView txtContacto;
    SpannableString ss1;
    ClickableSpan cs1;

    public NoUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_no_user, container, false);
        btnLogin = v.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        txtContacto = v.findViewById(R.id.txtContacto);
        ss1 = new SpannableString(getString(R.string.TXT_CONC_CONTACTO));
        cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText(getActivity(), "CONTACTO", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(getActivity(), R.color.text2));
                ds.setUnderlineText(false);
                ds.setTypeface(Typeface.DEFAULT_BOLD);
            }
        };
        ss1.setSpan(cs1,getString(R.string.TXT_TEXTO_CONTACTO).length(),getString(R.string.TXT_CONC_CONTACTO).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtContacto.setText(ss1);
        txtContacto.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                Toast.makeText(getActivity(), "LOGIN NO USER", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
