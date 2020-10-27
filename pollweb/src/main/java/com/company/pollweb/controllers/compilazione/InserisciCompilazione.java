package com.company.pollweb.controllers.compilazione;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.implementation.CompilazioneImpl;
import com.company.pollweb.data.models.Compilazione;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;
import org.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class InserisciCompilazione extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        try {
            if(request.getParameter("sondaggioId") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return ;
            }

            int sondaggioId = Integer.parseInt(request.getParameter("sondaggioId"));
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);

            //TODO Condizione di controllo per vedere se l'utente può accedere al sondaggio
            action_valida_compilazione(request,response);
            action_inserisci_compilazione(request, response, sondaggio);

        } catch (TemplateManagerException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void action_valida_compilazione(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void action_inserisci_compilazione(HttpServletRequest request, HttpServletResponse response, Sondaggio sondaggio) throws DataException, SQLException {
        //se sondaggio pubblico
        //inserisci compilazione
        Compilazione c = new CompilazioneImpl();
        if(sondaggio.getVisibilita() == 1) {
            //sondaggio pubblico
            c.setSondaggioId(sondaggio.getId());
            ((PollwebDataLayer) request.getAttribute("datalayer")).getCompilazioneDAO().salvaCompilazione(c);
        } else {
            //sondaggio privato
            c.setSondaggioId(sondaggio.getId());
            c.setEmail(request.getParameter("email"));
            ((PollwebDataLayer) request.getAttribute("datalayer")).getCompilazioneDAO().salvaCompilazione(c);
        }

        //se sondaggio privato
        //prendi id compilazione



        //inserisci domande
        Map<Integer, JSONArray> risposte = new HashMap<Integer, JSONArray>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {

            String paramName = parameterNames.nextElement();
            if(paramName.startsWith("domande[")) {
                String[] paramValues = request.getParameterValues(paramName);
                JSONArray risposta = new JSONArray();
                for (String paramValue : paramValues) {
                    risposta.put(paramValue);
                }
                risposte.put(Integer.valueOf(paramName.replace("domande[","").replace("]", "")), risposta);
            }
        }

        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        ((PollwebDataLayer) request.getAttribute("datalayer")).getCompilazioneDAO().salvaCompilazione(c.getId(), risposte);
    }
}
