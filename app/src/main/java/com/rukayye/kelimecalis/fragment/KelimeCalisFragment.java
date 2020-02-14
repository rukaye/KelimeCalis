package com.rukayye.kelimecalis.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rukayye.kelimecalis.model.KelimeModel;
import com.rukayye.kelimecalis.R;
import com.rukayye.kelimecalis.util.SqlLiteHelper;


import java.util.ArrayList;

public class KelimeCalisFragment extends Fragment  {

    private ArrayList<KelimeModel> kelimeList = new ArrayList<>();

    private TextView tvKelime;
    private TextView tvAnlam;
    private Button btnGeri;
    private Button btnIleri;
    private Button btnAnlam;
    private AppCompatImageView imgFav;

    private SqlLiteHelper helper;
    private boolean click = false;

    public static KelimeCalisFragment newInstance() {
        return new KelimeCalisFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fragment_kelime_calis, container, false);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            ProgressDialog progressDialog=new ProgressDialog(getContext());
            progressDialog.setMessage("Kelimeler Yükleniyor");
            progressDialog.show();

        tvKelime = view.findViewById(R.id.tv_kelime_id);
        tvAnlam = view.findViewById(R.id.tv_anlam);
        btnGeri = view.findViewById(R.id.btn_geri);
        btnIleri = view.findViewById(R.id.btn_ileri);
        btnAnlam = view.findViewById(R.id.btn_anlam);
        imgFav = view.findViewById(R.id.fav);

        helper = new SqlLiteHelper(getContext());

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kelime = kelimeList.get(index).getKelime();
                String anlam = kelimeList.get(index).getAnlam();

                boolean result = helper.isCheckedFav(kelime);

                if (result) {
                    // True, zaten veritabanında ekli
                    boolean isSucccess = helper.deleteByKelime(kelime);
                    if (isSucccess) {
                        Toast.makeText(getContext(), "Kelime silindi", Toast.LENGTH_SHORT).show();
                        imgFav.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
                    } else {
                        Toast.makeText(getContext(), "Kelime silme esnasında bir sorun oluştu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean isSucccess = helper.insertKelime(kelime, anlam);
                    if (isSucccess) {
                        Toast.makeText(getContext(), "Kelime eklendi", Toast.LENGTH_SHORT).show();
                        imgFav.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
                    } else {
                        Toast.makeText(getContext(), "Kelime ekleme esnasında bir sorun oluştu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnAnlam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean state = tvAnlam.getText().toString().equals("");
                updateUI(state);
            }
        });


        btnIleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (index < kelimeList.size() - 1) {
                    index++;
                    updateUI(false);
                } else {
                    Toast.makeText(getContext(), "Kelime bitti", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index--;
                    updateUI(false);
                } else
                    Toast.makeText(getContext(), "ileriye gidiniz", Toast.LENGTH_SHORT).show();
            }
        });

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (Object o : dataSnapshot.getChildren()) {
                    KelimeModel model = ((DataSnapshot) o).getValue(KelimeModel.class);
                    kelimeList.add(model);
                }
                updateUI(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    int index = 0;
    public void updateUI(boolean isAnlamVisible) {
        tvAnlam.setText("");
        KelimeModel model = kelimeList.get(index);
        tvKelime.setText(model.getKelime());
        if (isAnlamVisible) {
            tvAnlam.setText(model.getAnlam());
        } else {
            tvAnlam.setText("");
        }

        checkKelimeFavourite(model.getKelime(), imgFav);
    }
    private void checkKelimeFavourite(String kelime, AppCompatImageView img) {
        boolean result = helper.isCheckedFav(kelime);

        if (result) {
            img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_black_24dp));
        } else {
            img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
        }
    }
}

