package com.app.sanbenitoapp.Adapter;

import android.util.Log;

import com.app.sanbenitoapp.Model.Registro.Registro;
import com.app.sanbenitoapp.Model.Registro.RegistroResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ContentAdapter implements JsonDeserializer<RegistroResponse> {

    private static final String KEY_URI = "errorCode";
    private static final String KEY_METHOD = "errorMessage";
    private static final String KEY_PARAMETERS = "msg";


    @Override
    public RegistroResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            RegistroResponse userResponse = new Gson().fromJson(json, RegistroResponse.class);
            JsonObject jsonObject = json.getAsJsonObject();
        Log.e("ERRROOOR","ENTROOOO "+jsonObject);
            if (jsonObject.has("msg")) {

                JsonElement elem = jsonObject.get("msg");

                if (elem != null && !elem.isJsonNull()) {
                    if(elem.isJsonPrimitive()){
                        //userResponse.setMessage(elem.getAsString());
                        Log.e("ERRROOOR","ENTROOOO STRING");
                    }else{
                        Log.e("ERRROOOR","ENTROOOO OBJETO "+elem);
                        Gson gson = new Gson();
                        Registro r = gson.fromJson( elem, Registro.class);
                        userResponse.setMsg(r);
                    }
                }
            }
            return userResponse;

    }


}