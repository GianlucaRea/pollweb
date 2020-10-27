package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Compilazione;
import com.company.pollweb.data.proxy.CompilazioneProxy;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompilazioneDao_MySQL extends DAO implements CompilazioneDao {

    private PreparedStatement inserimento_compilazione;

    public CompilazioneDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {

        try {
            super.init();
            inserimento_compilazione = connection.prepareStatement("INSERT INTO Compilazione (sondaggio_id, email) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer internship tutor", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            inserimento_compilazione.close();
        } catch (SQLException ex) {
            Logger.getLogger(SondaggioDao_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.destroy();
    }

    @Override
    public CompilazioneProxy creazioneCompilazione() {
        return new CompilazioneProxy(getDataLayer());
    }

    @Override
    public CompilazioneProxy creazioneCompilazione(ResultSet rs) throws DataException {
        try {
            CompilazioneProxy a = creazioneCompilazione();
            if(rs.getInt("id") > 0 ) {
                a.setId(rs.getInt("id"));
            }
            a.setSondaggio_id(rs.getInt("sondaggio_id"));
            a.setEmail(rs.getString("email"));
            return a;
        } catch (SQLException ex) {
            throw new DataException("Incapace di creare domanda dal ResultSet", ex);
        }
    }

    @Override
    public void salvaCompilazione(Compilazione c) throws DataException {
        int id = c.getId();
        try (PreparedStatement ps = inserimento_compilazione ) {
            if (c.getId() > 0) {
                if (c instanceof CompilazioneProxy && ((CompilazioneProxy) c).isDirty()) {
                    return;
                }
                // TODO Qui ci va la modifica del sondaggio o meglio se un sondaggio esiste già si può modificare qui
            } else {
                ps.setInt(1, c.getSondaggioId());
                ps.setString(2, c.getEmail());

                // Set int ordine
                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getInt(1);
                        }
                    }
                    c.setId(id);
                }
                ps.close();
            }
            inserimento_compilazione.close();
            this.destroy();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DataException("Impossibile inserire o modificare la compilazione", ex);
        }

    }

    public void salvaCompilazione(int compilazioneId, Map<Integer, JSONArray> risposte) throws SQLException {
        //TODO INSERIMENTO DELLE RISPOSTE
        String inserisciRispostaSQL = "INSERT INTO CompilazioneDomanda(compilazione_id, domanda_id, risposta) VALUES ";
        for(int i=0; i <risposte.size(); i++) {
            inserisciRispostaSQL = inserisciRispostaSQL + "(?,?,?),";

        }

        inserisciRispostaSQL = inserisciRispostaSQL.substring(0, inserisciRispostaSQL.length()-1);
        inserisciRispostaSQL = inserisciRispostaSQL + ";";

        PreparedStatement inserisciRispostaQuery = connection.prepareStatement(inserisciRispostaSQL);
        AtomicInteger j= new AtomicInteger();
        risposte.forEach((domandaId, risposta) -> {
            try {
                inserisciRispostaQuery.setInt((3* j.get())+1, compilazioneId);
                inserisciRispostaQuery.setInt((3* j.get())+2, domandaId);
                inserisciRispostaQuery.setString((3* j.get())+3, JSONObject.valueToString(risposta.toString()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            j.set(j.get() + 1);
        });

        inserisciRispostaQuery.execute();

    }

}
