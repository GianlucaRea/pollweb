/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utenti.dao;

import com.company.pollweb.models.Sondaggio;
import com.company.pollweb.utility.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author gianlucarea
 */
public class SondaggioDao {
        public static boolean storeSondaggio(Sondaggio s) throws ClassNotFoundException, SQLException {
        
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Sondaggio (testofinale, testoiniziale, titolo, user_id) VALUES (?, ?, ?,1)");
            ps.setString(1, s.getTestofinale());
            ps.setString(2, s.getTestoiniziale());
            ps.setString(3, s.getTitolo());
        
            ps.execute();
            return true;
    }
}
