/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.dao;

import com.company.pollweb.models.Domanda;
import com.company.pollweb.utility.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author gianlucarea
 */
public class DomandaDao {
          public static boolean storeDomanda(Domanda d) throws ClassNotFoundException, SQLException {
        
            Connection con = Database.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Domanda (nota, testo, tipologia, sondagigo_id, obbligo) VALUES (?, ?, 1, ?, ?)");
            ps.setString(1, d.getNota());
            ps.setString(2, d.getTesto());
            ps.setInt(3, d.getTipologia());
            ps.setInt(4, d.getSondaggio_id());
            ps.setInt(5, d.getObbligo());
        
            ps.execute();
            return true;
    }
}
