package com.legion.picturebackend.model.vo;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  已登陆用户视图 （脱敏后：no password
*/
@Data
public class LoginUserVO implements Serializable {
    /**
     * id
     * Idtype.AUTO is allow to create table id in the pattern like 1,2,3,4
     *  which in danger of malicious data scrapping
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin
     */
    private String userRole;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}