package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Producto.Producto;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.app.sanbenitoapp.Model.TokenPush.UpdateTokenPushBody;
import com.app.sanbenitoapp.Model.TokenPush.UpdateTokenPushResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registroList;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class MainActivity extends AppCompatActivity {

    ImageView btnMedicina;
    ImageView btnConvivencia;
    ImageView btnLibreria;
    ImageView btnSucursales;

    static MainActivity mainActivity;

    String token;
    String tokenPush;
    String iduser;
    String correo;
    String tipo;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMedicina = findViewById(R.id.btnMedicina);
        btnConvivencia = findViewById(R.id.btnConvivencia);
        btnLibreria = findViewById(R.id.btnLibreria);
        btnSucursales = findViewById(R.id.btnSucursales);

        mainActivity = this;

        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();
        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        pedidodb   = db.getPedidoDao();
        invitadodb = db.getInvitadoDao();

        if (getIntent().getExtras() != null)
        {
            Intent i = getIntent();
            tipo = i.getExtras().getString("tipo");
            Log.e("TIPO", " "+tipo);
            if (tipo != null)
            {
                if (tipo.equals("1"))
                {
                    Intent intent = new Intent(MainActivity.this,SucursalActivity.class);
                    startActivity(intent);
                }
                else if(tipo.equals("2"))
                {
                    Intent intent = new Intent(MainActivity.this,PromocionActivity.class);
                    startActivity(intent);
                }
                else if(tipo.equals("3"))
                {
                    Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
                    intent.putExtra("Prod","1");
                    startActivity(intent);
                }
                else if(tipo.equals("4"))
                {
                    Intent intent = new Intent(MainActivity.this,EstadoPedidoActivity.class);
                    startActivity(intent);
                }
                else if(tipo.equals("7"))
                {
                    Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                    startActivity(intent);
                }

            }
        }



        if (userdb.getUser().size() > 0)
        {

            correo = userdb.getUser().get(0).getCorreo();
            iduser = userdb.getUser().get(0).getCorreo();
        }

        if (registrodb.getRegistro().size() > 0)
        {
            Log.i("token de usuario",""+registrodb.getRegistro().get(0).getToken());
        }

        if (invitadodb.getInvitado().size() > 0)
        {
            Log.e("token de invitado",""+invitadodb.getInvitado().get(0).getToken());
        }

        verifyStoragePermissions(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String mToken = instanceIdResult.getToken();
                Log.e("fToken",mToken);

                if(registrodb.getRegistro().size() > 0 && registroList != null && registroList.size()>0){
                    tokenPush = registrodb.getRegistro().get(0).getTokenP();
                    iduser = userdb.getUser().get(0).getIdusuario_app();
                    token = registrodb.getRegistro().get(0).getToken();
                    Log.i("tokenp","tokenp "+tokenPush);
                    if(tokenPush == null){
                        tokenPush = "old";
                    }
                    updateTokenPush(iduser,token,mToken);

                }
            }
        });


        btnMedicina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
                intent.putExtra("Prod","1");
                startActivity(intent);
            }
        });

        btnLibreria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductoActivity.class);
                intent.putExtra("Prod","2");
                startActivity(intent);
            }
        });

        btnConvivencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ProductoActivity.class);
                intent.putExtra("Prod","3");
                startActivity(intent);
            }
        });

        btnSucursales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SucursalActivity.class);
                startActivity(intent);
            }
        });

    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE

            );
        }

    }

    //-----------------------------------------------------------WS-----------------------------------------------------
    public void updateTokenPush(String iduser, final String token, final String tokenOld)
    {
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        UpdateTokenPushBody body = new UpdateTokenPushBody(
                ""+iduser,
                ""+token,
                ""+tokenOld,
                "1"
        );

        String json = new Gson().toJson(body);
        Log.e("bodyRes",""+json);

        Call<UpdateTokenPushResponse> callT = apiService.updateTokenPush(body);

        callT.enqueue(new Callback<UpdateTokenPushResponse>() {
            @Override
            public void onResponse(Call<UpdateTokenPushResponse> call, Response<UpdateTokenPushResponse> response) {
                final UpdateTokenPushResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0){


                    if(!token.equals(tokenOld)){
                        Registro u = registroList.get(0);
                        u.setTokenP(token);

                        registrodb.update(u);
                    }

                }else if(Res.getErrorCode() == 2)
                {
                    Log.e("ERROR","ERROR");
                }
            }

            @Override
            public void onFailure(Call<UpdateTokenPushResponse> call, Throwable t)
            {
                if (!call.isCanceled()) {
                    //onError();
                    Log.e("Error",""+t);

                }
            }
        });
    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

}
