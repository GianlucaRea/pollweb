package com.company.pollweb.controllers;


import com.company.pollweb.data.dao.PollwebDataLayer;
import  com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.security.SecurityLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


public class Login extends PollWebBaseController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException{
        try{
            //controllo se l'utente è già in sessione
            if(SecurityLayer.checkSession(request) != null){
                response.sendRedirect("Home");
            }
            if(request.getParameter("login") != null){
                String email = SecurityLayer.addSlashes(request.getParameter("email").toLowerCase());
                PollwebDataLayer dl = ((PollwebDataLayer)request.getAttribute("datalayer"));

                try{
                    String referrer = null;
                    if(request.getParameter("referrer") != null){

                        referrer = SecurityLayer.addSlashes(request.getParameter("referrer"));
                        request.setAttribute("referrer", referrer);

                    }

                    if(email != null && ! email.equals("")){

                        if(dl.getUtenteDAO().getUtenteByEmail(email) != null){
                            action_login_utente(request,response);
                        }else{
                            if(referrer != null){
                                response.sendRedirect("Login?referrer=" + URLEncoder.encode(referrer, "UTF-8"));
                            }else{
                                response.sendRedirect("Login");
                            }
                        }

                    }else{

                        if(referrer != null){
                            response.sendRedirect("Login?referrer=" + URLEncoder.encode(referrer, "UTF-8"));
                        }else{
                            response.sendRedirect("Login");
                        }

                    }
                } catch (DataException ex) {
                    new ErrorGenerator(Login.class.getName(), request, response, getServletContext()).handle_exception(ex, null);
                }
            }else{
                //rimando alla pagina di login per riempire la form di login
                renderizza_form_login(request,response);
            }
        }catch (IOException ex) {
            new ErrorGenerator(Login.class.getName(), request, response, getServletContext()).handle_exception(ex, null);
        }
    }

    private void renderizza_form_login(HttpServletRequest request, HttpServletResponse response) {
    }

    private void action_login_utente(HttpServletRequest request, HttpServletResponse response) {
    }
}
