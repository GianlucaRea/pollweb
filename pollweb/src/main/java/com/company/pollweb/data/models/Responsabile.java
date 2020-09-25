/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.models;

/**
 *
 * @author alessandrodorazio
 */
public class Responsabile extends Utente {
    public Responsabile() {
        super();
        this.ruolo_id = 2;
    }
    
    public Responsabile(String nome, String cognome, String email) {
        super();
        this.ruolo_id = 2;
    }
    
    @Override
    public String getNomeRuolo() {
        return "Responsabile";
    }
}
