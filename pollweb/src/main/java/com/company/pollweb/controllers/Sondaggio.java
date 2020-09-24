/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.controllers;
import com.company.pollweb.dao.SondaggioDao;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 *
 * @author gianlucarea
 */
@WebServlet("/sondaggio/nuovo_sondaggio")
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
        String user_id = "utente@utente.it";
        String titolo = in.getParameter("titolo");
        String testoiniziale = in.getParameter("testoiniziale");
        String testofinale = in.getParameter("testofinale");

        if(testofinale.length() == 0 || testoiniziale.length() == 0 || titolo.length()== 0){
            RequestDispatcher dispatcher = in.getRequestDispatcher("/sondaggi/creazione.ftl");
            in.setAttribute("error", "Campi mancanti");
            dispatcher.forward(in, out);
            return ;
        }

        com.company.pollweb.models.Sondaggio sondaggio = new com.company.pollweb.models.Sondaggio();
        sondaggio.setUserID(user_id);
        sondaggio.setTitolo(titolo);
        sondaggio.setTestoiniziale(testoiniziale);
        sondaggio.setTestofinale(testofinale);

        try {
            SondaggioDao.inserimentoSondaggio(sondaggio);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("/sondaggi/creazione.ftl");
        dispatcher.forward(in, out);
    }
    
    public String getServletInfo(){
        return "Servlet di login, versione 1.0";
    }
}
