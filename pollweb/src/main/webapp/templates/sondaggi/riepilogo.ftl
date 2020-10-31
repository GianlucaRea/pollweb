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
        <h1>Riepilogo "${sondaggio.getTitolo()}"</h1>
        <div class="card">
            <div class="card-body">
                <label for="visibilitaSondaggio">Visibilità del sondaggio</label>
                <form action="/sondaggi/invita_utenti?id=${sondaggio.getId()}" method="post"
                      enctype="multipart/form-data">
                    <div class="form-group">
                        <select name="visibilitaSondaggio" id="visibilitaSondaggio" class="form-control"
                                onchange="changeVisibility()">
                            <option value="1" <#if sondaggio.getVisibilita()==1>selected</#if> >Privato</option>
                            <option value="2" <#if sondaggio.getVisibilita()==2>selected</#if>>Pubblico</option>
                        </select>
                    </div>
                    <div id="invitoSondaggioPrivato" <#if sondaggio.getVisibilita()==2>style="display:none"</#if>>

                        <div class="row">
                            <div class="col-md-6">
                                <label for="invitaTramiteEmail">Invita tramite email</label>
                                <br>
                                <button type="button" id="invitaTramiteEmail" onclick="aggiungiInvitato()" class="btn btn-primary">Aggiungi email <i class="fad fa-plus"></i></button>
                            </div>
                            <div class="col-md-6">
                                <label for="invitaTramiteCSV">Invita tramite CSV</label>
                                <input type="file" id="invitaTramiteCSV" name="invitatiCSV" class="btn btn-secondary float-right form-control">
                            </div>
                        </div>

                        <h3>Lista utenti invitati</h3>
                        <div class="row" id="listaInvitati">

                        </div>
                        <div class="row">
                            <#list invitati as invitato>
                                <#if invitato != "">
                                    <div class="col-md-4 mt-2">
                                        <input type="text" class="form-control" value="${invitato}" disabled/>
                                    </div>
                                </#if>
                            </#list>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="float-right mt-2">
                        <button type="submit" class="btn btn-primary btn-lg">Salva modifiche</button>
                    </div>
                </form>
                <div class="clearfix"></div>

                <p class="mb-0"><strong>Testo iniziale</strong></p>
                <p>${sondaggio.getTestoiniziale()}</p>
                <p class="mb-0"><strong>Testo finale</strong></p>
                <p>${sondaggio.getTestofinale()}</p>
            </div>

        </div>
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
                    <p class="text-muted mb-0"><i class="fad fa-edit fa-fw"></i> ${domanda.getTipologia()} | <i
                                class="fad fa-exclamation fa-fw"></i> ${domanda.getNomeObbligo()}</p>
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
                        Attiva sondaggio
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<@globalTemplate.script />

<script>
    let countNuoviInvitati = 0;

    function aggiungiInvitato() {
        countNuoviInvitati++;
        $("#listaInvitati").append('' +
            '<div class="col-md-4 mt-2" id="nuovoInvitatoNome' + countNuoviInvitati + '">' +
            '<input type="text" name="nuovoInvitato[' + countNuoviInvitati + '][nome]" class="form-control" placeholder="Nome utente" />' +
            '</div>' +
            '<div class="col-md-4 mt-2" id="nuovoInvitato' + countNuoviInvitati + '">' +
            '<input type="email" name="nuovoInvitato[' + countNuoviInvitati + '][email]" class="form-control" placeholder="esempio@email.it" />' +
            '</div>' +
            '<div class="col-md-4 mt-2" id="nuovoInvitato' + countNuoviInvitati + '">' +
            '<input type="password" name="nuovoInvitato[' + countNuoviInvitati + '][password]" class="form-control" placeholder="Password" />' +
            '</div>' +
            '');
    }

    function changeVisibility() {
        let $visibilitaSondaggio = $("#visibilitaSondaggio");
        if($visibilitaSondaggio.val() == 1) {
            $("#invitoSondaggioPrivato").show();
        }
        if($visibilitaSondaggio.val() == 2) {
            $("#invitoSondaggioPrivato").hide();
        }
    }
</script>
</body>
</html>

