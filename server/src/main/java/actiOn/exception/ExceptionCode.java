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

    // BUSINESS
    BUSINESS_EXISTS(409, "Registration number exists."),
    INVALID_REGISTRATION_NUMBER(400, "Invalid registration number format"),

    // STORE
    STORE_NOT_FOUND(404, "Store not found"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    INVALID_SORT_PARAMETER(400, "Invalid parameter named 'sort'"),
    NULL_STORE_IMAGE(404, "Store image is null"),
    THUMBNAIL_NOT_FOUND(404, "Store thumbnail not found"),
    PARTNER_ONLY_CAN_DELETE(403,"Only partners who have registered a store can delete the store"),

    // RESERVATION
    RESERVATION_NOT_FOUND(404, "Reservation not found"),
    STORE_ITEM_INVALID(422,"The Item doesn't exist in the Store"),
    TOTAL_PRICE_INVALID(400,"The total amount you reserved does not match the total amount of each ticket price"),
    RESERVATION_DATE_INVALID(400,"Reservation date is not applicable to previous date"),
    RESERVATION_CHANGE_ONLY_MEMBER(403,"Only members who made a reservation can modify the reservation"),

    // REVIEW
    REVIEW_ONLY_RESERVED_MEMBER(403,"Only members who have made a reservation can write a review"),
    REVIEW_CONTENT_NOT_ABUSIVE(400, "The review contains abusive language"),

    // ITEM
    ITEM_NOT_FOUND(404,"Item not found"),

    //WISH
    WISH_EXIST(409, "Wish exists"),
    WISH_NOT_FOUND(404, "Wish not found");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
