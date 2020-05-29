/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utenti.dao;

import com.company.pollweb.models.Utente;
import com.company.pollweb.utility.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alessandrodorazio
 */
public class UtenteDao {
    public static boolean storeUtente(Utente u) throws ClassNotFoundException, SQLException {
        
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Utente (nome, cognome, email, password, ruolo_id) VALUES (?, ?, ?, 'password', 1)");
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
        
            ps.execute();
            return true;
    }
}
