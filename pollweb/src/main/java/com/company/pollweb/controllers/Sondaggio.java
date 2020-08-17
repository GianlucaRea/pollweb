/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.controllers;
import com.company.pollweb.utenti.dao.SondaggioDao;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author gianlucarea
 */
public class Sondaggio extends HttpServlet{
     @Override
     public void init(ServletConfig c) throws ServletException{
        super.init(c);
    }
    
    @Override
    public void doGet(HttpServletRequest in, HttpServletResponse out) throws ServletException, IOException {
        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("/sondaggi/creazione.ftl");
        dispatcher.forward(in, out);
    }
    
    public void doPost(HttpServletRequest in, HttpServletResponse out) throws IOException, ServletException {
        String testofinale = in.getParameter("testofinale");
        String testoiniziale = in.getParameter("testoiniziale");
        String titolo = in.getParameter("titolo");
        
        if(testofinale.length() == 0 || testoiniziale.length() == 0 || titolo.length()== 0){
            RequestDispatcher dispatcher = in.getRequestDispatcher("creazione.ftl");
            in.setAttribute("error", "Campi mancanti");
            dispatcher.forward(in, out);
            return ;
        }
        
        out.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = out.getWriter();  
        

        writer.close();
        
    }
    
    public String getServletInfo(){
        return "Servlet di login, versione 1.0";
    }
}
