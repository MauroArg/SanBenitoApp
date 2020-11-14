package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sanbenitoapp.Model.Password.TokenBody;
import com.app.sanbenitoapp.Model.Password.TokenResponse;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Rest.ApiClient;
import com.app.sanbenitoapp.Rest.ApiInterface;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.sanbenitoapp.Util.GlobalVariables.apiService;

public class EmailPassRecoveryActivity extends AppCompatActivity {

    private Button btBackup;
    private EditText txtEmail;

    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_pass_recovery);
        Window window = EmailPassRecoveryActivity.this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorAccentC));

        btBackup = findViewById(R.id.btBackup);
        txtEmail = findViewById(R.id.txtEmail);
        btBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence error = "Por favor complete todos los campos";
                int duration = Toast.LENGTH_SHORT;

                String email = txtEmail.getText().toString();

                if (txtEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(context,error,duration).show();
                    txtEmail.setHintTextColor(getResources().getColor(R.color.red));
                    txtEmail.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtEmail.setHintTextColor(getResources().getColor(R.color.colorGray));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else
                {
                    getNewRecoveryToken(email);
                }

            }
        });
    }

    public void getNewRecoveryToken(String email)
    {
        dialog = new ProgressDialog(EmailPassRecoveryActivity.this);
        dialog.setMessage("Cargando");
        dialog.show();
        apiService = ApiClient.getApiClient().create(ApiInterface.class);

        TokenBody body = null;

        try {
            body = new TokenBody(
                    ""+email
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        String json = new Gson().toJson(body);
        Log.i("bodyRes", json+"");

        Call<TokenResponse> callT = apiService.getNewRecoveryToken(body);

        callT.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                final TokenResponse Res = response.body();
                String json = new Gson().toJson(Res);
                Log.i("bodyRes", json + "");

                if (Res.getErrorCode() == 0)
                {
                    String email = txtEmail.getText().toString();
                    Intent intent = new Intent(EmailPassRecoveryActivity.this,ConfActivity.class);
                    intent.putExtra("email",""+email);
                    startActivity(intent);
                    finish();
                }
                else if (Res.getErrorCode() == 2)
                {
                    Toast.makeText(EmailPassRecoveryActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                else if (Res.getErrorCode() == 6)
                {
                    Toast.makeText(EmailPassRecoveryActivity.this, "Email does not exist", Toast.LENGTH_SHORT).show();
                }

                if (dialog.isShowing())
                {
                    dialog.hide();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
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
    public void onBackPressed()
    {
        Intent intent = new Intent(EmailPassRecoveryActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}