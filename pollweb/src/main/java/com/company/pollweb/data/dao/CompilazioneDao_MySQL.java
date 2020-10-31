package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Compilazione;
import com.company.pollweb.data.proxy.CompilazioneProxy;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.utility.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompilazioneDao_MySQL extends DAO implements CompilazioneDao {

    private PreparedStatement inserimento_compilazione , getUserList , get_risposte , get_compilazione_id , get_risposte_bySondaggioAndEmail;

    public CompilazioneDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {

        try {
            super.init();
            inserimento_compilazione = connection.prepareStatement("INSERT INTO Compilazione (sondaggio_id, utente_id) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
            getUserList = connection.prepareStatement("SELECT utente_id FROM Compilazione WHERE sondaggio_id=?;");
            get_risposte = connection.prepareStatement("SELECT risposta FROM CompilazioneDomanda WHERE domanda_id=?;");
            get_compilazione_id = connection.prepareStatement("SELECT risposta,utente_id FROM Compilazione JOIN CompilazioneDomanda ON Compilazione.id = CompilazioneDomanda.compilazione_id WHERE domanda_id = ?;");
            get_risposte_bySondaggioAndEmail = connection.prepareStatement("SELECT risposta FROM Compilazione JOIN CompilazioneDomanda ON Compilazione.id = CompilazioneDomanda.compilazione_id WHERE sondaggio_id = ? && email = ?;");
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer internship tutor", ex);
        }
    }

    public void destroy() throws DataException {
        try {
            inserimento_compilazione.close();
            getUserList.close();
            get_risposte.close();
            get_compilazione_id.close();
            get_risposte_bySondaggioAndEmail.close();
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
            a.setSondaggioId(rs.getInt("sondaggio_id"));
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

    public Compilazione getCompilazione(int sondaggioId, String email) throws SQLException {
        CompilazioneProxy c = creazioneCompilazione();
        String getCompilazioneSQL = "SELECT * FROM Compilazione WHERE sondaggio_id=? AND email=?";
        PreparedStatement getCompilazioneQuery = connection.prepareStatement(getCompilazioneSQL);
        getCompilazioneQuery.setInt(1, sondaggioId);
        getCompilazioneQuery.setString(2, email);
        try (ResultSet rs = getCompilazioneQuery.executeQuery()) {
            if (rs.next()) {
                return creazioneCompilazione(rs);
            }
        } catch (DataException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public List<Integer> getUserList(int sondaggioId) throws DataException {
        List<Integer> list = new ArrayList();
         try {
             getUserList.setInt(1, sondaggioId);
             try(ResultSet rs = getUserList.executeQuery()){
                 while(rs.next()) {
                     list.add(rs.getInt("utente_id"));
                 }
             }
         }catch (SQLException ex){
             throw new DataException("Impossibile caricare la lista di utente", ex);
         }
         return list;
    }

    @Override
    public List<String> getRisposteByDomandaId(int domandaId) throws DataException {
        List<String> list = new ArrayList();
        try {
            get_risposte.setInt(1, domandaId);
            try(ResultSet rs = get_risposte.executeQuery()){
                while(rs.next()) {
                    list.add(rs.getString("risposta"));
                }
            }
        }catch (SQLException ex){
            throw new DataException("Impossibile caricare la lista delle risposte dal id della domanda", ex);
        }
        return list;
    }

    public ArrayList<Pair>  getEmailByDomandaId(int domandaId) throws DataException {
        ArrayList<Pair> list = new ArrayList<Pair>();
        try {
            get_compilazione_id.setInt(1, domandaId);
            try(ResultSet rs = get_compilazione_id.executeQuery()){
                int i = 0;
                while(rs.next()) {
                    String b = rs.getString("risposta");
                    String a = rs.getString("email");
                    list.add(i,new Pair(a,b));
                    i++;
                }
            }
        }catch (SQLException ex){
            throw new DataException("Impossibile caricare la lista delle risposte alla domanda", ex);
        }
        return list;
    }

    public List<String> getRisposteBySondaggioAndEmail(int sondaggioid , Integer email) throws DataException {
        List<String> list = new ArrayList();
        try{
            get_risposte_bySondaggioAndEmail.setInt(1,sondaggioid);
            get_risposte_bySondaggioAndEmail.setInt(2,email);
            try(ResultSet rs = get_risposte_bySondaggioAndEmail.executeQuery()){
                while(rs.next()){
                    list.add(rs.getString("risposta"));
                }
            }
        }catch(SQLException ex){
            throw new DataException("Impossibile caricare la lista delle risposte dall'email di colui che ha compilato", ex);
        }
        return list;
    }



}
