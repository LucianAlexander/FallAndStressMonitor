package com.example.lux.fallandstressmonitor;

public class Events {

    private String data, ora, gsr,bpm,evento;
    public Events(String data, String ora, String bpm, String gsr, String evento){
        this.setData(data);
        this.setOra(ora);
        this.setBpm(bpm);
        this.setGsr(gsr);
        this.setEvento(evento);
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getGsr() {
        return gsr;
    }

    public void setGsr(String gsr) {
        this.gsr = gsr;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
