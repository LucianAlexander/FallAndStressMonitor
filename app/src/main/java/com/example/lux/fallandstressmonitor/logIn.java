package com.example.lux.fallandstressmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class logIn extends AppCompatActivity {
    SessionManagement session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // Session Manager
        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button bLogin = findViewById(R.id.bLogin);
        final Button bRegister = findViewById(R.id.btReg);

        Intent fromRegister = getIntent();
        String daRegEmail = fromRegister.getStringExtra("email");
        String daRegPass = fromRegister.getStringExtra("pass");

       // quando ritorna da registrazione con esito OK riporta i valori senza dover riscrivere
            etEmail.setText(daRegEmail);
            etPassword.setText(daRegPass);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(logIn.this,registrazioneCaregiver.class);
                logIn.this.startActivity(registerIntent);

            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                String nome = jsonResponse.getString("nome");
                                final String idCar = jsonResponse.getString("idCaregiver");
                                session.createLoginSession(email, password ,idCar,nome);
                                Intent intent = new Intent(logIn.this, caregiverArea.class);
                                intent.putExtra("nome",nome);
                                intent.putExtra("idCaregiver",idCar);
                                logIn.this.startActivity(intent);


                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder((logIn.this));
                                builder.setMessage("Login fallito")
                                        .setNegativeButton("Riprova o Registrati",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                getLogInRequest getLogInRequest = new getLogInRequest(email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(logIn.this);
                queue.add(getLogInRequest);
            }
        });
    }
}