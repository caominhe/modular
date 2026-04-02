-- Thêm các quyền (Role) cơ bản vào hệ thống
INSERT INTO roles (name, description) VALUES
('ADMIN', 'Quản trị viên toàn hệ thống'),
('SALES', 'Nhân viên tư vấn bán hàng'),
('CUSTOMER', 'Khách hàng sử dụng dịch vụ');

-- Thêm một vài dòng xe (Master Data) để test tính năng nhập kho & báo giá
INSERT INTO master_data (brand, model, version, base_price) VALUES
('Toyota', 'Camry', '2.0Q', 1050000000.00),
('Ford', 'Ranger', 'Wildtrak 4x4', 979000000.00);