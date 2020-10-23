package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SondaggioDao {

    public Sondaggio creazioneSondaggio(ResultSet rs) throws DataException;

    public Sondaggio creazioneSondaggio();

    public void salvaSondaggio(Sondaggio p) throws DataException;

    public Sondaggio getSondaggio(int sondaggioId) throws SQLException;

    public ArrayList<Domanda> getDomande(int sondaggioId) throws SQLException;

    public boolean pubblicaSondaggio(int sondaggioId) throws SQLException;

    public boolean chiudiSondaggio(int sondaggioId) throws SQLException;

    public ArrayList<Sondaggio> listaSondaggi() throws SQLException;

    public ArrayList<Sondaggio> listaSondaggiResponsabile(int utenteId) throws SQLException;

    public boolean isEmailAbilitataAllaCompilazione(Sondaggio sondaggio, String email) throws SQLException;

    public boolean invitaUtenti(int sondaggioId, String[] utenti) throws SQLException;

    public int modificaVisibilita(int sondaggioId, int nuovoValore) throws SQLException;

}
