package com.company.pollweb.data.impl;

import com.company.pollweb.data.models.Compilazione;

public class CompilazioneImpl implements Compilazione {

    protected int id_compilazione , id_sondaggio, utente_id;

    public CompilazioneImpl(){

    }

    public CompilazioneImpl(int id_sondaggio, int utenteId){
        this.id_sondaggio = id_sondaggio;
        this.utente_id = utenteId;
    }

    public void setUtenteId(int utenteId) {
        this.utente_id = utenteId;
    }

    public int getUtenteId() {
        return utente_id;
    }

    public void setId(int id) {
        this.id_compilazione = id;
    }

    public int getId() {
        return id_compilazione;
    }

    public int getSondaggioId() {
        return id_sondaggio;
    }

    public void setSondaggioId(int sondaggioId) {
        this.id_sondaggio = sondaggioId;
    }
}
