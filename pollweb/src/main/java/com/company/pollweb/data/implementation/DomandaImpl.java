/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.implementation;

import com.company.pollweb.data.models.Domanda;
import org.json.JSONObject;

/**
 *
 * @author gianlucarea
 */
public class DomandaImpl implements Domanda {
    protected String nota, testo, tipologia;
    protected int id ,sondaggio_id  , obbligo , ordine;
    protected JSONObject vincoli;
   
    //manca vincoli che è in json

    public DomandaImpl(){
        this.id = -1;
       this.nota = "";
       this.testo = "";
       this.obbligo = 0;
       this.ordine = 0;
    }
    
    public DomandaImpl(String nota, String testo, int sondaggio_id, String tipologia , JSONObject vincoli, int obbligo , int ordine ){
        this.nota = nota;
        this.testo = testo;
        this.sondaggio_id = sondaggio_id;
        this.tipologia = tipologia;
        this.obbligo = obbligo;
        this.ordine = ordine;
        this.vincoli = vincoli;
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
    
    public String getTipologia(){
        return this.tipologia;
    }
    
    public int getObbligo(){
        return this.obbligo;
    }

    public String getNomeObbligo() {
        if(obbligo==0) return "Non obbligatoria";
        return "Obbligatoria";
    }

    @Override
    public JSONObject getVincoli() {return this.vincoli;}

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
    
    public void setTipologia(String newTipologia){
        this.tipologia = newTipologia;
    }
    
    public void setObbligo(int newObbligo){
        this.obbligo = newObbligo;
    }

    public void setOrdine(int newOrdine){
        this.ordine = newOrdine;
    }

    @Override
    public void setVincoli(JSONObject newVincoli) {this.vincoli = newVincoli;}


}
