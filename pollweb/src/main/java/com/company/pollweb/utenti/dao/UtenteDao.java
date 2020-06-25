/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utenti.dao;

import com.company.pollweb.models.Utente;
import com.company.pollweb.utility.Database;
import com.company.pollweb.utility.ValidazioneCampi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alessandrodorazio
 */
public class UtenteDao {
    public static int storeUtente(Utente u) throws ClassNotFoundException, SQLException {
        
            Connection con = Database.getConnection();
            
            //TODO validazione dei dati
            if(ValidazioneCampi.emailPattern(u.getEmail()) == false || u.getNome().length() == 0 || u.getCognome().length() == 0) {
                return -2;
            }
            
            //TODO check se l'utente con quelle credenziali già esiste
            
            //TODO generare password casuale
            
            //TODO passare parametro tipo
            
            PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Utente (nome, cognome, email, password, ruolo_id) VALUES (?, ?, ?, 'password', 1)");
            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getEmail());
        
            ps.execute();
            return 1;
    }
    
    public static boolean isAdmin(String email) throws ClassNotFoundException, SQLException {
        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS conteggio FROM pollweb.Utente WHERE email=?");
        ps.setString(1, email);
        ResultSet ris = ps.executeQuery();
        while(ris.next()) {
            if(ris.getInt("conteggio") == 1) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isResponsabile(String email) {
        return true;
    }
}
