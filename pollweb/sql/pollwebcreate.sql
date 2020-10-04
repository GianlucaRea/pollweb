DROP DATABASE IF exists pollweb;
CREATE DATABASE pollweb;
USE pollweb;

DROP TABLE IF EXISTS `Compilazione`;
DROP TABLE IF EXISTS `Domanda`;
DROP TABLE IF EXISTS `Sondaggio`;
DROP TABLE IF EXISTS `Utente`;
DROP TABLE IF EXISTS `Ruolo`;


CREATE TABLE Ruolo(
id INT auto_increment PRIMARY KEY,
nome_ruolo VARCHAR(255) NOT NULL
);

CREATE TABLE Utente (
email VARCHAR(255)NOT NULL PRIMARY KEY,
nome VARCHAR(255) NOT NULL,
cognome VARCHAR(255) NOT NULL,
ruolo_id INT NOT NULL,
password VARCHAR(255) NOT NULL,
FOREIGN KEY (ruolo_id) REFERENCES Ruolo(id) on update cascade on delete cascade

);


CREATE TABLE Sondaggio(
id BIGINT auto_increment NOT NULL PRIMARY KEY,
utente_email VARCHAR(255)NOT NULL,
titolo VARCHAR(255) NOT NULL,
testoiniziale LONGTEXT NOT NULL,
testofinale LONGTEXT NOT NULL,
stato INT default 0,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (utente_email) REFERENCES Utente(email) on update cascade on delete cascade
);

CREATE TABLE Domanda(
id BIGINT auto_increment PRIMARY KEY,
sondaggio_id BIGINT NOT NULL,
testo VARCHAR(255) NOT NULL,
nota VARCHAR(255),
obbligo BOOLEAN,
ordine INT NOT NULL,
tipologia INT NOT NULL,
vincoli JSON,
FOREIGN KEY (sondaggio_id) REFERENCES Sondaggio(id) on update cascade on delete cascade
);

CREATE TABLE Compilazione (
id BIGINT auto_increment PRIMARY KEY,
sondaggio_id BIGINT NOT NULL,
utente_id VARCHAR(255)NOT NULL,
risposte JSON NOT NULL ,
FOREIGN KEY (utente_id) REFERENCES Utente(email) on update cascade on delete cascade,
FOREIGN KEY (sondaggio_id) REFERENCES Sondaggio(id) on update cascade on delete cascade
);

INSERT INTO `pollweb`.`ruolo`(`id`,`nome_ruolo`)VALUES('1','amministratore');
INSERT INTO `pollweb`.`ruolo`(`id`,`nome_ruolo`)VALUES('2','responsabile');
INSERT INTO `pollweb`.`ruolo`(`id`,`nome_ruolo`)VALUES('3','utente');



