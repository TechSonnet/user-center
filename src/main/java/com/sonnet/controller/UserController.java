package com.sonnet.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sonnet.model.domain.User;
import com.sonnet.model.request.UserLoginRequest;
import com.sonnet.model.request.UserRegisterRequest;
import com.sonnet.service.UserService;
import jakarta.annotation.Resource;
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

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest){


        int validateResult = validateRegisterParameters(userRegisterRequest);
        if (validateResult < 0){
            return null;
        }


        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        return userService.userRegister(userAccount,password,checkPassword);
    }


    private int validateRegisterParameters(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            return -1;
        }

        if (StrUtil.hasBlank(userRegisterRequest.getUserAccount(),userRegisterRequest.getPassword(),userRegisterRequest.getCheckPassword())){
            return -1;
        }

        return 1;
    }


    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest){


        if (!validateLoginParameter(userLoginRequest)) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        return userService.userLogin(userAccount,password);
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
