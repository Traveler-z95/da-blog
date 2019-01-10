package com.da.blog.service;


import com.da.blog.vo.User;
import com.da.blog.vo.UserInfo;

/**
*
*/
public interface UserService {


    User loadUserByUsername(String username);

    UserInfo getUserInfo();

    void updateAvatar(String url, String username);

    void updatePassword(User user);

    void updateUserInfo(UserInfo userInfo);

    User getCurrentUser();
}
