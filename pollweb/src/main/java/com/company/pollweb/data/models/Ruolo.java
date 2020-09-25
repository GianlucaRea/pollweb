/**
 * @author GianlucaRea
 */
package com.company.pollweb.data.models;

public class Ruolo {
    protected int ruolo_id;
    protected String nome_ruolo;

    public int getRuolo_id(){
        return this.ruolo_id;
    }

    public void setRuolo_id(int newRuolo_id){
        this.ruolo_id = newRuolo_id;
    }

    public String getNome_ruolo() {
        return this.nome_ruolo;
    }

    public void setNome_ruolo(String newNome_ruolo){
        this.nome_ruolo = newNome_ruolo;
    }
}

