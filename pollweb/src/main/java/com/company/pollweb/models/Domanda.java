/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.models;

/**
 *
 * @author gianlucarea
 */
public class Domanda {
    protected String nota, testo;
    protected int sondaggio_id , tipologia ,obbligo;
   
    //manca vincoli che è in json

    public Domanda(){
       this.nota = "";
       this.testo = "";
       this.obbligo = 0;
    }
    
    public Domanda(String nota,String testo,int sondaggio_id,int tipologia,int obbligo){
        this.nota = nota;
        this.testo = testo;
        this.sondaggio_id = sondaggio_id;
        this.tipologia = tipologia;
        this.obbligo = obbligo;
    }
    
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

}
