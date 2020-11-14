package com.app.sanbenitoapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;

import com.app.sanbenitoapp.Dao.AppDataBase;
import com.app.sanbenitoapp.Dao.RegistroDao;
import com.app.sanbenitoapp.Dao.UserDao;
import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.R;
import com.app.sanbenitoapp.Util.GlobalVariables;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.sanbenitoapp.Util.GlobalVariables.db;
import static com.app.sanbenitoapp.Util.GlobalVariables.registroList;
import static com.app.sanbenitoapp.Util.GlobalVariables.registrodb;
import static com.app.sanbenitoapp.Util.GlobalVariables.userdb;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "HASH";

    String type;

    private long splashDelay = 3000;

    String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = SplashActivity.this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorAccentC));

        //Instancia variabes room de GlobalVariables
        db = Room.databaseBuilder(this,
                AppDataBase.class, "db_sanbenito")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        if (getIntent().getExtras() != null)
        {
            Intent i = getIntent();
            tipo = i.getExtras().getString("tipo");
            Log.e("TIPO", " "+tipo);
            if (tipo != null)
            {
                if (tipo.equals("1"))
                {
                    Intent intent = new Intent(SplashActivity.this,SucursalActivity.class);
                    startActivity(intent);
                }
                else if(tipo.equals("2"))
                {
                    Intent intent = new Intent(SplashActivity.this,PromocionActivity.class);
                    startActivity(intent);
                }
                else if(tipo.equals("3"))
                {
                    Intent intent = new Intent(SplashActivity.this, ProductoActivity.class);
                    intent.putExtra("Prod","1");
                    startActivity(intent);
                }
                else if(tipo.equals("4"))
                {
                    Intent intent = new Intent(SplashActivity.this, ProductoActivity.class);
                    intent.putExtra("Prod","3");
                    startActivity(intent);
                }
                else if(tipo.equals("7"))
                {
                    Intent intent = new Intent(SplashActivity.this,ChatActivity.class);
                    startActivity(intent);
                }

            }
        }

        registrodb = db.getRegistroDao();
        userdb = db.getUserDao();
        registroList = registrodb.getRegistro();

        Log.e("LIST", "LIST" + registroList.size());



        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                //Verifica si hay datos de usuario en la la tabla de registro
                if(registroList.size()>0)
                {
                    //Si tiene manda a main porque hay una sesion iniciada
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    //De lo contrario envia a login
                    Intent a = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(a);
                    finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);

        printHashKey(this);
    }
    public  void printHashKey(Context pContext)
    {
        try
        {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "printHashKey()", e);
        }
    }
}
