/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Domanda;

import java.sql.SQLException;
/**
 *
 * @author gianlucarea
 */
public interface DomandaDao {

  public void storeDomanda(Domanda d) throws ClassNotFoundException, SQLException ;
}
