/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.implementation;

import com.company.pollweb.data.models.Domanda;

/**
 *
 * @author gianlucarea
 */
public class DomandaImpl implements Domanda {
    protected String nota, testo;
    protected int id ,sondaggio_id , tipologia , obbligo , ordine;
   
    //manca vincoli che è in json

    public DomandaImpl(){
       this.nota = "";
       this.testo = "";
       this.obbligo = 0;
       this.ordine = 0;
    }
    
    public DomandaImpl(String nota, String testo, int sondaggio_id, int tipologia, int obbligo , int ordine){
        this.nota = nota;
        this.testo = testo;
        this.sondaggio_id = sondaggio_id;
        this.tipologia = tipologia;
        this.obbligo = obbligo;
        this.ordine = ordine;
    }

    @Override
    public int getId() {return this.id;}

    public String getNota(){
        return this.nota;
    }
    
    public String getTesto(){
        return this.testo;
    }
    
    public int getSondaggio_id(){
        return this.sondaggio_id;
    }
    
    public int getTipologia(){
        return this.tipologia;
    }
    
    public int getObbligo(){
        return this.obbligo;
    }

    public int getOrdine(){
        return this.ordine;
    }

    @Override
    public void setId(int newId) {this.id = newId;}

    public void setNota(String newNota){
        this.nota = newNota;
    }
    
    public void setTesto(String newTesto){
        this.testo = newTesto;
    }
    
    public void setSondaggio_id(int newSondaggio_id){
         this.sondaggio_id = newSondaggio_id;
    }
    
    public void setTipologia(int newTipologia){
        this.tipologia = newTipologia;
    }
    
    public void setObbligo(int newObbligo){
        this.obbligo = newObbligo;
    }

    public void setOrdine(int newOrdine){
        this.ordine = newOrdine;
    }


}
