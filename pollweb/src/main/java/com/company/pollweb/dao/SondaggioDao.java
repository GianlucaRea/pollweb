package com.company.pollweb.dao;

import com.company.pollweb.models.Sondaggio;
import com.company.pollweb.utility.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SondaggioDao {

    public static boolean inserimentoSondaggio(Sondaggio s) throws ClassNotFoundException, SQLException {

        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Sondaggio (user_id, titolo, testoiniziale, testofinale) VALUES (1, ?, ?, ?)");
        ps.setString(3, s.getTitolo());
        ps.setString(2, s.getTestoiniziale());
        ps.setString(1, s.getTestofinale());

        ps.execute();
        return true;
    }
}
