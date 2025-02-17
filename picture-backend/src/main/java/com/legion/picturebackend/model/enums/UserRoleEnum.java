package com.legion.picturebackend.model.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER("user", "user"),
    ADMIN("admin", "admin");
    private final String text;
    private final String value;
    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }
    public static UserRoleEnum getEnumByValue(String value) {
        if(value==null){
            return null;
        }
        for (UserRoleEnum e : UserRoleEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
