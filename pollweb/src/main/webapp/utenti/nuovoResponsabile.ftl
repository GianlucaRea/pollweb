<#assign charset="UTF-8">
<#assign title="Example">
<#assign content>
This is content
</#assign>

<#import "/templates/globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<html>
    <head>
        <title>${title}</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@globalTemplate.style />
        </head>
    <body>
        <@globalTemplate.navbar />
        
        <div class="bg-light pt-5 pb-5">
            <div class="container">
                <h1>Nuovo responsabile</h1>
                <div class="pt-5">
                    <form action="nuovoResponsabile" method="post" name="nuovoResponsabileForm">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="nomeInput">Nome</label>
                                    <input type="text" name="nome" id="nomeInput" placeholder="Es. Mario" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="cognomeInput">Cognome</label>
                                    <input type="text" name="cognome" id="cognomeInput" placeholder="Es. Rossi" class="form-control" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="emailInput">Email</label>
                            <input type="email" name="email" id="emailInput" placeholder="Es. mario.rossi@email.it" class="form-control" />
                        </div>

                        <div class="form-group text-right">
                            <input type="submit" id="salvaForm" value="Salva" class="btn btn-primary btn-lg" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <@globalTemplate.script />
    </body>
</html>
