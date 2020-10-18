package com.company.pollweb.data.dao;

import com.company.pollweb.data.implementation.DomandaImpl;
import com.company.pollweb.data.implementation.SondaggioImpl;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.data.proxy.SondaggioProxy;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SondaggioDao_MySQL extends DAO implements SondaggioDao {


    private PreparedStatement inserimento_sondaggio;

    public SondaggioDao_MySQL(DataLayer d) {
        super(d);
    }

    @Override
    public void init() throws DataException {

        try {
            super.init();

            inserimento_sondaggio = connection.prepareStatement("INSERT INTO Sondaggio (utente_id, titolo, testoiniziale, testofinale, created_at) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP);",Statement.RETURN_GENERATED_KEYS);

        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer internship tutor", ex);
        }
    }

    @Override
    public void destroy() throws DataException {

        try {
            inserimento_sondaggio.close();
        } catch (SQLException ex) {
            Logger.getLogger(SondaggioDao_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.destroy();
    }

    @Override
    public SondaggioProxy creazioneSondaggio() {
        return new SondaggioProxy(getDataLayer());
    }


    @Override
    public SondaggioProxy creazioneSondaggio(ResultSet rs) throws DataException {
        try {
            SondaggioProxy a = creazioneSondaggio();
            a.setTitolo(rs.getString("titolo"));
            a.setTestoiniziale(rs.getString("testoiniziale"));
            a.setTestofinale(rs.getString("testofinale"));
            a.setUtenteId(rs.getInt("utente_id"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create Poll object from ResultSet", ex);
        }
    }


    public void salvaSondaggio(Sondaggio p) throws DataException {
        int id = p.getId();
        try (PreparedStatement ps = inserimento_sondaggio ) {
            if (p.getId() > 0) {
                if (p instanceof SondaggioProxy && ((SondaggioProxy) p).isDirty()) {
                    return;
                }
                // TODO Qui ci va la modifica del sondaggio o meglio se un sondaggio esiste già si può modificare qui
            } else {
                ps.setInt(1, p.getUtenteId());
                ps.setString(2, p.getTitolo());
                ps.setString(3, p.getTestoiniziale());
                ps.setString(4, p.getTestofinale());
                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getInt(1);
                        }
                    }
                    p.setId(id);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataException("Unable to insert or update Poll", ex);
        }
    }

    public Sondaggio getSondaggio(int sondaggioId) throws SQLException {
        PreparedStatement sondaggioQuery = connection.prepareStatement("SELECT * FROM Sondaggio WHERE id=?");
        sondaggioQuery.setInt(1, sondaggioId);
        ResultSet rs = sondaggioQuery.executeQuery();
        SondaggioImpl sondaggio = new SondaggioImpl();
        while(rs.next()) {
            sondaggio.setId(rs.getInt("id"));
            sondaggio.setUtenteId(rs.getInt("utente_id"));
            sondaggio.setTitolo(rs.getString("titolo"));
            sondaggio.setTestoiniziale(rs.getString("testoiniziale"));
            sondaggio.setTestofinale(rs.getString("testofinale"));
            sondaggio.setVisibilita(rs.getInt("visibilita"));
            sondaggio.setStato(rs.getInt("stato"));
        }
        rs.close();
        sondaggioQuery.close();
        return sondaggio;

    }

    public boolean isEmailAbilitataAllaCompilazione(Sondaggio sondaggio, String email) throws SQLException {
        if(sondaggio.getVisibilita() == 1) {
            return true;
        } else {
            PreparedStatement emailCompilazioneQuery = connection.prepareStatement("SELECT COUNT(*) AS rowcount FROM Compilazione WHERE sondaggio_id=? AND email=?");
            emailCompilazioneQuery.setInt(1, sondaggio.getId());
            emailCompilazioneQuery.setString(2, email);
            ResultSet rs = emailCompilazioneQuery.executeQuery();
            rs.next();
            return rs.getInt("rowcount")==1;
        }
    }

    public ArrayList<Domanda> getDomande(int sondaggioId) throws SQLException {



        //GET DOMANDE E INSERISCI IN ARRAY
        PreparedStatement getDomandeQuery = connection.prepareStatement("SELECT * FROM Domanda WHERE sondaggio_id=?");
        getDomandeQuery.setInt(1, sondaggioId);
        ResultSet rs = getDomandeQuery.executeQuery();

        //Stores properties of a ResultSet object, including column count
        ResultSetMetaData rsmd = rs.getMetaData();
        int countDomande = rsmd.getColumnCount();

        ArrayList<Domanda> domande = new ArrayList<Domanda>(countDomande);

        Domanda d;
        while(rs.next()) {
            d = new DomandaImpl();
            d.setId(rs.getInt("id"));
            d.setSondaggio_id(rs.getInt("sondaggio_id"));
            d.setTesto(rs.getString("testo"));
            d.setNota(rs.getString("nota"));
            d.setObbligo(rs.getInt("obbligo"));
            d.setOrdine(rs.getInt("ordine"));
            d.setTipologia(rs.getInt("tipologia"));
            domande.add(d);
        }
        rs.close();
        getDomandeQuery.close();
        return domande;
    }
}
