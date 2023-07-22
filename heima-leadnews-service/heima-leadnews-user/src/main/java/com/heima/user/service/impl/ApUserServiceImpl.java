package com.heima.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.user.mapper.ApUserMapper;
import com.heima.user.service.ApUserService;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ApUserServiceImpl extends ServiceImpl<ApUserMapper, ApUser> implements ApUserService {

    @Override
    public ResponseResult login(LoginDto loginDto) {
       if(StringUtils.isNotBlank(loginDto.getPhone())&&StringUtils.isNotBlank(loginDto.getPassword())){
           ApUser user=getOne(Wrappers.<ApUser>lambdaQuery().eq(ApUser::getPhone,loginDto.getPhone()));
           if(user==null){
               return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户信息不存在");
           }

           //比对密码
           String salt = user.getSalt();
           String password = loginDto.getPassword();
           String pswd = DigestUtils.md5DigestAsHex((password + salt).getBytes());
           if( !pswd.equals(user.getPassword())) {
               return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
           }

           //登录成功，获取token
           String token= AppJwtUtil.getToken(user.getId().longValue());
           Map<String,Object> map=new HashMap<>();
           user.setSalt("");
           user.setPassword("");
           map.put("token",token);
           map.put("user",user);

           return ResponseResult.okResult(map);
       }else{
           //游客登录
           Map<String,Object> map=new HashMap<>();

           map.put("token",0L);
           return ResponseResult.okResult(map);
       }
    }
}
