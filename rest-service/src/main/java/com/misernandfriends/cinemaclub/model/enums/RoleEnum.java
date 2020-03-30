package com.misernandfriends.cinemaclub.model.enums;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");

    String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
