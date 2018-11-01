package com.example.lux.fallandstressmonitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public EventsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(Events object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        EventsHolder eventsHolder;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            eventsHolder = new EventsHolder();
            eventsHolder.tx_data = row.findViewById(R.id.data);
            eventsHolder.tx_ora = row.findViewById(R.id.ora);
            eventsHolder.tx_bpm = row.findViewById(R.id.bpm);
            eventsHolder.tx_gsr = row.findViewById(R.id.gsr);
            eventsHolder.tx_evento = row.findViewById(R.id.evento);
            row.setTag(eventsHolder);
        }else{

            eventsHolder = (EventsHolder) row.getTag();
        }

        Events events = (Events) this.getItem(position);
        eventsHolder.tx_data.setText(events.getData());
        eventsHolder.tx_ora.setText(events.getOra());
        eventsHolder.tx_bpm.setText(events.getBpm());
        eventsHolder.tx_gsr.setText(events.getGsr());
        eventsHolder.tx_evento.setText(events.getEvento().toUpperCase()); ///stava zenza uppercase

        return row;
    }

    static class EventsHolder{
        TextView tx_data,tx_ora,tx_bpm,tx_gsr,tx_evento;
    }
}
