USE pollweb;

DROP TABLE IF EXISTS `Compilazione`;
DROP TABLE IF EXISTS `Domande`;
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
tipo INT NOT NULL,
ruolo_id INT NOT NULL,
password VARCHAR(255) NOT NULL,
FOREIGN KEY (ruolo_id) REFERENCES Ruolo(id) on update cascade on delete cascade

);


CREATE TABLE Sondaggio(
id BIGINT auto_increment PRIMARY KEY,
utente_id VARCHAR(255)NOT NULL,
titolo VARCHAR(255) NOT NULL,
testoiniziale LONGTEXT NOT NULL,
testofinale LONGTEXT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (utente_id) REFERENCES Utente(email) on update cascade on delete cascade
);

CREATE TABLE Domande(
id BIGINT auto_increment PRIMARY KEY,
sondaggio_id BIGINT NOT NULL,
testo VARCHAR(255) NOT NULL,
nota VARCHAR(255),
obbligo BOOLEAN,
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

