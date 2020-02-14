package com.rukayye.kelimecalis;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rukayye.kelimecalis.fragment.StartFragment;
import com.rukayye.kelimecalis.model.KelimeModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, StartFragment.newInstance())
                .commit();

        Toolbar toolbar = findViewById(R.id.mytoolbar);
        toolbar.setTitle(" Kelime Çalış");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextAppearance(this,R.style.ActionBar_ToolText);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.action_settings){ System.exit(0);}
                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Toolbara menüyü ekle
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;

        }



}
