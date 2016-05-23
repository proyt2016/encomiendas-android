package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;


public class RegistroGrupal extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    int codTerminal;
    ListView l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_grupal);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        int codTerminal = getIntent().getExtras().getInt("codigo");


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setCurrentItem(0);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ListFragmentViajesRecorrido(), "RECORRIDO");
        viewPagerAdapter.addFragments(new ListFragmentViajesHorario(), "HORARIO");
        viewPagerAdapter.addFragments(new ListFragmentViajesCoche(), "COCHE");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    /*    ListFragmentViajes listFragmentViajes = (ListFragmentViajes) getSupportFragmentManager().findFragmentByTag("ListFragmentViajes");

        if (listFragmentViajes == null) {
            listFragmentViajes = new ListFragmentViajes();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(android.R.id.content, listFragmentViajes, "listTerminalesFragment");
            transaction.commit();


        }*/

    }
    public int obtenerCodigoTerminal(){
        return this.codTerminal;
    }

        }
