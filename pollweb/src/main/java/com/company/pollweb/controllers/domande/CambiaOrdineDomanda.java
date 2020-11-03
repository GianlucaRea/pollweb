package com.company.pollweb.controllers.domande;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class CambiaOrdineDomanda extends PollWebBaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, TemplateManagerException {

        try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                action_update(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException | DataException e) {
            e.printStackTrace();
        }
    }

    private void action_update(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws DataException, TemplateManagerException {
        try {
            int sondaggioId = Integer.parseInt(request.getParameter("id"));
            int domandaId1 = Integer.parseInt(request.getParameter("domanda_id1"));
            int domandaId2 = Integer.parseInt(request.getParameter("domanda_id2"));
            PollwebDataLayer pd = ((PollwebDataLayer) request.getAttribute("datalayer"));
            Domanda d1 = pd.getDomandaDAO().getDomandaByID(domandaId1);
            Domanda d2 = pd.getDomandaDAO().getDomandaByID(domandaId1);
            if (d1 != null && d2 != null ) {
                pd.getDomandaDAO().UpdateOrdine(d1.getId() , d2.getOrdine());
                pd.getDomandaDAO().UpdateOrdine(d2.getId() , d1.getOrdine());
                action_write(request,response,sondaggioId);
            } else {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "La domanda non esiste");
                res.activate("/error.ftl", request, response);
            }
        }catch (TemplateManagerException | IOException e){
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Errore Modifica Sondaggio");
            res.activate("/error.ftl", request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response, int sondaggioId) throws IOException {
        response.sendRedirect("/sondaggi/riepilogo?id=" + sondaggioId);
    }


    private void action_redirect(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        response.sendRedirect("/login");
    }
}
