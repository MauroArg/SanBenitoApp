package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Carrito.Carrito;
import com.app.sanbenitoapp.Model.Pedido.MedicamentoPedido;
import com.app.sanbenitoapp.Model.Pedido.MedicamentoSolicitado;
import com.app.sanbenitoapp.Model.Pedido.Pedido;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoBody;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.app.sanbenitoapp.Util.GPSTracker;
import com.app.sanbenitoapp.Util.GlobalVariables;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.app.sanbenitoapp.Activity.MainActivity.verifyStoragePermissions;
import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView imgLocationPinUp;
    GPSTracker gpstracker;
    private static final int REQUEST = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };

    Button ubicacion;
    Marker marker;


    String token,iduser;

    ProgressDialog dialog;

    List<Carrito> carritos;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        gpstracker = new GPSTracker(MapsActivity.this);
        verifyStoragePermissions(MapsActivity.this);

        db = Room.databaseBuilder(MapsActivity.this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        carritodb  = db.getCarritoDao();
        pedidodb   = db.getPedidoDao();


        token  = registrodb.getRegistro().get(0).getToken();
        iduser = userdb.getUser().get(0).getIdusuario_app();


        imgLocationPinUp = findViewById(R.id.imgLocationPinUp);
        ubicacion        = findViewById(R.id.ubicacion);

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText taskEditText = new EditText(MapsActivity.this);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        MapsActivity.this);

                alertDialogBuilder.setTitle("San Benito");

                alertDialogBuilder
                        .setMessage("Brindanos una dirección de referencia:")
                        .setCancelable(false)
                        .setView(taskEditText)
                        .setPositiveButton("Enviar Pedido",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String direccion = String.valueOf(taskEditText.getText());

                                if(direccion.equals("")){
                                    Toast.makeText(MapsActivity.this, "Ingrese una dirección como referencia", Toast.LENGTH_SHORT).show();
                                }else{
                                    String lat = ""+marker.getPosition().latitude;
                                    String lon = ""+marker.getPosition().longitude;
                                    Log.e("latlon",lat+"   "+lon);
                                    enviarPedido(iduser,token,lat,lon,"",direccion);
                                    dialog.cancel();
                                }


                            }
                        });
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(gpstracker.getLatitude(), gpstracker.getLongitude());


        marker  = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.mipmap.picker)).draggable(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,18f));
        mMap.setMyLocationEnabled(true);


        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                mMap.clear();
                imgLocationPinUp.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                MarkerOptions markeroption = new MarkerOptions().position(mMap.getCameraPosition().target).icon(BitmapDescriptorFactory.fromResource(R.mipmap.picker));
                marker  = mMap.addMarker(markeroption);

                imgLocationPinUp.setVisibility(View.GONE);

            }
        });
    }

    public boolean verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.i("Click permision1",""+permission);
        Log.i("Click permision2",""+ PackageManager.PERMISSION_GRANTED);

        boolean b;
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS,
                    REQUEST

            );

            b = false;
        }else{

            b = true;
        }
        return b;
    }

    public void enviarPedido(String iduser, String token,String latitud,String longitud,String comentario,String direccion){
        dialog = new ProgressDialog(MapsActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        List<MedicamentoPedido> medicamentoPedidos =new ArrayList<>();

        carritos = carritodb.getCarrito();

        for (int i = 0; i < carritos.size(); i++) {
            medicamentoPedidos.add(new MedicamentoPedido(carritos.get(i).getId()+"",""+carritos.get(i).getCantidad()));
        }



        SavePedidoBody body = new SavePedidoBody(
                ""+iduser,
                ""+token,
                ""+latitud,
                ""+longitud,
                ""+comentario,
                ""+direccion,
                medicamentoPedidos

        );

        String json = new Gson().toJson(body);
        Log.i("body", json + "");

        Call<SavePedidoResponse> callT = apiService.savePedido(body);

        callT.enqueue(new Callback<SavePedidoResponse>() {

            @Override
            public void onResponse(Call<SavePedidoResponse> call, retrofit2.Response<SavePedidoResponse> response) {
                final SavePedidoResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0){
                    carritodb.deleteAll();

                    pedidodb.insert(Res.getMsg().get(0));


                    Intent a = new Intent(MapsActivity.this,EstadoPedidoActivity.class);
                    a.putExtra("idpedido",""+Res.getMsg().get(0).getIdpedido());
                    a.putExtra("tiempo_espera",""+Res.getMsg().get(0).getTiempo_espera());
                    a.putExtra("codigo_envio",""+Res.getMsg().get(0).getCodigo_envio());
                    a.putExtra("latitud",""+Res.getMsg().get(0).getLatitud());
                    a.putExtra("longitud",""+Res.getMsg().get(0).getLongitud());
                    a.putExtra("viene", "0" );
                    startActivity(a);
                    finish();

                }else if(Res.getErrorCode() == 4){
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MapsActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("San Benito");

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Ya tiene una orden activa")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    Intent a = new Intent(MapsActivity.this,MainActivity.class);
                                    a.putExtra("tipo","0");
                                    startActivity(a);
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(MapsActivity.this,CarritoActivity.class);
            startActivity(a);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}