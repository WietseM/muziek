insert into albums(artiestId, labelId, naam, jaar, barcode, score)
values ((select id from artiesten where naam = 'test'),
        (select id from labels where naam = 'test'), 'test', 2000, 900000000000, 2);

