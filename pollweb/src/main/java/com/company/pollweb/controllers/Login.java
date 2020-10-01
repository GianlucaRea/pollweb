package com.company.pollweb.controllers;

import  com.company.pollweb.data.dao.PollwebDataLayer;
import  com.company.pollweb.data.models.Utente;
import  com.company.pollweb.framework.data.DataException;
import  com.company.pollweb.framework.result.FailureResult;
import  com.company.pollweb.framework.result.SplitSlashesFmkExt;
import  com.company.pollweb.framework.result.TemplateManagerException;
import  com.company.pollweb.framework.result.TemplateResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpSession;

import static com.company.pollweb.framework.security.SecurityLayer.*;

public class Login extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession s = checkSession(request);
            if (s!=null){
                action_update(request, response, s);
            } else if((request.getParameter("email")!=null)&&(request.getParameter("password")!=null)) {
                action_write(request, response, request.getParameter("email"), request.getParameter("password"));
            } else {
                action_default(request, response);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession s = checkSession(request);
            if(s!=null){
                System.out.println("loggato");
            } else {
                System.out.println("non loggato");
            }
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            res.activate("login.ftl", request, response);
        } catch (TemplateManagerException e) {
            e.printStackTrace();
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response, String email, String password) throws IOException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            Utente newUser = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(email, HashingMaps(password));
            if (newUser!= null) {  HttpSession session=request.getSession();
                createSession(request, newUser.getNome(),newUser.getEmail());
                response.sendRedirect("/sondaggi/nuovo_sondaggio");
            } else {
                request.setAttribute("login_error", "Username o password errati");
                res.activate("login.ftl", request, response);
            }
        } catch (DataException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void action_update(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws IOException, ServletException, TemplateManagerException {
        response.sendRedirect(request.getAttribute("urlrequest").toString());
    }

    //Necessario per gestire le return di errori
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }
}
