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
        <form id="creationForm" method="post" action="/inserisci_sondaggio">
            <div class="card">
                <div class="card-body">
                    <h1 class="text-primary">Nuovo Sondaggio</h1>

                    <div class="form-group">
                        <label for="titolo"><strong>Titolo del sondaggio</strong></label>
                        <input id="titolo" type="text" name="titolo" class="form-control" placeholder="Il nome del sondaggio" required>
                    </div>
                    <div class="form-group">
                        <label for="testoiniziale"><strong>Testo di apertura</strong></label>
                        <textarea id="testoiniziale" type="text" name="testoiniziale" class="form-control" placeholder="Il testo che comparirà all'inizio del sondaggio"
                                  required rows="3" cols="50"></textarea>
                        <p class="small">Il testo che sarà mostrato all'inizio della compilazione.</p>
                    </div>
                    <div class="form-group">
                        <label for="testofinale"><strong>Testo di chiusura</strong></label>
                        <textarea id="testofinale" type="text" name="testofinale" class="form-control" placeholder="Il testo che comparirà alla fine del sondaggio"
                                  required rows="3" cols="50"></textarea>
                        <p class="small">Il testo che sarà mostrato alla fine della compilazione.</p>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            * attributo obbligatorio
                        </div>
                        <div class="col-md-6">
                            <div class="float-right">
                                <button type="submit" class="btn btn-primary btn-lg">Vai al riepilogo <i class="fas fa-arrow-right"></i></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<@globalTemplate.script />
</body>

</html>

