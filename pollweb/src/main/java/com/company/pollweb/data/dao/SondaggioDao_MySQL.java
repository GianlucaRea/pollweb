package com.company.pollweb.data.dao;

import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.data.proxy.SondaggioProxy;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SondaggioDao_MySQL extends DAO implements SondaggioDao {

    public SondaggioDao_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public SondaggioProxy creazioneSondaggio() {
        return new SondaggioProxy(getDataLayer());
    }

    @Override
    public SondaggioProxy creazioneSondaggio(ResultSet rs) throws DataException {
        try {
            SondaggioProxy a = creazioneSondaggio();
            a.setTitolo(rs.getString("title"));
            a.setTestoiniziale(rs.getString("openText"));
            a.setTestofinale(rs.getString("closeText"));
            a.setUserID(rs.getInt("IDuser"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create Poll object from ResultSet", ex);
        }
    }

}
