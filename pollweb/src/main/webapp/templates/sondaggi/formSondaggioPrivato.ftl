<#assign charset="UTF-8">
<#assign title="Sondaggio privato">

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
        <div class="text-center">
            <h1>${title}</h1>
            <p class="lead">Questo sondaggio è privato. <br> Per compilarlo, inserisci il tuo indirizzo email.</p>
        </div>
        <div class="pt-3">
            <div class="row">
                <div class="col-md-6 offset-md-3">
                    <div class="card">
                        <div class="card-body">
                            <form id="formSondaggioPrivato" method="get">
                                <input type="hidden" name="id" value="${sondaggioId}">
                                <div class="form-group">
                                    <label for="inputEmail"><strong>Indirizzo email</strong></label>
                                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="mario.rossi@email.it" />
                                </div>
                                <button type="submit" class="btn btn-primary">Accedi al sondaggio</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<@globalTemplate.script />
</body>
</html>
