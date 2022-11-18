INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Petra', 'Kos', 'petra.kos@hotmail.com', 'petrakos');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Miha', 'Novak', 'miha.novak@gmail.com', 'miha123');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime) VALUES ('Bine', 'Markelj', 'bine.markelj@gmail.com', 'bm1223');
INSERT INTO kategorija (ime) VALUES ('Pisala');
INSERT INTO kategorija (ime) VALUES ('Zvezki');
INSERT INTO trgovina (ime) VALUES ('Hofer');
INSERT INTO trgovina (ime) VALUES ('Merkator');
INSERT INTO izdelek (ime, opis, cena, kategorija_id, trgovina_id) VALUES ('Nalivno pero', 'Kakovostno nalivno pero zelene barve.', 10, 1, 2);
INSERT INTO izdelek (ime, opis, cena, kategorija_id, trgovina_id) VALUES ('Črnilo', 'Nalivno črnilo modre barve.', 5, 2, 1);
