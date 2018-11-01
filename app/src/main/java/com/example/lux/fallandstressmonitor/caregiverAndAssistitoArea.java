package com.example.lux.fallandstressmonitor;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class caregiverAndAssistitoArea extends AppCompatActivity {
    String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area_and_assistito);
        final Button toGraph = findViewById(R.id.btGraf);
        Intent fromUserArea = getIntent();
        final String asisid = fromUserArea.getStringExtra("idass");
        final String nome = fromUserArea.getStringExtra("nome");
        final String cognome = fromUserArea.getStringExtra("cognome");
        final String eta = fromUserArea.getStringExtra("eta");
        final String telefono = fromUserArea.getStringExtra("tel");
        final String bpm = fromUserArea.getStringExtra("bpm");
        final String gsr = fromUserArea.getStringExtra("gsr");

        final TextView tvNoCO = findViewById(R.id.textView5);
        final TextView tvEta = findViewById(R.id.etEta);
        final TextView tvTel = findViewById(R.id.tvphone);
        final TextView tvBpm = findViewById(R.id.etbpm);
        final TextView tvgsr = findViewById(R.id.etStres);

        TextView shogsr = findViewById(R.id.showgsr);
        StringBuilder aaa = new StringBuilder(gsr + "μSiemens");
        shogsr.setText(aaa);
        TextView shobpm = findViewById(R.id.showbpm);
        StringBuilder bbb = new StringBuilder(bpm +" BPM");
        shobpm.setText(bbb);

        ImageView happy = findViewById(R.id.imfericit);
        happy.setVisibility(View.GONE);
        ImageView neutro = findViewById(R.id.imneutru);
        neutro.setVisibility(View.GONE);
        ImageView stresat = findViewById(R.id.imstresat);
        stresat.setVisibility(View.GONE);
        Double stresed = Double.parseDouble(gsr);
        if(stresed<=3){
            happy.setVisibility(View.VISIBLE);
        }else if(stresed>3 && stresed<=4.5){
            neutro.setVisibility(View.VISIBLE);
        }else{
            stresat.setVisibility(View.VISIBLE);
        }

        StringBuilder davede = new StringBuilder(nome + " " + cognome + "-" +asisid);
        StringBuilder davede2 = new StringBuilder("Eta:"+eta);
        StringBuilder davede3 = new StringBuilder("Tel : "+ telefono);
        StringBuilder davedebpm = new StringBuilder("Battito Cardiaco ");
        StringBuilder davedegsr = new StringBuilder("Stato d'animo ");

        tvNoCO.setText(davede);
        tvEta.setText(davede2);
        tvTel.setText(davede3);
        tvBpm.setText(davedebpm);
        tvgsr.setText(davedegsr);

        toGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSON_STRING = jsonResponse.toString();

                            Intent toGrafici = new Intent(caregiverAndAssistitoArea.this, grafici.class);

                            toGrafici.putExtra("idass", asisid);
                            toGrafici.putExtra("json",JSON_STRING);

                            caregiverAndAssistitoArea.this.startActivity(toGrafici);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder((caregiverAndAssistitoArea.this));
                            builder.setMessage("Dati non disponibili")
                                    .setNegativeButton("Riprova",null)
                                    .create()
                                    .show();
                        }
                    }
                };

                getDataGraph getDataGraph = new getDataGraph(asisid,responseListener);
                RequestQueue queue = Volley.newRequestQueue(caregiverAndAssistitoArea.this);
                queue.add(getDataGraph);
            }
        });

    }
}
