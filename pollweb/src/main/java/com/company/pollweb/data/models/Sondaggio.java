package com.company.pollweb.data.models;

public interface Sondaggio {

    public int getId();

    public String getTestofinale();

    public String getTestoiniziale();

    public String getTitolo();

    public int getUtenteId();

    public void setId(int Nid);

    public void setTestofinale(String Ntestofinale);

    public void setTestoiniziale(String Ntestoiniziale);

    public void setTitolo(String Ntitolo);

    public void setUtenteId(int Nutente_id);

}
