/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.controllers;

import com.company.pollweb.data.dao.LoginDao;
import com.company.pollweb.utility.FiltroAutenticazione;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author alessandrodorazio
 */
public class Login extends PoolWebBaseController {

    public void init(ServletConfig c) throws ServletException {
        super.init(c);
    }

    @Override
    public void doGet(HttpServletRequest in, HttpServletResponse out) throws ServletException, IOException {
        //check se l'utente risulta già loggato        
        if(FiltroAutenticazione.checkLoggato(in) == false) {
            RequestDispatcher dispatcher = in.getRequestDispatcher("/index.ftl");
            in.setAttribute("success", "Sei già loggato!");
            dispatcher.forward(in, out);
            //return;
        }

        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
        dispatcher.forward(in, out);
        return;
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest in, HttpServletResponse out) throws IOException, ServletException {
        String email = in.getParameter("email");
        String password = in.getParameter("password");

        if (email.length() == 0 || password.length() == 0) {
            RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
            in.setAttribute("error", "Campi mancanti");
            dispatcher.forward(in, out);
            return;
        }

        out.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = out.getWriter();

        if (LoginDao.validate(email, password)) {
            HttpSession session = in.getSession();
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            
            //TODO redirect in base, da decidere come procedere
            writer.print("LOGGATO");
        } else {
            RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
            in.setAttribute("error", "Combinazione email/password errata");
            dispatcher.forward(in, out);
        }
        writer.close();

    }

    public String getServletInfo() {
        return "Servlet di login, versione 1.0";
    }
}
