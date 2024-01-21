package com.sonnet.controller;

import cn.hutool.core.util.StrUtil;
import com.sonnet.model.domain.User;
import com.sonnet.model.request.UserLoginRequest;
import com.sonnet.model.request.UserRegisterRequest;
import com.sonnet.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户接口
 *
 * @author techsonnet
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping("/register")
    public long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){

        if (userRegisterRequest == null){
            return (long)-1;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if(StrUtil.hasBlank(userAccount,password,checkPassword)){
            return (long) -1;
        }

        return userService.userRegister(userAccount,password,checkPassword);
    }





    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){


        if (!validateLoginParameter(userLoginRequest)) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        return userService.userLogin(userAccount,password,request);
    }

    private boolean validateLoginParameter(UserLoginRequest userLoginRequest) {

        if (userLoginRequest == null){
            return false;
        }

        if (StrUtil.hasBlank(userLoginRequest.getUserAccount(),userLoginRequest.getPassword())){
            return false;
        }

        return true;
    }



}
