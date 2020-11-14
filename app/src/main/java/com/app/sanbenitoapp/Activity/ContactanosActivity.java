package com.app.sanbenitoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Pedido.DetallePedidoBody;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidoList;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class ContactanosActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private ProgressDialog dialog;

    String token;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactanos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contactanos!");

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        carritodb  = db.getCarritoDao();
        invitadodb = db.getInvitadoDao();
        pedidodb   = db.getPedidoDao();

        pedidoList = pedidodb.getPedido();

        if (userdb.getUser().size() > 0)
        {
            iduser = userdb.getUser().get(0).getIdusuario_app();
        }
        else if (invitadodb.getInvitado().size() > 0)
        {
            iduser = invitadodb.getInvitado().get(0).getIduser();
        }

        if (registrodb.getRegistro().size() > 0)
        {
            token = registrodb.getRegistro().get(0).getToken();
        }
        else if (invitadodb.getInvitado().size() > 0)
        {
            token = invitadodb.getInvitado().get(0).getToken();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_contacto).setChecked(true);
        navigationView.bringToFront();

        if (invitadodb.getInvitado().size() > 0)
        {
            nav_menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_menu.findItem(R.id.nav_chat).setVisible(false);
            nav_menu.findItem(R.id.nav_logout).setTitle("Iniciar Sesion");
            nav_menu.findItem(R.id.nav_logout).setIcon(R.drawable.login);
        }
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
                if (pedidoList.size() > 0)
                {
                    getDetallePedido(iduser,token,pedidoList.get(0).getIdpedido());
                }
                else
                {
                    Intent intent = new Intent(ContactanosActivity.this,CarritoActivity.class);
                    startActivity(intent);
                }
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
                break;
            case R.id.nav_logout:
                if (invitadodb.getInvitado().size() > 0)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("San Benito");
                    alertDialog.setMessage("Desea iniciar sesión?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Iniciar sesión",
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
                                    LoginManager.getInstance().logOut();
                                    Intent login = new Intent(ContactanosActivity.this,LoginActivity.class);
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
                }
                else
                {
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
                                    Intent login = new Intent(ContactanosActivity.this,LoginActivity.class);
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
                }
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getDetallePedido(String iduser, String token,String idpedido){
        dialog = new ProgressDialog(ContactanosActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);


        DetallePedidoBody body = new DetallePedidoBody(
                ""+iduser,
                ""+token,
                ""+idpedido

        );

        String json = new Gson().toJson(body);
        Log.i("body", json + "");

        Call<SavePedidoResponse> callT = apiService.getDetallePedido(body);

        callT.enqueue(new Callback<SavePedidoResponse>() {

            @Override
            public void onResponse(Call<SavePedidoResponse> call, retrofit2.Response<SavePedidoResponse> response) {
                final SavePedidoResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0){

                    pedidodb.insert(Res.getMsg().get(0));

                    if (Res.getMsg().get(0).getIdestados_pedido().equals("4") || Res.getMsg().get(0).getIdestados_pedido().equals("6")){
                        pedidodb.deleteAll();
                        Intent intent = new Intent(ContactanosActivity.this,CarritoActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent a = new Intent(ContactanosActivity.this, EstadoPedidoActivity.class);
                        a.putExtra("idpedido", "" + Res.getMsg().get(0).getIdpedido());
                        a.putExtra("tiempo_espera", "" + Res.getMsg().get(0).getTiempo_espera());
                        a.putExtra("codigo_envio", "" + Res.getMsg().get(0).getCodigo_envio());
                        a.putExtra("latitud", "" + Res.getMsg().get(0).getLatitud());
                        a.putExtra("longitud", "" + Res.getMsg().get(0).getLongitud());
                        a.putExtra("viene", "1" );
                        startActivity(a);
                        finish();
                    }

                }else if(Res.getErrorCode() == 4){
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(final Call<SavePedidoResponse> call, final Throwable t) {
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
