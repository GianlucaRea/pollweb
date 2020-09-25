package com.company.pollweb.data.models;

public class Compilazione {

    protected int id_compilazione , id_sondaggio;
    protected String mail_utente;

    public Compilazione(){
        this.id_compilazione = 0;
        this.id_sondaggio = 0;
        this.mail_utente = "";
    }

    public Compilazione(int id_sondaggio, String mail_utente){
        this.id_sondaggio = id_sondaggio;
        this.mail_utente = mail_utente;
    }

    public void setMail_utente(String mail_utente) {
        this.mail_utente = mail_utente;
    }

    public String getMail_utente() {
        return mail_utente;
    }

    public void setId_compilazione(int id_compilazione) {
        this.id_compilazione = id_compilazione;
    }

    public int getId_compilazione() {
        return id_compilazione;
    }

    public int getId_sondaggio() {
        return id_sondaggio;
    }

    public void setId_sondaggio(int id_sondaggio) {
        this.id_sondaggio = id_sondaggio;
    }
}
