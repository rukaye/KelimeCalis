package com.rukayye.kelimecalis.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rukayye.kelimecalis.R;
import com.rukayye.kelimecalis.model.KelimeModel;

public class KelimeEkleFragment extends Fragment {

    private EditText etKelime;
    private EditText etAnlam;
    public static KelimeEkleFragment newInstance() {
        return new KelimeEkleFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_ykelime__ekle, container, false);

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        etKelime = (EditText) view.findViewById(R.id.ing_kelime_et);
        etAnlam = (EditText) view.findViewById(R.id.anlam1_et);

        Button btn_ekle = (Button) view.findViewById(R.id.btn_ekle);
        btn_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get total available quest
                        long kelime_id = dataSnapshot.getChildrenCount();
                        String kelime = etKelime.getText().toString();
                        String kelimeAnlam = etAnlam.getText().toString();

                        KelimeModel model = new KelimeModel(kelime_id, kelime, kelimeAnlam, false);
                        mDatabase
                                .child(String.valueOf(kelime_id))
                                .setValue(model);

                        Toast.makeText(getContext(), "Kelime başarıyla eklendi", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        return view;
    }
}
