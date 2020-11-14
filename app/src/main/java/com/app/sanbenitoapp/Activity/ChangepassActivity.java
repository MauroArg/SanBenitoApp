package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sanbenitoapp.Model.Password.NewBody;
import com.app.sanbenitoapp.Model.Password.NewResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;

public class ChangepassActivity extends AppCompatActivity {

    String email;
    EditText txtPassword;
    EditText txtConfPassword;
    Button btPass;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        Window window = ChangepassActivity.this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorAccentC));

        btPass = findViewById(R.id.btPass);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfPassword = findViewById(R.id.txtConfPassword);
        email = getIntent().getStringExtra("email");

        btPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Por favor complete todos los campos";
                CharSequence error2 = "La contraseña no coincide";

                if (txtPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                    txtPassword.setHintTextColor(getResources().getColor(R.color.red));
                    txtPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtPassword.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (txtConfPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                    txtConfPassword.setHintTextColor(getResources().getColor(R.color.red));
                    txtConfPassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtConfPassword.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else if (!txtPassword.getText().toString().equals(txtConfPassword.getText().toString()))
                {
                    Toast.makeText(context,error2,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String password = txtPassword.getText().toString();
                    setNewPassword(email,password);
                }


            }
        });
    }

    public void setNewPassword(String correo, String password)
    {
        dialog = new ProgressDialog(ChangepassActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        NewBody body = null;

        try {
            body = new NewBody(
                    ""+correo,
                    ""+SHA1(password)
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<NewResponse> callT = apiService.setNewPassword(body);

        callT.enqueue(new Callback<NewResponse>() {
            @Override
            public void onResponse(Call<NewResponse> call, Response<NewResponse> response) {
                final NewResponse Res =response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    Intent intent = new Intent(ChangepassActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(ChangepassActivity.this,"Error en el servidor",Toast.LENGTH_SHORT).show();
                }

                else if (Res.getErrorCode() == 6)
                {
                    Toast.makeText(ChangepassActivity.this, "El correo no existe en el sistema", Toast.LENGTH_SHORT).show();
                }
                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<NewResponse> call, Throwable t) {
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


    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ChangepassActivity.this,"No puede salir de esta pantalla",Toast.LENGTH_SHORT).show();
    }
}