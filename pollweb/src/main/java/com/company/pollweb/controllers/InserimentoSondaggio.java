package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        action_poll1(request, response);
       /* try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                action_poll1(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } */
    }

    private void action_poll(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException {
        try {
            if (request.getParameterMap() != null) {
                Sondaggio p;
                Utente user;
                p = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
                user = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((String) s.getAttribute("user_email"));
                if (p != null) {
                    p.setTitolo(request.getParameter("titolo"));
                    p.setTestoiniziale(request.getParameter("testoapertura"));
                    p.setTestofinale(request.getParameter("testochiusura"));
                    p.setUtenteEmail(user.getEmail());
                    ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
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

    private void action_poll1(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {
        if (request.getParameterMap() != null) {
            Sondaggio p;
            //Utente user;
            p = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().creazioneSondaggio();
            //user = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((String) s.getAttribute("user_email"));
            if (p != null) {
                p.setTitolo(request.getParameter("titolo"));
                p.setTestoiniziale(request.getParameter("testoiniziale"));
                p.setTestofinale(request.getParameter("testofinale"));
                p.setUtenteEmail("utente@mail.it");
                ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().salvaSondaggio(p);
                action_write(request, response);
            }
            else {
                request.setAttribute("message", "Errore aggiornamento del sondaggio");
                action_error(request, response);
            }
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
