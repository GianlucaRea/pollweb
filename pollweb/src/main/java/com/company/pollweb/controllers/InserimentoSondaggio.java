package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.FailureResult;
import com.company.pollweb.utility.Serializer;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class InserimentoSondaggio extends PollWebBaseController {

    protected String  pattern, chooses;
    protected int max_lenght,min_lenght,max_num,min_num,max_chooses,min_chooses,data;
    protected JSONObject Vincoli;

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
                    p.setUtenteId(user.getId()); //TODO MANCA CHECK SE UTENTE ABILITATO
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
                        System.out.println(request.getParameter("domande["+i+"][ordine]"));
                        d.setOrdine(Integer.parseInt(request.getParameter("domande["+i+"][ordine]")));
                       if (request.getParameter("domande["+i+"][obbligo]") != null){
                           d.setObbligo(1);
                       }else{
                           d.setObbligo(0);
                       }
                       d.setTipologia(request.getParameter("domande["+i+"][tipologia]"));
                       String type = request.getParameter("domande["+i+"][tipologia]");
                        switch(type) {
                            case "testo_breve":
                                max_lenght = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMassimaTestoBreve]"));
                                pattern = request.getParameter("domande["+i+"][PatternTestoBreve]");
                                Vincoli = Serializer.testobreveToJSON(max_lenght,pattern);
                                d.setVincoli(Vincoli);
                                break;
                            case "testo_lungo":
                                max_lenght = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMassimaTestoLungo]"));
                                min_lenght = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMinimaTestoLungo]"));
                                pattern = request.getParameter("domande["+i+"][PatternTestoLungo]");
                                Vincoli = Serializer.testolungoToJSON(max_lenght,min_lenght,pattern);
                                d.setVincoli(Vincoli);
                                break;
                            case "numero":
                                max_num = Integer.parseInt(request.getParameter("domande["+i+"][Numeromassimo]"));
                                min_num = Integer.parseInt(request.getParameter("domande["+i+"][Numerominimo]"));
                                Vincoli = Serializer.numeroToJSON(max_num,min_num);
                                d.setVincoli(Vincoli);
                                break;
                            case "data":
                                data = 1;
                                Vincoli = Serializer.dataToJSON(data);
                                d.setVincoli(Vincoli);
                                break;
                            case "scelta_singola":
                                chooses = request.getParameter("domande["+i+"][sceltasingola]");
                                Vincoli = Serializer.sceltaSingolaToJSON(chooses);
                                d.setVincoli(Vincoli);
                            case "scelta_multipla":
                                chooses = request.getParameter("domande["+i+"][sceltamultipla]");
                                System.out.println(request.getParameter("domande["+i+"][Numerominimoscelte]"));
                                System.out.println(request.getParameter("domande["+i+"][Numeromassimoscelte]"));
                                min_chooses = Integer.parseInt(request.getParameter("domande["+i+"][Numerominimoscelte]"));
                                max_chooses = Integer.parseInt(request.getParameter("domande["+i+"][Numeromassimoscelte]"));
                                Vincoli = Serializer.sceltaMultiplaToJSON(chooses,min_chooses,max_chooses);
                                d.setVincoli(Vincoli);
                                break;
                        }
                        ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().salvaDomanda(d);
                    }
                    else {
                        request.setAttribute("message", "Errore aggiornamento della domanda");
                        action_error(request, response);
                    }
                }
                action_write(request, response, p.getId());
            }

        } catch (DataException e) {
            request.setAttribute("message", "Errore creazione del sondaggio");
            action_error(request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response, int sondaggioId) throws IOException {
        response.sendRedirect("/sondaggi/riepilogo?id=" + sondaggioId);
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
