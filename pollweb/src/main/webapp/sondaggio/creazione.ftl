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
       <div class="container">
                    <h2 class="text-primary">Nuovo Sondaggio</h2>
                    <form id="creationForm" onsubmit="">
                        <div class="form-row">
                            <label for="titoloInput">Nome del sondaggio</label>
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
        </div>
    </body>
</html>

