package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.proxy.DomandaProxy;
import com.company.pollweb.data.proxy.SondaggioProxy;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.utility.Database;
import org.json.JSONObject;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DomandaDao_MySQL extends DAO implements DomandaDao{

    private PreparedStatement inserimento_domanda;

    public DomandaDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {

        try {
            super.init();
            inserimento_domanda = connection.prepareStatement("INSERT INTO Domanda (sondaggio_id, testo, nota, obbligo, tipologia, vincoli) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer internship tutor", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            inserimento_domanda.close();
        } catch (SQLException ex) {
            Logger.getLogger(SondaggioDao_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.destroy();
    }

    @Override
    public DomandaProxy creazioneDomanda() {
        return new DomandaProxy(getDataLayer());
    }

    @Override
    public DomandaProxy creazioneDomanda(ResultSet rs) throws DataException {
        try {
            DomandaProxy a = creazioneDomanda();
            a.setTesto(rs.getString("testo"));
            a.setNota(rs.getString("nota"));
            a.setObbligo(rs.getInt("obbligo"));
            a.setTipologia(rs.getString("tipologia"));
            a.setVincoli((JSONObject) rs.getObject("vincoli"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Incapace di creare domanda dal ResultSet", ex);
        }
    }

    @Override
    public void salvaDomanda(Domanda d) throws DataException {
        int id = d.getId();
        try (PreparedStatement ps = inserimento_domanda ) {
            if (d.getId() > 0) {
                if (d instanceof DomandaProxy && ((DomandaProxy) d).isDirty()) {
                    return;
                }
                // TODO Qui ci va la modifica del sondaggio o meglio se un sondaggio esiste già si può modificare qui
            } else {
                ps.setInt(1, d.getSondaggio_id());
                ps.setString(2, d.getTesto());
                ps.setString(3, d.getNota());
                ps.setInt(4, d.getObbligo());
                ps.setString(5, d.getTipologia());
                String stringToBeInserted = JSONObject.valueToString(d.getVincoli());
                ps.setString(6,stringToBeInserted);
                // Set int ordine
                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getInt(1);
                        }
                    }
                    d.setId(id);
                }
                ps.close();
            }
            inserimento_domanda.close();
            this.destroy();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataException("Impossibile inserire o modificare la Domanda", ex);
        }

    }
}
