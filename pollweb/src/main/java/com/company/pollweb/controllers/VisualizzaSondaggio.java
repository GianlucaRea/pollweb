package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author alessandrodorazio
 */
public class VisualizzaSondaggio extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException {
        try {
            int sondaggioId = 1; //Integer.parseInt(request.getParameter("sondaggio_id"));
            action_check_visibility(request, response, sondaggioId);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void action_check_visibility(HttpServletRequest request, HttpServletResponse response, int sondaggioId) throws TemplateManagerException {
        int visibilita = 1; //1=pubblico, 2=privato
        if(visibilita == 0)  {
            // mostra direttamente il sondaggio
        } else {
            // inserimento email per verificare che sia stato invitato
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("sondaggio/formSondaggioPrivato.ftl", request, response);
        }
    }
}
