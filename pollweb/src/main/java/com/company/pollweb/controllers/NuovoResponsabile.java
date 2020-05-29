/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.controllers;

import com.company.pollweb.models.Utente;
import com.company.pollweb.utenti.dao.UtenteDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        out.setContentType("text/html;charset=UTF-8");
        RequestDispatcher dispatcher = in.getRequestDispatcher("nuovoResponsabile.ftl");
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
        
        //TODO controllo se l'utente loggato è amministratore
        
        //TODO validazione dei campi
        
        Utente u = new Utente(nome, cognome, email);
        
        try {
            //inserimento nuovo responsabile
            if(UtenteDao.storeUtente(u)) {
                //mostrare pagina nuovo responsabile con esito
                RequestDispatcher dispatcher = in.getRequestDispatcher("login.ftl");
                in.setAttribute("error", "Credenziali errate");
                dispatcher.forward(in, out);
            }
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
