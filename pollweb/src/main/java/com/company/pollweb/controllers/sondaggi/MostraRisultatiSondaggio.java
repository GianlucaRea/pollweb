package com.company.pollweb.controllers.sondaggi;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class MostraRisultatiSondaggio extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, TemplateManagerException, DataException, SQLException {
        try {
            HttpSession s = checkSession(request);
            //controllo che sia stato immesso l'id
            if(request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "I risultati del sondaggio non sono stati trovati");
                res.activate("/error.ftl", request, response);
                return ;
            }

            if (s!= null) {
                action_edit(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (TemplateManagerException e) {
            e.printStackTrace();
        }
    }

    private void action_redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            request.setAttribute("urlrequest", request.getRequestURL());
            RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private void action_edit(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws SQLException, DataException, TemplateManagerException {
        int sondaggioId = Integer.parseInt(request.getParameter("id"));
        PollwebDataLayer pd = (PollwebDataLayer) request.getAttribute("datalayer");
        Utente utente = pd.getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
        Sondaggio sondaggio = pd.getSondaggioDAO().getSondaggio(sondaggioId);
        if(sondaggio != null) {
            if (sondaggio.getUtenteId() == utente.getId() || utente.getId() == 1) {
                request.setAttribute("sondaggio", sondaggio);
                if(sondaggio.getVisibilita() == 2){
                List<Integer> utente_ids = pd.getCompilazioneDAO().getUserListIds(sondaggioId);
                int i = 1;
                if (utente_ids != null) {
                    Map<String, List> risultati = new HashMap<>();
                    for (int utente_id : utente_ids) {
                        Utente u = pd.getUtenteDAO().getUtente(utente_id);
                        List<String> risposte = pd.getCompilazioneDAO().getRisposteBySondaggioAndUtente(sondaggioId, u.getId());
                        risultati.put(u.getEmail(), risposte);
                    }
                    for (String email : risultati.keySet()) {
                        request.setAttribute("email[" + i + "]", email);
                        request.setAttribute("risposte[" + i + "]", risultati.get(email));
                        i++;
                    }
                    TemplateResult res = new TemplateResult(getServletContext());
                    res.activate("sondaggi/visualizzaRisultato.ftl", request, response);
                } else {
                    TemplateResult res = new TemplateResult(getServletContext());
                    request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                    request.setAttribute("error", "Il sondaggio non è stato compilato da nessuno");
                    res.activate("/error.ftl", request, response);
                }
            }else{
                    List<Integer> ids = pd.getCompilazioneDAO().getCompilazioneListIds(sondaggioId);
                    int i = 1;
                    if (ids != null) {
                        Map<Integer, List> risultati = new HashMap<>();
                        for (int id : ids) {
                            List<String> risposte = pd.getCompilazioneDAO().getRisposteByCompilazioneId(id);
                            risultati.put(id, risposte);
                        }
                        for (int id : risultati.keySet()) {
                            request.setAttribute("key[" + i + "]", id);
                            request.setAttribute("risposte[" + i + "]", risultati.get(id));
                            i++;
                        }
                        TemplateResult res = new TemplateResult(getServletContext());
                        res.activate("sondaggi/visualizzaRisultato.ftl", request, response);
                    } else {
                        TemplateResult res = new TemplateResult(getServletContext());
                        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                        request.setAttribute("error", "Il sondaggio non è stato compilato da nessuno");
                        res.activate("/error.ftl", request, response);
                    }
                }
            } else {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Permesso negato");
                res.activate("/error.ftl", request, response);
            }
        } else {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Il sondaggio non esiste");
            res.activate("/error.ftl", request, response);
        }
    }



}
