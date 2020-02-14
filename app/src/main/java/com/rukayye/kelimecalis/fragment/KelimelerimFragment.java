package com.rukayye.kelimecalis.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rukayye.kelimecalis.KelimeAdapter;
import com.rukayye.kelimecalis.R;
import com.rukayye.kelimecalis.util.SqlLiteHelper;

import java.util.ArrayList;

import static android.content.Context.SEARCH_SERVICE;

public class KelimelerimFragment extends Fragment {

    private RecyclerView recyclerView;
    private KelimeAdapter madapter;

    public KelimelerimFragment() {
    }

    public static KelimelerimFragment newInstance() {
        return new KelimelerimFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();//önceden kalanları temizle
        inflater.inflate(R.menu.menu_searchview, menu);

        //SEARCH
        final SearchManager searchManager = (SearchManager) getContext().getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //TODO: Reset your views
                    return false;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false; //do the default
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    madapter.filter(s);
                    madapter.notifyDataSetChanged();
                    return false;
                }
            });

        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(getContext(), "ara", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.action_settings:
                System.exit(0);
                Toast.makeText(getContext(), "çıkış", Toast.LENGTH_SHORT).show();
                return false;
            default:
                break;

        }
        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_fragment_kelimelerim, container, false);

        setHasOptionsMenu(true);//bunun kendine has bir menusu var

        ((AppCompatActivity) getActivity())
                .getSupportActionBar()
                .setTitle("Kelime Çalış");

        recyclerView = root.findViewById(R.id.kelimelerim_rv);
        SqlLiteHelper helper = new SqlLiteHelper(getContext());

        madapter = new KelimeAdapter(getContext(), helper.getDatas());
        recyclerView.setAdapter(madapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

}

