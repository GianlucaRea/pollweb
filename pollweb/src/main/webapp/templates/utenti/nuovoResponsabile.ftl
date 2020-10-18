<#assign charset="UTF-8">
<#assign title="Nuovo responsabile">

<#import "/globalTemplate.ftl" as globalTemplate>

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
                <#if success??>
                    <@globalTemplate.success success />
                </#if>
                <#if error??>
                    <@globalTemplate.error error />
                </#if>
                <h1>Nuovo responsabile</h1>
                <div class="pt-3">
                    <form action="/utenti/nuovo_responsabile" method="post" name="nuovoResponsabileForm">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="nomeInput"><strong>Nome</strong>*</label>
                                    <input type="text" name="nome" id="nomeInput" placeholder="Es. Mario" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="cognomeInput"><strong>Cognome</strong>*</label>
                                    <input type="text" name="cognome" id="cognomeInput" placeholder="Es. Rossi" class="form-control" />
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="emailInput"><strong>Email</strong>*</label>
                            <input type="email" name="email" id="emailInput" placeholder="Es. mario.rossi@email.it" class="form-control" />
                        </div>
                        
                        <p class="small">* attributo obbligatorio</p>

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
