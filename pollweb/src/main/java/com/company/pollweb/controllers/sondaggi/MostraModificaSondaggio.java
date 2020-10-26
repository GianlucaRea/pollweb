package com.company.pollweb.controllers.sondaggi;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class MostraModificaSondaggio extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {

        try {
            HttpSession s = checkSession(request);

            //controllo che sia stato immesso l'id
            if(request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return ;
            }

            if (s!= null) {
                action_edit(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException | TemplateManagerException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void action_edit(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws TemplateManagerException, DataException, SQLException {
        int sondaggioId = Integer.parseInt(request.getParameter("id"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
        Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);
        if (sondaggio != null) {
            //controlli per verificare che il sondaggio sia chiuso
            if(sondaggio.getStato() == 1) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Non puoi modificare un sondaggio pubblicato");
                res.activate("/error.ftl", request, response);
                return ;
            }
            if(sondaggio.getStato() == 2){
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Non puoi modificare un sondaggio chiuso");
                res.activate("/error.ftl", request, response);
                return ;
            }

            //controllo che l'utente loggato possa modificare il sondaggio
            if(sondaggio.getUtenteId() == utente.getId() || sondaggio.getUtenteId() == 1) {
                try {
                        request.setAttribute("sondaggio", sondaggio);
                        List<Domanda> domande = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomandeBySondaggioID(sondaggioId);
                        for(int i = 0; i < domande.size(); i++)
                        {
                            Domanda domanda = domande.get(i);
                            String type = domanda.getTipologia();
                            switch(type) {
                                case "testo_breve":
                                    JSONObject tbJSON = domanda.getVincoli();
                                    domanda.setMax_length(tbJSON.getInt("max_length"));
                                    domanda.setPattern(tbJSON.getString("pattern"));
                                    break;
                                case "testo_lungo":
                                    JSONObject tlJSON = domanda.getVincoli();
                                    domanda.setMin_length(tlJSON.getInt("min_length"));
                                    domanda.setMax_length(tlJSON.getInt("max_length"));
                                    domanda.setPattern(tlJSON.getString("pattern"));
                                    break;
                                case "numero":
                                    JSONObject nJSON = domanda.getVincoli();
                                    domanda.setMin_num(nJSON.getInt("min_num"));
                                    domanda.setMax_num(nJSON.getInt("max_num"));
                                    break;
                                case "data":
                                    JSONObject dJSON = domanda.getVincoli();
                                    domanda.setDataSuccessivaOdierna(dJSON.getInt("date"));
                                    break;
                                case "scelta_singola":
                                    JSONObject ssJSON = domanda.getVincoli();
                                    domanda.setChooses(ssJSON.getJSONArray("chooses"));
                                    break;
                                case "scelta_multipla":
                                    JSONObject smJSON = domanda.getVincoli();
                                    domanda.setChooses(smJSON.getJSONArray("chooses"));
                                    domanda.setMin_chooses(smJSON.getInt("min_chooses"));
                                    domanda.setMax_chooses(smJSON.getInt("max_chooses"));
                                    break;
                            }
                        }
                        request.setAttribute("domande", domande);
                        TemplateResult res = new TemplateResult(getServletContext());
                        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                        res.activate("sondaggi/compilazione.ftl", request, response);
            } catch (DataException ex) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Non è possibile caricare il sondaggio");
                res.activate("/error.ftl", request, response);
                return ;
            }
        } else {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Permesso negato");
            res.activate("/error.ftl", request, response);
        }
    }  else {
                          TemplateResult res = new TemplateResult(getServletContext());
                          request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                          request.setAttribute("error", "Il sondaggio non esiste");
                          res.activate("/error.ftl", request, response);
                          return ;
        }
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
}
