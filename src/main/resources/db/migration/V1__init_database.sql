-- ==============================================================================
-- MODULE 0: AUTH & IDENTITY (Quản lý Định danh & Phân quyền)
-- ==============================================================================
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE COMMENT 'Tên quyền: ROLE_ADMIN, ROLE_SALES, ROLE_CUSTOMER',
    description VARCHAR(255) COMMENT 'Mô tả quyền'
) COMMENT='Bảng lưu trữ các quyền trong hệ thống';

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'Email đăng nhập',
    password_hash VARCHAR(255) COMMENT 'Mật khẩu đã băm (Null nếu login qua Google)',
    full_name VARCHAR(100) NOT NULL COMMENT 'Họ và tên',
    phone VARCHAR(20) UNIQUE COMMENT 'Số điện thoại',
    role_id INT NOT NULL COMMENT 'Khóa ngoại đến bảng roles',
    showroom_id BIGINT COMMENT 'ID của showroom người này làm việc',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'Trạng thái: ACTIVE, INACTIVE, BANNED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(id)
) COMMENT='Bảng lưu trữ thông tin người dùng';

-- ==============================================================================
-- MODULE 1: INVENTORY (Quản lý Sản phẩm & Kho bãi)
-- ==============================================================================
CREATE TABLE master_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL COMMENT 'Hãng xe',
    model VARCHAR(50) NOT NULL COMMENT 'Dòng xe',
    version VARCHAR(50) NOT NULL COMMENT 'Phiên bản',
    base_price DECIMAL(15, 2) NOT NULL COMMENT 'Giá niêm yết',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Bảng danh mục các loại xe';

CREATE TABLE cars (
    vin VARCHAR(17) PRIMARY KEY COMMENT 'Số khung (VIN)',
    master_data_id BIGINT NOT NULL COMMENT 'Khóa ngoại tham chiếu dòng xe',
    engine_number VARCHAR(50) NOT NULL UNIQUE COMMENT 'Số máy',
    color VARCHAR(30) NOT NULL COMMENT 'Màu sắc xe',
    showroom_id BIGINT COMMENT 'ID của showroom đang giữ xe',
    status VARCHAR(20) NOT NULL DEFAULT 'IN_WAREHOUSE' COMMENT 'Trạng thái: IN_WAREHOUSE, AVAILABLE, LOCKED, RESERVED, SOLD',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (master_data_id) REFERENCES master_data(id)
) COMMENT='Bảng quản lý từng chiếc xe vật lý trong kho';

-- ==============================================================================
-- MODULE 2: MARKETING (Sự kiện, Khuyến mãi & Voucher)
-- ==============================================================================
CREATE TABLE campaigns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT 'Tên chương trình khuyến mãi',
    discount_type VARCHAR(20) NOT NULL COMMENT 'Loại: CASH, PERCENT, GIFT',
    discount_value DECIMAL(15, 2) NOT NULL COMMENT 'Giá trị giảm',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Bảng quản lý chương trình khuyến mãi';

CREATE TABLE vouchers (
    code VARCHAR(50) PRIMARY KEY COMMENT 'Mã Voucher',
    campaign_id BIGINT NOT NULL COMMENT 'Khóa ngoại thuộc campaign nào',
    user_id BIGINT COMMENT 'ID khách hàng sở hữu (Ref tới users)',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'Trạng thái: ACTIVE, CLAIMED, USED, EXPIRED',
    expired_at DATETIME NOT NULL COMMENT 'Hạn sử dụng',
    FOREIGN KEY (campaign_id) REFERENCES campaigns(id)
) COMMENT='Bảng quản lý vòng đời từng mã Voucher';

CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT 'Tên sự kiện',
    description TEXT COMMENT 'Mô tả chi tiết',
    start_date DATETIME NOT NULL COMMENT 'Ngày bắt đầu',
    end_date DATETIME NOT NULL COMMENT 'Ngày kết thúc',
    showroom_id BIGINT COMMENT 'Áp dụng cho showroom nào',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Bảng quản lý sự kiện';

-- ==============================================================================
-- MODULE 3: CRM (Tiếp cận, Khách hàng & Lái thử)
-- ==============================================================================
CREATE TABLE leads (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT COMMENT 'Nếu khách đã có tài khoản (Ref tới users)',
    full_name VARCHAR(100) NOT NULL COMMENT 'Tên khách hàng',
    phone VARCHAR(20) NOT NULL COMMENT 'SĐT liên hệ',
    source VARCHAR(50) COMMENT 'Nguồn khách: WEB, EVENT, REFERRAL',
    assigned_sales_id BIGINT COMMENT 'Nhân viên sales phụ trách (Ref tới users)',
    status VARCHAR(20) NOT NULL DEFAULT 'NEW' COMMENT 'Trạng thái: NEW, CONTACTING, APPOINTED, WON, LOST',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Bảng quản lý khách hàng tiềm năng';

CREATE TABLE test_drives (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lead_id BIGINT NOT NULL COMMENT 'Khóa ngoại đến bảng leads',
    car_model_id BIGINT NOT NULL COMMENT 'Mẫu xe muốn lái thử (Ref tới master_data)',
    schedule_time DATETIME NOT NULL COMMENT 'Thời gian hẹn lái thử',
    feedback TEXT COMMENT 'Đánh giá của khách',
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED' COMMENT 'Trạng thái: SCHEDULED, COMPLETED, CANCELLED',
    FOREIGN KEY (lead_id) REFERENCES leads(id)
) COMMENT='Bảng quản lý lịch hẹn lái thử xe';

-- ==============================================================================
-- MODULE 4: SALES (Bán hàng & Hợp đồng)
-- ==============================================================================
CREATE TABLE quotations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lead_id BIGINT NOT NULL COMMENT 'Khách hàng nhận báo giá (Ref tới leads)',
    car_vin VARCHAR(17) NOT NULL COMMENT 'Số khung xe cụ thể (Ref tới cars)',
    voucher_code VARCHAR(50) COMMENT 'Mã voucher áp dụng (Ref tới vouchers)',
    total_amount DECIMAL(15, 2) NOT NULL COMMENT 'Tổng tiền trước giảm giá',
    final_amount DECIMAL(15, 2) NOT NULL COMMENT 'Số tiền cuối cùng phải trả',
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT' COMMENT 'Trạng thái: DRAFT, SENT, ACCEPTED, REJECTED',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Bảng lưu trữ báo giá';

CREATE TABLE contracts (
    contract_no VARCHAR(50) PRIMARY KEY COMMENT 'Số hợp đồng',
    quotation_id BIGINT NOT NULL UNIQUE COMMENT 'Khóa ngoại 1-1 với báo giá',
    sales_id BIGINT NOT NULL COMMENT 'Sales chốt hợp đồng (Ref tới users)',
    signed_date DATETIME COMMENT 'Ngày ký hợp đồng',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'Trạng thái: PENDING, SIGNED, COMPLETED, CANCELLED',
    FOREIGN KEY (quotation_id) REFERENCES quotations(id)
) COMMENT='Bảng quản lý hợp đồng mua bán';

-- ==============================================================================
-- MODULE 5: FINANCE & HANDOVER (Thanh toán, Giấy tờ & Bàn giao)
-- ==============================================================================
CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_no VARCHAR(50) NOT NULL COMMENT 'Thanh toán cho hợp đồng nào (Ref tới contracts)',
    amount DECIMAL(15, 2) NOT NULL COMMENT 'Số tiền thanh toán',
    payment_type VARCHAR(20) NOT NULL COMMENT 'Loại: DEPOSIT, INSTALLMENT, FULL',
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Ngày thanh toán',
    status VARCHAR(20) NOT NULL DEFAULT 'SUCCESS' COMMENT 'Trạng thái: PENDING, SUCCESS, FAILED'
) COMMENT='Bảng ghi nhận lịch sử thanh toán';

CREATE TABLE handovers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_no VARCHAR(50) NOT NULL UNIQUE COMMENT 'Bàn giao cho hợp đồng nào (Ref tới contracts)',
    license_plate VARCHAR(20) UNIQUE COMMENT 'Biển số xe',
    handover_date DATETIME COMMENT 'Ngày bàn giao xe thực tế',
    status VARCHAR(20) NOT NULL DEFAULT 'PROCESSING' COMMENT 'Trạng thái: PROCESSING, HANDED_OVER'
) COMMENT='Bảng theo dõi tiến độ giấy tờ và giao xe';

-- ==============================================================================
-- MODULE 6: AFTERSALES (Kích hoạt Bảo hành & Hậu mãi)
-- ==============================================================================
CREATE TABLE warranty_books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_vin VARCHAR(17) NOT NULL UNIQUE COMMENT 'Bảo hành cho xe nào (Ref tới cars)',
    license_plate VARCHAR(20) NOT NULL COMMENT 'Biển số (Ref tới handovers)',
    start_date DATE NOT NULL COMMENT 'Ngày bắt đầu bảo hành',
    end_date DATE NOT NULL COMMENT 'Ngày kết thúc bảo hành',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) COMMENT='Sổ bảo hành điện tử';

CREATE TABLE service_tickets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    warranty_id BIGINT NOT NULL COMMENT 'Khóa ngoại đến sổ bảo hành',
    service_date DATETIME NOT NULL COMMENT 'Ngày làm dịch vụ',
    description TEXT NOT NULL COMMENT 'Mô tả nội dung',
    total_cost DECIMAL(15, 2) NOT NULL COMMENT 'Chi phí dịch vụ',
    FOREIGN KEY (warranty_id) REFERENCES warranty_books(id)
) COMMENT='Phiếu ghi nhận lịch sử sửa chữa/bảo dưỡng';