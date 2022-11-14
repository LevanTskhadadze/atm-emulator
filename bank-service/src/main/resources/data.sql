IF (SELECT COUNT(id) FROM Customer) = 0
    INSERT INTO Customer VALUES (NEXT VALUE FOR CustomerSequence, 'Levan', 'Tskhadadze', '78945612312');

IF (SELECT COUNT(id) FROM Account) = 0
    INSERT INTO Account VALUES (NEXT VALUE FOR AccountSequence, 0, 0, (SELECT id from Customer where personalNumber = '78945612312'));

IF (SELECT COUNT(id) FROM AuthenticationDetails) = 0
    INSERT INTO AuthenticationDetails VALUES (NEXT VALUE FOR AuthenticationSequence, 'PIN_CODE', '$2a$12$ucrpSoG.Xsbd1sE7sHLq0OBcEA6/gkkXGUbu7ScDtsun82PCGBId.', '$2a$12$NcNtNuFQOw4m5yNxS5GwOO1iK.VOTgOyU/KrSmkFo2J4hdrqrJeFq');

IF (SELECT COUNT(id) FROM Card) = 0
    INSERT INTO Card VALUES (NEXT VALUE FOR CardSequence, '4126600767369562', (SELECT A.id from Account A LEFT JOIN Customer C on C.id = A.customer_id where C.personalNumber = '78945612312'), (SELECT TOP 1 A.id from AuthenticationDetails A));