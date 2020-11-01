<#import "../globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>Compila sondaggio - Pollweb</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <@globalTemplate.style />
</head>
<body>
<@globalTemplate.navbar />
<div class="bg-light pt-5 pb-5">
    <div class="container" id="creationForm">
        <#if error ??>
            <div class="alert alert-danger">Si è verificato un errore con l'inserimento della compilazione. I campi non sono stati compilati correttamente.</div>
        </#if>
        <h2 class="text-primary">${sondaggio.getTitolo()}</h2>
        <p class="lead">${sondaggio.getTestoiniziale()}</p>
        <form id="creationForm" method="post" action="/sondaggi/inserisci_compilazione">
            <input type="hidden" name="sondaggioId" value="${sondaggio.getId()}">
            <#if email ??>
                <input type="hidden" name="utente_id" value="${utente_id}">
            </#if>
            <#list domande as domanda>
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${domanda.getTesto()}</h5>
                        <p>${domanda.getNota()}</p>
                        <#switch domanda.getTipologia()>
                            <#case "testo_breve">
                                <input type="text" name="domande[${domanda.getId()}]" class="form-control"
                                        <#if domanda.getMin_length() gte 0> minlength="${domanda.getMin_length()}" </#if>
                                        <#if domanda.getMax_length() gte 0> maxlength="${domanda.getMax_length()}"</#if>
                                        <#if domanda.getPattern().length() gt 0> pattern="${domanda.getPattern()}" </#if>
                                        <#if domanda.getObbligo()==1>required</#if> >
                                <p class="small">
                                    <#if domanda.getMin_length() gte 0> Lunghezza minima: ${domanda.getMin_length()} <br> </#if>
                                    <#if domanda.getMax_length() gte 0> Lunghezza massima: ${domanda.getMax_length()} <br></#if>
                                    <#if domanda.getPattern().length() gt 0>Pattern: "${domanda.getPattern()}" </#if>
                                </p>
                            <#break>
                            <#case "testo_lungo">
                                <textarea name="domande[${domanda.getId()}]" class="form-control"
                                          cols="30" rows="10"
                                            <#if domanda.getMin_length() gte 0> min="${domanda.getMin_length()}" </#if>
                                            <#if domanda.getMax_length() gte 0> maxlength="${domanda.getMax_length()}"</#if>
                                            <#if domanda.getPattern().length() gt 0> pattern="${domanda.getPattern()}" </#if>
                                            <#if domanda.getObbligo()==1>required</#if>></textarea>
                                <p class="small">
                                    <#if domanda.getMin_length() gte 0> Lunghezza minima: ${domanda.getMin_length()} <br> </#if>
                                    <#if domanda.getMax_length() gte 0> Lunghezza massima: ${domanda.getMax_length()} <br></#if>
                                    <#if domanda.getPattern() gt 0>Pattern: "${domanda.getPattern()}" </#if>
                                </p>
                            <#break>
                            <#case "numero">
                                <input type="number" class="form-control" name="domande[${domanda.getId()}]"
                                       <#if domanda.getMin_num() gte 0 || domanda.getMin_num() lt 0>min="${domanda.getMin_num()}"</#if>
                                        <#if domanda.getMax_num() gte 0 || domanda.getMax_num() lt 0>max="${domanda.getMax_num()}"</#if>
                                       <#if domanda.getObbligo()==1>required</#if>>
                                <p class="small">
                                    <#if domanda.getMin_num() gte 0 || domanda.getMin_num() lt 0>Numero minimo: ${domanda.getMin_num()} <br></#if>
                                    <#if domanda.getMax_num() gte 0 || domanda.getMax_num() lt 0>Numero massimo: ${domanda.getMax_num()}"</#if></p>
                            <#break>
                            <#case "data">
                                <input type="date" class="form-control" name="domande[${domanda.getId()}]" <#if domanda.getObbligo()==1>required</#if>>
                            <#break>
                            <#case "scelta_singola">
                                <select class="form-control" name="domande[${domanda.getId()}]" <#if domanda.getObbligo()==1>required</#if>>
                                    <#list domanda.getChoosesArrayList() as choose>
                                        <option value="${choose}">${choose}</option>
                                    </#list>
                                </select>
                            <#break>
                            <#case "scelta_multipla">
                                <select class="form-control" name="domande[${domanda.getId()}]" multiple <#if domanda.getObbligo()==1>required</#if>>
                                    <#list domanda.getChoosesArrayList() as choose>
                                        <option value="${choose}">${choose}</option>
                                    </#list>
                                </select>
                                <p class="small"><#if domanda.getMin_chooses() gte 0>Numero scelte minime: ${domanda.getMin_chooses()}
                                        <br></#if> <#if domanda.getMax_chooses() gte 0>Numero scelte massime: ${domanda.getMax_chooses()}</#if></p>
                            <#break>
                        </#switch>
                    </div>
                </div>
            </#list>
            <div class="float-right">
                <button type="submit" class="btn btn-success">Invia compilazione</button>
            </div>
        </form>
        <p class="lead">${sondaggio.getTestofinale()}</p>
    </div>
</div>

<@globalTemplate.script />
</body>

</html>