
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Luka', 'Kolak', 'KUPAC', 'lukak@gmail.com', 'luka', 'luka123', '064789123', current_date , 'slika1', FALSE, 'opis1', 8.5, 0);
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Vuk', 'Ilic', 'PRODAVAC', 'ilic@gmail.com', 'vuk', 'vuk456', '063128389', current_date, 'slika2', FALSE, 'opis2',  9, 1);
INSERT INTO KORISNIK ( ime, prezime, user_role, email, korisnicko_Ime, lozinka, broj_Telefona, datum_Rodjenja, profilna_Slika, blokiran, opis,  prosecna_Ocena, uloga) VALUES ( 'Matija', 'Bozanic', 'KUPAC', 'matijab@gmail.com', 'matija', 'matija789', '06912386', current_date, 'slika3', FALSE, 'opis3',9, 0);

INSERT INTO KATEGORIJA (naziv) VALUES ('namestaj');
INSERT INTO KATEGORIJA (naziv) VALUES ('elektrotehnika');
INSERT INTO KATEGORIJA (naziv) VALUES ('sport');
INSERT INTO KATEGORIJA (naziv) VALUES ('odeca');
INSERT INTO KATEGORIJA (naziv) VALUES ('knjige');
INSERT INTO KATEGORIJA (naziv) VALUES ('muzika');
INSERT INTO KATEGORIJA (naziv) VALUES ('igracke');
INSERT INTO KATEGORIJA (naziv) VALUES ('automobili');


INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 1 , 15000,'2023-12-31', 'Stolica', 'drvena', 'slika', 'FIKSNACENA', 1, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (TRUE, FALSE, FALSE, 2 , 90000,'2023-11-11', 'Laptop', 'Dell', 'slika2', 'FIKSNACENA', 2, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id,kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 3 , 5000,'2023-10-31', 'Lopta', 'kosarka', 'slika3', 'AUKCIJA', 3, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 4, 2000, '2024-01-15', 'Majica', 'Pamucna', 'slika4', 'FIKSNACENA', 4, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 5, 1500, '2024-02-20', 'Knjiga', 'Fikcija', 'slika5', 'FIKSNACENA', 5, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 6, 8000, '2024-03-05', 'Gitara', 'Akusticna', 'slika6', 'AUKCIJA', 6, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (TRUE, FALSE, FALSE, 7, 1200, '2024-04-10', 'Lutka', 'Plasticna', 'slika7', 'FIKSNACENA', 7, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (TRUE, TRUE, TRUE, 8, 200000, '2024-05-25', 'Automobil', 'Polovan', 'slika8', 'AUKCIJA', 8, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 9, 3000, '2024-06-15', 'Pantalone', 'Trenerka', 'slika9', 'FIKSNACENA', 4, 2, 3);
INSERT INTO PROIZVOD (prodat, recenzija_od_kupca, recenzija_od_prodavca, id, cena, datum_objavljivanja, naziv, opis, slika, tip_prodaje, kategorija_id, prodavac_id, kupljeni_proizvodi_id) VALUES (FALSE, FALSE, FALSE, 10, 2500, '2024-07-01', 'Strip', 'Superhero', 'slika10', 'AUKCIJA', 5, 2, 3);



INSERT INTO PONUDA (cena, kupac_id) VALUES (30000, 1);
INSERT INTO PONUDA (cena, kupac_id) VALUES (15000, 2);
INSERT INTO PONUDA (cena, kupac_id) VALUES (10000, 3);

INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Spam', '2024-05-06', 1, 2, 'PODNETA');
INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Nasilje', '2024-11-16', 2, 3, 'PRIHVACENA');
INSERT INTO PRIJAVA (razlog_za_prijavu, datum_prijave, podneo_prijavu_id, odnosi_prijava_id, status) VALUES ('Spam', '2023-01-06', 1, 3, 'ODBIJENA');

INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (8.2,'test1','2023-06-07',1);
INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (7.6,'test2','2023-05-01',2);
INSERT INTO RECENZIJA (ocena,komentar,datum,korisnik_id) VALUES (7.1,'test3','2023-04-02',3);

