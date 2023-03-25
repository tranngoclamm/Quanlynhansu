CREATE TABLE if not exists chuc_vu (
    ma_chuc_vu INT NOT NULL PRIMARY KEY,
    ten_chuc_vu VARCHAR(50) NOT NULL,
    he_so_luong FLOAT NOT NULL
);

CREATE TABLE if not exists nhan_vien (
    ma_nv VARCHAR(6) NOT NULL PRIMARY KEY,
    ho_ten VARCHAR(50) NOT NULL,
    ngay_sinh DATE NOT NULL,
    gioi_tinh ENUM('Nam', 'Nữ') NOT NULL,
    email VARCHAR(50) NOT NULL,
    ma_chuc_vu INT NOT NULL,
    so_ngay_lam_viec INT NOT NULL,
    tong_tien_luong DOUBLE NOT NULL,
    FOREIGN KEY (ma_chuc_vu) REFERENCES chuc_vu(ma_chuc_vu)
);
INSERT INTO `quanlynhansu`.`chuc_vu` (ma_chuc_vu, ten_chuc_vu, he_so_luong) 
VALUES 
    (1, 'Giám đốc', 6.2),
    (2, 'Phó giám đốc', 5.6),
    (3, 'Kế toán', 4.9),
    (4, 'Nhân viên', 2.5);

INSERT INTO `quanlynhansu`.`nhan_vien` (ma_nv,ho_ten, ngay_sinh, gioi_tinh, email, ma_chuc_vu, so_ngay_lam_viec, tong_tien_luong)
VALUES 
    ('NV001', 'Nguyễn Văn An', '1990-01-01', 'Nam', 'nva@gmail.com', 1, 20, 18680000),
    ('NV002', 'Trần Thị Huệ', '1995-05-10', 'Nữ', 'tth@gmail.com', 2, 18, 14900000),
    ('NV003', 'Lê Văn Cường', '1992-09-20', 'Nam', 'lvc@gmail.com', 3, 22, 16378000),
    ('NV004', 'Nguyễn Thị Dung', '1994-03-15', 'Nữ', 'ntd@gmail.com', 3, 20, 14620000),
    ('NV005', 'Phạm Văn Tuấn', '1998-12-30', 'Nam', 'pvt@gmail.com', 3, 18, 13122000),
    ('NV006', 'Trần Văn Chiến', '1993-06-05', 'Nam', 'tvc@gmail.com', 4, 22, 8255000),
    ('NV007', 'Lê Thị Giang', '1996-11-25', 'Nữ', 'ltg@gmail.com', 4, 20, 7450000),
    ('NV008', 'Phạm Văn Hưng', '1991-01-02', 'Nam', 'pvh@gmail.com', 4, 18, 6685000),
    ('NV009', 'Trần Thị Lan Anh', '1997-04-12', 'Nữ', 'ttla@gmail.com', 4, 16, 5960000),
    ('NV010', 'Nguyễn Văn Khánh', '1990-08-22', 'Nam', 'nvk@gmail.com', 4, 14, 5235000),
    ('NV011', 'Lê Yến Nhi', '1995-02-18', 'Nữ', 'lyn@gmail.com', 4, 12, 4510000);