package com.example.lux.fallandstressmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class registrazioneCaregiver extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        final EditText etName = findViewById(R.id.etName);
        final EditText etCognome = findViewById(R.id.etCognome);
        final EditText etEmail = findViewById(R.id.etEma);
        final EditText etPassword = findViewById(R.id.etPassword);
        final EditText etTel = findViewById(R.id.pula);
        final Button bRegister = findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_TOK),"");
                final String nome = etName.getText().toString();
                final String cognome = etCognome.getText().toString();
                final String email = etEmail.getText().toString();
                final String tel = etTel.getText().toString();
                final String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {

                                Intent tologin = new Intent(registrazioneCaregiver.this,logIn.class);
                                tologin.putExtra("email",email);
                                tologin.putExtra("pass",password);
                                registrazioneCaregiver.this.startActivity(tologin);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder((registrazioneCaregiver.this));
                                builder.setMessage("registrazioneCaregiver Fallita")
                                        .setNegativeButton("Riprova", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                putCaregiverRegistration putCaregiverRegistration = new putCaregiverRegistration(nome,cognome,tel,email,password,token,responseListener);
                RequestQueue queue = Volley.newRequestQueue(registrazioneCaregiver.this);
                queue.add(putCaregiverRegistration);
            }
        });
    }
}