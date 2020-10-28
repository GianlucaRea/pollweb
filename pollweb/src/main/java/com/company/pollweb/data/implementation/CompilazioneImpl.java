package com.company.pollweb.data.implementation;

import com.company.pollweb.data.models.Compilazione;
import com.company.pollweb.data.models.Domanda;

import java.util.ArrayList;

public class CompilazioneImpl implements Compilazione {

    protected int id, id_sondaggio;
    protected String email;

    public CompilazioneImpl(){

    }

    public CompilazioneImpl(int id_sondaggio, String email){
        this.id_sondaggio = id_sondaggio;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSondaggioId() {
        return id_sondaggio;
    }

    public void setSondaggioId(int sondaggioId) {
        this.id_sondaggio = sondaggioId;
    }

}
