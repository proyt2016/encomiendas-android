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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentIntegrator;
import com.sourcey.materiallogindemo.com.google.zxing.integration.android.IntentResult;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public String codTerminal;
    private Button btnManual;
    private Button btnEscaner;

    private TextView txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codTerminal = getIntent().getExtras().getString("idTerminal");

        btnManual = (Button) findViewById(R.id.manual);
        btnEscaner = (Button) findViewById(R.id.Escaner);

        btnManual.setOnClickListener(this);
        btnEscaner.setOnClickListener(this);



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
    //MANEJO DE OPCIONES MENU
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.BusquedaMasiva) {
            codTerminal = getIntent().getExtras().getString("idTerminal");
            int AsignarEncomiendas = 1;
            Intent i = new Intent(MenuPrincipal.this, RegistroGrupal.class);
            i.putExtra("codigo", codTerminal);
            i.putExtra("flag",AsignarEncomiendas);
            startActivity(i);
        } else if (id == R.id.BusquedaIndividual) {


        }
        else if (id == R.id.AsignarEncomiendasCoche) {
            codTerminal = getIntent().getExtras().getString("idTerminal");
            int AsignarEncomiendas = 2;
            Intent i = new Intent(MenuPrincipal.this, RegistroGrupal.class);
            i.putExtra("codigo",codTerminal );
            i.putExtra("flag",AsignarEncomiendas);
            startActivity(i);
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.Escaner){
            //ESCANERO
            Intent i = new Intent(this,DialogEstados.class);
            startActivity(i);
            //IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //scanIntegrator.initiateScan();
        }
        if(v.getId() == R.id.manual){

            Intent i = new Intent(MenuPrincipal.this, BusquedaIndividual.class);
            i.putExtra("codigo", codTerminal);
            startActivity(i);

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (resultCode != RESULT_CANCELED) {

            if (scanningResult != null) {
                final String scanContent = scanningResult.getContents();




            }
        }
    }
}
