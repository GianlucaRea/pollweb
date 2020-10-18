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

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class ChiudiSondaggio extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        try {
            HttpSession s = checkSession(request);

            if (request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return;
            }

            if (s != null) {
                action_pubblica_sondaggio(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException | TemplateManagerException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void action_pubblica_sondaggio(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws DataException, SQLException, TemplateManagerException {
        int sondaggioId = Integer.parseInt(request.getParameter("id"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);

        if (sondaggio.getStato() == 0) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Il sondaggio deve essere pubblicato prima di essere chiuso");
            res.activate("/error.ftl", request, response);
            return;
        }

        if (sondaggio.getStato() == 2) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Il sondaggio è già stato chiuso");
            res.activate("/error.ftl", request, response);
            return;
        }

        if (sondaggio.getUtenteId() == utente.getId() || utente.getId() == 1) {
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().chiudiSondaggio(sondaggio.getId());
            TemplateResult res = new TemplateResult(getServletContext());
            res.activate("sondaggi/chiuso.ftl", request, response);
        } else {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Permesso negato");
            res.activate("/error.ftl", request, response);
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
}
