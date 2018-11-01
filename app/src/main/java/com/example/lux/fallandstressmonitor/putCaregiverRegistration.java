package com.example.lux.fallandstressmonitor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class putCaregiverRegistration extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://192.168.0.4/server/registrazioneCaregiver.php";
    private Map<String,String> params;

    public putCaregiverRegistration(String nome, String cognome, String tel, String email, String password, String token, Response.Listener<String>listener){

        super(Method.POST, REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("nome",nome);
        params.put("cognome",cognome);
        params.put("tel",tel);
        params.put("email",email);
        params.put("password",password);
        params.put("token",token);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}