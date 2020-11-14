package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanbenitoapp.Adapter.ProductoAdapter;
import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Agenda.AgendaResponse;
import com.app.sanbenitoapp.Model.Agenda.SetAgendaBody;
import com.app.sanbenitoapp.Model.Producto.Producto;
import com.app.sanbenitoapp.Model.Producto.ProductoResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.carritodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.pedidodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class CrearAgendaActivity extends AppCompatActivity implements  ProductoAdapter.ItemListener{

    private DatePickerDialog fromDatePickerDialog;
    EditText                 txtFromdate,txtDosis,txtMedicinas;
    TextView                 txtEmpty;
    ImageView                imgEmpty;
    private SimpleDateFormat dateFormatter;
    ProgressDialog           dialog;
    RecyclerView             listaAgendaMed;
    RelativeLayout           medicinasLayout;
    GridLayoutManager        layoutManager;
    Button                   btnGuardarAgenda;
    ProductoAdapter          searchAdapter;

    String token,iduser;
    String idMedicamento = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_agenda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Agregar Agenda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        txtMedicinas     = findViewById(R.id.txtMedicinas);
        txtFromdate      = findViewById(R.id.etxt_fromdate);
        txtDosis         = findViewById(R.id.textDosis);
        btnGuardarAgenda = findViewById(R.id.btnGuardarAgenda);
        listaAgendaMed   = findViewById(R.id.listaAgendaMed);
        txtEmpty         = findViewById(R.id.txtEmpty);
        imgEmpty         = findViewById(R.id.imgEmpty);
        medicinasLayout  = findViewById(R.id.medicinasLayout);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        carritodb  = db.getCarritoDao();
        invitadodb = db.getInvitadoDao();
        pedidodb   = db.getPedidoDao();

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

        layoutManager = new GridLayoutManager(this,1);
        listaAgendaMed.setHasFixedSize(true);
        listaAgendaMed.setLayoutManager(layoutManager);

        txtMedicinas.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction()==KeyEvent.ACTION_DOWN)&&(i == KeyEvent.KEYCODE_ENTER))
                {
                    txtMedicinas.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtMedicinas.getWindowToken(), 0);
                    if (!txtMedicinas.getText().toString().isEmpty())
                    {
                        if (medicinasLayout.getVisibility() == View.GONE)
                        {
                            medicinasLayout.setVisibility(View.VISIBLE);
                        }
                        getSearchData(iduser,token,txtMedicinas.getText().toString());

                    }
                    else
                    {
                        if (medicinasLayout.getVisibility() == View.VISIBLE)
                        {
                            medicinasLayout.setVisibility(View.GONE);
                        }
                        Toast.makeText(CrearAgendaActivity.this,"Por favor ingrese  un valor para la busqueda",Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(CrearAgendaActivity.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtFromdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        txtFromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                fromDatePickerDialog.show();
            }
        });

        btnGuardarAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha = txtFromdate.getText().toString();
                String dosis = txtDosis.getText().toString();
                if (invitadodb.getInvitado().size() > 0)
                {
                    Log.e("INVITED","INVITED");
                    final AlertDialog.Builder alerta2 = new AlertDialog.Builder(
                            CrearAgendaActivity.this);

                    alerta2.setTitle("Funcion no disponible en modo invitado")
                            .setMessage("Debe iniciar sesion antes de enviar un pedido")
                            .setCancelable(false)
                            .setPositiveButton("Iniciar Sesion", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog2, int which) {
                                    Intent intent = new Intent(CrearAgendaActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    CrearAgendaActivity.this.finish();
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
                else if(idMedicamento.equals("")){
                    Toast.makeText(CrearAgendaActivity.this, "Favor Seleccionar un medicamento", Toast.LENGTH_SHORT).show();
                }else if(dosis.equals("")){
                    Toast.makeText(CrearAgendaActivity.this, "Escriba la dosis de su medicamento", Toast.LENGTH_SHORT).show();
                }else if(fecha.equals("")){
                    Toast.makeText(CrearAgendaActivity.this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();
                }else{
                    setAgenda(token,iduser,idMedicamento,fecha,dosis);
                }
            }
        });

    }
    private void getSearchData(String iduser, String token, String nombre)
    {
        dialog = new ProgressDialog(CrearAgendaActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        Call<ProductoResponse> callT = apiService.getSearchData(iduser,token,nombre,"1");

        callT.enqueue(new Callback<ProductoResponse>() {
            @Override
            public void onResponse(Call<ProductoResponse> call, Response<ProductoResponse> response) {
                final ProductoResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if (Res.getErrorCode() == 0)
                {
                    List<Producto> productos = Res.getMsg();
                    searchAdapter = new ProductoAdapter(CrearAgendaActivity.this,productos,CrearAgendaActivity.this);
                    listaAgendaMed.setAdapter(searchAdapter);
                    if (listaAgendaMed.getVisibility() == View.GONE)
                    {
                        listaAgendaMed.setVisibility(View.VISIBLE);
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
                    listaAgendaMed.setVisibility(View.GONE);
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

    public void setAgenda(String token, String iduser,String idmedicamento,String fecha,String dosis){
        dialog = new ProgressDialog(CrearAgendaActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        SetAgendaBody body = new SetAgendaBody(
                iduser+"",
                token+"",
                idmedicamento+"",
                fecha+"",
                dosis+"",
                "1"
        );

        String json = new Gson().toJson(body);
        Log.i("body", json + "");

        Call<AgendaResponse> callT = apiService.setAgenda(body);

        callT.enqueue(new Callback<AgendaResponse>() {

            @Override
            public void onResponse(Call<AgendaResponse> call, retrofit2.Response<AgendaResponse> response) {
                final AgendaResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0){

                    Intent a = new Intent(CrearAgendaActivity.this, AgendaActivity.class);
                    startActivity(a);
                    finish();
                    Toast.makeText(CrearAgendaActivity.this, "Agenda Guardada", Toast.LENGTH_SHORT).show();

                }else if(Res.getErrorCode() == 2)
                {
                }

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(final Call<AgendaResponse> call, final Throwable t) {
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
    public void onItemClick(Producto item) {

        txtMedicinas.setText(item.getNombre());
        idMedicamento = item.getId();
        medicinasLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (medicinasLayout.getVisibility() == View.VISIBLE)
            {
                medicinasLayout.setVisibility(View.GONE);
            }
            else
            {
                Intent a = new Intent(CrearAgendaActivity.this, AgendaActivity.class);
                startActivity(a);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed()
    {
        if (medicinasLayout.getVisibility() == View.VISIBLE)
        {
            medicinasLayout.setVisibility(View.GONE);
        }
        else
        {
            Intent a = new Intent(CrearAgendaActivity.this, AgendaActivity.class);
            startActivity(a);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (medicinasLayout.getVisibility() == View.VISIBLE)
                {
                    medicinasLayout.setVisibility(View.GONE);
                }
                else
                {
                    Intent a = new Intent(CrearAgendaActivity.this, AgendaActivity.class);
                    startActivity(a);
                    finish();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}