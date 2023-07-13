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
    PARTNER_INVALID(403, "Only Partner can delete the company."),
    PARTNER_ONLY_CAN_DELETE(403, "Partner Only Can Delete"),

    // RESERVATION
    RESERVATION_NOT_FOUND(404, "Reservation not found"),
    RESERVATION_DATE_ERROR(400, "Reservation Date Not Invalid"),
    STORE_ITEM_INVALID(400,"Items That Don't Exist In The Store"),
    RESERVED_AMOUNT_INVALID(400, "Reserved Amount Not Invalid Item Total price"),
    ONLY_MEMBER_RESERVATION_MODIFY(403,"Only members who have made a reservation can modify it"),
    TICKET_OVER(400,"Number of tickets exceeded the maximum allowed"),

    // REVIEW
    CURSE_WORD_IN_REVIEW(400,"Curse Word In The Review."),
    MEMBER_RESERVATION_CAN_REVIEW(400,"Only members who have made a reservation can write a review"),
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
