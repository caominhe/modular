package com.fcar.be.modules.crm.enums;

public enum LeadStatus {
    NEW, // Khách mới đổ về
    NEEDS_CONSULTATION, // Cần tư vấn (Đã gọi điện/nhắn tin)
    TEST_DRIVE_SCHEDULED, // Đã có lịch lái thử
    TEST_DRIVEN, // Đã lái thử & có Comment đánh giá
    QUOTING, // Đang báo giá (Tự động nhảy khi Sales làm báo giá)
    WON, // Chốt sale thành công
    LOST // Khách hủy / Không mua nữa
}
