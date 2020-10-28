<#import "../globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>Crea Un Sondaggio - Pollweb</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <@globalTemplate.style />
    <link rel="stylesheet" href="../../css/sondaggio.css"/>
</head>
<body>
<@globalTemplate.navbar />
<div class="bg-light pt-5 pb-5">
    <div class="container" id="creationForm">
        <h2 class="text-primary">Nuovo Sondaggio</h2>
        <form id="creationForm" method="post" action="/inserisci_sondaggio">
            <div class="card">
                <div class="card-body">
                    <div class="form-group">
                        <label for="titolo">Titolo del sondaggio</label>
                        <input id="titolo" type="text" name="titolo" class="form-control" placeholder="Il nome del sondaggio" required>
                    </div>
                    <div class="form-group">
                        <label for="testoiniziale">Testo Iniziale</label>
                        <textarea id="testoiniziale" type="text" name="testoiniziale" class="form-control" placeholder="Il testo che comparirà all'inizio del sondaggio"
                                  required rows="3" cols="50"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="testofinale">Testo Finale</label>
                        <textarea id="testofinale" type="text" name="testofinale" class="form-control" placeholder="Il testo che comparirà alla fine del sondaggio"
                                  required rows="3" cols="50"></textarea>
                    </div>
                </div>
            </div>
            <div id="domande">
            </div>
            <div class="row">
                <div class="col-md-6">
                    <button id="btnNuovaDomanda" type="button" class="btn btn-primary">Nuova Domanda</button>
                </div>
                <div class="col-md-6">
                    <div class="float-right">
                        <button type="submit" class="btn btn-primary">Vai al riepilogo</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<@globalTemplate.script />
</body>

<script>

    function spostaDomanda(numeroDomanda, direzione) {
        let rowAttuale = $("#rowDomanda" + numeroDomanda);
        let rowScambio;
        console.log(direzione);
        if(direzione == 'sopra') {
            rowScambio = rowAttuale.prev();
        }
        if(direzione == 'sotto') {
            rowScambio = rowAttuale.next();
        }

        if(rowScambio.hasClass("rowDomanda")) {
            let appRowAttuale = rowAttuale.clone();
            let appRowScambio = rowScambio.clone();

            let idRowAttuale = rowAttuale.data("numero-domanda");
            let idRowScambio = rowScambio.data("numero-domanda");

            let valTipologiaAttuale = $("#tipologiaDomanda"+idRowAttuale).val();
            let valTipologiaScambio = $("#tipologiaDomanda"+idRowScambio).val();

            rowScambio.replaceWith(appRowAttuale);
            rowAttuale.replaceWith(appRowScambio);

            //modifica ordine
            let valOrdineRowAttuale = $("#domandaInputOrdine"+idRowAttuale).val();
            let valOrdineRowScambio = $("#domandaInputOrdine"+idRowScambio).val();

            //imposta tipologia
            $("#tipologiaDomanda"+idRowAttuale).val(valTipologiaAttuale);
            $("#tipologiaDomanda"+idRowScambio).val(valTipologiaScambio);

            //swap valore ordine input hidden
            $("input[id=domandaInputOrdine" + idRowAttuale + "]").val(valOrdineRowScambio);
            $("input[id=domandaInputOrdine" + idRowScambio + "]").val(valOrdineRowAttuale);
        }

    }

    function cambiaTitoloDomanda(numeroDomanda) {
        $("#titoloDomanda" + numeroDomanda).text($("#testoDomanda"+numeroDomanda).val());
    }

    function updateVincoli(numeroDomanda) {

        let selectValue = $("#tipologiaDomanda"+numeroDomanda).val();
        let vincoliSelect = $("#vincoliSelect"+numeroDomanda);

        switch(selectValue) {
            case "testo_breve":
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<label for="LunghezzaMassimaTestoBreve' + numeroDomanda + '">Lunghezza Massima Testo Breve</label>' +
                    '<input id="LunghezzaMassimaTestoBreve' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMassimaTestoBreve]" class="form-control" placeholder="250" required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="PatternTestoBreve' + numeroDomanda + '">PatternTestoBreve</label>' +
                    '<input id="PatternTestoBreve' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][PatternTestoBreve]" class="form-control" placeholder="Pattern" required>' +
                    '</div>' +
                    '</div>'
                );
                break;
            case "testo_lungo":
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<label for="LunghezzaMassimaTestoLungo' + numeroDomanda + '">Lunghezza Massima Testo Lungo</label>' +
                    '<input id="LunghezzaMassimaTestoLungo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMassimaTestoLungo]" class="form-control" placeholder="Lunghezza Massima Testo Lungo" required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="LunghezzaMinimaTestoLungo' + numeroDomanda + '">Lunghezza Minima Testo Lungo</label>' +
                    '<input id="LunghezzaMinimaTestoLungo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMinimaTestoLungo]" class="form-control" placeholder="Lunghezza Minima Testo Lungo" required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="PatternTestoLugo' + numeroDomanda + '">Pattern Testo Lungo</label>' +
                    '<input id="PatternTestoLungo' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][PatternTestoLungo]" class="form-control" placeholder="Pattern Testo Lungo" required>' +
                    '</div>' +
                    '</div>'
                );
                break;
            case "numero":
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<label for="Numerominimo' + numeroDomanda + '">Numero minimo</label>' +
                    '<input id="Numerominimo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numerominimo]" class="form-control" placeholder="Numero Minimo" required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="Numeromassimo' + numeroDomanda + '">Numero massimo</label>' +
                    '<input id="Numeromassimo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numeromassimo]" class="form-control" placeholder="Numero Massimo" required>' +
                    '</div>' +
                    '</div>'
                );
                break;
            case "data":
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<input id="dataSuccessivaOdierna' + numeroDomanda + '" type="checkbox" name="domande[' + numeroDomanda + '][dataSuccessivaOdierna]" required>' +
                    '<label for="dataSuccessivaOdierna' + numeroDomanda + '">Accetta solamente date successive a quella odierna</label>' +
                    '</div>' +
                    '</div>'
                );
                break;
            case "scelta_singola":
                text = "Inserisci le varie opzioni separate dalla virgola";
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<label for="sceltasingola' + numeroDomanda + '">Scelta Singola</label>' +
                    '<input id="sceltasingola' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][sceltasingola]" class="form-control" placeholder="Opzione1,Opzione2,..." required>' +
                    '</div>' +
                    '</div>'
                );
                break;
            case "scelta_multipla":
                text = "Inserisci le varie opzioni separate dalla virgola";
                vincoliSelect.html('<div class="domanda mt-3 mb-3" id="vincoloDomanda' + numeroDomanda + '">' +
                    '<div class="form-group">' +
                    '<label for="sceltamultipla' + numeroDomanda + '">Scelta Multipla</label>' +
                    '<input id="sceltamultipla' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][sceltamultipla]" class="form-control" placeholder="Opzione1,Opzione2,..." required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="Numerominimoscelte' + numeroDomanda + '">Numero minimo</label>' +
                    '<input id="Numerominimoscelte' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numerominimoscelte]" class="form-control" placeholder="es.1" required>' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<label for="Numeromassimoscelte' + numeroDomanda + '">Numero massimo</label>' +
                    '<input id="Numeromassimoscelte' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numeromassimoscelte]" class="form-control" placeholder="es.4" required>' +
                    '</div>' +
                    '</div>'
                );
                break;
            default:
        }
    }

    $(document).ready(function () {
        let numeroDomanda = 0;
        $('#btnNuovaDomanda').on('click', function () {
            numeroDomanda++;
            $("#domande").append(
                '<div class="row rowDomanda" id="rowDomanda' + numeroDomanda + '" data-numero-domanda="' + numeroDomanda + '">' +
                    '<div class="col-11">' +
                        '<div class="domanda mt-3 mb-3 card" id="domanda' + numeroDomanda + '">' +
                            '<div class="card-body">'+
                                '<div class="row">' +
                                    '<div class="col-md-6">'+
                                        '<h2 class="text-primary" id="titoloDomanda' + numeroDomanda + '">Domanda</h2>' +
                                    '</div>'+
                                    '<div class="col-md-6">'+
                                        '<div class="float-right">'+
                                            '<a class="btn btn-primary" data-toggle="collapse" href="#collapseDomanda' + numeroDomanda + '" role="button" aria-expanded="false" aria-controls="collapseExample">'+
                                                'Espandi/Minimizza'+
                                            '</a>'+
                                        '</div>'+
                                    '</div>'+
                                '</div>' + //close row
                                '<div class="collapse show" id="collapseDomanda' + numeroDomanda + '">'+
                                    '<input type="hidden" id="domandaInputOrdine' + numeroDomanda + '" name="domande['+ numeroDomanda +'][ordine]" value="'+ numeroDomanda + '" />'+
                                    '<div class="row">' +
                                        '<div class="col-md-6">'+
                                            '<div class="form-group">' +
                                                '<label for="testoDomanda' + numeroDomanda + '">Testo Della domanda</label>' +
                                                '<input id="testoDomanda' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][testo]" onkeyup="cambiaTitoloDomanda(' + numeroDomanda + ')" class="form-control" placeholder="La mia Domanda" required>' +
                                            '</div>' +
                                            '<div class="form-group">' +
                                                '<label for="notaDomanda' + numeroDomanda + '">Nota</label>' +
                                                '<input id="notaDomanda' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][nota]" class="form-control" placeholder="Una nota" required>' +
                                            '</div>' +
                                            '<div class="form-group">' +
                                                '<label for="tipologiaDomanda">Tipologia</label>' +
                                                '<select id="tipologiaDomanda' + numeroDomanda + '" name="domande[' + numeroDomanda + '][tipologia]" class="form-control" onchange="updateVincoli(' + numeroDomanda + ')" required>' +
                                                    '<option value="null" selected disabled>Seleziona un\'opzione...</option>'+
                                                    '<option value="testo_breve">Testo breve</option>'+
                                                    '<option value="testo_lungo">Testo lungo</option>'+
                                                    '<option value="numero">Numero</option>'+
                                                    '<option value="data">Data</option>'+
                                                    '<option value="scelta_singola">Scelta singola</option>'+
                                                    '<option value="scelta_multipla">Scelta multipla</option>' + '' +
                                                '</select>'+
                                            '</div>'+
                                            '<div class="form-group">' +
                                                '<input type="checkbox" name="domande[' + numeroDomanda + '][obbligo]"><label>&nbsp;Domanda obbligatoria</label>' +
                                            '</div>' +
                                        '</div>'+
                                        '<div class="col-md-6">'+
                                            '<div class="vincoliSelect" id="vincoliSelect' + numeroDomanda + '">'+
                                            '</div>'+
                                        '</div>'+
                                    '</div>' +
                                '</div>'+
                            '</div>'+
                        '</div>'+
                    '</div>'+
                    '<div class="col-1">'+
                        '<div class="mt-3 float-right">'+
                            '<p><button type="button" class="btn btn-secondary btn-sm" onclick="spostaDomanda(' + numeroDomanda + ', \'sopra\')"><i class="fas fa-arrow-up"></i></button></p>'+
                            '<p><button type="button" class="btn btn-secondary btn-sm" onclick="spostaDomanda(' + numeroDomanda + ', \'sotto\')"><i class="fas fa-arrow-down"></i></button></p>'+
                        '</div>'+
                    '</div>'+
                '</div>');
        });
    });


</script>
</html>

