package com.example.secaidserver.model.enums;

public enum ResponseCriteriaEnum {
    NONE(0),
    REACTIVE(1),
    EARLY(2),
    DEVELOPING(3),
    MATURE(4),
    LEADING(5),
    EXEMPLARY(6);

    private final Integer rating;

    ResponseCriteriaEnum(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
