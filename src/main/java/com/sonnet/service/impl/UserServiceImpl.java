package com.sonnet.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sonnet.model.domain.User;
import com.sonnet.service.UserService;
import com.sonnet.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
* @author chang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-01-16 20:18:12
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;


    @Resource
    private

    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {

        // 1. 对参数进行校验

        // 判断用户名、密码、二次密码是否为空
        boolean hasBlank = StrUtil.hasBlank(userAccount, password, checkPassword);
        if (hasBlank){
            return -1;
        }

        // 用户名长度大于 4 位
        if(userAccount.length() < 4){
            return -1;
        }

        // 密码长度必须大于 6 位
        if(password.length() < 6){
            return -1;
        }

        // 不包含特殊字符
        String userAccountPattern = "^[a-zA-Z0-9]+$";
        if(!userAccount.matches(userAccountPattern)){
            return -1;
        }

        // 密码和二次密码相等
        if(!password.equals(checkPassword)){
            return -1;
        }

        // 账号不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        Long count = userMapper.selectCount(userQueryWrapper);
        if (count > 0){
            return -1;
        }


        // 2. 对密码进行加密

        String encryptPassword = bCryptPasswordEncoder.encode(password);


        // 3，将数据存入数据库
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }



        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String password) {

        validateLoginParameter(userAccount,password);

        // 密码正确则返回用户信息，否则返回 null
        isCorrectPassword(userAccount,password);

        hindUserInfo();

        saveUserStatus();


        return null;
    }

    private void validateLoginParameter(String userAccount, String password) {
    }

    private void isCorrectPassword(String userAccount, String password) {

    }

    private void hindUserInfo() {

    }

    private void saveUserStatus() {

    }

}




