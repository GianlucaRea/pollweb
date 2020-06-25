/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.models;

/**
 *
 * @author alessandrodorazio
 */
public class Utente {
    protected String nome, cognome, email;
    protected int ruolo_id;
    
    public Utente() {
        this.nome = "";
        this.cognome = "";
        this.email = "";
        this.ruolo_id = 1;
    }
    
    public Utente(String nome, String cognome, String email, int ruolo_id) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.ruolo_id = ruolo_id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public String getCognome() {
        return this.cognome;
    }
    
    public String getNomeCompleto() {
        return this.nome + " " + this.cognome;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public int getRuolo() {
        return this.ruolo_id;
    }
    
    public String getNomeRuolo() {
        if(this.ruolo_id == 3) return "Amministratore";
        if(this.ruolo_id == 2) return "Responsabile";
        return "Utente";
    }
}
