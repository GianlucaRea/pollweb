package com.company.pollweb.controllers.sondaggi;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.framework.data.DataException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class InvitaUtentiSondaggio extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {

        try {
            HttpSession s = checkSession(request);
            int sondaggioId = Integer.parseInt(request.getParameter("id"));
            if (s!= null) {
                action_invite(request, response, s, sondaggioId);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void action_invite(HttpServletRequest request, HttpServletResponse response, HttpSession s, int sondaggioId) throws SQLException, IOException, ServletException, DataException {
        String[] listaInvitati = request.getParameterValues("nuovoInvitato");
        if(listaInvitati != null) {
            ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().invitaUtenti(sondaggioId, listaInvitati);
        }

        //input da file
        Part csvPart = request.getPart("invitatiCSV");
        InputStream csv = csvPart.getInputStream();
        String result = convertStreamToString(csv);
        listaInvitati = result.replace("\n", "").replace("\r", "").replace(" ","").split("\\;", -1);
        if(listaInvitati.length > 0) {
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().invitaUtenti(sondaggioId, listaInvitati);
        }
        int nuovaVisibilita = Integer.parseInt(request.getParameter("visibilitaSondaggio"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().modificaVisibilita(sondaggioId, nuovaVisibilita);
    }

    private void action_change_visibility(HttpServletRequest request, HttpServletResponse response, HttpSession s, int sondaggioId) throws DataException, SQLException {
        int nuovaVisibilita = Integer.parseInt(request.getParameter("visibilitaSondaggio"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().modificaVisibilita(sondaggioId, nuovaVisibilita);
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

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
