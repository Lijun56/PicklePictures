package com.legion.picturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.legion.picturebackend.model.dto.user.UserQueryRequest;
import com.legion.picturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.legion.picturebackend.model.vo.LoginUserVO;
import com.legion.picturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lijunzhu
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-02-15 22:48:12
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * get current login user 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);






    String getEncryptPassword(String userPassword);

    /**
     * transfer raw user data to the loginUserVO(without password and otheer sensitive) to send to frontend
     * LoginUserVO is for user themselves to their own info
     * @param user
     * @return
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * UserVO is for other users query
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * get List of user vo , mainly used for admin
     * @param userslist
     * @return
     */
    List<UserVO> getUserVOList(List<User> userslist);

    /**
     * input request, output:
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getqueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * user logout: essenstially means remove the session ID
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

}
