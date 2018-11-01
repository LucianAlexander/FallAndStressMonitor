package com.example.lux.fallandstressmonitor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getDataEventi extends StringRequest{
    private static final String GET_DATA_EVENTI_URL = "http://192.168.0.4/server/getDataEvento.php";
    private Map<String,String> params;

    public getDataEventi(String asisid, Response.Listener<String>listener){

        super(Request.Method.POST, GET_DATA_EVENTI_URL,listener,null);
        params = new HashMap<>();
        params.put("assistito",asisid);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}




