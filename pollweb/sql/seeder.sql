INSERT INTO `pollweb`.`Utente`(`email`,`nome`,`cognome`,`ruolo_id`,`password`)VALUES('amministratore@email.it','amministratoreNome','amministratoreCognome','3','password');
INSERT INTO `pollweb`.`Utente`(`email`,`nome`,`cognome`,`ruolo_id`,`password`)VALUES('responsabile@email.it','responsabileNome','responsabileCognome','2','password');

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggio1', 'testoIniziale1', 'testoFinale1', 0, 1);

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggioPubblico2', 'testoInizialePubblico2', 'testoFinalePubblico2', 0, 1);

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggioPrivato3', 'testoInizialePrivato3', 'testoFinalePrivato3', 0, 2);

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(1, 'testoDomanda1', 'nota1', false, 1, 1, '{}');

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(1, 'testoDomanda2', 'nota2', false, 2, 1, '{}');

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(1, 'testoDomanda3', 'nota3', false, 3, 1, '{}');

INSERT INTO `pollweb`.`Compilazione`(`sondaggio_id`, `email`, `risposte`)VALUES(3, 'test@test.it', '{}')