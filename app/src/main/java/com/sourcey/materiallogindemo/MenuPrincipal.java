package com.sourcey.materiallogindemo;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    public String codTerminal;
    private Button btnManual;
    private Button btnEscaner;
    private DrawerLayout pantalla;
    private RelativeLayout fondoPantalla;
    private Farcade farcade;
    private TextView titulo;
    private TextView tituloEscaner;
    private TextView tituloManual;
    private LinearLayout MenuDesplegable;
    private TextView nombreEmpresa;
    private TextView emailEmpresa;
    private ImageView logoEmpresa;
    private CoordinatorLayout trozoDePantalla;
    private NavigationView nav;
    private TextView usr;
    private ClipData.Item terminal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        codTerminal = getIntent().getExtras().getString("idTerminal");

        btnManual = (Button) findViewById(R.id.manual);
        btnEscaner = (Button) findViewById(R.id.Escaner);

        pantalla = (DrawerLayout)findViewById(R.id.drawer_layout);
        fondoPantalla = (RelativeLayout)findViewById(R.id.fondolayout);
        MenuDesplegable = (LinearLayout)findViewById(R.id.menudesplegableheader);
        //trozoDePantalla = (CoordinatorLayout)findViewById(R.id.trozodepantalla);
        nav = (NavigationView) findViewById(R.id.nav_view);
        logoEmpresa = (ImageView)findViewById(R.id.imageview);

        //TEXTOS MENU DESPLEGABLE
        nombreEmpresa = (TextView)findViewById(R.id.nombreempresa);
        emailEmpresa = (TextView)findViewById(R.id.mailempresa);

        titulo = (TextView)findViewById(R.id.titulomenuprincipal);
        tituloEscaner = (TextView)findViewById(R.id.subtituloescaner);
        tituloManual = (TextView)findViewById(R.id.subtitulomanual);

        btnManual.setOnClickListener(this);
        btnEscaner.setOnClickListener(this);

        if(Farcade.configuracionEmpresa.getId()!=null){

            if(Farcade.configuracionEmpresa.getColorFondosDePantalla()!=null){
                pantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                fondoPantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                trozoDePantalla.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
                MenuDesplegable.setBackgroundColor(Color.parseColor("#FFFFFF"));
                nav.setBackgroundColor(Color.parseColor(Farcade.configuracionEmpresa.getColorFondosDePantalla()));
            }else{
                pantalla.setBackgroundColor(Color.parseColor("#FFFFFF"));
                MenuDesplegable.setBackgroundResource(R.drawable.side_nav_bar);
                fondoPantalla.setBackgroundResource(R.drawable.side_nav_bar);
              //  trozoDePantalla.setBackgroundResource(R.drawable.side_nav_bar);
                nav.setBackgroundColor(Color.parseColor("#FFFFFF"));


            }
            if(Farcade.configuracionEmpresa.getColorTitulo()!=null){
                titulo.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorTitulo()));
            }else{
                titulo.setTextColor(Color.parseColor("#FFFFFF"));
            }
            if(Farcade.configuracionEmpresa.getColorLetras()!=null){
                tituloManual.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));
                tituloEscaner.setTextColor(Color.parseColor(Farcade.configuracionEmpresa.getColorLetras()));

                nombreEmpresa.setTextColor(Color.parseColor("#000000"));
               emailEmpresa.setTextColor(Color.parseColor("#000000"));

            }else{
                tituloEscaner.setTextColor(Color.parseColor("#FFFFFF"));
                tituloManual.setTextColor(Color.parseColor("#FFFFFF"));
                nombreEmpresa.setTextColor(Color.parseColor("#FFFFFF"));
                emailEmpresa.setTextColor(Color.parseColor("#FFFFFF"));
            }
            if(Farcade.configuracionEmpresa.getNombre()!=null){
                nombreEmpresa.setText(Farcade.configuracionEmpresa.getNombre());
            }else{
                nombreEmpresa.setText("LAC BUS");
            }
            if(Farcade.configuracionEmpresa.getEmails()!=null) {
                if (!Farcade.configuracionEmpresa.getEmails().isEmpty()) {
                    emailEmpresa.setText(Farcade.configuracionEmpresa.getEmails().get(0).getEmail());
                } else {
                    emailEmpresa.setText("tecnologo2016@gmail.com");
                }
            }else{
                emailEmpresa.setText("tecnologo2016@gmail.com");
                }
        }else{
            //No existe configuracion disponible
            pantalla.setBackgroundColor(Color.parseColor("#FFFFFF"));
            MenuDesplegable.setBackgroundResource(R.drawable.side_nav_bar);
            fondoPantalla.setBackgroundResource(R.drawable.side_nav_bar);
            titulo.setTextColor(Color.parseColor("#FFFFFF"));
            tituloEscaner.setTextColor(Color.parseColor("#FFFFFF"));
            tituloManual.setTextColor(Color.parseColor("#FFFFFF"));
            nombreEmpresa.setTextColor(Color.parseColor("#FFFFFF"));
            emailEmpresa.setTextColor(Color.parseColor("#FFFFFF"));
            nombreEmpresa.setText("LAC BUS");
            emailEmpresa.setText("tecnologo2016@gmail.com");
            logoEmpresa.setImageResource(R.drawable.icono_bondi);


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
       /* if (id == R.id.action_settings) {
            return true;
        }*/

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
        } /*else if (id == R.id.BusquedaIndividual) {


        }*/
        else if (id == R.id.AsignarEncomiendasCoche) {
            codTerminal = getIntent().getExtras().getString("idTerminal");
            int AsignarEncomiendas = 2;
            Intent i = new Intent(MenuPrincipal.this, RegistroGrupal.class);
            i.putExtra("codigo",codTerminal );
            i.putExtra("flag",AsignarEncomiendas);
            startActivity(i);
        }
        else if (id == R.id.datosEmpleado) {
           Intent i = new Intent(MenuPrincipal.this, DialogInformacion.class);
            i.putExtra("flag",1 );
            startActivity(i);
        }
        else if (id == R.id.datosTerminal) {
            Intent i = new Intent(MenuPrincipal.this, DialogInformacion.class);
            i.putExtra("flag",2 );
            startActivity(i);
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

}
