package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.RegistroBody;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.app.sanbenitoapp.Util.GlobalVariables;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class RegistroActivity extends AppCompatActivity {

    //Variables de items graficos
    EditText txtNombre;
    EditText txtApellido;
    EditText txtCorreo;
    EditText txtTelefono1;
    EditText txtTelefono2;
    EditText txtPassword;
    EditText ttxtConfirmPassword;
    Button   btnRegistro;

    //Rueda de carga
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();

        txtNombre           = findViewById(R.id.txtNombre);
        txtApellido         = findViewById(R.id.txtApellido);
        txtCorreo           = findViewById(R.id.txtCorreo);
        txtTelefono1        = findViewById(R.id.txtTelefono1);
        txtTelefono2        = findViewById(R.id.txtTelefono2);
        txtPassword         = findViewById(R.id.txtPassword);
        ttxtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegistro         = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre          = txtNombre.getText().toString();
                String apellido        = txtApellido.getText().toString();
                String correo          = txtCorreo.getText().toString();
                String telefono1       = txtTelefono1.getText().toString();
                String telefono2       = txtTelefono2.getText().toString();
                String password        = txtPassword.getText().toString();
                String confirmPassword = ttxtConfirmPassword.getText().toString();

                boolean cancel = false;
                View focusView = null;

                //Verifica contraseña
                if (!TextUtils.isEmpty(password) && !isPasswordValid(password))
                {
                    //mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = txtPassword;
                    Toast.makeText(RegistroActivity.this, "Ingrese una contraseña valida", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verifica nombre
                if (TextUtils.isEmpty(nombre)) {
                    //mEmailView.setError(getString(R.string.error_field_required));
                    focusView = txtNombre;
                    Toast.makeText(RegistroActivity.this, "Ingrese su nombre", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verifica apellido
                else if (TextUtils.isEmpty(apellido))
                {
                    //mEmailView.setError(getString(R.string.error_field_required));
                    focusView = txtApellido;
                    Toast.makeText(RegistroActivity.this, "Ingrese sus apellidos", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verficia correo vacio
                else if (TextUtils.isEmpty(correo))
                {
                    //mEmailView.setError(getString(R.string.error_field_required));
                    focusView = txtCorreo;
                    Toast.makeText(RegistroActivity.this, "Ingrese un correo electrónico", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verifica email valido
                else if (!isEmailValid(correo))
                {
                    //mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = txtCorreo;
                    Toast.makeText(RegistroActivity.this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verifica numero de telefono
                else if (TextUtils.isEmpty(telefono1) && TextUtils.isEmpty(telefono2))
                {
                    //mEmailView.setError(getString(R.string.error_field_required));
                    focusView = txtTelefono1;
                    Toast.makeText(RegistroActivity.this, "Ingrese un número telefónico", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                //Verifica igualdad de contraseñas
                if (!password.equals(confirmPassword))
                {
                    //mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = ttxtConfirmPassword;
                    Toast.makeText(RegistroActivity.this, "Debe confirmar el password", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    setRegistration(nombre,apellido,correo,password,telefono1,telefono2);

                }


            }
        });


    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
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

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    //---------------------------------------------------------------------------------WS-------------------------------

    public void setRegistration (String nombre, String apellido, String correo, String password, String telefono1, String telefono2)
    {
        progressDialog = new ProgressDialog(RegistroActivity.this);
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);
        RegistroBody body = null;
        try
        {
            body = new RegistroBody(
                    ""+nombre,
                    ""+apellido,
                    ""+correo,
                    ""+SHA1(password),
                    "",
                    "",
                    ""+telefono1,
                    ""+telefono2,
                    "",
                    "1");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        String json = new Gson().toJson(body);
        Log.i("bodyRES", json + "");

        Call<RegistroResponse> callT = apiService.setRegistration(body);

        callT.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                final RegistroResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0)
                {
                    Registro registro = (Registro) Res.getMsg();
                    invitadodb.deleteAll();
                    registrodb.deleteAll();
                    userdb.deleteAll();
                    registrodb.insert(registro);
                    userdb.insert(registro.getUserData().get(0));

                    Toast.makeText(RegistroActivity.this, "Registrado exitósamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistroActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else if(Res.getErrorCode() == 2)
                {
                    Log.e("Error","Error");
                }
                else if(Res.getErrorCode() == 3)
                {
                    Toast.makeText(RegistroActivity.this, "Ya existe un usuario con este correo, por favor inténtelo de nuevo", Toast.LENGTH_SHORT).show();
                }

                if (progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
                if (!call.isCanceled())
                {
                    Log.e("Error",""+t);
                    if (progressDialog.isShowing())
                    {
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }
}
