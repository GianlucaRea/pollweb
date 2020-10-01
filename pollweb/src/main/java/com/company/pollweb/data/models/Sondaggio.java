package com.company.pollweb.data.models;

public interface Sondaggio {

    public int getId();

    public String getTestofinale();

    public String getTestoiniziale();

    public String getTitolo();

    public String getUtenteEmail();

    public void setId(int Nid);

    public void setTestofinale(String Ntestofinale);

    public void setTestoiniziale(String Ntestoiniziale);

    public void setTitolo(String Ntitolo);

    public void setUtenteEmail(String Nemail);

}
