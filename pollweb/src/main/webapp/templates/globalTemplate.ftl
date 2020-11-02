<#macro style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/css/fontawesome.css" crossorigin="anonymous">
</#macro>
    
<#macro script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>  
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
</#macro>
    
<#macro navbar>
    <nav class="navbar navbar-dark bg-primary">
        <a class="navbar-brand" href="/home">
          <img src="/img/logo_icona.png" width="30" height="30" class="d-inline-block align-top" alt="Pollweb logo" />
          Pollweb
        </a>
    </nav>
</#macro>
    
<#macro success success="">
    <#if success??>
        <div class="alert alert-success" role="alert">
            ${success}

            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
         </div>
    </#if>
</#macro>
    
<#macro error error="">
    <#if error??>
        <div class="alert alert-danger" role="alert">
            ${error}

            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
         </div>
    </#if>
</#macro>