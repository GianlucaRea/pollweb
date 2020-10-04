/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.impl;

import com.company.pollweb.data.models.Sondaggio;

/**
 *
 * @author gianlucarea
 */
public class SondaggioImpl implements Sondaggio {
    
    protected String  titolo, testoiniziale, testofinale, user_email;
    protected int id;

    
    public SondaggioImpl(){
    }
    
    public SondaggioImpl(String titolo, String testoiniziale, String testofinale, String user_id){
        this.titolo = titolo;
        this.testoiniziale = testoiniziale;
        this.testofinale = testofinale;
        this.user_email = user_email;
    }

    public int getId(){return this.id;}

    public String getTestofinale(){
        return this.testofinale;
    }
    
    public String getTestoiniziale(){
        return this.testoiniziale;
    }
    
    public String getTitolo(){
        return this.titolo;
    }
    
    public String getUtenteEmail(){
        return this.user_email;
    }

    public void setId(int nID){this.id = nID;}
    
    public void setTestofinale(String Ntestofinale){
         this.testofinale = Ntestofinale;
    }
    
    public void setTestoiniziale(String Ntestoiniziale){
         this.testoiniziale =  Ntestoiniziale;
    }
    
    public void setTitolo(String Ntitolo){
         this.titolo = Ntitolo;
    }
    
    public void setUtenteEmail(String Nuser_email){
         this.user_email = Nuser_email;
    }
    
    
}
