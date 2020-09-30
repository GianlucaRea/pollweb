package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Utente;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.data.DataLayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtendeDao_MySQL extends DAO implements UtenteDao {

    private final String SELECT_UTENTE_BY_EMAIL = "SELECT * FROM utente WHERE email = ?";

    private PreparedStatement utenteByEmail;


    public UtendeDao_MySQL(DataLayer d) {
        super(d);
    }

    public void init() throws DataException {
        try {
            super.init();
            utenteByEmail = connection.prepareStatement(SELECT_UTENTE_BY_EMAIL);
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

    public Utente getUtente(String email) throws DataException {
        try {
            utenteByEmail.setString(1, email);
            try (ResultSet rs = utenteByEmail.executeQuery()) {
                if (rs.next()) {
                    return createUser(rs); //Metodo da fare!!
                }
            }
        } catch (SQLException ex) {
            throw new DataException("Unable to load User by ID", ex);
        }
        return null;
    }

}
