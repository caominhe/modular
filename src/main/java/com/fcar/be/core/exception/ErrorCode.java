package com.fcar.be.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    // Lỗi hệ thống & Validation cơ bản (1xxx)
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến Identity (User/Auth) (10xx)
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),

    // Lỗi liên quan đến Inventory (Car/MasterData) (2xxx)
    CAR_NOT_FOUND(2001, "Car with this VIN not found", HttpStatus.NOT_FOUND),
    CAR_EXISTED(2002, "Car with this VIN already exists in the system", HttpStatus.BAD_REQUEST),
    ENGINE_NUMBER_EXISTED(2003, "Car with this engine number already exists", HttpStatus.BAD_REQUEST),
    MASTER_DATA_NOT_FOUND(2004, "Master data (Car model) not found", HttpStatus.NOT_FOUND),


    // Lỗi liên quan đến Marketing (Voucher/Campaign) (3xxx)
    CAMPAIGN_NOT_FOUND(3001, "Campaign not found in the system", HttpStatus.NOT_FOUND),
    VOUCHER_NOT_FOUND(3002, "Voucher code does not exist", HttpStatus.NOT_FOUND),
    VOUCHER_EXPIRED(3003, "Voucher has expired", HttpStatus.BAD_REQUEST),
    VOUCHER_INVALID_STATUS(3004, "Voucher is not in the correct state to perform this action", HttpStatus.BAD_REQUEST),
    VOUCHER_NOT_OWNED(3005, "You do not own this voucher or it does not exist", HttpStatus.FORBIDDEN),
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