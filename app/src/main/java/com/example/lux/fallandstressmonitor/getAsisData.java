package com.example.lux.fallandstressmonitor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class getAsisData extends StringRequest {
    private static final String GET_ASSISTITO_DATA_URL = "http://192.168.0.4/server/getDataAssistito.php";
    //private static final String GET_ASSISTITO_DATA_URL = "http://192.168.0.4/server/getDataAssistito.php";
    private Map<String,String> params;

    public getAsisData(String carid, Response.Listener<String>listener){

        super(Method.POST, GET_ASSISTITO_DATA_URL,listener,null);
        params = new HashMap<>();
        params.put("carid",carid);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
