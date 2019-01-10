package com.da.blog.controller.admin;

import com.da.blog.constant.ProjectConstant;
import com.da.blog.service.UserService;
import com.da.blog.util.Md5Util;
import com.da.blog.util.ResultInfo;
import com.da.blog.util.ResultInfoFactory;
import com.da.blog.vo.User;
import com.da.blog.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * FILE: com.da.blog.controller.admin.LoginController.java
 * MOTTO:  不积跬步无以至千里,不积小流无以至千里
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录的验证，暂时用不上
     * 已经通过spring security去操作
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/login/auth",method = RequestMethod.POST)
    public ResultInfo loginAuth(User user, HttpSession session){
        ResultInfo resultInfo = null;
        User userInfo = userService.loadUserByUsername(user.getUsername());
        if (userInfo==null){
            resultInfo =  ResultInfoFactory.getErrorResultInfo("账号不存在");
        }else{
            if (userInfo.getPassword().equals(Md5Util.pwdDigest(user.getPassword()))){
                resultInfo = ResultInfoFactory.getSuccessResultInfo();
            }else {
                resultInfo = ResultInfoFactory.getErrorResultInfo("账号或密码错误");
            }
        session.setAttribute("user",userInfo);
        }

        return resultInfo;

    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param request
     * @return
     */
    @RequestMapping("/admin/password/update")
    @ResponseBody
    public ResultInfo updatePassword(String oldPassword, String newPassword, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (!Md5Util.pwdDigest(oldPassword).equals(user.getPassword())){
            return ResultInfoFactory.getErrorResultInfo("原密码错误！！！");
        }
        user.setPassword(Md5Util.pwdDigest(newPassword));
        userService.updatePassword(user);
        return ResultInfoFactory.getSuccessResultInfo("修改成功！！！");
    }

    /**
     * 获取用户信息
     * @param model 对象
     * @return
     */
    @RequestMapping("/admin/userinfo/get")
    public String getUserInfo(Model model){
        UserInfo userInfo = userService.getUserInfo();
        model.addAttribute(ProjectConstant.USERINFO,userInfo);
        return "admin/partial/userinfo";
    }

    @RequestMapping("/admin/userInfo/getUserInfo")
    public String getUserInfo1(Model model){
        UserInfo userInfo = userService.getUserInfo();
        model.addAttribute(ProjectConstant.USERINFO,userInfo);
        return "admin/partial/sidebar";
    }
    /**
     * 更新个人信息
     * @param userInfo
     * @return
     */
    @RequestMapping("/admin/userinfo/update")
    @ResponseBody
    public ResultInfo updateInfo(UserInfo userInfo){
        userService.updateUserInfo(userInfo);
        return ResultInfoFactory.getSuccessResultInfo("更新个人信息成功!!");
    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/admin/loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?loginOut";
    }
}


