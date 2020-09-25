package com.company.pollweb.data.models;

public class Risposta {

    protected int risposta_id ,domanda_id;
    protected String risposta;

    public Risposta(){
        this.risposta_id = 0;
        this.domanda_id = 0;
        this.risposta = "";
    }

    public Risposta(int domanda_id,String risposta){
        this.domanda_id = domanda_id;
        this.risposta = risposta;
    }

    public int getRisposta_id(){
        return this.risposta_id;
    }

    public void setRisposta_id(int newRisposta_id) {
        this.risposta_id = newRisposta_id;
    }

    public int getDomanda_id(){
        return this.domanda_id;
    }

    public void setDomanda_id(int newDomanda_id){
        this.domanda_id = newDomanda_id;
    }

    public String getRisposta(){
        return this.risposta;
    }

    public void setRisposta(String newRisposta){
        this.risposta = newRisposta;
    }
}
