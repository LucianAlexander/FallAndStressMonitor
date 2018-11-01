package com.example.lux.fallandstressmonitor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class putAssistitoRegistrazione extends StringRequest {

    private static final String REGISTER_ASSISTITO_URL = "http://192.168.0.4/server/registrazioneAssistito.php";
    private Map<String,String> params;

    public putAssistitoRegistrazione(String nome, String cognome, String eta, String tel, String carid, Response.Listener<String>listener){

        super(Method.POST, REGISTER_ASSISTITO_URL,listener,null);
        params = new HashMap<>();
        params.put("nome",nome);
        params.put("cognome",cognome);
        params.put("eta",eta);
        params.put("telefono",tel);
        params.put("carid",carid);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
