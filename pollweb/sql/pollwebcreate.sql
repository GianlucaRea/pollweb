USE pollweb;

DROP TABLE IF EXISTS `Ruolo`;
DROP TABLE IF EXISTS `Compilazione`;
DROP TABLE IF EXISTS `Domande`;
DROP TABLE IF EXISTS `Sondaggio`;
DROP TABLE IF EXISTS `Utente`;





CREATE TABLE Utente (
email VARCHAR(63)NOT NULL PRIMARY KEY,
nome VARCHAR(30) NOT NULL,
cognome VARCHAR(30) NOT NULL,
tipo VARCHAR(30) NOT NULL,
password VARCHAR(30) NOT NULL
);

CREATE TABLE Ruolo(
id INT(9) auto_increment PRIMARY KEY,
RefEmail VARCHAR(63)NOT NULL,
nome_ruolo VARCHAR(30) NOT NULL,
FOREIGN KEY (RefEmail) REFERENCES Utente(email) on update cascade on delete cascade
);

CREATE TABLE Sondaggio(
id INT(90) auto_increment PRIMARY KEY,
RefEmail VARCHAR(63)NOT NULL,
titolo VARCHAR(40) NOT NULL,
testoiniziale VARCHAR(255) NOT NULL,
testofinale VARCHAR(255) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (RefEmail) REFERENCES Utente(email) on update cascade on delete cascade
);

CREATE TABLE Domande(
id INT(90) auto_increment PRIMARY KEY,
idSondaggio INT(90) NOT NULL,
testo VARCHAR(40) NOT NULL,
nota VARCHAR(255),
obbligo BOOLEAN,
tipologia VARCHAR(50),
vincoliCompilazione JSON NOT NULL ,
FOREIGN KEY (idSondaggio) REFERENCES Sondaggio(id) on update cascade on delete cascade
);

CREATE TABLE Compilazione (
id INT(90) auto_increment PRIMARY KEY,
idSondaggio INT(90) NOT NULL,
RefEmail VARCHAR(63)NOT NULL,
risposte JSON NOT NULL ,
FOREIGN KEY (RefEmail) REFERENCES Utente(email) on update cascade on delete cascade,
FOREIGN KEY (idSondaggio) REFERENCES Sondaggio(id) on update cascade on delete cascade
);

