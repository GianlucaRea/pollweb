<#import "/globalTemplate.ftl" as globalTemplate>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <title>Compilazione completata - Pollweb</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <@globalTemplate.style />
</head>
<body>
<@globalTemplate.navbar />
<div class="bg-light pt-5 pb-5">
    <div class="container" id="creationForm">
        <div class="alert alert-success">
            <p><strong>Congratulazioni</strong></p>
            <p>Hai completato correttamente la compilazione!</p>
        </div>
    </div>
</div>

<@globalTemplate.script />
</body>

</html>