INSERT INTO COMPANY (name, address) VALUES ('FTN', 'Trg Dositeja Obradovića 6');

INSERT INTO DEPARTMENT (name, company_id) VALUES ('Menadžment', 1);
INSERT INTO DEPARTMENT (name, company_id) VALUES ('Računarski centar', 1);

INSERT INTO EMPLOYEE (first_name, last_name, position, department_id) VALUES ('Aleksandar', 'Aleksić', 'radnik', 1);
INSERT INTO EMPLOYEE (first_name, last_name, position, department_id) VALUES ('Milica', 'Milić', 'menadžer', 1);
INSERT INTO EMPLOYEE (first_name, last_name, position, department_id) VALUES ('Slavica', 'Slavić', 'radnik', 2);

INSERT INTO PROJECT (name, deadline) VALUES ('Biću student FTN', '2019-02-10');
INSERT INTO PROJECT (name, deadline) VALUES ('Podizanje sistema', '2019-05-1');

INSERT INTO WORKING (project_id, employee_id) VALUES (1, 1);
INSERT INTO WORKING (project_id, employee_id) VALUES (1, 2);
INSERT INTO WORKING (project_id, employee_id) VALUES (2, 3);


INSERT INTO KORISNIK (blokiran, datum_Rodjenja, id, broj_Telefona, email, ime, korisnicko_Ime, lozinka, opis, prezime, profilna_Slika, uloga) VALUES (FALSE, '2003-12-31', 4232, '123456', 'kolak@gmail.com', 'Luka', 'kolut', 'password', 'opis', 'Kolak', 'slika', 'KUPAC');

INSERT INTO PRIJAVA (status, datum_prijave,id,razlog_za_prijavu) VALUES ('PRIHVACENA','2022-01-01',1,'abc');

