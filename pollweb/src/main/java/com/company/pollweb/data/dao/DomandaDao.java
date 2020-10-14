/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.data.proxy.DomandaProxy;
import com.company.pollweb.framework.data.DataException;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author gianlucarea
 */
public interface DomandaDao {

  public DomandaProxy creazioneDomanda(ResultSet rs) throws DataException;

  public DomandaProxy creazioneDomanda();

  void salvaDomanda(Domanda d) throws DataException;
}
