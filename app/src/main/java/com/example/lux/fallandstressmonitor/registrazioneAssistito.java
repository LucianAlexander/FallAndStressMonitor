package com.example.lux.fallandstressmonitor;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class registrazioneAssistito extends AppCompatActivity {
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione_assistito);

        final EditText etName = findViewById(R.id.etName);
        final EditText etCognome = findViewById(R.id.etCognome);
        final EditText etEta = findViewById(R.id.etEta);
        final EditText etTel = findViewById(R.id.pula);
        final Button bRegister = findViewById(R.id.bRegAss);
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        session.checkLogin();

        // get user data from sessios
        HashMap<String, String> user = session.getUserDetails();

        final String carid= user.get(SessionManagement.KEY_IDCAR);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nome = etName.getText().toString();
                final String cognome = etCognome.getText().toString();
                final String eta = etEta.getText().toString();
                final String tel = etTel.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Intent intent = new Intent(registrazioneAssistito.this,caregiverArea.class);
                                registrazioneAssistito.this.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder((registrazioneAssistito.this));
                                builder.setMessage("registrazioneCaregiver Fallita, Assistito già presente")
                                        .setNegativeButton("Riprova", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                putAssistitoRegistrazione regAsisRequest = new putAssistitoRegistrazione(nome,cognome,eta,tel,carid,responseListener);
                RequestQueue queue = Volley.newRequestQueue(registrazioneAssistito.this);
                queue.add(regAsisRequest);
            }
        });
    }
}
