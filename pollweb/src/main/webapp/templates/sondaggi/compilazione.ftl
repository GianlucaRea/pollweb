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
                <input type="hidden" name="email" value="${email}">
            </#if>
            <#list domande as domanda>
                <div class="card mb-3">
                    <div class="card-body">
                        <h5 class="card-title">${domanda.getTesto()}</h5>
                        <#switch domanda.getTipologia()>
                            <#case "testo_breve">
                                <input type="text" name="domande[${domanda.getId()}]" class="form-control" maxlength="${domanda.getMax_length()}" <#if domanda.getPattern()> pattern="${domanda.getPattern()}" </#if> <#if domanda.getObbligo()==1>required</#if>>
                            <#break>
                            <#case "testo_lungo">
                                <textarea name="domande[${domanda.getId()}]" class="form-control" minlength="${domanda.getMin_length()}" maxlength="${domanda.getMax_length()}" cols="30" rows="10" <#if domanda.getObbligo()==1>required</#if>></textarea>
                            <#break>
                            <#case "numero">
                                <input type="number" class="form-control" name="domande[${domanda.getId()}]" <#if domanda.getObbligo()==1>required</#if>>
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
                            <#break>
                        </#switch>
                        <p class="small">${domanda.getNota()}</p>
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