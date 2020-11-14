package com.app.sanbenitoapp.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanbenitoapp.Adapter.ProductoAdapter;
import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.Model.Pedido.DetallePedidoBody;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoResponse;
import com.app.sanbenitoapp.Model.Producto.Producto;
import com.app.sanbenitoapp.Model.Producto.ProductoResponse;
import com.app.sanbenitoapp.Model.Promocion.Promocion;
import com.app.sanbenitoapp.Model.Promocion.PromocionResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.login.LoginManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.travijuu.numberpicker.library.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidoList;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class ProductoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, ProductoAdapter.ItemListener
{
    SliderLayout sliderLayout;

    private ProgressDialog dialog;

        private SearchView searchView = null;

    private int page_number = 0;

    private boolean scrollear = true;

    private boolean isLoading = true;
    private int currentItems,totalItems,scrollOutItems;

    String token;
    String iduser;
    String correo;
    String prod;

    RecyclerView          recyclerView;
    ProductoAdapter       productoAdapter;
    ProductoAdapter       searchAdapter;
    ProgressBar           progressBar;
    GridLayoutManager     layoutManager;
    AlertDialog           alertDialog;
    TextView              txtEmpty;
    ImageView             imgEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        carritodb  = db.getCarritoDao();
        invitadodb = db.getInvitadoDao();
        pedidodb   = db.getPedidoDao();

        pedidoList = pedidodb.getPedido();


        NavigationView navigationView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.list_producto);
        txtEmpty     = findViewById(R.id.txtEmpty);
        imgEmpty     = findViewById(R.id.imgEmpty);
        progressBar  = findViewById(R.id.progressBar);

        if (userdb.getUser().size() > 0)
        {
            correo = userdb.getUser().get(0).getCorreo();
            iduser = userdb.getUser().get(0).getIdusuario_app();
        }
        else if (invitadodb.getInvitado().size() > 0)
        {
            correo = "Invitado";
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

        prod = getIntent().getExtras().getString("Prod");
        if (prod.equals("1"))
        {
            getSupportActionBar().setTitle("Medicamentos");
        }
        else if (prod.equals("2"))
        {
            getSupportActionBar().setTitle("Librería");
        }
        else if(prod.equals("3"))
        {
            getSupportActionBar().setTitle("Convivencia");
        }


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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
        sliderLayout = findViewById(R.id.slider);

        layoutManager = new GridLayoutManager(this,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        getPromociones();
        getSucPromMeds1(iduser,token,page_number,Integer.parseInt(prod));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isLoading = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                totalItems = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();
                if (isLoading && (currentItems + scrollOutItems) == totalItems && scrollear)
                {
                    isLoading = false;
                    page_number += 20;
                    performPagination(iduser,token,page_number,Integer.parseInt(prod));
                }
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
                if (pedidoList.size() > 0)
                {
                    getDetallePedido(iduser,token,pedidoList.get(0).getIdpedido());
                }
                else
                {
                    Intent intent = new Intent(ProductoActivity.this,CarritoActivity.class);
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
                                    MainActivity.getInstance().finish();
                                    LoginManager.getInstance().logOut();
                                    Intent login = new Intent(ProductoActivity.this,LoginActivity.class);
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
                                    LoginManager.getInstance().logOut();
                                    Intent login = new Intent(ProductoActivity.this,LoginActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.e("TIPO",prod);
                getSearchData(iduser,token,String.valueOf(searchView.getQuery()),prod);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        return true;
    }
    //------------------------------------------WS------------------------------------------------------
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
                        TextSliderView textSliderView = new TextSliderView(ProductoActivity.this);
                        // initialize a SliderLayout
                        textSliderView
                                .description(name)
                                .image(url_maps.get(name))
                                .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                .setOnSliderClickListener(ProductoActivity.this);

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
                    sliderLayout.addOnPageChangeListener(ProductoActivity.this);
                }
            }

            @Override
            public void onFailure(Call<PromocionResponse> call, Throwable t) {

            }
        });
    }

    public void getSucPromMeds1(String iduser, String token, int page, int n1)
    {
        dialog = new ProgressDialog(ProductoActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ProductoResponse> callT = apiService.getSucPromMeds1(iduser,token,page,n1);

        callT.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                final ProductoResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if (Res.getErrorCode() == 0)
                {
                    List<Producto> productos = Res.getMsg();
                    productoAdapter = new ProductoAdapter(ProductoActivity.this,productos,ProductoActivity.this);
                    recyclerView.setAdapter(productoAdapter);

                    if (imgEmpty.getVisibility() == View.VISIBLE)
                    {
                        imgEmpty.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.GONE);
                    }
                }
                else if(Res.getErrorCode() == 3)
                {
                    imgEmpty.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.VISIBLE);
                }
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Log.e("Error",""+t);
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }
        });
    }



    private void performPagination(String iduser, String token, int page, int n1)
    {
        progressBar.setVisibility(View.VISIBLE);
        Call<ProductoResponse> callT = apiService.getSucPromMeds1(iduser,token,page,n1);

        callT.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                final ProductoResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if (Res.getErrorCode() == 0)
                {
                    List<Producto> productos = Res.getMsg();
                    productoAdapter.addProducto(productos);
                    Log.e("PAGINA", ""+page);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Log.e("Error",""+t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getSearchData(String iduser, String token, String nombre, String tipo)
    {
        dialog = new ProgressDialog(ProductoActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        scrollear = false;

        Call<ProductoResponse> callT = apiService.getSearchData(iduser,token,nombre,tipo);

        callT.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                final ProductoResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if (Res.getErrorCode() == 0)
                {
                    List<Producto> productos = Res.getMsg();
                    searchAdapter = new ProductoAdapter(ProductoActivity.this,productos,ProductoActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                    if (recyclerView.getVisibility() == View.GONE)
                    {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    if (imgEmpty.getVisibility() == View.VISIBLE)
                    {
                        imgEmpty.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.GONE);
                    }
                }
                else if(Res.getErrorCode() == 3)
                {
                    imgEmpty.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    txtEmpty.setText("No hay productos que coincidan :(");
                }
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<ProductoResponse> call, Throwable t) {
                Log.e("Error",""+t);
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }
        });

    }

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (!scrollear)
        {
            scrollear = true;
            if(recyclerView.getVisibility() == View.GONE)
            {
                recyclerView.setVisibility(View.VISIBLE);
            }
            if (imgEmpty.getVisibility() == View.VISIBLE)
            {
                imgEmpty.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.GONE);
            }
            getSucPromMeds1(iduser,token,0,Integer.parseInt(prod));
        }
        else
        {
            Intent intent = new Intent(ProductoActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onItemClick(Producto item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ProductoActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_agregar_carrito, null);
        alertDialogBuilder.setView(dialogView);


        ImageView imgProd                   = dialogView.findViewById(R.id.imgProd);
        TextView nombreProd                 = dialogView.findViewById(R.id.nombreProd);
        TextView precioProd                = dialogView.findViewById(R.id.precioProd);
        TextView descripcionProd = dialogView.findViewById(R.id.descripcionProd);
        final EditText editComentario = dialogView.findViewById(R.id.editComentario);
        final NumberPicker pickerCantidad = dialogView.findViewById(R.id.pickerCantidad);
        Button Agregar = dialogView.findViewById(R.id.Agregar);

        Glide.with(ProductoActivity.this)
                .load(item.getImagen())
                .into(imgProd);

        nombreProd.setText(item.getNombre());
        precioProd.setText("$"+item.getPrecio());
        descripcionProd.setText(item.getDescripcion());

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int cantidad = pickerCantidad.getValue();
                if(cantidad <= 0){
                    Toast.makeText(ProductoActivity.this, "Agregue una cantidad", Toast.LENGTH_SHORT).show();
                }else{
                    Carrito carrito = new Carrito(
                            item.getId(),
                            item.getNombre(),
                            item.getDescripcion(),
                            item.getCreacion(),
                            item.getImagen(),
                            item.getDosis(),
                            "",
                            item.getCantidad_unidad(),
                            item.getPresentacion(),
                            item.getPrecio(),
                            item.getPrecio_promo(),
                            cantidad,
                            editComentario.getText().toString()+""
                    );
                    carritodb.insert(carrito);
                    Toast.makeText(ProductoActivity.this, "Se ha agregado al carrito", Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void getDetallePedido(String iduser, String token,String idpedido){
        dialog = new ProgressDialog(ProductoActivity.this);
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
                        Intent intent = new Intent(ProductoActivity.this,CarritoActivity.class);
                        startActivity(intent);
                    }
                    else
                        {
                        Intent a = new Intent(ProductoActivity.this, EstadoPedidoActivity.class);
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
