package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.data.proxy.SondaggioProxy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SondaggioDao_MySQL extends DAO implements SondaggioDao {

    private static final String INSERIMENTO_SONDAGGIO = "INSERT INTO Sondaggio (utente_email, titolo, testoiniziale, testofinale, created_at)" + "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)";


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
            a.setUtenteEmail(rs.getString("utenteEmail"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Unable to create Poll object from ResultSet", ex);
        }
    }


    public void salvaSondaggio(Sondaggio p) throws DataException {
        int id = p.getId();
        try (PreparedStatement ps = connection.prepareStatement(INSERIMENTO_SONDAGGIO,Statement.RETURN_GENERATED_KEYS)) {
            if (p.getId() > 0) {
                if (p instanceof SondaggioProxy && ((SondaggioProxy) p).isDirty()) {
                    return;
                }
                // TODO Qui ci va la modifica del sondaggio o meglio se un sondaggio esiste già si può modificare qui
            } else {
                ps.setString(1, p.getUtenteEmail());
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
}
