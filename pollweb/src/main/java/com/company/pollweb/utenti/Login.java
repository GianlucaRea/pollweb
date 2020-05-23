/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utenti;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;  

/**
 *
 * @author alessandrodorazio
 */

public class Login extends HttpServlet {
    public void init(ServletConfig c) throws ServletException{
        super.init(c);
    }
    
    @Override
    public void doGet(HttpServletRequest in, HttpServletResponse out) throws ServletException, IOException {
        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
        dispatcher.forward(in, out);
    }
    
    public void doPost(HttpServletRequest in, HttpServletResponse out) throws IOException, ServletException {
        String email = in.getParameter("email");
        String password = in.getParameter("password");
        
        out.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = out.getWriter();  
        
        if(LoginDao.validate(email, password)) {
            writer.print("LOGGATO");
        } else {
            RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
            in.setAttribute("error", "Credenziali errate");
            dispatcher.forward(in, out);
        }
        writer.close();
        
    }
    
    public String getServletInfo(){
        return "Servlet di login, versione 1.0";
    }
}
