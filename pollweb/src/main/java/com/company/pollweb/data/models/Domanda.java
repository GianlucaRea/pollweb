package com.company.pollweb.data.models;

public interface Domanda {

    public int getId();

    public String getNota();

    public String getTesto();

    public int getSondaggio_id();

    public int getTipologia();

    public int getObbligo();

    public int getOrdine();

    public void setId(int newId);

    public void setNota(String newNota);

    public void setTesto(String newTesto);

    public void setSondaggio_id(int newSondaggio_id);

    public void setTipologia(int newTipologia);

    public void setObbligo(int newObbligo);

    public void setOrdine(int newOrdine);

}
