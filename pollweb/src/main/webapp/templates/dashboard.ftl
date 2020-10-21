<#assign charset="UTF-8">
<#assign title="Dashboard">

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
        <h1>Lista sondaggi</h1>
        <div class="row">
            <#list sondaggi as sondaggio>
                <div class="col-md-4 mt-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${sondaggio.getTitolo()}</h5>
                            <p><i class="fad fa-eye fa-fw"></i> ${sondaggio.getNomeVisibilita()}</p>
                            <p><i class="fad fa-toggle-on fa-fw"></i> ${sondaggio.getNomeStato()}</p>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
        <h2>Lista responsabili</h2>
        <div class="row">
            <#list responsabili as responsabile>
                <div class="col-md-4 mt-2">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">${responsabile.getNome()} ${responsabile.getCognome()}</h5>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>

<@globalTemplate.script />
</body>
</html>