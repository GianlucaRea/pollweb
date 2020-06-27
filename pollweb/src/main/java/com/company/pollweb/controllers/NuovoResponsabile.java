/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.controllers;

import com.company.pollweb.models.Utente;
import com.company.pollweb.utenti.dao.UtenteDao;
import com.company.pollweb.utility.FiltroAutenticazione;
import com.company.pollweb.utility.ValidazioneCampi;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author alessandrodorazio
 */
public class NuovoResponsabile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NuovoResponsabile</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NuovoResponsabile at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest in, HttpServletResponse out)
            throws ServletException, IOException {
        
        //controllo se utente loggato
        FiltroAutenticazione.soloLoggati(in, out);
        //controllo se utente loggato è amministratore
        FiltroAutenticazione.soloAdmin(in, out);
        
        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("/utenti/nuovoResponsabile.ftl");
        dispatcher.forward(in, out);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest in, HttpServletResponse out)
            throws ServletException, IOException {
                
        String nome = in.getParameter("nome");
        String cognome = in.getParameter("cognome");
        String email = in.getParameter("email");
        int risInserimentoUtente; //verifica se l'inserimento dell'utente è andato a buon fine
        Utente u; //conterrà i dati dell'utente che creeremo, prima di salvarlo sul db
        
        //controllo se utente loggato
        FiltroAutenticazione.soloLoggati(in, out);
        //controllo se utente loggato è amministratore
        FiltroAutenticazione.soloAdmin(in, out);
       
        //validazione dei campi
        if(nome.length() == 0 || cognome.length() == 0 || email.length() == 0) { //verifica se i campi sono stati compilati
            RequestDispatcher dispatcher = in.getRequestDispatcher("/utenti/nuovoResponsabile.ftl");
            in.setAttribute("error", "Tutti i campi devono essere compilati!");
            dispatcher.forward(in, out);
            return ;
        }
        
        if(ValidazioneCampi.emailPattern(email) == false) { //l'email inserita ha un pattern corretto?
            RequestDispatcher dispatcher = in.getRequestDispatcher("/utenti/nuovoResponsabile.ftl");
            in.setAttribute("error", "Inserire un indirizzo email valido!");
            dispatcher.forward(in, out);
            return ;
        }
        //fine validazione
        
        u = new Utente(nome, cognome, email, 2);
        
        try {
            //inserimento nuovo responsabile
            risInserimentoUtente = UtenteDao.storeUtente(u);
            RequestDispatcher dispatcher = in.getRequestDispatcher("/utenti/nuovoResponsabile.ftl");
            if (risInserimentoUtente == 1) { //inserimento avvenuto con successo
                in.setAttribute("success", "Responsabile inserito nel sistema");
                
            } else {
                if(risInserimentoUtente == -1) { //utente già esistente
                    in.setAttribute("error", "L'email inserita appartiene già ad un altro utente!");
                }
                if(risInserimentoUtente == -2) { //dati non validi
                    in.setAttribute("error", "I dati inseriti non sono corretti. Compila tutti i campi ed inserisci un'email valida");
                }
            }
            dispatcher.forward(in, out);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NuovoResponsabile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(NuovoResponsabile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Creazione nuovo responsabile";
    }// </editor-fold>

}
