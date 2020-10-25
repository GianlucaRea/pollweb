package com.company.pollweb.controllers.utenti;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
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

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class UpdateResponsabilePassword extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, DataException, IOException, TemplateManagerException {

        HttpSession s = checkSession(request);
        if (s!= null) {
            action_update_password(request, response, s);
        } else {
            action_redirect(request, response);
        }
    }

    private void action_update_password(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws TemplateManagerException {
        try {
            Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
            if(utente != null){
                utente.setPassword(request.getParameter("newPassword"));
                utente.setId(utente.getId());
                ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().updatePassword(utente);
                action_dashboard(request, response);
            }else{
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "L'utente non esiste");
                res.activate("/error.ftl", request, response);
            }
        } catch (DataException | TemplateManagerException e) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "L'utente non esiste");
            res.activate("/error.ftl", request, response);
        }
    }

    private void action_dashboard(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect("/dashboard");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
