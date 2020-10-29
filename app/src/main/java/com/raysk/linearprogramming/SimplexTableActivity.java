package com.raysk.linearprogramming;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.raysk.linearprogramming.logic.Simplex;
import com.raysk.linearprogramming.ui.main.SectionsPagerAdapter;

public class SimplexTableActivity extends AppCompatActivity{
    Simplex simplex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simplex_table);

        simplex = new Simplex(null);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), getTabsTitles());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }


    private String[] getTabsTitles(){
        String[] titles = new String[simplex.getTablas().size()+1];
        titles[0] = "Tabla inicial";
        titles[titles.length-1] = "resultados";

        for (int i = 1; i < titles.length-1 ; i++) {
            titles[i] = "Tabla "+ i;
        }
        return titles;
    }



}