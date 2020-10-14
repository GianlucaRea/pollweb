package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.FailureResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class InserimentoSondaggio extends PollWebBaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {

        try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                action_poll(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void action_poll(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException {
        try {
            if (request.getParameterMap() != null) {
                Sondaggio p;
                Utente user;
                Domanda d ;
                p = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
                user = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
                if (p != null) {
                    p.setTitolo(request.getParameter("titolo"));
                    p.setTestoiniziale(request.getParameter("testoiniziale"));
                    p.setTestofinale(request.getParameter("testofinale"));
                    p.setUtenteId(user.getId());
                    ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().salvaSondaggio(p);
                }
                else {
                    request.setAttribute("message", "Errore aggiornamento del sondaggio");
                    action_error(request, response);
                }
                for(int i = 1 ;request.getParameter("domande["+i+"][testo]") != null ; i++){
                ((PollwebDataLayer) request.getAttribute("datalayer")).init();
                   d = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().creazioneDomanda();
                    if (d != null) {
                        d.setSondaggio_id(p.getId());
                        d.setTesto(request.getParameter("domande["+i+"][testo]"));
                        d.setNota(request.getParameter("domande["+i+"][nota]"));

                        //obbligo
                        //ordine
                        //tipologia

                        ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().salvaDomanda(d);

                    }
                    else {
                        request.setAttribute("message", "Errore aggiornamento della domanda");
                        action_error(request, response);
                    }
                }
                action_write(request, response);
            }

        } catch (DataException e) {
            request.setAttribute("message", "Errore creazione del sondaggio");
            action_error(request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/inserimentoriuscito");
    }

    private void action_redirect(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        try {
            request.setAttribute("urlrequest", request.getRequestURL());
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } catch (ServletException e) {
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
