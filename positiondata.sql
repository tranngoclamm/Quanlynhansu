CREATE TABLE if not exists chuc_vu (
    ten_chuc_vu VARCHAR(50) NOT NULL,
    he_so_luong FLOAT NOT NULL
);
INSERT INTO `quanlynhansu`.`chuc_vu` (ten_chuc_vu, he_so_luong) 
VALUES 
    ('Director', 6.2),
    ('Manager', 5.6),
    ('Accountant', 4.9),
    ('Employee', 2.5);