package com.company.pollweb.controllers.compilazione;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.dao.SondaggioDao;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MostraCompilazione extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        try {
            if(request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return ;
            }

            int sondaggioId =  Integer.parseInt(request.getParameter("id"));
            SondaggioDao sondaggioDao = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO();
            Sondaggio sondaggio = sondaggioDao.getSondaggio(sondaggioId);
            if(sondaggio.getStato() == 0 || sondaggio.getStato() == 2) {
                //verifica se il sondaggio è attivo
                action_check_is_active(request, response, sondaggio);
            } else {
                //verifica se il sondaggio è visibile
                action_check_visibility(request, response, sondaggio, sondaggioDao);
            }

        } catch (TemplateManagerException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void action_check_is_active(HttpServletRequest request, HttpServletResponse response, Sondaggio sondaggio) throws TemplateManagerException {
        if(sondaggio.getStato() == 0) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Sondaggio non attivo");
            res.activate("/error.ftl", request, response);
            return ;
        }

        if(sondaggio.getStato() == 2) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Sondaggio scaduto");
            res.activate("/error.ftl", request, response);
            return ;
        }
    }

    private void action_check_visibility(HttpServletRequest request, HttpServletResponse response, Sondaggio sondaggio, SondaggioDao sondaggioDao) throws TemplateManagerException, SQLException, DataException {
        if(sondaggio.getId() == -1) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Questo sondaggio non esiste oppure non è più disponibile");
            res.activate("/error.ftl", request, response);
            return ;
        }
        if(sondaggio.getVisibilita() == 1)  {
            action_compila_sondaggio(request, response);
        } else {
            // verifica se è stata inserita l'email e l'utente può accedervi
            String email = request.getParameter("email");
            //TODO controllo se email rispetta il pattern
            if(email != null) { //email abilitata alla compilazione
                if(sondaggioDao.isEmailAbilitataAllaCompilazione(sondaggio, email)) {
                    request.setAttribute("email", email);
                    action_compila_sondaggio(request, response);
                } else {
                    TemplateResult res = new TemplateResult(getServletContext());
                    request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                    request.setAttribute("sondaggioId", sondaggio.getId());
                    request.setAttribute("error", "Questa email non è abilitata alla compilazione del sondaggio");
                    res.activate("sondaggi/formSondaggioPrivato.ftl", request, response);
                }

            } else { // altrimenti, rimanda al form di inserimento email per verificare che sia stato invitato
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("sondaggioId", sondaggio.getId());
                res.activate("sondaggi/formSondaggioPrivato.ftl", request, response);
            }


        }
    }

    private void action_compila_sondaggio(HttpServletRequest request, HttpServletResponse response) throws DataException, SQLException, TemplateManagerException {
        try {
            int sondaggioId = Integer.parseInt(request.getParameter("id"));

            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);
            if (sondaggio != null) {
                request.setAttribute("sondaggio", sondaggio);
                List<Domanda> domande = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomandeBySondaggioID(sondaggioId);
                for(int i = 0; i < domande.size(); i++)
                {
                    Domanda domanda = domande.get(i);
                    String type = domanda.getTipologia();
                    switch(type) {
                        case "testo_breve":
                            JSONObject tbJSON = domanda.getVincoli();
                            if(tbJSON.has("max_length")){
                                domanda.setMax_length(tbJSON.getInt("max_length"));
                            }
                            if(tbJSON.has("pattern")) {
                                domanda.setPattern(tbJSON.getString("pattern"));
                            }
                            break;
                        case "testo_lungo":
                            JSONObject tlJSON = domanda.getVincoli();
                            if(tlJSON.has("min_length")) {
                                domanda.setMin_length(tlJSON.getInt("min_length"));
                            }
                            if(tlJSON.has("max_length")) {
                                domanda.setMax_length(tlJSON.getInt("max_length"));
                            }
                            if(tlJSON.has("pattern")) {
                                domanda.setPattern(tlJSON.getString("pattern"));
                            }
                            break;
                        case "numero":
                            JSONObject nJSON = domanda.getVincoli();
                            if(nJSON.has("min_num")){
                                domanda.setMin_num(nJSON.getInt("min_num"));
                            }
                            if(nJSON.has("max_num")) {
                                domanda.setMax_num(nJSON.getInt("max_num"));
                            }
                            break;
                        case "data":
                            JSONObject dJSON = domanda.getVincoli();
                            if(dJSON.has("date")) {
                                domanda.setDataSuccessivaOdierna(dJSON.getInt("date"));
                            }
                            break;
                        case "scelta_singola":
                            JSONObject ssJSON = domanda.getVincoli();
                            domanda.setChooses(ssJSON.getJSONArray("chooses"));
                            break;
                        case "scelta_multipla":
                            JSONObject smJSON = domanda.getVincoli();
                            domanda.setChooses(smJSON.getJSONArray("chooses"));
                            if(smJSON.has("min_chooses")) {
                                domanda.setMin_chooses(smJSON.getInt("min_chooses"));
                            }
                            if(smJSON.has("max_chooses")) {
                                domanda.setMax_chooses(smJSON.getInt("max_chooses"));
                            }
                            break;
                    }

                }
                request.setAttribute("domande", domande);
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("sondaggi/compilazione.ftl", request, response);
            } else {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Il sondaggio non esiste");
                res.activate("/error.ftl", request, response);
                return ;
            }
        } catch (DataException ex) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Non è possibile caricare il sondaggio");
            res.activate("/error.ftl", request, response);
            return ;
        }
    }
}
