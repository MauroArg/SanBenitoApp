package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sanbenitoapp.Model.Password.VeryBody;
import com.app.sanbenitoapp.Model.Password.VeryResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;

public class ConfActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private EditText num3;
    private EditText num4;
    private EditText num5;

    private Button btContinue;

    String email;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);
        Window window = ConfActivity.this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorAccentC));

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        btContinue = findViewById(R.id.btContinue);

        email = getIntent().getStringExtra("email");

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Por favor completa el codigo";
                int duration = Toast.LENGTH_SHORT;
                if (num1.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num2.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num3.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num4.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else if (num5.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                }
                else
                {
                    String codigo = num1.getText().toString()+num2.getText().toString()+num3.getText().toString()+num4.getText().toString()+num5.getText().toString();
                    veryCodeRecoveryPassword(email,codigo);

                }

            }
        });

        num1.requestFocus();

        num2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num2.getText().toString().length()!=0){
                        num2.setText("");
                    }
                    else
                    {
                        num1.setText("");
                        num1.requestFocus();
                    }
                }
                return false;
            }
        });

        num3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num3.getText().toString().length()!=0){
                        num3.setText("");
                    }
                    else
                    {
                        num2.setText("");
                        num2.requestFocus();
                    }
                }
                return false;
            }
        });

        num4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num4.getText().toString().length()!=0){
                        num4.setText("");
                    }
                    else
                    {
                        num3.setText("");
                        num3.requestFocus();
                    }
                }
                return false;
            }
        });

        num5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL)
                {

                    if (num5.getText().toString().length()!=0){
                        num5.setText("");
                    }
                    else
                    {
                        num4.setText("");
                        num4.requestFocus();
                    }
                }
                return false;
            }
        });

        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num1.getText().toString().length()>0){
                    num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num2.getText().toString().length()>0){
                    num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (num3.getText().toString().length()>0){
                    num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



                if (num4.getText().toString().length()>0){
                    num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void veryCodeRecoveryPassword(String correo, String codigo)
    {
        dialog = new ProgressDialog(ConfActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        VeryBody body = null;

        try {
            body = new VeryBody(
                    ""+correo,
                    ""+codigo
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<VeryResponse> callT = apiService.veryCodeRecoveryPassword(body);

        callT.enqueue(new Callback<VeryResponse>() {
            @Override
            public void onResponse(Call<VeryResponse> call, Response<VeryResponse> response) {
                final VeryResponse Res =response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    Intent intent = new Intent(ConfActivity.this, ChangepassActivity.class);
                    intent.putExtra("email",""+email);
                    startActivity(intent);
                }

                else if (Res.getErrorCode() == 5)
                {
                    Toast.makeText(ConfActivity.this, "Codigo Incorrecto", Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<VeryResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    //onError();
                    Log.e("Error",""+t);
                    if (dialog.isShowing()) {
                        dialog.hide();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ConfActivity.this,"No puede salir de esta pantalla",Toast.LENGTH_SHORT).show();
    }
}