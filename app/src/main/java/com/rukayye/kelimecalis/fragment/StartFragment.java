package com.rukayye.kelimecalis.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rukayye.kelimecalis.R;

public class StartFragment extends Fragment {

    private Button btnKelimeCalis;
    private Button btnTest;
    private Button btnYeniKelime;
    private Button btnKelimelerim;

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        btnKelimeCalis = (Button) view.findViewById(R.id.btn_calis);
        btnTest = (Button) view.findViewById(R.id.btn_test);
        btnYeniKelime = (Button) view.findViewById(R.id.btn_yeni_kelime_ekle);
        btnKelimelerim = (Button) view.findViewById(R.id.btn_yeni_kelimeler);

        btnKelimeCalis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, KelimeCalisFragment.newInstance())
                        .addToBackStack(null)// uygulamada geri gitmemizi sağlar
                        .commit();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Ne yapılacak?
            }
        });

        btnYeniKelime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, KelimeEkleFragment.newInstance())
                        .addToBackStack(null)// uygulamada geri gitmemizi sağlar
                        .commit();

            }

        });

        btnKelimelerim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment, KelimelerimFragment.newInstance())
                        .commit();
            }
        });

        return view;
    }
}
