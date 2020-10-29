package com.company.pollweb.controllers.compilazione;

import com.company.pollweb.controllers.PollWebBaseController;
import com.company.pollweb.data.dao.PollwebDataLayer;
import com.company.pollweb.data.models.Domanda;
import com.company.pollweb.data.models.Sondaggio;
import com.company.pollweb.data.models.Utente;
import com.company.pollweb.framework.data.DataException;
import com.company.pollweb.framework.result.SplitSlashesFmkExt;
import com.company.pollweb.framework.result.TemplateManagerException;
import com.company.pollweb.framework.result.TemplateResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

/**
 * @author gianlucarea
 */
public class EsportaCompilazione extends PollWebBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException, DataException, SQLException {
        HttpSession s = checkSession(request);
        if (s != null && request.getParameter("id") != null) {
            action_esporta(request, response, s);
        } else {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Permesso negato");
            res.activate("/error.ftl", request, response);
        }
    }

    private void action_esporta(HttpServletRequest request, HttpServletResponse response, HttpSession s)throws DataException , SQLException {
        try{
            int sondaggioId = Integer.parseInt(request.getParameter("id"));
            Utente utente = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
            Sondaggio sondaggio = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);
            List<Domanda> domande = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomandeBySondaggioID(sondaggio.getId());
            if (utente != null && sondaggio != null && domande != null && utente.getId() == sondaggio.getUtenteId()){
                    // Creazione di una lista di lista di stringhe: domanda, risposta
                    List<List<String>> result = new ArrayList<List<String>>();
                    for (Domanda d : domande) {
                        List<String> rList = ((PollwebDataLayer) request.getAttribute("datalayer")).getCompilazioneDAO().getRisposteByDomandaId(d.getId());
                        for (String r: rList) {
                            System.out.println(r);
                            List<String> Stringrispota = new ArrayList<>();
                            Stringrispota.add(d.getTesto());
                            Stringrispota.add(r);
                            result.add(answerString);
                        }
                    }
            }
        }catch(){

        }

    }
}
}
