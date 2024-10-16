
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Luka', 'Kolak', 'KUPAC', 'lukak@gmail.com', 'luka', 'luka123', '064789123', current_date , 'slika1', FALSE, 'opis1', 8.5, 0);
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Vuk', 'Ilic', 'PRODAVAC', 'ilic@gmail.com', 'vuk', 'vuk456', '063128389', current_date, 'slika2', FALSE, 'opis2',  9, 1);
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Matija', 'Bozanic', 'KUPAC', 'matijab@gmail.com', 'matija', 'matija789', '06912386', current_date, 'slika3', FALSE, 'opis3',9, 0);

INSERT INTO KATEGORIJA (naziv) VALUES ('namestaj');
INSERT INTO KATEGORIJA (naziv) VALUES ('elektrotehnika');
INSERT INTO KATEGORIJA (naziv) VALUES ('sport');

INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 1 , 15000,'2023-12-31', 'Stolica', 'drvena', 'slika', 'FIKSNACENA', 1, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (TRUE, FALSE, FALSE, 2 , 90000,'2023-11-11', 'Laptop', 'Dell', 'slika2', 'FIKSNACENA', 2, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 3 , 5000,'2023-10-31', 'Lopta', 'kosarka', 'slika3', 'AUKCIJA', 3, 2, 3);

INSERT INTO PONUDA (cena, kupac_id) VALUES (30000, 1);
INSERT INTO PONUDA (cena, kupac_id) VALUES (15000, 2);
INSERT INTO PONUDA (cena, kupac_id) VALUES (10000, 3);

INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Spam', '2024-05-06', 1, 2, 'PODNETA');
INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Nasilje', '2024-11-16', 2, 3, 'PRIHVACENA');
INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Spam', '2023-01-06', 1, 3, 'ODBIJENA');

INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (8.2,'test1','2023-06-07',1);
INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (7.6,'test2','2023-05-01',2);
INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (7.1,'test3','2023-04-02',3);

