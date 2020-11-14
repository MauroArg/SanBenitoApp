package com.app.sanbenitoapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanbenitoapp.Adapter.CarritoAdapter;
import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.app.sanbenitoapp.Util.RecyclerItemTouchHelper;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class CarritoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    RecyclerView carritoRecycler;
    TextView total;
    List<Carrito> carritos;
    CarritoAdapter mAdapter;
    Double Dtotal = 0.0;
    Button ordenar;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Carrito");

        db = Room.databaseBuilder(CarritoActivity.this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();
        carritodb  = db.getCarritoDao();
        invitadodb = db.getInvitadoDao();

        carritoRecycler = findViewById(R.id.carritoRecycler);
        total           = findViewById(R.id.total);
        ordenar         = findViewById(R.id.btnOrdenar);

        carritos = carritodb.getCarrito();
        Log.i("carrito","carrito "+carritos.size());

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu nav_menu = navigationView.getMenu();
        nav_menu.findItem(R.id.nav_carrito).setChecked(true);
        navigationView.bringToFront();

        if (invitadodb.getInvitado().size() > 0)
        {
            nav_menu.findItem(R.id.nav_perfil).setVisible(false);
            nav_menu.findItem(R.id.nav_chat).setVisible(false);
            nav_menu.findItem(R.id.nav_logout).setTitle("Iniciar Sesion");
            nav_menu.findItem(R.id.nav_logout).setIcon(R.drawable.login);
        }

        mAdapter = new CarritoAdapter(this, carritos);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        carritoRecycler.setLayoutManager(mLayoutManager);
        carritoRecycler.setItemAnimator(new DefaultItemAnimator());
        carritoRecycler.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(carritoRecycler);

        for (int i = 0; i < carritos.size(); i++) {
            Dtotal = Dtotal + (Double.parseDouble(carritos.get(i).getPrecio()) * carritos.get(i).getCantidad());
        }

        total.setText("$"+String.format("%.2f", Dtotal));

        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        CarritoActivity.this);

                alertDialogBuilder.setTitle("San Benito");

                alertDialogBuilder
                        .setMessage("Desea enviar esta orden?")
                        .setCancelable(false)
                        .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if (invitadodb.getInvitado().size() > 0)
                                {
                                    dialogLogin();
                                }
                                else if(carritos.size() == 0){
                                    Toast.makeText(CarritoActivity.this, "Debe ingresar productos", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Intent a = new Intent(CarritoActivity.this,MapsActivity.class);
                                    startActivity(a);
                                    finish();
                                }

                                //postEnviarOrden(idpedido,cplatos);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

                // create alert dialog
                alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

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
                                    Intent login = new Intent(CarritoActivity.this,LoginActivity.class);
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
                                    Intent login = new Intent(CarritoActivity.this,LoginActivity.class);
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

    public void dialogLogin()
    {
        Log.e("INVITED","INVITED");
        final AlertDialog.Builder alerta2 = new AlertDialog.Builder(
                CarritoActivity.this);

        alerta2.setTitle("Funcion no disponible en modo invitado")
                .setMessage("Debe iniciar sesion antes de enviar un pedido")
                .setCancelable(false)
                .setPositiveButton("Iniciar Sesion", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog2, int which) {
                        Intent intent = new Intent(CarritoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        CarritoActivity.this.finish();
                        dialog2.dismiss();
                    }
                })
                .setNegativeButton("Seguir como invitado", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog2, int which)
                    {
                        dialog2.dismiss();
                    }
                });
        alerta2.show();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CarritoAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar

            // backup of removed item for undo purpose
            final Carrito deletedItem = carritos.get(viewHolder.getAdapterPosition());
            carritodb.delete(deletedItem);
            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            carritos = carritodb.getCarrito();
            Dtotal = 0.0;
            for (int i = 0; i < carritos.size(); i++) {
                Dtotal = Dtotal + Double.parseDouble(String.valueOf(Double.parseDouble(carritos.get(i).getPrecio()) * (double) carritos.get(i).getCantidad()));
            }

            total.setText("$"+String.format("%.2f", Dtotal));

            Toast.makeText(CarritoActivity.this, "Producto eliminado de su carrito", Toast.LENGTH_SHORT).show();
        }
    }
}
