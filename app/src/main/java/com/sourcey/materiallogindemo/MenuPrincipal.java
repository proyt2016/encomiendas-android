package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sourcey.materiallogindemo.api.ViajesApi;
import com.sourcey.materiallogindemo.model.Viaje;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public int codTerminal;
    public boolean cargo = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codTerminal = getIntent().getExtras().getInt("codigo");
if(cargo == false) {
    Call<List<Viaje>> call = ViajesApi.createService().getSearch(String.valueOf(codTerminal));
    call.enqueue(new Callback<List<Viaje>>() {
        @Override
        public void onResponse(Call<List<Viaje>> call, Response<List<Viaje>> response) {
            List<Viaje> viajes = response.body();
            for (Viaje v : viajes) {
                Farcade.listaViajes.add(new Viaje(v.getId(), v.getNombre(), v.getNroCoche(), v.getOrigenId(), v.getDestinoId(), v.getFecha(), v.getSalida(), v.getLlegada(), v.getListaEncomiendas()));
                cargo = true;
            }
        }

        @Override
        public void onFailure(Call<List<Viaje>> call, Throwable t) {
            System.out.println("onFailure");
        }
    });
}




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.BusquedaMasiva) {
            Intent i = new Intent(MenuPrincipal.this, RegistroGrupal.class);
            i.putExtra("codigo",codTerminal );
            startActivity(i);


        } else if (id == R.id.BusquedaIndividual) {
            Intent i = new Intent(MenuPrincipal.this, RegistroIndividual.class);
            i.putExtra("codigo",codTerminal );
            startActivity(i);
        }
        else if (id == R.id.datosTerminal) {
          /*  Intent i = new Intent(MenuPrincipal.this, RegistroIndividual.class);
            i.putExtra("codigo",codTerminal );
            startActivity(i);*/
        }
        else if (id == R.id.datosEmpleado) {
         /*   Intent i = new Intent(MenuPrincipal.this, RegistroIndividual.class);
            i.putExtra("codigo",codTerminal );
            startActivity(i);*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
