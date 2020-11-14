package com.app.sanbenitoapp.Rest;


import com.app.sanbenitoapp.Adapter.ContentAdapter;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by administrador on 29/8/17.
 */

public class ApiClient {

    //PRODUCCION
    public static final String BASE_URLF = "http://farmaciasanbenitoapp.com/API/v1/";
    public static final String BASE_URL = "http://165.227.12.178/API/v1/";

    //PRUEBA
//    public static final String BASE_URL = "http://157.230.135.154/API/v1/";

    private static Retrofit retrofit = null;
    private static Gson gson;


    public static Retrofit getApiClient() {

        gson = new GsonBuilder()
                .registerTypeAdapter(RegistroResponse.class, new ContentAdapter())
                .create();

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getApiClientF() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}
