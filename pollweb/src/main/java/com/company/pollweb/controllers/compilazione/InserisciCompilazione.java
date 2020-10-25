package com.company.pollweb.controllers.compilazione;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class InserisciCompilazione extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        try {
            if(request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return ;
            }

            int sondaggioId = Integer.parseInt(request.getParameter("sondaggioId"));
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            //TODO Condizione di controllo per vedere se l'utente può accedere al sondaggio
            action_valida_sondaggio(request,response);
            action_inserisci_sondaggio(request, response);

        } catch (TemplateManagerException e) {
            e.printStackTrace();
        }
    }

    protected void action_valida_sondaggio(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void action_inserisci_sondaggio(HttpServletRequest request, HttpServletResponse response) {

    }
}
