package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Utente;
import com.company.pollweb.data.proxy.UtenteProxy;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtendeDao_MySQL extends DAO implements UtenteDao {

    private final String SELECT_UTENTE_BY_EMAIL = "SELECT * FROM utente WHERE email = ?";
    private final String SELECT_UTENTE_BY_NAME_AND_PASSWORD ="SELECT * FROM utente where email = ? AND password = ?";

    private PreparedStatement utenteByEmail;
    private PreparedStatement utenteByLogin;



    public UtendeDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            utenteByEmail = connection.prepareStatement(SELECT_UTENTE_BY_EMAIL);
            utenteByLogin = connection.prepareStatement(SELECT_UTENTE_BY_NAME_AND_PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void destroy() throws DataException {
        try {
            utenteByEmail.close();
        } catch (SQLException ex){
            //
        }
        super.destroy();
    }

    @Override
    public UtenteProxy creaUtente() {
        return new UtenteProxy(getDataLayer());
    }

    @Override
    public UtenteProxy creaUtente(ResultSet res) throws DataException {
        UtenteProxy a = creaUtente();
        try {
            a.setNome(res.getString("nome"));
            a.setEmail(res.getString("email"));
            a.setPassword(res.getString("password"));
            a.setRuolo(1);
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }


    public Utente getUtente(String email) throws DataException {
        try {
            utenteByEmail.setString(1, email);
            try (ResultSet rs = utenteByEmail.executeQuery()) {
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

