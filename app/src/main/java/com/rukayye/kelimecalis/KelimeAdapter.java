package com.rukayye.kelimecalis;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.rukayye.kelimecalis.model.KelimeModel;
import com.rukayye.kelimecalis.util.SqlLiteHelper;
import java.util.ArrayList;
public class KelimeAdapter extends RecyclerView.Adapter<KelimeAdapter.MyViewHolder> {

    private ArrayList<KelimeModel> mKelimeList;
    private ArrayList<KelimeModel> sourceList;
    private Context context;
    private LayoutInflater inflater;

    public KelimeAdapter(Context context, ArrayList<KelimeModel> kelime_modul) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.mKelimeList = kelime_modul;
        //Collections.copy(mKelimeList, sourceList);
        sourceList = new ArrayList<>(mKelimeList);
    }

    public ArrayList<KelimeModel> getmKelimeList() {
        return mKelimeList;
    }

    public void setmKelimeList(ArrayList<KelimeModel> mKelimeList) {
        this.mKelimeList = mKelimeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.kelimelerim_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        KelimeModel selectedKelime = mKelimeList.get(position);

        holder.txtKelime.setText(selectedKelime.getKelime());
        holder.txtAnlam.setText(selectedKelime.getAnlam());
    }

    @Override
    public int getItemCount() {
        return mKelimeList.size();
    }

    public void filter(String s) {
            mKelimeList.clear();

            if (s.equals("")) {
                mKelimeList = new ArrayList<>(sourceList);
            } else {
                for (KelimeModel wp : sourceList) {
                    if (wp.getKelime().toLowerCase().contains(s.toLowerCase())
                            || wp.getAnlam().toLowerCase().contains(s.toLowerCase())) {
                        mKelimeList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtKelime, txtAnlam;
        private ImageView imgSil;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtKelime = (TextView) itemView.findViewById(R.id.tv_kelime);
            txtAnlam = (TextView) itemView.findViewById(R.id.tv_anlami);
            imgSil = (ImageView) itemView.findViewById(R.id.img_sil);

            imgSil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog mdialog = AskOption();
                    mdialog.show();
                }
            });

        }

        private AlertDialog AskOption() {
            AlertDialog mDialog = new AlertDialog.Builder(context)
                    .setTitle("UYARI")
                    .setMessage("Silmek istediğinize emin misiniz")
                    .setIcon(R.drawable.ic_delete_black_24dp)
                    .setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            SqlLiteHelper helper = new SqlLiteHelper(context);
                            KelimeModel kelime = mKelimeList.get(getLayoutPosition());
                            boolean result = helper.deleteByKelime(kelime.getKelime());
                            if (result) {
                                mKelimeList = helper.getDatas();
                                notifyDataSetChanged();
                                Toast.makeText(context, "kelime silindi", Toast.LENGTH_SHORT).show();
                            }

                            dialog.dismiss();

                        }

                    })
                    .setNegativeButton("çık", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).create();
            return mDialog;
        }

    }
}