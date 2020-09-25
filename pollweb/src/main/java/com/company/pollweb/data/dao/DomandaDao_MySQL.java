package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.framework.data.DAO;
import com.company.pollweb.framework.data.DataLayer;
import com.company.pollweb.utility.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DomandaDao_MySQL extends DAO implements DomandaDao{

    public DomandaDao_MySQL(DataLayer d) {
        super(d);
    }

    public void storeDomanda(Domanda d) throws ClassNotFoundException, SQLException {

        Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement("INSERT INTO pollweb.Domanda (nota, testo, tipologia, sondagigo_id, obbligo) VALUES (?, ?, 1, ?, ?)");
        ps.setString(1, d.getNota());
        ps.setString(2, d.getTesto());
        ps.setInt(3, d.getTipologia());
        ps.setInt(4, d.getSondaggio_id());
        ps.setInt(5, d.getObbligo());

        ps.execute();
    }
}
