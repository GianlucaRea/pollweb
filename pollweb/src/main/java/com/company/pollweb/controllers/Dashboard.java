package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Responsabile;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.FailureResult;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class Dashboard extends PollWebBaseController{

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession s = checkSession(request);
            if(s != null) {
                action_dashboard(request, response, s);
            } else {
                action_error(request, response);
            }
        } catch (IOException ioexc) {
            ioexc.printStackTrace();
        }
    }

    private void action_dashboard(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException, ServletException {
        try {
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();

            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());

            ArrayList<Sondaggio> sondaggi = new ArrayList<Sondaggio>();
            ArrayList<Utente> responsabili = new ArrayList<Utente>();

            if(utente.getRuolo() == 3) { //amministratore
                sondaggi = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().listaSondaggi();


                ((PollwebDataLayer) request.getAttribute("datalayer")).init();
                responsabili = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().listaResponsabili();
                request.setAttribute("responsabili", responsabili);
            } else if(utente.getRuolo() == 2) { //responsabile
                sondaggi = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().listaSondaggiResponsabile(utente.getId());

            } else { //utente o guest, accesso non autorizzato
                action_error(request, response);
                return ;
            }


            request.setAttribute("sondaggi", sondaggi);
            res.activate("dashboard.ftl", request, response);
        } catch (TemplateManagerException | DataException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
}
