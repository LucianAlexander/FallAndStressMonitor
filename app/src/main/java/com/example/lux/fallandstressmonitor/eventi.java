package com.example.lux.fallandstressmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class eventi extends AppCompatActivity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    EventsAdapter eventsAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventi);

        eventsAdapter = new EventsAdapter(this,R.layout.row_layout);
        listView = findViewById(R.id.listview);
        listView.setAdapter(eventsAdapter);

        Intent fromGrafici = getIntent();

        json_string = fromGrafici.getStringExtra("json");
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String data,ora,bpm,gsr,evento;
            while(count<jsonArray.length()){
                 JSONObject JO = jsonArray.getJSONObject(count);
                 data = JO.getString("data");
                 ora = JO.getString("ora");
                 bpm = JO.getString("bpm");
                 gsr = JO.getString("gsr");
                 evento = JO.getString("evento");
                 Events events = new Events(data,ora,bpm,gsr,evento);
                 eventsAdapter.add(events);
                 count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
