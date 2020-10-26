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
import com.company.pollweb.utility.Serializer;
import org.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLException;
import java.util.List;

import static com.company.pollweb.framework.security.SecurityLayer.checkSession;

public class InserisciModificaSondaggio extends PollWebBaseController {

    protected String  pattern, chooses;
    protected int max_length, min_length,max_num,min_num,max_chooses,min_chooses,data;
    protected JSONObject Vincoli;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws TemplateManagerException {

        try {
            HttpSession s = checkSession(request);
            if (s!= null) {
                action_modifica_sondaggio(request, response, s);
            } else {
                action_redirect(request, response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void action_modifica_sondaggio(HttpServletRequest request, HttpServletResponse response, HttpSession s) throws TemplateManagerException {
        try {
            if (request.getParameterMap() != null) {
                int sondaggioId = Integer.parseInt(request.getParameter("id"));
                Domanda d ;
                Sondaggio p = ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().getSondaggio(sondaggioId);
                Utente user = ((PollwebDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente((int) s.getAttribute("user_id"));
                if (p != null) {
                    p.setTitolo(request.getParameter("titolo"));
                    p.setTestoiniziale(request.getParameter("testoiniziale"));
                    p.setTestofinale(request.getParameter("testofinale"));
                    p.setUtenteId(user.getId());
                    ((PollwebDataLayer) request.getAttribute("datalayer")).getSondaggioDAO().salvaSondaggio(p);
                    List<Integer> idsD = ((PollwebDataLayer)request.getAttribute("datalayer")).getDomandaDAO().getDomandeIdsBySondaggioID(sondaggioId);
                    for(int i = 1 ;request.getParameter("domande["+i+"][testo]") != null ; i++){
                        ((PollwebDataLayer) request.getAttribute("datalayer")).init();
                        if(i <= idsD.size()) {
                            d = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().getDomandaByID(idsD.get(i-1));
                        }else{
                            d = ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().creazioneDomanda();
                        }
                        if (d != null) {
                            d.setSondaggio_id(p.getId());
                            d.setTesto(request.getParameter("domande["+i+"][testo]"));
                            d.setNota(request.getParameter("domande["+i+"][nota]"));
                            System.out.println(request.getParameter("domande["+i+"][ordine]"));
                            d.setOrdine(Integer.parseInt(request.getParameter("domande["+i+"][ordine]")));
                            if (request.getParameter("domande["+i+"][obbligo]") != null){
                                d.setObbligo(1);
                            }else{
                                d.setObbligo(0);
                            }
                            d.setTipologia(request.getParameter("domande["+i+"][tipologia]"));
                            String type = request.getParameter("domande["+i+"][tipologia]");
                            switch(type) {
                                case "testo_breve":
                                    max_length = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMassimaTestoBreve]"));
                                    pattern = request.getParameter("domande["+i+"][PatternTestoBreve]");
                                    Vincoli = Serializer.testobreveToJSON(max_length,pattern);
                                    d.setVincoli(Vincoli);
                                    break;
                                case "testo_lungo":
                                    max_length = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMassimaTestoLungo]"));
                                    min_length = Integer.parseInt(request.getParameter("domande["+i+"][LunghezzaMinimaTestoLungo]"));
                                    pattern = request.getParameter("domande["+i+"][PatternTestoLungo]");
                                    Vincoli = Serializer.testolungoToJSON(max_length, min_length,pattern);
                                    d.setVincoli(Vincoli);
                                    break;
                                case "numero":
                                    max_num = Integer.parseInt(request.getParameter("domande["+i+"][Numeromassimo]"));
                                    min_num = Integer.parseInt(request.getParameter("domande["+i+"][Numerominimo]"));
                                    Vincoli = Serializer.numeroToJSON(max_num,min_num);
                                    d.setVincoli(Vincoli);
                                    break;
                                case "data":
                                    data = Integer.parseInt(request.getParameter("domande["+i+"][dataSuccessivaOdierna]"));
                                    Vincoli = Serializer.dataToJSON(data);
                                    d.setVincoli(Vincoli);
                                    break;
                                case "scelta_singola":
                                    chooses = request.getParameter("domande["+i+"][sceltasingola]");
                                    Vincoli = Serializer.sceltaSingolaToJSON(chooses);
                                    d.setVincoli(Vincoli);
                                    break;
                                case "scelta_multipla":
                                    chooses = request.getParameter("domande["+i+"][sceltamultipla]");
                                    System.out.println(request.getParameter("domande["+i+"][Numerominimoscelte]"));
                                    System.out.println(request.getParameter("domande["+i+"][Numeromassimoscelte]"));
                                    min_chooses = Integer.parseInt(request.getParameter("domande["+i+"][Numerominimoscelte]"));
                                    max_chooses = Integer.parseInt(request.getParameter("domande["+i+"][Numeromassimoscelte]"));
                                    Vincoli = Serializer.sceltaMultiplaToJSON(chooses,min_chooses,max_chooses);
                                    d.setVincoli(Vincoli);
                                    break;
                            }
                            ((PollwebDataLayer) request.getAttribute("datalayer")).getDomandaDAO().salvaDomanda(d);
                        } else {
                            TemplateResult res = new TemplateResult(getServletContext());
                            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                            request.setAttribute("error", "La domanda non esiste");
                            res.activate("/error.ftl", request, response);
                        }
                    }
                }else {
                    TemplateResult res = new TemplateResult(getServletContext());
                    request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
                    request.setAttribute("error", "Il sondaggio non esiste");
                    res.activate("/error.ftl", request, response);
                }
                action_write(request, response, p.getId());
            }

        } catch (DataException | SQLException | TemplateManagerException | IOException e) {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("error", "Errore Modifica Sondaggio");
            res.activate("/error.ftl", request, response);
        }
    }

    private void action_write(HttpServletRequest request, HttpServletResponse response, int sondaggioId) throws IOException {
        response.sendRedirect("/sondaggi/riepilogo?id=" + sondaggioId);
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
