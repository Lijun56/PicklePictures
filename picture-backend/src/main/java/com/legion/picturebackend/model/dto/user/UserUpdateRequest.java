package com.legion.picturebackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * updateUser through ID
 */
@Data
public class UserUpdateRequest implements Serializable {

    /**
     * id, update through Id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    private static final long serialVersionUID = 1L;
}
