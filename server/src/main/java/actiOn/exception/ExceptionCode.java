package actiOn.exception;

import lombok.Getter;

public enum ExceptionCode {
    // MEMBER
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    NICKNAME_EXISTS(409, "member nickname exists"),
    PHONE_NUMBER_EXISTS(409, "member phone number exists"),
    MEMBER_NOT_AUTHORIZED(403, "Member not authorized"),
    PROFILE_IMAGE_NOT_FOUND(404, "Profile image not found"),

    // AUTH
    USERNAME_NOT_FOUND(404, "Username not found"),
    UNAUTHORIZED_PRINCIPAL(404, "Unauthorized principal"),
    ROLE_NOT_FOUND(404, "Role not found"),
    INVALID_MEMBER(400, "Invalid member access"),

    // BUSINESS
    BUSINESS_EXISTS(409, "Registration number exists."),
    INVALID_REGISTRATION_NUMBER(400, "Invalid registration number format"),

    // STORE
    STORE_NOT_FOUND(404, "Store not found"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_SORT_PARAMETER(400, "Invalid parameter named 'sort'"),
    NULL_STORE_IMAGE(404, "Store image is null"),
    THUMBNAIL_NOT_FOUND(404, "Store thumbnail not found"),
    UNAUTHORIZED(401, "Unauthorized to update store"),
    INVALID_PARAMETER_VALUE(400, "INVALID_PARAMETER_VALUE"),

    // RESERVATION
    RESERVATION_NOT_FOUND(404, "Reservation not found"),

    //WISH
    WISH_EXIST(409, "Wish exists"),
    WISH_NOT_FOUND(404, "Wish not found"),

    // ITEM
    ITEM_NOT_FOUND(404, "Item not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
