package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Utente;
import com.company.pollweb.data.proxy.UtenteProxy;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteDao_MySQL extends DAO implements UtenteDao {


    private PreparedStatement utenteById,utenteByLogin,utenteByEmail,inserimentoUtente;

    public UtenteDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            utenteById = connection.prepareStatement("SELECT * FROM utente WHERE id = ?;");
            utenteByLogin = connection.prepareStatement("SELECT * FROM utente where email = ? AND password = ?;");
            utenteByEmail = connection.prepareStatement("SELECT * FROM utente where email = ?;");
            inserimentoUtente = connection.prepareStatement("INSERT INTO studenti (email,nome,cognome,password,ruolo_id) VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException ex) {
            throw new DataException("Errore durante l'inizializzazione del data layer pollweb", ex);
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            utenteById.close();
            utenteByLogin.close();
            utenteByEmail.close();
            inserimentoUtente.close();
        } catch (SQLException ex){
            Logger.getLogger(UtenteDao_MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.destroy();
    }

    @Override
    public UtenteProxy creaUtente() {
        return new UtenteProxy(getDataLayer());
    }

    @Override
    public UtenteProxy creaUtente(ResultSet res) throws DataException {
        UtenteProxy u = creaUtente();
        try {
            u.setId(res.getInt("id"));
            u.setNome(res.getString("nome"));
            u.setEmail(res.getString("email"));
            u.setPassword(res.getString("password"));
            u.setRuolo(res.getInt("ruolo_id"));
        } catch (Exception ex) {
            throw new DataException("Impossibile creare l'oggetto Utente dal ResultSet", ex);
        }
        return u;
    }


    @Override
    public void inserisciUtente (Utente utente) throws DataException { // per update e insert
        int id = utente.getId();
        int ruolo = 2;
        try {
            if (utente.getId() > 0) {
                if (utente instanceof UtenteProxy && !((UtenteProxy) utente).isDirty()) {
                    return;
                }

                //TODO Parte update utente

            } else { //insert

                inserimentoUtente.setString(1, utente.getEmail());
                inserimentoUtente.setString(2, utente.getNome());
                inserimentoUtente.setString(3, utente.getCognome());
                inserimentoUtente.setString(4, utente.getPassword());
                inserimentoUtente.setInt(5, utente.getRuolo());


                if (inserimentoUtente.executeUpdate() == 1) {
                    try (ResultSet rs = inserimentoUtente.getGeneratedKeys()) {
                        if (rs.next()) {
                            id = rs.getInt(1);
                        }
                    }
                    utente.setId(id);
                }
            }

            if (utente instanceof UtenteProxy) {
                ((UtenteProxy) utente).setDirty(false);
            }

        } catch (SQLException ex) {
            throw new DataException("Impossibile aggiornare/inserire l'utente", ex);
        }
    }


    public Utente getUtente(int id) throws DataException {
        try {
            utenteById.setInt(1, id);
            try (ResultSet rs = utenteById.executeQuery()) {
                if (rs.next()) {
                    return creaUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load User by Email", ex);
        }
        return null;
    }

    @Override
    public Utente getUtenteByEmail(String email) throws DataException {
        try {
            utenteByEmail.setString(1, email);
            try (ResultSet rs = utenteByEmail.executeQuery()) {
                if (rs.next()) {
                    return creaUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Impossibile caricare Utente By Email", ex);
        }
        return null;
    }

    @Override
    public Utente getUtente(String email, String password) throws DataException {
        try {
            utenteByLogin.setString(1, email);
            utenteByLogin.setString(2, password);
            try (ResultSet rs = utenteByLogin.executeQuery()) {
                if (rs.next()) {
                    return creaUtente(rs);
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load User by Email", ex);
        }
        return null;
    }

}

