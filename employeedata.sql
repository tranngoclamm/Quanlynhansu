CREATE TABLE if not exists nhan_vien (
    ma_nv VARCHAR(6) NOT NULL PRIMARY KEY,
    ho_ten VARCHAR(50) NOT NULL,
    ngay_sinh DATE NOT NULL,
    email VARCHAR(50) NOT NULL,
    chuc_vu VARCHAR(25) NOT NULL,
    he_so_luong FLOAT NOT NULL,
    ngay_lam_viec INT NOT NULL,
    tong_tien_luong DOUBLE NOT NULL
);

INSERT INTO `quanlynhansu`.`nhan_vien` (ma_nv,ho_ten, ngay_sinh, email, chuc_vu, he_so_luong, ngay_lam_viec, tong_tien_luong)
VALUES
('NV001', 'Nguyen Van An', '1990-01-01', 'nva@gmail.com', 'Director', 6.2, 20, 14584615),
('NV002', 'Tran Thi Hue', '1995-05-10', 'tth@gmail.com', 'Manager', 5.6, 18, 11673846),
('NV003', 'Le Van Cuong', '1992-09-20', 'lvc@gmail.com', 'Accountant', 4.9, 22, 12854615),
('NV004', 'Nguyen Thi Dung', '1994-03-15', 'ntd@gmail.com', 'Accountant', 4.9, 20, 11523846),
('NV005', 'Pham Van Tuan', '1998-12-30', 'pvt@gmail.com', 'Accountant', 4.9, 18, 10384615),
('NV006', 'Tran Van Chien', '1993-06-05', 'tvc@gmail.com', 'Employee', 2.5, 22, 5432692),
('NV007', 'Le Thi Giang', '1996-11-25', 'ltg@gmail.com', 'Employee', 2.5, 20, 4913461),
('NV008', 'Pham Van Hung', '1991-01-02', 'pvh@gmail.com', 'Employee', 2.5, 18, 4394231),
('NV009', 'Tran Thi Lan Anh', '1997-04-12', 'ttla@gmail.com', 'Employee', 2.5, 16, 3875000),
('NV010', 'Nguyen Van Khanh', '1990-08-22', 'nvk@gmail.com', 'Employee', 2.5, 14, 3355769),
('NV011', 'Le Yen Nhi', '1995-02-18', 'lyn@gmail.com', 'Employee', 2.5, 12, 2836538);