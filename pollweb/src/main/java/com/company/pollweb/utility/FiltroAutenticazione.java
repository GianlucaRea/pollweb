/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.pollweb.utility;

//import com.company.pollweb.controllers.NuovoResponsabile;
import com.company.pollweb.data.dao.UtenteDao;
import com.company.pollweb.data.models.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alessandrodorazio
 */
public class FiltroAutenticazione {
    
    public static boolean checkLoggato(HttpServletRequest in) {
        HttpSession session= in.getSession(false); 
        if(session == null || session.getAttribute("email") == null) {
            return false;
        }
        
        return true;
    }
    
    public static void soloLoggati(HttpServletRequest in, HttpServletResponse out) throws ServletException, IOException {
        //controllo se l'utente è loggato
        HttpSession session=in.getSession(false);  
        
        if(session == null || session.getAttribute("email") == null) {
            out.setContentType("text/html;charset=UTF-8");
            RequestDispatcher dispatcher = in.getRequestDispatcher("/error.ftl");
            in.setAttribute("error", "Non sei autorizzato ad accedere a questa pagina");
            out.setStatus(403);
            dispatcher.forward(in, out);
            return ;
        }
        
    }
    
    public static void soloAdmin(HttpServletRequest in, HttpServletResponse out) throws ServletException, IOException{
        //controllo se l'utente loggato è amministratore
        HttpSession session=in.getSession(false); 
        String emailUtenteLoggato=(String)session.getAttribute("email");
        try {
            if(emailUtenteLoggato == null || UtenteDao.isAdmin(emailUtenteLoggato) == false) {
                out.setContentType("text/html;charset=UTF-8");
                RequestDispatcher dispatcher = in.getRequestDispatcher("/error.ftl");
                in.setAttribute("error", "Non sei autorizzato ad accedere a questa pagina");
                dispatcher.forward(in, out);
                return ;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);//Al posto di utente nuovo responsabile
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);//Al posto di utente nuovo responsabile
        }
    }
}
