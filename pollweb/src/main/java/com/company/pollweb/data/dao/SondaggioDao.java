package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SondaggioDao {

    public Sondaggio creazioneSondaggio(ResultSet rs) throws DataException;

    public Sondaggio creazioneSondaggio();

    public void salvaSondaggio(Sondaggio p) throws DataException;

    public Sondaggio getSondaggio(int sondaggioId) throws SQLException;

    public boolean isEmailAbilitataAllaCompilazione(Sondaggio sondaggio, String email) throws SQLException;

}
