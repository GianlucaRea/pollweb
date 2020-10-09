package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.dao.SondaggioDao;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.FailureResult;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author alessandrodorazio
 */
public class VisualizzaSondaggio extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException {
        try {
            int sondaggioId =  Integer.parseInt(request.getParameter("id"));
            action_check_visibility(request, response, sondaggioId);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void action_check_visibility(HttpServletRequest request, HttpServletResponse response, int sondaggioId) throws TemplateManagerException, SQLException {
        SondaggioDao sondaggioDao = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO();
        Sondaggio sondaggio = sondaggioDao.getSondaggio(sondaggioId);
        if(sondaggio.getId() == -1) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Questo sondaggio non esiste oppure non è più disponibile");
            res.activate("/error.ftl", request, response);
            return ;
        }
        System.out.println(sondaggio.getVisibilita());
        if(sondaggio.getVisibilita() == 1)  {
            // mostra direttamente il sondaggio
            System.out.println("SONDAGGIO VISIBILE");
        } else {
            // verifica se è stata inserita l'email e l'utente può accedervi
            String email = request.getParameter("email");
            if(email != null && sondaggioDao.isEmailAbilitataAllaCompilazione(sondaggio, email)) {
                System.out.println("EMAIL ABILITATA");
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("sondaggio/compilazione.ftl", request, response);
            } else { // altrimenti, rimanda al form di inserimento email per verificare che sia stato invitato
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("sondaggio/formSondaggioPrivato.ftl", request, response);
            }


        }
    }
}
