INSERT INTO `pollweb`.`Utente`(`email`,`nome`,`cognome`,`ruolo_id`,`password`)VALUES('amministratore@email.it','amministratoreNome','amministratoreCognome','3','2IehbF5176j9p7zbdMdmEod7kIi+piia');
INSERT INTO `pollweb`.`Utente`(`email`,`nome`,`cognome`,`ruolo_id`,`password`)VALUES('responsabile@email.it','responsabileNome','responsabileCognome','2','password');

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggio1', 'testoIniziale1', 'testoFinale1', 0, 1);

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggioPubblico2', 'testoInizialePubblico2', 'testoFinalePubblico2', 0, 1);

INSERT INTO `pollweb`.`Sondaggio`(`utente_id`, `titolo`, `testoiniziale`, `testofinale`, `stato`, `visibilita`)
VALUES(1, 'titoloSondaggioPrivato3', 'testoInizialePrivato3', 'testoFinalePrivato3', 0, 2);

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(1, 'testoDomanda1', 'nota1', false, 1,'scelta singola', '{}');

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(3, 'testoDomanda2', 'nota2', false, 2, 'scelta singola', '{}');

INSERT INTO `pollweb`.`Domanda`(`sondaggio_id`, `testo`, `nota`, `obbligo`, `ordine`, `tipologia`, `vincoli`)VALUES
(1, 'testoDomanda3', 'nota3', false, 3,'scelta singola', '{}');

INSERT INTO `pollweb`.`Compilazione`(`sondaggio_id`, `email`)VALUES(3, 'test@test.it');

INSERT INTO `pollweb`.`CompilazioneDomanda`(`compilazione_id`, `domanda_id`, `risposta`)VALUES(1, 2, '{"value": "test"}');