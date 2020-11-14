package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.app.sanbenitoapp.Util.GlobalVariables;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class PerfilSucursalActivity extends AppCompatActivity implements OnMapReadyCallback {
    CollapsingToolbarLayout collapsingToolbar;
    AppBarLayout appBarLayout;

    private Menu collapsedMenu;
    private boolean appBarExpanded = true;

    RecyclerView scrollableview;
    ImageView header;
    TextView Horario,descri,titul,labelHorario;

    String id, titulo, descripion, img,hora,tipo;

    String token;
    String iduser;
    String correo;
    String lat;
    String lon;


    FloatingActionButton flot;
    private GoogleMap mMap;
    Marker marker;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_sucursal);
        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        carritodb  = db.getCarritoDao();

        token  = registrodb.getRegistro().get(0).getToken();
        iduser = userdb.getUser().get(0).getIdusuario_app();
        correo = userdb.getUser().get(0).getCorreo();

        Log.i("params",iduser+"   "+token);

        Intent i = getIntent();
        id = i.getExtras().getString("id");
        titulo = i.getExtras().getString("titulo");
        descripion = i.getExtras().getString("descripcion");
        img = i.getExtras().getString("img");
        hora = i.getExtras().getString("horario");
        tipo = i.getExtras().getString("tipo");




        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout   = findViewById(R.id.appbar);
        scrollableview = findViewById(R.id.scrollableview);
        flot           = findViewById(R.id.flot);
        header         = findViewById(R.id.header);
        Horario        = findViewById(R.id.Horario);
        descri         = findViewById(R.id.descri);
        titul          = findViewById(R.id.titul);
        labelHorario   = findViewById(R.id.labelHorario);
        cardView       = findViewById(R.id.cardView);

        Horario.setText(hora);
        descri.setText(descripion);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);

        if(tipo.equals("1")) {
            cardView.setVisibility(View.VISIBLE);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            titul.setText("Dirección:");
            lat = i.getExtras().getString("lat");
            lon = i.getExtras().getString("lon");
            collapsingToolbar.setTitle(titulo);
            labelHorario.setText("Horario");
            flot.setImageResource(R.mipmap.chati);
            flot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent w = new Intent(PerfilSucursalActivity.this,ChatActivity.class);
                    w.putExtra("correo",""+correo);
                    startActivity(w);
                }
            });
        }else if(tipo.equals("2")){
            cardView.setVisibility(View.VISIBLE);
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            lat = "13.6799156";
            lon = "-89.2734078";

            titul.setText(titulo);
            labelHorario.setText("Fecha Limite");
            collapsingToolbar.setTitle(" ");
            flot.setImageResource(R.mipmap.chati);
            flot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent w = new Intent(PerfilSucursalActivity.this,ChatActivity.class);
                    w.putExtra("correo",""+correo);
                    startActivity(w);
                }
            });
        }

        Glide.with(this)
                .load(img)
                .apply(RequestOptions.circleCropTransform())
                .into(header);


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        scrollableview.setLayoutManager(layoutManager);
        scrollableview.setNestedScrollingEnabled(false);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(PerfilSucursalActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil_sucursal, menu);
        collapsedMenu = menu;
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(tipo.equals("1")) {
            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            mMap = googleMap;
            marker = mMap.addMarker(new MarkerOptions().position(sydney).title(titulo).draggable(false));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));
        }else{
            LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
            mMap = googleMap;
            marker = mMap.addMarker(new MarkerOptions().position(sydney).title(titulo).draggable(false));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent intent = new Intent(PerfilSucursalActivity.this, SucursalActivity.class);
            startActivity(intent);
            finish();
        return super.onOptionsItemSelected(item);
    }
}