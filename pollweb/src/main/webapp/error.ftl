<#assign charset="UTF-8">
<#assign title="Errore">
<#assign content>
This is content
</#assign>
<#assign error>
Messaggio di errore
</#assign>

<#import "/templates/globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<html>
    <head>
        <title>${title} | PollWeb</title>
        <meta charset="${charset}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <@globalTemplate.style />
        </head>
    <body class="bg-light">
        <@globalTemplate.navbar />
        
        <div class="pt-5 pb-5 text-center">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <p>
                        <i class="fad fa-times fa-5x fa-fw text-danger"></i>
                    </p>
                    <h1>Si è verificato un errore</h1>
                    <p class="lead">${error}</p>
                    <div>
                        <a href="/" class="lead">
                            <i class="fad fa-long-arrow-left fa-fw"></i> Torna alla home
                        </a>
                    </div>
                </div>
                <div class="col-lg-6">
                    <img src="/img/error.png" class="img-fluid" />
                </div>
            </div>
        </div>
        
        <@globalTemplate.script />
    </body>
</html>
