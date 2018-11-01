package com.example.lux.fallandstressmonitor;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class caregiverArea extends AppCompatActivity {
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        final TextView tv1 = findViewById(R.id.tv1);
        final Button viewAsis = findViewById(R.id.viewasis);
        final Button registerAsis = findViewById(R.id.regasis);
        registerAsis.setVisibility(View.GONE);

        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        final String nome = user.get(SessionManagement.KEY_NOME);

        final String carid= user.get(SessionManagement.KEY_IDCAR);
        StringBuilder hell = new StringBuilder("Ciao "+nome);
        tv1.setText(hell);

        registerAsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAsisReg = new Intent(caregiverArea.this,registrazioneAssistito.class);
                caregiverArea.this.startActivity(toAsisReg);

            }
        });

        viewAsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {

                                String idass = jsonResponse.getString("idasis");
                                String nome = jsonResponse.getString("nome");
                                String cog = jsonResponse.getString("cognome");
                                String eta = jsonResponse.getString("eta");
                                String tel = jsonResponse.getString("tel");
                                String bpm = jsonResponse.getString("bpm");
                                String gsr = jsonResponse.getString("gsr");

                                Intent todata = new Intent(caregiverArea.this, caregiverAndAssistitoArea.class);
                                todata.putExtra("idass", idass);
                                todata.putExtra("nome", nome);
                                todata.putExtra("cognome", cog);
                                todata.putExtra("eta", eta);
                                todata.putExtra("tel", tel);
                                todata.putExtra("gsr", gsr);
                                todata.putExtra("bpm", bpm);
                                caregiverArea.this.startActivity(todata);

                            }else{
                                registerAsis.setVisibility(View.VISIBLE);
                                AlertDialog.Builder builder = new AlertDialog.Builder((caregiverArea.this));
                                builder.setMessage("Prima di vedere i dati relativi al tuo assistito è necessario registrarlo.")
                                        .setNegativeButton("Chiudi",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder((caregiverArea.this));
                            builder.setMessage("pula mea")
                                    .setNegativeButton("Riprova",null)
                                    .create()
                                    .show();
                        }
                    }
                };

                getAsisData getData = new getAsisData(carid,responseListener);
                RequestQueue queue = Volley.newRequestQueue(caregiverArea.this);
                queue.add(getData);
            }
        });

    }
}
