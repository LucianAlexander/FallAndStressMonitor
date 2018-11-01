package com.example.lux.fallandstressmonitor;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class grafici extends AppCompatActivity {
    String JSON_STRING;
    String STRING_JSON;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<Double> GSR;
    ArrayList<Integer> BPM;
    ArrayList<String> ORA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafici);


        //get data from intent
        Intent fromUserAreaAndAssistito = getIntent();
        final String asisid = fromUserAreaAndAssistito.getStringExtra("idass");
        STRING_JSON = fromUserAreaAndAssistito.getStringExtra("json");

        //set the graphs
        GraphView graph1 = findViewById(R.id.graph1);
        GraphView graph2 = findViewById(R.id.graph2);
        final Button toEventi = findViewById(R.id.btToEventi);
        GSR = new ArrayList<>();
        BPM = new ArrayList<>();
        ORA = new ArrayList<>();


        try {
            jsonObject = new JSONObject(STRING_JSON);
            jsonArray = jsonObject.getJSONArray("response");

            int count = 0;
            String ora, bpm, gsr;
            while (count < jsonArray.length()) {

                JSONObject JO = jsonArray.getJSONObject(count);

                ora = JO.getString("ora");
                bpm = JO.getString("bpm");
                gsr = JO.getString("gsr");

                GSR.add(Double.parseDouble(gsr));
                BPM.add(Integer.parseInt(bpm));
                ORA.add(ora);

                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(generateGSR());
        graph1.addSeries(series);
        graph1.getViewport().setYAxisBoundsManual(true);
        graph1.getViewport().setMinY(0);
        graph1.getViewport().setMaxY(20);
        graph1.getViewport().setXAxisBoundsManual(true);
        graph1.getViewport().setMinX(0);
        graph1.getViewport().setMaxX(GSR.size());
        graph1.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph1.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        graph1.setTitle("Dati Del Giorno");

        series.setColor(Color.BLUE);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        series.setThickness(4);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(generateBPM());
        graph2.addSeries(series2);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(0);
        graph2.getViewport().setMaxY(200);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(BPM.size());
        graph2.setTitle("Dati Del Giorno");
        graph2.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph2.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        series2.setColor(Color.RED);
        series2.setDrawDataPoints(true);
        series2.setDataPointsRadius(5);
        series2.setThickness(4);

        toEventi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSON_STRING = jsonResponse.toString();

                            Intent toEventi = new Intent(grafici.this, eventi.class);
                            toEventi.putExtra("json", JSON_STRING);

                            grafici.this.startActivity(toEventi);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder((grafici.this));
                            builder.setMessage("Dati non disponibili")
                                    .setNegativeButton("Riprova", null)
                                    .create()
                                    .show();
                        }
                    }
                };

                getDataEventi getEventi = new getDataEventi(asisid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(grafici.this);
                queue.add(getEventi);
            }
        });


    }

    public DataPoint[] generateGSR() {

        int count = GSR.size();
        DataPoint[] values = new DataPoint[count];
        for (int i = 0; i < count; i++) {
            double y = GSR.get(i);
            DataPoint v = new DataPoint(i, y);
            values[i] = v;
        }
        return values;
    }

    public DataPoint[] generateBPM() {

        int count = BPM.size();
        DataPoint[] values = new DataPoint[count];
            for (int i = 0; i < count; i++) {
                int y = BPM.get(i);
                DataPoint v = new DataPoint(i, y);
                values[i] = v;
            }
        return values;
        }


}


