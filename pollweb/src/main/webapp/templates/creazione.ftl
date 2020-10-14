<#import "globalTemplate.ftl" as globalTemplate>

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
                <form id="creationForm"  method="post" action="/inserisci_sondaggio">
                    <div class="form-row">
                        <label for="titolo">Titolo del sondaggio</label>
                        <input id="titolo" type="text" name="titolo" class="form-control" placeholder="Il mio Sondaggio" required>
                    </div>
                    <br>
                    <div class="form-row">
                        <label for="testoiniziale">Testo Iniziale</label>
                        <textarea id="testoiniziale" type="text" name="testoiniziale" placeholder="Aggiungi una nota personalizzata" required rows="3" cols="50"></textarea>
                        <label for="testofinale">Testo Finale</label>
                        <textarea id="testofinale" type="text" name="testofinale" placeholder="Aggiungi una nota personalizzata" required rows="3" cols="50"></textarea>
                    </div>

                    <div id="Container">
                    <h2 class="text-primary">Domanda n°</h2>
                    <div class="form-row">
                        <label for="testo">Titolo Della domanda</label>
                        <input id="testo" type="text" name="testo" class="form-control" placeholder="La mia Domanda" required>
                    </div>
                    <div class="form-row">
                        <label for="nota">Nota</label>
                        <input id="nota" type="text" name="nota" class="form-control" placeholder="Una nota" required>
                    </div>
                    <div class="form-row">
                    <label><input type=checkbox checked name=obbligo disabled> Obbligo</label>
                    </div>
                    </div>
                    <button id="nuovaDomanda">Nuova Domanda</button>
                    <div class="form-row">
                        <input type="submit" value="CreaSondaggio">
                    </div>

                </form>

            </div>
    </body>

    <script>
        $(document).ready(function () {
            $('#nuovaDomanda').on('click', function () {
                $('#Container')
                    .clone()
                    .appendTo("body");
            });
        });
    </script>
</html>

