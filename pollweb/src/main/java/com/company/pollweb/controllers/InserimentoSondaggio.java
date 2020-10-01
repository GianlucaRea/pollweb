package com.company.pollweb.controllers;

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

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class InserimentoSondaggio extends PollWebBaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
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
                p = ((com.company.pollweb.data.dao.PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
                user = ((com.company.pollweb.data.dao.PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((String) s.getAttribute("user_email"));
                if (p != null) {
                    p.setTitolo(request.getParameter("titolo"));
                    p.setTestoiniziale(request.getParameter("testoapertura"));
                    p.setTestofinale(request.getParameter("testochiusura"));
                    p.setUserID(user.getEmail());
                    ((com.company.pollweb.data.dao.PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
                    action_write(request, response);
                }
                else {
                    request.setAttribute("message", "Errore aggiornamento del sondaggio");
                    action_error(request, response);
                }
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
            RequestDispatcher rd = request.getRequestDispatcher("/accedi");
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
