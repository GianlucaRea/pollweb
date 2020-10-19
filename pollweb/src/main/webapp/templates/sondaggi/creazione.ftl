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
                        <button type="submit" class="btn btn-primary">Crea sondaggio</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<@globalTemplate.script />
</body>

<script>
    $(document).ready(function () {
        let numeroDomanda = 0;
        $('#btnNuovaDomanda').on('click', function () {
            numeroDomanda++;
            $("#domande").append('<div class="domanda mt-3 mb-3 card" id="domanda' + numeroDomanda + '">' +
                '<div class="card-body">'+
                '<div class="row">' +
                '<div class="col-md-6">'+
                '<h2 class="text-primary">Domanda n°' + numeroDomanda + '</h2>' +
                '</div>'+
                '<div class="col-md-6">'+
                '<div class="float-right">'+
                '<a class="btn btn-primary" data-toggle="collapse" href="#collapseDomanda' + numeroDomanda + '" role="button" aria-expanded="false" aria-controls="collapseExample">'+
                'Espandi/Minimizza'+
                '</a>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '<div class="collapse show" id="collapseDomanda' + numeroDomanda + '">'+
                '<div class="form-group">' +
                '<label for="testoDomanda' + numeroDomanda + '">Titolo Della domanda</label>' +
                '<input id="testoDomanda' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][testo]" class="form-control" placeholder="La mia Domanda" required>' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="notaDomanda' + numeroDomanda + '">Nota</label>' +
                '<input id="notaDomanda' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][nota]" class="form-control" placeholder="Una nota" required>' +
                '</div>' +
                '<div class="form-group">' +
                '<label><input type="checkbox" name="domande[' + numeroDomanda + '][obbligo]"> Obbligo</label>' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="tipologiaDomanda">Tipologia</label>' +
                '<select id="tipologiaDomanda" name="domande[' + numeroDomanda + '][tipologia]"  required>' +
                '<option value="testo_breve"  selected="selected">Testo breve</option>'+
                '<option value="testo_lungo">Testo lungo</option>'+
                '<option value="numero">Numero</option>'+
                '<option value="data">Data</option>'+
                '<option value="scelta_singola">Scelta singola</option>'+
                '<option value="scelta_multipla">Scelta multipla</option></select>'+
                '<button id="btnTipologia" type="button" class="btn btn-primary">Scegli</button>'+
                '<div id="vincoloSelect">'+
                '</div>'+
                '</div>' +
                '</div>'+
                '</div>'+
                '</div>');

            $('#btnTipologia').on('click', function () {

                let tipologia = document.getElementById("tipologiaDomanda").value;
                console.log(tipologia);
                switch(tipologia) {
                    case "testo_breve":
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
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
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
                            '<div class="form-group">' +
                            '<label for="LunghezzaMassimaTestoLungo' + numeroDomanda + '">Lunghezza Massima Testo Lungo</label>' +
                            '<input id="LunghezzaMassimaTestoLungo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMassimaTestoBreve]" class="form-control" placeholder="Lunghezza Massima Testo Lungo" required>' +
                            '</div>' +
                            '<div class="form-group">' +
                            '<label for="LunghezzaMinimaTestoLungo' + numeroDomanda + '">Lunghezza Minima Testo Lungo</label>' +
                            '<input id="LunghezzaMinimaTestoLungo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMinimaTestoLungo]" class="form-control" placeholder="Lunghezza Minima Testo Lungo" required>' +
                            '</div>' +
                            '<div class="form-group">' +
                            '<label for="PatternTestoBreve' + numeroDomanda + '">PatternTestoBreve</label>' +
                            '<input id="PatternTestoBreve' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][PatternTestoBreve]" class="form-control" placeholder="PatternTestoBreve" required>' +
                            '</div>' +
                            '</div>'
                        );
                        break;
                    case "numero":
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
                            '<div class="form-group">' +
                            '<label for="Numerominimo' + numeroDomanda + '">Numero minimo</label>' +
                            '<input id="Numerominimo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numerominimo]" class="form-control" placeholder="Numero Massim0" required>' +
                            '</div>' +
                            '<div class="form-group">' +
                            '<label for="Numeromassimo' + numeroDomanda + '">Numero massimo</label>' +
                            '<input id="Numeromassimo' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][Numeromassimo]" class="form-control" placeholder="Numero Minimo" required>' +
                            '</div>' +
                            '</div>'
                        );
                        break;
                    case "data":
                        text = "Gli utenti potranno scegliere solo date successive all'odierna";
                        break;
                    case "scelta_singola":
                        text = "Inserisci le varie opzioni separate dalla virgola";
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
                            '<div class="form-group">' +
                            '<label for="sceltasingola' + numeroDomanda + '">Scelta Singola</label>' +
                            '<input id="sceltasingola' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][sceltasingola]" class="form-control" placeholder="Opzione1,Opzione2,..." required>' +
                            '</div>' +
                            '</div>'
                        );
                    case "scelta_multipla":
                        text = "Inserisci le varie opzioni separate dalla virgola";
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
                            '<div class="form-group">' +
                            '<label for="sceltasingola' + numeroDomanda + '">Scelta Singola</label>' +
                            '<input id="sceltasingola' + numeroDomanda + '" type="text" name="domande[' + numeroDomanda + '][sceltasingola]" class="form-control" placeholder="Opzione1,Opzione2,..." required>' +
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
                        $("#vincoloSelect").append('<div class="domanda mt-3 mb-3 card" id="vincoloDomanda' + numeroDomanda + '">' +
                            '<div class="form-group">' +
                            '<label for="LunghezzaMassimaTestoBreve' + numeroDomanda + '">Titolo Della domanda</label>' +
                            '<input id="LunghezzaMassimaTestoBreve' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][LunghezzaMassimaTestoBreve]" class="form-control" placeholder="La mia Domanda" required>' +
                            '</div>' +
                            '<div class="form-group">' +
                            '<label for="PatternTestoBreve' + numeroDomanda + '">Titolo Della domanda</label>' +
                            '<input id="PatternTestoBreve' + numeroDomanda + '" type="number" name="domande[' + numeroDomanda + '][PatternTestoBreve]" class="form-control" placeholder="La mia Domanda" required>' +
                            '</div>' +
                            '</div>'
                        );
                }
            });
        });
    });


</script>
</html>

