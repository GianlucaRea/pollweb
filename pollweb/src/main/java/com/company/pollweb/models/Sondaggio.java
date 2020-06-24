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
public class Sondaggio {
    
    protected String testofinale, testoiniziale, titolo , user_id;
    
    public Sondaggio(){
        this.testofinale = "";
        this.testoiniziale = "";
        this.titolo = "";
        this.user_id = "";
    }
    
    public Sondaggio(String testofinale, String testoiniziale,String titolo,String user_id){
        this.testofinale = testofinale;
        this.testoiniziale = testoiniziale;
        this.titolo = titolo;
        this.user_id = user_id;
    }
    
    public String getTestofinale(){
        return this.testofinale;
    }
    
    public String getTestoiniziale(){
        return this.testoiniziale;
    }
    
    public String getTitolo(){
        return this.titolo;
    }
    
    public String getUserID(){
        return this.user_id;
    }
    
    public void setTestofinale(String Ntestofinale){
         this.testofinale = Ntestofinale;
    }
    
    public void setTestoiniziale(String Ntestoiniziale){
         this.testoiniziale =  Ntestoiniziale;
    }
    
    public void setTitolo(String Ntitolo){
         this.titolo = Ntitolo;
    }
    
    public void setUserID(String Nuser_id){
         this.user_id = Nuser_id;
    }
    
    
}
