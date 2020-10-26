package com.company.pollweb.controllers.compilazione;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
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

            int sondaggioId = Integer.parseInt(request.getParameter("id"));
            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            //TODO Condizione di controllo per vedere se l'utente può accedere al sondaggio
            action_compila_sondaggio(request, response);

        } catch (TemplateManagerException | SQLException e) {
            e.printStackTrace();
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
