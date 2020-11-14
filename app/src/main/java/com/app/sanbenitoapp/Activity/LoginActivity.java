package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Model.Login.LoginBody;
import com.app.sanbenitoapp.Model.Registro.FacebookBody;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.app.sanbenitoapp.Util.GlobalVariables;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;
import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitado;
import static com.app.sanbenitoapp.Util.GlobalVariables.invitadodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class LoginActivity extends AppCompatActivity {

    //Variables de Items graficos
    private EditText    txtCorreo;
    private EditText    txtContrasena;
    private TextView    tvForgot;
    private Button      btnIniciarSesion;
    private Button      btnRegistro;
    private LoginButton btnFacebook;
    private Button      btnInvitado;

    //Rueda de carga
    ProgressDialog dialog;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instancia base de datos
        db = Room.databaseBuilder(this, AppDataBase.class, "db_sanbenito").allowMainThreadQueries().build();

        registrodb = db.getRegistroDao();
        userdb     = db.getUserDao();
        invitadodb = db.getInvitadoDao();

        callbackManager = CallbackManager.Factory.create();

        //Instancia items
        txtCorreo        = findViewById(R.id.txtCorreo);
        txtContrasena    = findViewById(R.id.txtContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistro      = findViewById(R.id.btnRegistro);
        btnFacebook      = findViewById(R.id.btnFacebook);
        btnInvitado      = findViewById(R.id.btnInvitado);
        tvForgot         = findViewById(R.id.tvForgot);

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,EmailPassRecoveryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Cuando se presiona el texto de invitado
        btnInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inserta datos en la tabla de invitado
                invitadodb.insert(invitado);

                //Inicia el main activity
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Cuando presiona el boton de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(intent);
            }
        });

        //Cuando presiona el boton de login
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });


        btnFacebook.setPermissions("email");
        btnFacebook.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        btnFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Codigo
            }

            @Override
            public void onCancel() {
                //Codigo
            }

            @Override
            public void onError(FacebookException error) {
                //Codigo
            }
        });



        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Log.e("accessToken","a: "+accessToken);
                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void onStart() {
        super.onStart();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            Log.e("accessToken","a: "+accessToken);
            useLoginInformation(accessToken);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void useLoginInformation(final AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    //String fecha = object.getString("birthday");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Log.e("nombre","nombre "+ name);
                    Log.e("apellido","apellido "+ last_name);
                    Log.e("correo","correo "+ email);
                    Log.e("imagen","imagen "+ image);
                    Log.e("token","token "+ accessToken.getToken());

                    getDatosAccesoFacebook(email,name,last_name,accessToken.getToken());
                    //Log.e("entro","entro "+ fecha);
                    //postRegistro(email,name +" "+ last_name,"",""+accessToken.getToken(),"1",""+accessToken.getToken());
                    // displayName.setText(name);
                    // emailID.setText(email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200),first_name,last_name");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
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

    private void attemptLogin() {

        // Setea Errores
        txtCorreo.setError(null);
        txtContrasena.setError(null);

        // Asigna variables a texto de los campos
        String email = txtCorreo.getText().toString();
        String password = txtContrasena.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtContrasena.setError(getString(R.string.error_invalid_password));
            focusView = txtContrasena;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email))
        {
            txtCorreo.setError(getString(R.string.error_field_required));
            focusView = txtCorreo;
            cancel = true;
        } else if (!isEmailValid(email))
        {
            txtCorreo.setError(getString(R.string.error_invalid_email));
            focusView = txtCorreo;
            cancel = true;
        }

        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            getAccesData(email, password);
        }
    }

    //Valida longitud de la contrasena
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    //Valida formato del correo
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    //------------------------------------------------------WEB SERVICES-----------------------------------------//

    public void getDatosAccesoFacebook(String correo, String nombre, String apellido, String token)
    {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        FacebookBody body =new FacebookBody(
                ""+correo,
                ""+nombre,
                ""+apellido,
                "",
                ""+token,
                "",
                "",
                "",
                "1"
        );

        String json = new Gson().toJson(body);
        Log.i("bodyRESFace", json + "");

        Call<RegistroResponse> callT = apiService.getDatosAccesoFacebook(body);

        callT.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                final RegistroResponse res = response.body();

                String json = new Gson().toJson(res);
                Log.i("bodyRES", json + "");

                if(!json.equals("null"))
                {
                    if (res.getErrorCode() == 0 || res.getErrorCode() == 4)
                    {

                        Registro registro = (Registro) res.getMsg();
                        invitadodb.deleteAll();
                        registrodb.deleteAll();
                        userdb.deleteAll();
                        registrodb.insert(registro);
                        userdb.insert(registro.getUserData().get(0));

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (res.getErrorCode() == 2)
                {
                    Toast.makeText(LoginActivity.this, "No se encuentra el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
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
    public void onBackPressed()
    {
        Toast.makeText(LoginActivity.this,"No puede volver atras", Toast.LENGTH_SHORT).show();
    }

    public void getAccesData(String correo, String pass)
    {
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        LoginBody body = null;
        try
        {
            body = new LoginBody(
                    ""+correo,
                    ""+SHA1(pass));
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        Call<RegistroResponse> callT = apiService.getAccessData(body);
        callT.enqueue(new Callback<RegistroResponse>() {
            @Override
            public void onResponse(Call<RegistroResponse> call, Response<RegistroResponse> response) {
                final RegistroResponse Res = response.body();

                String json = new Gson().toJson(Res);
                Log.i("bodyRES", json + "");

                if( Res.getErrorCode() == 0)
                {

                    Registro r = (Registro) Res.getMsg();

                    registrodb.deleteAll();
                    userdb.deleteAll();
                    invitadodb.deleteAll();
                    registrodb.insert(r);
                    userdb.insert(r.getUserData().get(0));

                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if(Res.getErrorCode() == 2)
                {
                    Log.e("Error","Error");
                }
                else if(Res.getErrorCode() == 4)
                {
                    Toast.makeText(LoginActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
                else if(Res.getErrorCode() == 8)
                {
                    Toast.makeText(LoginActivity.this, "Usuario no ha realizado la validacion", Toast.LENGTH_SHORT).show();
                }
                else if(Res.getErrorCode() == 9)
                {
                    Toast.makeText(LoginActivity.this, "Usuario bloqueado", Toast.LENGTH_SHORT).show();
                }

                if (dialog.isShowing())
                {
                    dialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<RegistroResponse> call, Throwable t) {
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
