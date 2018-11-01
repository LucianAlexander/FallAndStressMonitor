package com.example.lux.fallandstressmonitor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getLogInRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://192.168.0.4/server/login.php";
    private Map<String,String> params;

    getLogInRequest(String email, String password, Response.Listener<String>listener){

        super(Request.Method.POST, LOGIN_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("email",email);

        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}