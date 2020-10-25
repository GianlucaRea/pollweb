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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class CompilazioneSondaggio extends PollWebBaseController {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DataException {
        try {
            HttpSession s = checkSession(request);

            if(request.getParameter("id") == null) {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Sondaggio non trovato");
                res.activate("/error.ftl", request, response);
                return ;
            }

            if (s!= null) {
                int sondaggioId = Integer.parseInt(request.getParameter("id"));
                Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
                ((PollwebDataLayer) request.getAttribute("datalayer")).init();
                //TODO Condizione di controllo per vedere se l'utente può accedere al sondaggio
                action_compila_sondaggio(request, response, s);
            } else {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Non puoi accedere al sondaggio");
                res.activate("/error.ftl", request, response);
            }
        } catch (TemplateManagerException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void action_compila_sondaggio(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws DataException, SQLException {
        try {
            int sondaggioId = Integer.parseInt(request.getParameter("id"));

            ((PollwebDataLayer) request.getAttribute("datalayer")).init();
            Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);
            if (sondaggio != null) {
                request.setAttribute("titolo", sondaggio.getTitolo());
                request.setAttribute("testoiniziale", sondaggio.getTestoiniziale());
                request.setAttribute("testofinale", sondaggio.getTestofinale());
                List<Domanda> domande = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomandeBySondaggioID(sondaggioId);
                for(int i = 0; i < domande.size(); i++)
                {
                    Domanda domanda = domande.get(i);
                    request.setAttribute("domande["+i+"][testo]",domanda.getTesto());
                    request.setAttribute("domande["+i+"][nota]",domanda.getNota());
                    request.setAttribute("domande["+i+"][obbligo]",domanda.getObbligo());
                    request.setAttribute("domande["+i+"][ordine]",domanda.getOrdine());
                    request.setAttribute("domande["+i+"][tipologia]",domanda.getTipologia());
                    String type = domanda.getTipologia();
                    switch(type) {
                        case "testo_breve":
                            JSONObject tbJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][max_length]",tbJSON.getInt("max_length"));
                            request.setAttribute("domande["+i+"][pattern]",tbJSON.getString("pattern"));
                            break;
                        case "testo_lungo":
                            JSONObject tlJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][max_length]",tlJSON.getInt("max_length"));
                            request.setAttribute("domande["+i+"][min_length]",tlJSON.getInt("min_length"));
                            request.setAttribute("domande["+i+"][pattern]",tlJSON.getString("pattern"));
                            break;
                        case "numero":
                            JSONObject nJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][max_num]",nJSON.getInt("max_num"));
                            request.setAttribute("domande["+i+"][min_num]",nJSON.getInt("min_num"));
                            break;
                        case "data":
                            JSONObject dJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][data]",dJSON.getInt("date"));
                            break;
                        case "scelta_singola":
                            JSONObject ssJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][chooses]",ssJSON.getJSONArray("chooses"));
                            break;
                        case "scelta_multipla":
                            JSONObject smJSON = domanda.getVincoli();
                            request.setAttribute("domande["+i+"][chooses]",smJSON.getJSONArray("chooses"));
                            request.setAttribute("domande["+i+"][max_chooses]",smJSON.getInt("max_chooses"));
                            request.setAttribute("domande["+i+"][min_chooses]",smJSON.getInt("min_chooses"));
                            break;
                    }

                }
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                res.activate("sondaggi/compilazione.ftl", request, response);
            } else {
                TemplateResult res = new TemplateResult(getServletContext());
                request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                request.setAttribute("error", "Non siamo capaci di caricare il sondaggio");
                res.activate("/error.ftl", request, response);
                return ;
            }
        } catch (DataException ex) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Questo è il catch");
            try {
                res.activate("/error.ftl", request, response);
            } catch (TemplateManagerException e) {
                e.printStackTrace();
            }
            return ;
        } catch (TemplateManagerException e) {
            e.printStackTrace();
        }
    }
}
