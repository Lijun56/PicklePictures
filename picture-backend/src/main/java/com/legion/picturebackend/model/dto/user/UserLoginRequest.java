package com.legion.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -1461076289468178989L;
    private String userAccount;
    private String userPassword;
}
