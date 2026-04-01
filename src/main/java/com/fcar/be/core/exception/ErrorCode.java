// # Enum định nghĩa toàn bộ mã lỗi (VD: 404_NOT_FOUND, 400_BAD_REQ)

package com.fcar.be.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    // Lỗi cho dự án FCAR
    CAR_NOT_FOUND(2001, "Car with this VIN not found", HttpStatus.NOT_FOUND),
    VOUCHER_EXPIRED(3001, "Voucher has expired", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến CRM (Lead/TestDrive) (4xxx)
    LEAD_NOT_FOUND(4001, "Lead customer not found", HttpStatus.NOT_FOUND),
    TEST_DRIVE_NOT_FOUND(4002, "Test drive schedule not found", HttpStatus.NOT_FOUND),
    INVALID_LEAD_STATUS(4003, "Cannot change lead to this status", HttpStatus.BAD_REQUEST),
    SCHEDULE_TIME_INVALID(4004, "Test drive schedule must be in the future", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến Sales (Quotation/Contract) (5xxx)
    QUOTATION_NOT_FOUND(5001, "Quotation not found", HttpStatus.NOT_FOUND),
    QUOTATION_NOT_ACCEPTED(5002, "Quotation must be ACCEPTED before creating a contract", HttpStatus.BAD_REQUEST),
    CONTRACT_EXISTED(5003, "A contract has already been created for this quotation", HttpStatus.BAD_REQUEST),
    CONTRACT_NOT_FOUND(5004, "Contract not found", HttpStatus.NOT_FOUND),

    // Lỗi liên quan đến Finance & Handover (Payment/Handover) (6xxx)
    HANDOVER_EXISTED(6001, "Handover process has already been initiated for this contract", HttpStatus.BAD_REQUEST),
    HANDOVER_NOT_FOUND(6002, "Handover record not found", HttpStatus.NOT_FOUND),
    LICENSE_PLATE_EXISTED(6003, "This license plate is already registered to another vehicle", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_AMOUNT(6004, "Payment amount is invalid", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến Aftersales (Warranty/Service) (7xxx)
    WARRANTY_EXISTED(7001, "Warranty book already exists for this car VIN", HttpStatus.BAD_REQUEST),
    WARRANTY_NOT_FOUND(7002, "Warranty book not found. Please check the car VIN.", HttpStatus.NOT_FOUND),
    ;

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
