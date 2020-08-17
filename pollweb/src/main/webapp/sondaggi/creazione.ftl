<#import "../templates/globalTemplate.ftl" as globalTemplate>

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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="../css/sondaggio.css"/>
    </head>
    <body>
            <div  id="creation-side">
                <img src="../img/logo_icona.png" alt="Pollweb logo colorato" />
            </div>
            <div class="container" id="creationForm">
                <h2 class="text-primary">Nuovo Sondaggio</h2>
                <form id="creationForm" onsubmit="">
                    <div class="form-row">
                        <label for="titoloInput">Titolo del sondaggio</label>
                        <input id="titoloInput" type="text" name="titolo" class="form-control" placeholder="Il mio Sondaggio" required>
                    </div>
                    <br>
                    <div class="form-row">
                        <label for="testoInizialeInput">Testo Iniziale</label>
                        <textarea id="testoInizialeInput" name="testoiniziale" placeholder="Aggiungi una nota personalizzata" required rows="3" cols="50"></textarea>
                        <label for="testoFinaleInput">Testo Finale</label>
                        <textarea id="testoFinaleInput" name="testofinale" placeholder="Aggiungi una nota personalizzata" required rows="3" cols="50"></textarea>
                    </div>
                </form>
            </div>
            <div class="container" id="creationQuestionForm">
                <h4 class="text-primary">Nuova Domanda</h4>
                <form id="creationQuestionForm" onsubmit="">
                    <div class="form-row">
                        <label for="titoloQInput">Titolo</label>
                        <input id="titoloQInput" type="text" name="titolo" class="form-control" placeholder="Quale domanda vuoi porre?" required>
                    </div>
                    <div class="form-row">
                        <label for="tipologia-select">Scegli una tipologia:</label>
                        <select name="tipologia" id="tipologia-select">
                            <option value="">--Scegli un opzione--</option>
                            <option value="testobreve">testo breve</option>
                            <option value="testolungo">testo lungo</option>
                            <option value="numero">numero</option>
                            <option value="data">data</option>
                            <option value="sceltasingola">scelta singola</option>
                            <option value="sceltamultipla">scelta multipla</option>
                        </select>
                    </div>
                    <label for="nota">Nota</label>
                    <textarea id="nota" name="nota" placeholder="Aggiungi una nota personalizzata" required rows="3" cols="50"></textarea>
                </form>
            </div>

    </body>
</html>

