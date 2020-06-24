error=""<#macro style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.13.0/css/duotone.css" integrity="sha384-oRY9z8lvkaf2a1RyLPsz9ba5IbYiz1X/udoO3kZH3WM+gidZ+eELnojAqaBwvAmB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.13.0/css/fontawesome.css" integrity="sha384-tSxOKkJ+YPQOZg1RZd01upxL2FeeFVkHtkL0+04oWgcm9jnvH+EQNLxhpaNYblG2" crossorigin="anonymous">

</#macro>
    
<#macro script>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js" crossorigin="anonymous"></script>  
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.bundle.min.js"></script>
</#macro>
    
<#macro navbar>
    <nav class="navbar navbar-dark bg-primary">
        <a class="navbar-brand" href="#">
          <img src="../img/logo_icona.png" width="30" height="30" class="d-inline-block align-top" alt="Pollweb logo" />
          Pollweb
        </a>
        <div class="ml-auto">
            <i class="fad fa-user fa-2x fa-fw text-white"></i>
        </div>
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