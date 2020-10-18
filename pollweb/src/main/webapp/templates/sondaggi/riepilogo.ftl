<#import "../globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>Riepilogo sondaggio</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <@globalTemplate.style />
</head>
<body class="bg-light">
<@globalTemplate.navbar />
<div class="pt-5 pb-5">
    <div class="container">
        <h1>Riepilogo sondaggio ${sondaggio.getTitolo()}</h1>
        <p><strong>Testo iniziale</strong>: ${sondaggio.getTestoIniziale()}</p>
        <p><strong>Testo finale</strong>: ${sondaggio.getTestoFinale()}</p>
        <h2>Domande</h2>
        <#list domande as domanda>
            <div class="card mb-2">
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-8">
                            <h5 class="card-title"><strong>${domanda.getTesto()}</strong></h5>
                        </div>
                        <div class="col-md-4">
                            <p><strong>Ordine</strong>: ${domanda.getOrdine()}</p>
                        </div>
                    </div>
                    <p class="text-muted mb-0"><i class="fad fa-edit fa-fw"></i> ${domanda.getTipologia()} | <i class="fad fa-exclamation fa-fw"></i> ${domanda.getNomeObbligo()}</p>
                </div>
            </div>
        </#list>
        <div class="row">
            <div class="col-md-6">
                <a href="/sondaggi/modifica?id=${sondaggio.getId()}" class="btn btn-primary">
                    Modifica sondaggio
                </a>
            </div>
            <div class="col-md-6">
                <div class="float-right">
                    <a href="/sondaggi/pubblica?id=${sondaggio.getId()}" class="btn btn-success">
                        Pubblica sondaggio
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<@globalTemplate.script />
</body>
</html>

