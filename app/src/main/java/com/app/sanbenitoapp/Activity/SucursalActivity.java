package com.app.sanbenitoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.app.sanbenitoapp.Adapter.SucursarAdapter;
import com.app.sanbenitoapp.Model.Promocion.Promocion;
import com.app.sanbenitoapp.Model.Promocion.PromocionResponse;
import com.app.sanbenitoapp.Model.Sucursal.Sucursal;
import com.app.sanbenitoapp.Model.Sucursal.SucursalResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class SucursalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener,SucursarAdapter.ItemListener
{
    String token;
    String iduser;
    String correo;

    RecyclerView list_sucursal;

    SliderLayout sliderLayout;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_menu = navigationView.getMenu();
        navigationView.bringToFront();

        if (invitadodb.getInvitado().size() > 0)
        {
            nav_menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_menu.findItem(R.id.nav_chat).setVisible(false);
            nav_menu.findItem(R.id.nav_logout).setTitle("Iniciar Sesion");
            nav_menu.findItem(R.id.nav_logout).setIcon(R.drawable.login);
        }

        sliderLayout  = findViewById(R.id.slider);
        list_sucursal = findViewById(R.id.list_sucursal);
        list_sucursal.setLayoutManager(new LinearLayoutManager(this));

        getPromociones();
        getSucursales();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.nav_principal:
                Intent principal = new Intent(this,MainActivity.class);
                startActivity(principal);
                finish();
                break;
            case R.id.nav_promociones:
                Intent promo = new Intent(this,PromocionActivity.class);
                startActivity(promo);
                finish();
                break;
            case R.id.nav_carrito:
                Intent carrito = new Intent(this,CarritoActivity.class);
                startActivity(carrito);
                finish();
                break;
            case R.id.nav_agenda:
                Intent agenda = new Intent(this, AgendaActivity.class);
                startActivity(agenda);
                finish();
                break;
            case R.id.nav_perfil:
                Intent perfil = new Intent(this,PerfilActivity.class);
                startActivity(perfil);
                finish();
                break;
            case R.id.nav_chat:
                Intent chat = new Intent(this,ChatActivity.class);
                startActivity(chat);
                finish();
                break;
            case R.id.nav_contacto:
                Intent contacto = new Intent(this, ContactanosActivity.class);
                startActivity(contacto);
                finish();
                break;
            case R.id.nav_logout:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("San Benito");
                alertDialog.setMessage("Desea cerrar sesión?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Salir",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                invitadodb.deleteAll();
                                registrodb.deleteAll();
                                userdb.deleteAll();
                                pedidodb.deleteAll();
                                if (carritodb != null)
                                {
                                    carritodb.deleteAll();
                                }
                                MainActivity.getInstance().finish();
                                LoginManager.getInstance().logOut();
                                Intent login = new Intent(SucursalActivity.this,LoginActivity.class);
                                startActivity(login);
                                finish();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //-----------------------------------------------------LISTENER------------------------------------------------------------//

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onItemClick(Sucursal item, ImageView imageview) {
        Intent a = new Intent(SucursalActivity.this, PerfilSucursalActivity.class);
        a.putExtra("id",""+item.getId());
        a.putExtra("titulo",""+item.getNombre());
        a.putExtra("descripcion",""+item.getDireccion());
        a.putExtra("img",""+item.getImagen());
        a.putExtra("horario",""+item.getHorario());
        a.putExtra("tipo","1");
        a.putExtra("lat",""+ item.getLatitud());
        a.putExtra("lon",""+item.getLongitud());
        startActivity(a);
    }


    //-------------------------------------------------------------WS-------------------------------------------------------//

    public void getPromociones()
    {
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        Call<PromocionResponse> callT = apiService.getPromociones();

        callT.enqueue(new Callback<PromocionResponse>() {
            @Override
            public void onResponse(Call<PromocionResponse> call, Response<PromocionResponse> response) {
                final PromocionResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if (Res.getErrorCode() == 0)
                {
                    HashMap<String,String> url_maps = new HashMap<String, String>();

                    List<Promocion> promociones = new ArrayList<>();
                    for (int i = 0; i < Res.getMsg().size(); i++)
                    {
                        url_maps.put(Res.getMsg().get(i).getNombre(), Res.getMsg().get(0).getImagen());

                        promociones.add(new Promocion(
                                ""+ Res.getMsg().get(0).getId(),
                                ""+ Res.getMsg().get(0).getNombre(),
                                ""+Res.getMsg().get(0).getDescripcion(),
                                ""+Res.getMsg().get(0).getCreacion(),
                                ""+Res.getMsg().get(0).getImagen(),
                                ""+Res.getMsg().get(0).getIdmedicina(),
                                ""+Res.getMsg().get(0).getFecha_limite()));
                    }

                    for(String name : url_maps.keySet()){
                        TextSliderView textSliderView = new TextSliderView(SucursalActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(url_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                .setOnSliderClickListener(SucursalActivity.this);

                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra",name);

                        sliderLayout.addSlider(textSliderView);
                    }
                    sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                    sliderLayout.setCustomAnimation(new DescriptionAnimation());
                    sliderLayout.setDuration(4000);
                    sliderLayout.addOnPageChangeListener(SucursalActivity.this);
                }


            }

            @Override
            public void onFailure(Call<PromocionResponse> call, Throwable t) {

            }
        });
    }

    public void getSucursales(){
        dialog = new ProgressDialog(SucursalActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        Call<SucursalResponse> callT = apiService.getSucursales();

        callT.enqueue(new Callback<SucursalResponse>() {

            @Override
            public void onResponse(Call<SucursalResponse> call, retrofit2.Response<SucursalResponse> response) {
                final SucursalResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0){

                    SucursarAdapter adapter = new SucursarAdapter(SucursalActivity.this,Res.getMsg(),SucursalActivity.this);
                    list_sucursal.setAdapter(adapter);

                }else if(Res.getErrorCode() == 2)
                {
                    Log.e("ERROR","ERROR");
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(final Call<SucursalResponse> call, final Throwable t) {
                if (!call.isCanceled()) {
                    //onError();
                    Log.e("Error",""+t);
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            }
        });
    }
}
