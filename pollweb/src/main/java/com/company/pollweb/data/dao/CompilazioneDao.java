package com.company.pollweb.data.dao;

import com.company.pollweb.data.models.Compilazione;
import com.company.pollweb.data.proxy.CompilazioneProxy;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.utility.Pair;
import org.json.JSONArray;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CompilazioneDao {
    CompilazioneProxy creazioneCompilazione();

    CompilazioneProxy creazioneCompilazione(ResultSet rs) throws DataException;

    void salvaCompilazione(Compilazione c) throws DataException;

    void salvaCompilazione(int compilazioneId, Map<Integer, JSONArray> risposte) throws DataException, SQLException;

    Compilazione getCompilazione(int sondaggioId, String email) throws SQLException;

    List<String> getUserList(int sondaggioId) throws  DataException;

    List<String> getRisposteByDomandaId(int domandaId) throws DataException;

    ArrayList<Pair> getEmailByDomandaId(int domandaId) throws DataException;

    List<String> getRisposteBySondaggioAndEmail(int sondaggioid , String email) throws DataException;
}
