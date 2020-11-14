package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Pedido.PedidoBody;
import com.app.sanbenitoapp.Model.Pedido.PedidoResponse;
import com.app.sanbenitoapp.Model.Pedido.SavePedidoResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class EstadoPedidoActivity extends AppCompatActivity {

    String token;
    String iduser;

    String idpedido,tiempo_espera,codigo_envio,latitud,longitud,viene,tipo;

    TextView pedido,time,titupedido;
    Button btUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        db = Room.databaseBuilder(this,
                AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb = db.getUserDao();

        token = registrodb.getRegistro().get(0).getToken();
        iduser = userdb.getUser().get(0).getIdusuario_app();

        getActivePedidos(iduser,token);

        Intent i = getIntent();
        idpedido = i.getExtras().getString("idpedido");
        tiempo_espera = i.getExtras().getString("tiempo_espera");
        codigo_envio = i.getExtras().getString("codigo_envio");
        latitud = i.getExtras().getString("latitud");
        longitud = i.getExtras().getString("longitud");
        viene = i.getExtras().getString("viene");
        tipo = i.getExtras().getString("tipo");

        pedido = (TextView) findViewById(R.id.pedido);
        titupedido = (TextView) findViewById(R.id.titupedido);
        time = (TextView) findViewById(R.id.time);

        btUpdate = findViewById(R.id.btUpdate);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivePedidos(iduser,token);
            }
        });

        if(viene.equals("0")){
            titupedido.setText("Tu pedido ha sido procesado!");
        }else{
            titupedido.setText("Ya tienes un pedido en proceso!");
        }
        pedido.setText(""+codigo_envio);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent a = new Intent(EstadoPedidoActivity.this, MainActivity.class);
                startActivity(a);
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent a = new Intent(EstadoPedidoActivity.this,MainActivity.class);
            a.putExtra("tipo",""+tipo);
            startActivity(a);
            finish();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void getActivePedidos(String iduser, String token)
    {
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        PedidoBody body = null;

        try
        {
            body = new PedidoBody(
                    "" +iduser,
                    "" + token
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("body", json+"");

        Call<PedidoResponse> callT = apiService.getActivePedidos(body);

        callT.enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                final PedidoResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("Res", json+"");

                if (Res.getErrorCode() == 0)
                {
                    if (Res.getMsg().get(0).getIdestados_pedido().equals("1"))
                    {

                        time.setText("Orden ha sido generada por usuario pero no ha sido aceptada por Farmacia");
                    }
                    else if (Res.getMsg().get(0).getIdestados_pedido().equals("2"))
                    {
                        if (Res.getMsg().get(0).getMin_restantes().equals("0"))
                        {

                            time.setText("Su pedido llegara pronto");
                        }
                        else
                        {

                            time.setText(Res.getMsg().get(0).getMin_restantes() +" min aprox\n tiempo de entrega\n puede variar");
                        }
                    }
                    else if (Res.getMsg().get(0).getIdestados_pedido().equals("3"))
                    {
                        if (Res.getMsg().get(0).getMin_restantes().equals("0"))
                        {

                            time.setText("Su pedido llegara pronto");
                        }
                        else
                        {

                            time.setText(Res.getMsg().get(0).getMin_restantes() +" min aprox\n tiempo de entrega\n puede variar");
                        }
                    }
                    else if (Res.getMsg().get(0).getIdestados_pedido().equals("4"))
                    {
                        time.setText("Orden ha sido entregada al cliente");
                    }
                    else if (Res.getMsg().get(0).getIdestados_pedido().equals("6"))
                    {
                        time.setText("Tu orden a sido cancelada");
                    }
                }
                else
                {
                    time.setText("Su pedido esta pendiente te confirmacion");
                }
            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {
                if (!call.isCanceled())
                {
                    Log.e("Error",""+t);
                }
            }
        });
    }
}